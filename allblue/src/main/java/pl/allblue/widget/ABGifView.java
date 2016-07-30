package pl.allblue.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.io.InputStream;

import pl.allblue.R;

/**
 * Created by SfTd on 16/05/2015.
 */
public class ABGifView extends View
{
    private int srcId = 0;
    private InputStream inputStram = null;
    private Movie movie = null;
    private int movie_Duration = 0;
    private long movie_TimeStart = 0;

    private boolean isPlaying = false;

    public ABGifView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        this.addAttributes(context, attrs);

        this.initializeGif();
        this.play();
    }

    public void play()
    {
        if (this.movie == null)
            return;

        this.isPlaying = true;
    }

    public void stop()
    {
        this.isPlaying = false;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawColor(Color.TRANSPARENT);
        super.onDraw(canvas);

        if (this.movie != null) {
            final long time_now = SystemClock.uptimeMillis();

            if (!this.isPlaying)
                this.movie_TimeStart = time_now;

            final int movie_time = (int)((time_now - this.movie_TimeStart) % this.movie_Duration);

            this.movie.setTime(movie_time);
            this.movie.draw(canvas, 0, 0);
        }

        this.invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = this.movie.width();
        int height = this.movie.height();
        this.setMeasuredDimension(width, height);
    }

    private void addAttributes(Context context, AttributeSet attrs)
    {
        TypedArray typed_array = context.obtainStyledAttributes(attrs, R.styleable.ABGifView);

        final int indexes_length = typed_array.getIndexCount();
        for (int i = 0; i < indexes_length; ++i) {
            int attr = typed_array.getIndex(i);
            if (attr == R.styleable.ABGifView_ab_src)
                this.srcId = typed_array.getResourceId(attr, 0);
        }

        typed_array.recycle();
    }

    private void initializeGif()
    {
        if (this.srcId == 0)
            return;

        try {
            this.inputStram = this.getContext().getResources().openRawResource(this.srcId);
            this.movie = Movie.decodeStream(this.inputStram);
            this.movie_Duration = this.movie.duration();
        } catch (Exception e) {
            Log.d("ABGif", "Error while loading GIF: " + e.toString());
            // Do nothing.
        }
    }

}
