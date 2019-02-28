package com.artear.thumbnailkit.strategy

import android.content.Context
import com.artear.networking.util.ConnectionUtil
import com.artear.thumbnailkit.image.CDNThumbnail
import com.artear.tools.media.Size

class CDNStrategyConnection(private val appContext: Context) : CDNStrategyDefault() {

    override fun getCDNThumbnail(width: Int, height: Int): CDNThumbnail? {
        val connectionSize = getConnectionSize(width, height)
        return super.getCDNThumbnail(connectionSize.width, connectionSize.height)
    }


    private fun getConnectionSize(width: Int, height: Int): Size {
        var result = Size(width / 3, height / 3)
        try {
            result = when (ConnectionUtil.connectionType(appContext)) {
                ConnectionUtil.ConnectionType.WIFI -> Size(width, height)
                ConnectionUtil.ConnectionType._4G -> Size(width * 2 / 3, height * 2 / 3)
                ConnectionUtil.ConnectionType._3G -> Size(width / 2, height / 2)
                else -> Size(width / 3, height / 3)
            }
        } catch (ignore: Exception) {
        }

        return result
    }
}
