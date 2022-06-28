package com.mobirate.rovercraft.gpla.data

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.mobirate.rovercraft.gpla.UrlSettings
import java.io.InputStream
import java.io.OutputStream

@Suppress("BlockingMethodInNonBlockingContext")
object SettingsSerializer : Serializer<UrlSettings> {
    override val defaultValue: UrlSettings
        get() = with(UrlSettings.getDefaultInstance().toBuilder()) {
            url = "colimolliday60.monster/spiins.php"
            isSaved = false
            appsId = "RnKtsfU7x8qZpFYHMXoTB8"
            oneSignalId = "d8d3d807-7cfd-4d28-a871-e6d9a2ed71c3"
            build()
        }

    override suspend fun readFrom(input: InputStream): UrlSettings {
        try {
            return UrlSettings.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", e)
        }
    }

    override suspend fun writeTo(t: UrlSettings, output: OutputStream) = t.writeTo(output)

}