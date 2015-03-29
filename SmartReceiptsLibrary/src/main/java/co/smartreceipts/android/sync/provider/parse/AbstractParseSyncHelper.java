package co.smartreceipts.android.sync.provider.parse;

import android.support.annotation.NonNull;

import co.smartreceipts.android.sync.model.Syncable;
import co.smartreceipts.android.sync.request.SyncRequest;
import co.smartreceipts.android.sync.request.SyncRequestType;
import co.smartreceipts.android.sync.response.SyncError;
import co.smartreceipts.android.sync.response.SyncResponse;
import co.smartreceipts.android.sync.response.listener.SyncListenersManager;

/**
 * A package private abstract class, which we will use to manage uploads to parse. This class
 * has been designed as a helper in order that we can easily manage the upload of a particular
 * data type {@link T}.
 *
 * @author Will Baumann
 */
abstract class AbstractParseSyncHelper<T extends Syncable> {

    private final SyncListenersManager mSyncListenersManager;

    protected AbstractParseSyncHelper(@NonNull SyncListenersManager syncListenersManager) {
        mSyncListenersManager = syncListenersManager;
    }

    /**
     * This should be over-ridden as needed by subclasses to manage the implementation details of actually submitting
     * the sync request
     *
     * @param request - the {@link java.util.List} of {@link co.smartreceipts.android.sync.request.SyncRequest}s to send.
     */
    protected abstract void onSubmitSyncRequestWithNetwork(@NonNull SyncRequest<T> request);

    /**
     * Notifies all listeners that we've completed a successful sync
     *
     * @param type     the {@link co.smartreceipts.android.sync.request.SyncRequestType} of the request
     * @param response the {@link co.smartreceipts.android.sync.response.SyncResponse} that was received
     */
    protected void notifySyncSuccess(@NonNull SyncRequestType type, @NonNull SyncResponse<T> response) {
        mSyncListenersManager.notifySyncSuccess(type, response);
    }

    /**
     * Notifies all listeners that we've completed a successful sync
     *
     * @param type  the {@link co.smartreceipts.android.sync.request.SyncRequestType} of the request
     * @param error the {@link co.smartreceipts.android.sync.response.SyncError}, which details why the sync failed
     */
    protected void notifySyncError(@NonNull SyncRequestType type, @NonNull SyncError<T> error) {
        mSyncListenersManager.notifySyncError(type, error);
    }

}
