package com.developerpaul123.tictactoe.views;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.view.View;
import android.view.animation.Animation;

/**
 * Created by Paul on 11/24/2015.
 *
 * Animated path. Animates the drawing of itself.
 */
public class AnimatedPath extends Path {

    private View v;
    private Path mPath;
    private float pathLength;
    private Paint mPaint;

    /**
     * Default constructor.
     * @param v the view this path is bound to, i.e. where it will be drawn.
     * @param p the path that will be drawn.
     * @param paint the paint that will be used to paint the path.
     */
    public AnimatedPath(View v, Path p, Paint paint) {
        this.v = v;
        this.mPath = p;
        PathMeasure measure = new PathMeasure(p, false);
        this.pathLength = measure.getLength();
        this.mPaint = paint;
        this.mPaint.setPathEffect(new DashPathEffect(new float[] {pathLength, pathLength}, 0.0f));
    }

    /**
     * Start the animation.
     * @param listener the animation listener for the animation.
     */
    public void animate(Animation.AnimationListener listener) {
        InterpolatedAnimation animation = new InterpolatedAnimation(new InterpolatedAnimation.InterpolatedAnimationCallback() {
            @Override
            public void onTimeUpDate(float interpTime) {
                mPaint.setPathEffect(new DashPathEffect(new float[] {pathLength, pathLength},
                        (1.0f - interpTime) * pathLength));
                if(v != null) {
                    v.invalidate();
                }
            }
        });
        v.clearAnimation();
        animation.setDuration(200);
        animation.setAnimationListener(listener);
        v.startAnimation(animation);

    }

    /**
     * Injectable method into the views onDraw() method. Pass the views
     * canvas to this method.
     * @param canvas the view's canvas.
     */
    public void onDraw(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
    }
}
