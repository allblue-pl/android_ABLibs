package pl.allblue.ablibs.helpers;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;

import pl.allblue.ablibs.databinding.ActivityImageBinding;

public class ImageActivity extends Activity {
    static private final String Extras_Image = "pl.allblue.ablibs.ImageActivity.Extras_Image";

    static public void displayImage(Activity activity, Bitmap bitmap, int requestCode) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        Intent i = new Intent(activity, ImageActivity.class);
        i.putExtra(Extras_Image, byteArray);

        activity.startActivity(i);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityImageBinding b = ActivityImageBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        byte[] byteArray = getIntent().getExtras().getByteArray(Extras_Image);
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        b.image.setImageBitmap(bitmap);
    }
}
