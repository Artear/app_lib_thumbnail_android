package com.artear.app_library_android_cdnthumbnailkit.connection

/**
 * Moving average calculation for ConnectionClass.
 */
internal class ExponentialGeometricAverage(private val mDecayConstant: Double) {
    private val mCutover: Int

    var average = -1.0
        private set
    private var mCount: Int = 0

    init {
        mCutover = if (mDecayConstant == 0.0)
            Integer.MAX_VALUE
        else
            Math.ceil(1 / mDecayConstant).toInt()
    }

    /**
     * Adds a new measurement to the moving average.
     * @param measurement - Bandwidth measurement in bits/ms to add to the moving average.
     */
    fun addMeasurement(measurement: Double) {
        val keepConstant = 1 - mDecayConstant
        if (mCount > mCutover) {
            average = Math.exp(keepConstant * Math.log(average) + mDecayConstant * Math.log(measurement))
        } else if (mCount > 0) {
            val retained = keepConstant * mCount / (mCount + 1.0)
            val newcomer = 1.0 - retained
            average = Math.exp(retained * Math.log(average) + newcomer * Math.log(measurement))
        } else {
            average = measurement
        }
        mCount++
    }

    /**
     * Reset the moving average.
     */
    fun reset() {
        average = -1.0
        mCount = 0
    }
}
