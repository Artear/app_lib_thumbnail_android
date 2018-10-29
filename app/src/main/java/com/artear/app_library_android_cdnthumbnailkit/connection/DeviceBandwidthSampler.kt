package com.artear.app_library_android_cdnthumbnailkit.connection

import android.net.TrafficStats
import android.os.*
import java.util.concurrent.atomic.AtomicInteger

/**
 * Class used to read from TrafficStats periodically, in order to determine a ConnectionClass.
 */
class DeviceBandwidthSampler private constructor(
        /**
         * The DownloadBandwidthManager that keeps track of the moving average and ConnectionClass.
         */
        private val mConnectionClassManager: ConnectionClassManager) {

    private val mSamplingCounter: AtomicInteger

    private val mHandler: SamplingHandler
    private val mThread: HandlerThread

    private var mLastTimeReading: Long = 0

    /**
     * @return True if there are still threads which are sampling, false otherwise.
     */
    val isSampling: Boolean
        get() = mSamplingCounter.get() != 0

    // Singleton.
    private object DeviceBandwidthSamplerHolder {
        val instance = DeviceBandwidthSampler(ConnectionClassManager.instance)
    }

    init {
        mSamplingCounter = AtomicInteger()
        mThread = HandlerThread("ParseThread")
        mThread.start()
        mHandler = SamplingHandler(mThread.looper)
    }

    /**
     * Method call to start sampling for download bandwidth.
     */
    fun startSampling() {
        if (mSamplingCounter.getAndIncrement() == 0) {
            mHandler.startSamplingThread()
            mLastTimeReading = SystemClock.elapsedRealtime()
        }
    }

    /**
     * Finish sampling and prevent further changes to the
     * ConnectionClass until another timer is started.
     */
    fun stopSampling() {
        if (mSamplingCounter.decrementAndGet() == 0) {
            mHandler.stopSamplingThread()
            addFinalSample()
        }
    }

    /**
     * Method for polling for the change in total bytes since last update and
     * adding it to the BandwidthManager.
     */
    protected fun addSample() {
        val newBytes = TrafficStats.getTotalRxBytes()
        val byteDiff = newBytes - sPreviousBytes
        if (sPreviousBytes >= 0) {
            synchronized(this) {
                val curTimeReading = SystemClock.elapsedRealtime()
                mConnectionClassManager.addBandwidth(byteDiff, curTimeReading - mLastTimeReading)

                mLastTimeReading = curTimeReading
            }
        }
        sPreviousBytes = newBytes
    }

    /**
     * Resets previously read byte count after recording a sample, so that
     * we don't count bytes downloaded in between sampling sessions.
     */
    protected fun addFinalSample() {
        addSample()
        sPreviousBytes = -1
    }

    private inner class SamplingHandler(looper: Looper) : Handler(looper) {

        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MSG_START -> {
                    addSample()
                    sendEmptyMessageDelayed(MSG_START, SAMPLE_TIME)
                }
                else -> throw IllegalArgumentException("Unknown what=" + msg.what)
            }
        }


        fun startSamplingThread() {
            sendEmptyMessage(MSG_START)
        }

        fun stopSamplingThread() {
            removeMessages(MSG_START)
        }
    }

    companion object {
        private var sPreviousBytes: Long = -1

        /**
         * Retrieval method for the DeviceBandwidthSampler singleton.
         * @return The singleton instance of DeviceBandwidthSampler.
         */
        val instance: DeviceBandwidthSampler
            get() = DeviceBandwidthSamplerHolder.instance

        internal val SAMPLE_TIME: Long = 1000

        private val MSG_START = 1
    }
}