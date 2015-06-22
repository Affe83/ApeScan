package co.smartreceipts.android.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import co.smartreceipts.android.R;

/**
 * <p>
 * Extends the default Android {@link android.widget.EditText} behavior to allow for different network
 * states within this box via a right-aligned icon. Users of this class should maintain responsibility
 * for driving the different network states of this class via the following methods:
 * <ul>
 * <p/>
 * </ul>
 * </p>
 * <p>
 * Please note that this class overrides the <pre>@attr ref android.R.styleable#TextView_drawableEnd</pre>
 * attribute. Manually calling any of the setCompoundDrawable methods may break the behavior of this class.
 * </p>
 */
public class NetworkRequestAwareEditText extends EditText {

    /**
     * Tracks the various network states this layout can exist within. All states must be driven externally
     */
    public enum State {

        /**
         * Indicates that this view is ready to submit a network request
         */
        Ready,

        /**
         * Indicates that we are currently loading
         */
        Loading,

        /**
         * Indicates that we successfully performed a network request
         */
        Success,

        /**
         * Indicates that the network request failed
         */
        Failure
    }

    public interface RetryListener {

        /**
         * Callback method that is called whenever the user wish to retry the network request
         */
        void onUserRetry();
    }

    private State mState = State.Ready;
    private CharSequence mOriginalHint;
    private CharSequence mFailedHint;
    private RetryListener mRetryListener;

    public NetworkRequestAwareEditText(Context context) {
        super(context);
        init();
    }

    public NetworkRequestAwareEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NetworkRequestAwareEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NetworkRequestAwareEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mOriginalHint = getHint();
        mFailedHint = getHint();
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        final Drawable drawableEnd;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            drawableEnd = getCompoundDrawablesRelative()[2];
        } else {
            drawableEnd = getCompoundDrawables()[2];
        }

        if (drawableEnd != null) {
            final int start;
            if (View.LAYOUT_DIRECTION_LTR == ViewCompat.getLayoutDirection(this)) {
                start = getWidth() - ViewCompat.getPaddingEnd(this) - drawableEnd.getIntrinsicWidth();
            } else {
                start = ViewCompat.getPaddingEnd(this);
            }

            final boolean wasTapped = event.getX() > start && event.getX() < start + drawableEnd.getIntrinsicWidth();
            if (wasTapped) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (mRetryListener != null) {
                        mRetryListener.onUserRetry();
                    }
                    return true;
                } else {
                    return super.onTouchEvent(event);
                }
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * @return the current network state of this view
     */
    @NonNull
    public State getCurrentState() {
        return mState;
    }

    /**
     * Updates the current state of this view
     *
     * @param state the desired "new" state
     */
    public synchronized void setCurrentState(@NonNull State state) {
        if (mState == state) {
            // Exit early if nothing is changing
            return;
        }

        mState = state;
        final Drawable[] drawables;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            drawables = getCompoundDrawablesRelative();
        } else {
            drawables = getCompoundDrawables();
        }

        final Drawable drawableStart = drawables[0];
        final Drawable drawableTop = drawables[1];
        final Drawable drawableBottom = drawables[3];
        final Drawable drawableEnd;
        switch (state) {
            case Ready:
                drawableEnd = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_refresh, getContext().getTheme());
                setHint(mOriginalHint);
                break;
            case Loading:
                drawableEnd = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_refresh_in_progress, getContext().getTheme());
                setHint(mOriginalHint);
                break;
            case Failure:
                drawableEnd = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_refresh, getContext().getTheme());
                setHint(mFailedHint);
                break;
            case Success:
                drawableEnd = null;
                setHint(mOriginalHint);
                break;
            default:
                drawableEnd = null;
                setHint(mOriginalHint);
                break;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            setCompoundDrawablesRelativeWithIntrinsicBounds(drawableStart, drawableTop, drawableEnd, drawableBottom);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(drawableStart, drawableTop, drawableEnd, drawableBottom);
        }

        if (drawableEnd instanceof Animatable) {
            ((Animatable)drawableEnd).start();
        }
    }

    public void setRetryListener(@Nullable RetryListener retryListener) {
        mRetryListener = retryListener;
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (TextUtils.isEmpty(text)) {
            setCurrentState(State.Ready);
        } else {
            setCurrentState(State.Success);
        }
    }

    /**
     * @return the hint that appears if we enter the Failed state
     */
    public CharSequence getFailedHint() {
        return mFailedHint;
    }

    /**
     * Sets the hint that appears if we failed to complete the network request
     *
     * @param failedHint the new failed hint
     */
    public void setFailedHint(CharSequence failedHint) {
        mFailedHint = failedHint;
    }

    /**
     * Sets the hint that appears if we failed to complete the network request
     *
     * @param failedHint the new failed hint
     */
    public void setFailedHint(@StringRes int failedHint) {
        mFailedHint = getContext().getString(failedHint);
    }

}