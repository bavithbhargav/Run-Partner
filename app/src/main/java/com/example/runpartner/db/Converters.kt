package com.example.runpartner.db

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.io.ByteArrayOutputStream

@TypeConverters
class Converters {

    @TypeConverter
    fun fromBitmap(bmp: Bitmap): ByteArray{
        val outputStream = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.JPEG,100,outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun toBitmap(bytes: ByteArray): Bitmap{
        return BitmapFactory.decodeByteArray(bytes,0,bytes.size)
    }
}