package pl.allblue.gestures;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

public class Swype
{

    static public float MinDistance = 50;
    static public float MinVelocity = 0.01f;


    MotionEvent e1, e2;
    float distanceX, distanceY;
    float distanceX_Abs, distanceY_Abs;
    float velocityX, velocityY;
    float velocityX_Abs, velocityY_Abs;


    public Swype(Context context, MotionEvent e1, MotionEvent e2,
            float relative_velocity_x, float relative_velocity_y)
    {
        this.e1 = e1;
        this.e2 = e2;

        float max_fling_velocity = ViewConfiguration.get(context)
                .getScaledMaximumFlingVelocity();

        this.distanceX = e2.getX() - e1.getX();
        this.distanceX_Abs = Math.abs(this.distanceX);
        this.velocityX = relative_velocity_x / max_fling_velocity;
        this.velocityX_Abs = Math.abs(this.velocityX);

        this.distanceY = e2.getY() - e1.getY();
        this.distanceY_Abs = Math.abs(this.distanceY);
        this.velocityY = relative_velocity_y / max_fling_velocity;
        this.velocityY_Abs = Math.abs(this.velocityY);

        Log.d("Swype", "X: " + this.velocityX);
    }

    public boolean isLeft()
    {
        if (!this.isHorizontal())
            return false;

        if (this.velocityX <= 0)
            return false;

        return true;
    }

    public boolean isRight()
    {
        if (!this.isHorizontal())
            return false;

        if (this.velocityX >= 0)
            return false;

        return true;
    }

    private boolean isHorizontal()
    {
        if (this.distanceX_Abs < this.distanceY_Abs)
            return false;

        if (this.distanceX_Abs < Swype.MinDistance)
            return false;

        if (this.velocityX_Abs < Swype.MinVelocity)
            return false;

        return true;
    }

}
