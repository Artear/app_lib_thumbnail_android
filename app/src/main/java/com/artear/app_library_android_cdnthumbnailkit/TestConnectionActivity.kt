package com.artear.app_library_android_cdnthumbnailkit

import android.app.Activity
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import com.artear.app_library_android_cdnthumbnailkit.connection.ConnectionClassManager
import com.artear.app_library_android_cdnthumbnailkit.connection.ConnectionQuality
import com.artear.app_library_android_cdnthumbnailkit.connection.DeviceBandwidthSampler
import kotlinx.android.synthetic.main.connection_layout.*
import java.io.IOException
import java.net.URL

class TestConnectionActivity : Activity() {

    private var mListener: ConnectionChangedListener? = null
    private var mRunningBar: View? = null

    private val mURL = "https://cdn.tn.com.ar/sites/default/files/styles/650x365/public/2018/07/18/tai8.jpg"
    private var mTries = 0
    private var mConnectionClass = ConnectionQuality.UNKNOWN

    private val testButtonClicked = View.OnClickListener { DownloadImage().execute(mURL) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.connection_layout)

        test_btn.setOnClickListener(testButtonClicked)

        connection_class.text = ConnectionClassManager.instance.currentBandwidthQuality.toString()
        mRunningBar = runningBar
        mRunningBar!!.visibility = View.GONE
        mListener = ConnectionChangedListener()
    }

    override fun onPause() {
        super.onPause()
        ConnectionClassManager.instance.remove(mListener)
    }

    override fun onResume() {
        super.onResume()
        ConnectionClassManager.instance.register(mListener)
    }

    /**
     * Listener to update the UI upon connectionclass change.
     */
    private inner class ConnectionChangedListener : ConnectionClassManager.ConnectionClassStateChangeListener {

        override fun onBandwidthStateChange(bandwidthState: ConnectionQuality) {
            mConnectionClass = bandwidthState
            runOnUiThread { connection_class.text = mConnectionClass.toString() }
        }
    }

    /**
     * AsyncTask for handling downloading and making calls to the timer.
     */
    private inner class DownloadImage : AsyncTask<String, Void, Void>() {

        override fun onPreExecute() {
            DeviceBandwidthSampler.instance.startSampling()
            mRunningBar!!.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg url: String?): Void? {
            val imageURL = url[0]
            try {
                // Open a stream to download the image from our URL.
                val connection = URL(imageURL).openConnection()
                connection.useCaches = false
                connection.connect()
                val input = connection.getInputStream()
                try {
                    val buffer = ByteArray(1024)

                    // Do some busy waiting while the stream is open.
                    while (input.read(buffer) != -1) {
                    }
                } finally {
                    input.close()
                }
            } catch (e: IOException) {
                Log.e(TAG, "Error while downloading image.")
            }

            return null
        }

        override fun onPostExecute(v: Void?) {
            DeviceBandwidthSampler.instance.stopSampling()
            // Retry for up to 10 times until we find a ConnectionClass.
            if (mConnectionClass === ConnectionQuality.UNKNOWN && mTries < 10) {
                mTries++
                DownloadImage().execute(mURL)
            }
            if (!DeviceBandwidthSampler.instance.isSampling) {
                mRunningBar!!.visibility = View.GONE
            }
        }
    }

    companion object {

        private val TAG = "ConnectionClass-Sample"
    }
}
