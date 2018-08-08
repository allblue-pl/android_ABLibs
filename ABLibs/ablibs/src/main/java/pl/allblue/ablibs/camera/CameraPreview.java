package pl.allblue.ablibs.camera;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

public class CameraPreview extends ViewGroup implements SurfaceHolder.Callback {

    private SurfaceView surfaceView = null;
    private SurfaceHolder surfaceHolder = null;


    public CameraPreview(Context context)
    {
        super(context);

        this.surfaceView = new SurfaceView(context);
        this.addView(this.surfaceView);

        this.surfaceHolder = this.surfaceView.getHolder();
        this.surfaceHolder.addCallback(this);
    }


    /* SurfaceHolder.Callback Overrides */
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder)
    {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2)
    {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder)
    {

    }
    /* / SurfaceHolder.Callback Overrides */


    /* ViewGroup Overrides */
    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3)
    {

    }
        /* ViewGroup Overrides */
}
