package com.artear.thumbnailkit.strategy

import android.content.Context
import com.artear.thumbnailkit.image.CDNThumbnail
import com.artear.tools.media.Size
import com.artear.tools.network.ConnectionUtil

class CDNStrategyConnection(private val appContext: Context) : CDNStrategyDefault() {

    override fun getCDNThumbnail(width: Int, height: Int): CDNThumbnail? {
        var width = width
        var height = height

        val connectionSize = getConnectionSize(width, height)
        width = connectionSize.width
        height = connectionSize.height

        return super.getCDNThumbnail(width, height)
    }


    private fun getConnectionSize(width: Int, height: Int): Size {
        var result = Size(width / 3, height / 3)
        try {
            when (ConnectionUtil.connectionType(appContext)) {
                ConnectionUtil.ConnectionType.WIFI -> result = Size(width, height)
                ConnectionUtil.ConnectionType._4G -> result = Size(width * 2 / 3, height * 2 / 3)
                ConnectionUtil.ConnectionType._3G -> result = Size(width / 2, height / 2)
                else -> result = Size(width / 3, height / 3)
            }
        } catch (ignore: Exception) {
        }

        return result
    }
}
