package co.smartreceipts.android.sync.response;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import co.smartreceipts.android.sync.model.Syncable;
import co.smartreceipts.android.sync.request.SyncRequest;

/**
 * This is used to encapsulate error data that may occur during a sync operation
 *
 * @author Will Baumann
 */
public interface SyncError<T extends Syncable> extends Parcelable {

    /**
     * @return - the {@link co.smartreceipts.android.sync.request.SyncRequest} that was used to
     * produce this error.
     */
    @NonNull
    SyncRequest<T> getRequest();

    /**
     * Gets the type of error
     *
     * @return - the {@link co.smartreceipts.android.sync.response.SyncErrorType} associated with this error
     */
    @NonNull
    SyncErrorType getErrorType();

}
