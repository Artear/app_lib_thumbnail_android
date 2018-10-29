package com.artear.app_library_android_cdnthumbnailkit.connection

import com.artear.app_library_android_cdnthumbnailkit.connection.ConnectionClassManager.ConnectionClassStateChangeListener
import java.util.*
import java.util.concurrent.atomic.AtomicReference

/**
 *
 *
 * Class used to calculate the approximate bandwidth of a user's connection.
 *
 *
 *
 * This class notifies all subscribed [ConnectionClassStateChangeListener] with the new
 * ConnectionClass when the network's ConnectionClass changes.
 *
 */
class ConnectionClassManager// Force constructor to be private.
private constructor() {

    /** Current bandwidth of the user's connection depending upon the response.  */
    private val mDownloadBandwidth = ExponentialGeometricAverage(DEFAULT_DECAY_CONSTANT)
    @Volatile
    private var mInitiateStateChange = false
    private val mCurrentBandwidthConnectionQuality = AtomicReference(ConnectionQuality.UNKNOWN)
    private var mNextBandwidthConnectionQuality: AtomicReference<ConnectionQuality>? = null
    private val mListenerList = ArrayList<ConnectionClassStateChangeListener>()
    private var mSampleCounter: Int = 0

    /**
     * Get the ConnectionQuality that the moving bandwidth average currently represents.
     * @return A ConnectionQuality representing the device's bandwidth at this exact moment.
     */
    val currentBandwidthQuality: ConnectionQuality
        @Synchronized get() = if (mDownloadBandwidth == null) {
            ConnectionQuality.UNKNOWN
        } else mapBandwidthQuality(mDownloadBandwidth.average)


    /**
     * Accessor method for the current bandwidth average.
     * @return The current bandwidth average, or -1 if no average has been recorded.
     */
    val downloadKBitsPerSecond: Double
        @Synchronized get() = if (mDownloadBandwidth == null)
            -1.0
        else
            mDownloadBandwidth.average

    // Singleton.
    private object ConnectionClassManagerHolder {
        val instance = ConnectionClassManager()
    }

    /**
     * Adds bandwidth to the current filtered latency counter. Sends a broadcast to all
     * [ConnectionClassStateChangeListener] if the counter moves from one bucket
     * to another (i.e. poor bandwidth -> moderate bandwidth).
     */
    @Synchronized
    fun addBandwidth(bytes: Long, timeInMs: Long) {

        //Ignore garbage values.
        if (timeInMs == 0L || bytes * 1.0 / timeInMs * BYTES_TO_BITS < BANDWIDTH_LOWER_BOUND) {
            return
        }

        val bandwidth = bytes * 1.0 / timeInMs * BYTES_TO_BITS
        mDownloadBandwidth.addMeasurement(bandwidth)

        if (mInitiateStateChange) {
            mSampleCounter += 1
            if (currentBandwidthQuality !== mNextBandwidthConnectionQuality!!.get()) {
                mInitiateStateChange = false
                mSampleCounter = 1
            }
            if (mSampleCounter >= DEFAULT_SAMPLES_TO_QUALITY_CHANGE && significantlyOutsideCurrentBand()) {
                mInitiateStateChange = false
                mSampleCounter = 1
                mCurrentBandwidthConnectionQuality.set(mNextBandwidthConnectionQuality!!.get())
                notifyListeners()
            }
            return
        }

        if (mCurrentBandwidthConnectionQuality.get() !== currentBandwidthQuality) {
            mInitiateStateChange = true
            mNextBandwidthConnectionQuality = AtomicReference(currentBandwidthQuality)
        }
    }

    private fun significantlyOutsideCurrentBand(): Boolean {
        if (mDownloadBandwidth == null) {
            // Make Infer happy. It wouldn't make any sense to call this while mDownloadBandwidth is null.
            return false
        }
        val currentQuality = mCurrentBandwidthConnectionQuality.get()
        val bottomOfBand: Double
        val topOfBand: Double
        when (currentQuality) {
            ConnectionQuality.POOR -> {
                bottomOfBand = 0.0
                topOfBand = DEFAULT_POOR_BANDWIDTH.toDouble()
            }
            ConnectionQuality.MODERATE -> {
                bottomOfBand = DEFAULT_POOR_BANDWIDTH.toDouble()
                topOfBand = DEFAULT_MODERATE_BANDWIDTH.toDouble()
            }
            ConnectionQuality.GOOD -> {
                bottomOfBand = DEFAULT_MODERATE_BANDWIDTH.toDouble()
                topOfBand = DEFAULT_GOOD_BANDWIDTH.toDouble()
            }
            ConnectionQuality.EXCELLENT -> {
                bottomOfBand = DEFAULT_GOOD_BANDWIDTH.toDouble()
                topOfBand = java.lang.Float.MAX_VALUE.toDouble()
            }
            else // If current quality is UNKNOWN, then changing is always valid.
            -> return true
        }
        val average = mDownloadBandwidth.average
        if (average > topOfBand) {
            if (average > topOfBand * HYSTERESIS_TOP_MULTIPLIER) {
                return true
            }
        } else if (average < bottomOfBand * HYSTERESIS_BOTTOM_MULTIPLIER) {
            return true
        }
        return false
    }

    /**
     * Resets the bandwidth average for this instance of the bandwidth manager.
     */
    fun reset() {
        mDownloadBandwidth.reset()
        mCurrentBandwidthConnectionQuality.set(ConnectionQuality.UNKNOWN)
    }

    private fun mapBandwidthQuality(average: Double): ConnectionQuality {
        if (average < 0) {
            return ConnectionQuality.UNKNOWN
        }
        if (average < DEFAULT_POOR_BANDWIDTH) {
            return ConnectionQuality.POOR
        }
        if (average < DEFAULT_MODERATE_BANDWIDTH) {
            return ConnectionQuality.MODERATE
        }
        return if (average < DEFAULT_GOOD_BANDWIDTH) {
            ConnectionQuality.GOOD
        } else ConnectionQuality.EXCELLENT
    }

    /**
     * Interface for listening to when [com.facebook.network.connectionclass.ConnectionClassManager]
     * changes state.
     */
    interface ConnectionClassStateChangeListener {
        /**
         * The method that will be called when [com.facebook.network.connectionclass.ConnectionClassManager]
         * changes ConnectionClass.
         * @param bandwidthState The new ConnectionClass.
         */
        fun onBandwidthStateChange(bandwidthState: ConnectionQuality)
    }

    /**
     * Method for adding new listeners to this class.
     * @param listener [ConnectionClassStateChangeListener] to add as a listener.
     */
    fun register(listener: ConnectionClassStateChangeListener?): ConnectionQuality {
        if (listener != null) {
            mListenerList.add(listener)
        }
        return mCurrentBandwidthConnectionQuality.get()
    }

    /**
     * Method for removing listeners from this class.
     * @param listener Reference to the [ConnectionClassStateChangeListener] to be removed.
     */
    fun remove(listener: ConnectionClassStateChangeListener?) {
        if (listener != null) {
            mListenerList.remove(listener)
        }
    }

    private fun notifyListeners() {
        val size = mListenerList.size
        for (i in 0 until size) {
            mListenerList[i].onBandwidthStateChange(mCurrentBandwidthConnectionQuality.get())
        }
    }

    companion object {

        /*package*/ internal val DEFAULT_SAMPLES_TO_QUALITY_CHANGE = 5.0
        private val BYTES_TO_BITS = 8

        /**
         * Default values for determining quality of data connection.
         * Bandwidth numbers are in Kilobits per second (kbps).
         */
        /*package*/ internal val DEFAULT_POOR_BANDWIDTH = 150
        /*package*/ internal val DEFAULT_MODERATE_BANDWIDTH = 550
        /*package*/ internal val DEFAULT_GOOD_BANDWIDTH = 2000
        /*package*/ internal val DEFAULT_HYSTERESIS_PERCENT: Long = 20
        private val HYSTERESIS_TOP_MULTIPLIER = 100.0 / (100.0 - DEFAULT_HYSTERESIS_PERCENT)
        private val HYSTERESIS_BOTTOM_MULTIPLIER = (100.0 - DEFAULT_HYSTERESIS_PERCENT) / 100.0

        /**
         * The factor used to calculate the current bandwidth
         * depending upon the previous calculated value for bandwidth.
         *
         * The smaller this value is, the less responsive to new samples the moving average becomes.
         */
        private val DEFAULT_DECAY_CONSTANT = 0.05

        /**
         * The lower bound for measured bandwidth in bits/ms. Readings
         * lower than this are treated as effectively zero (therefore ignored).
         */
        internal val BANDWIDTH_LOWER_BOUND: Long = 10

        /**
         * Retrieval method for the DownloadBandwidthManager singleton.
         * @return The singleton instance of DownloadBandwidthManager.
         */
        val instance: ConnectionClassManager
            get() = ConnectionClassManagerHolder.instance
    }
}