package co.smartreceipts.android.sync.provider.parse;

import android.support.annotation.NonNull;

import co.smartreceipts.android.sync.model.Syncable;
import co.smartreceipts.android.sync.request.SyncRequest;
import co.smartreceipts.android.sync.response.SyncResponse;

/**
 * A package private abstract class, which we will use to manage uploads to parse. This class
 * has been designed as a helper in order that we can easily manage the upload of a particular
 * data type {@link T}.
 *
 * @author Will Baumann
 */
abstract class AbstractParseSyncHelper<T extends Syncable> {

    /**
     * This should be over-ridden as needed by subclasses to manage the implementation details of actually submitting
     * the sync request
     *
     * @param request - the {@link java.util.List} of {@link co.smartreceipts.android.sync.request.SyncRequest}s to send.
     */
    protected abstract void onSubmitSyncRequestWithNetwork(@NonNull SyncRequest<T> request);

}
