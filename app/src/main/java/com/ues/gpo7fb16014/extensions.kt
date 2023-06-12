package com.ues.gpo7fb16014

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.camera.core.ImageProxy

fun ImageProxy.toBitmap() : Bitmap{
    val buffer = this.planes[0].buffer
    val bytes = ByteArray(buffer.remaining())
    buffer[bytes]
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size, null)
}

fun Bitmap.rotate(angle : Double) : Bitmap{
    val matrix = Matrix().apply {
        postRotate(angle.toFloat())
    }
    return Bitmap.createBitmap(this, 0, 0, this.width, this.height, matrix, true)
}