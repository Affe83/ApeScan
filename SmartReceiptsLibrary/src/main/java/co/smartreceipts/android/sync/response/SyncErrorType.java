package co.smartreceipts.android.sync.response;

import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Tracks different error types that a {@link co.smartreceipts.android.sync.response.SyncError} can have
 */
public interface SyncErrorType extends Parcelable {

    /**
     * Gets the error code
     *
     * @return the error code for this type
     */
    int getCode();

    /**
     * The message about why this error occurred
     *
     * @return the {@link java.lang.String} about why this error occurred.
     */
    @NonNull
    String getMessage();


}
