package com.test.cameraxdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.camera.core.ImageProxy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * @author hwm
 * @date 2021/9/3
 * @desc
 */
public class BaseImageUtils {

    public static Bitmap imageProxyToBitmap(ImageProxy imageProxy) {
        ByteBuffer byteBuffer = imageProxy.getPlanes()[0].getBuffer();
        byte[] bytes = new byte[byteBuffer.remaining()];
        byteBuffer.get(bytes);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }
}
