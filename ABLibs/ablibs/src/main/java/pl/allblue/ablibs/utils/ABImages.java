package pl.allblue.ablibs.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ABImages
{

    static public byte[] ConvertToByteArray(Bitmap bitmap,
            Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(compressFormat, quality, stream);
        byte[] byteArray = stream.toByteArray();
        bitmap.recycle();

        return byteArray;
    }

    static public Bitmap CreateFromPath(String path)
    {
        ExifInterface exifInterface = null;
        try {
            exifInterface = new ExifInterface(path);
        } catch (IOException e) {

        }

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(path, bitmapOptions);
        if (exifInterface != null) {
            int rotation = 0;
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotation = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotation = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotation = 270;
                    break;
            }

            Matrix m = new Matrix();
            m.postRotate(rotation);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), m, true);
        }

        return bitmap;
    }

    static public Bitmap ScaleToMin(Bitmap bitmap, int width, int height)
    {
        float fw = (float)width / (float)bitmap.getWidth();
        float fh = (float)height / (float)bitmap.getHeight();
        float f = Math.max(fw, fh);

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap,
                (int)(f * bitmap.getWidth()), (int)(f * bitmap.getHeight()),
                true);

        return scaledBitmap;
    }

}
