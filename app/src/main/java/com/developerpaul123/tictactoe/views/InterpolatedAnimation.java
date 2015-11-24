package com.developerpaul123.tictactoe.views;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Class that aids in interpolations for animations.
 *
 * Created by Paul on 11/24/2015.
 *
 */
public class InterpolatedAnimation extends Animation {
    /**
     * Interface for the interpolation animation.
     */
    public interface InterpolatedAnimationCallback {
        /**
         * Gets the interpolated time.
         * @param interpTime, the time between 0 and 1 after it goes through the animation
         *                    interpolator.
         */
        public void onTimeUpDate(float interpTime);
    }

    private InterpolatedAnimationCallback mCallback;
    public InterpolatedAnimation(InterpolatedAnimationCallback callback) {
        mCallback = callback;
    }
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        if(mCallback != null) {
            mCallback.onTimeUpDate(interpolatedTime);
        }
    }

}

