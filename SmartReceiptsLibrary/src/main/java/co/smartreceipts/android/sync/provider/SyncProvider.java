package co.smartreceipts.android.sync.provider;

import android.support.annotation.NonNull;

import co.smartreceipts.android.sync.network.NetworkStateChangeListener;
import co.smartreceipts.android.sync.request.SyncRequest;
import co.smartreceipts.android.sync.response.listener.SyncListener;

public interface SyncProvider extends NetworkStateChangeListener {

    /**
     * Registers a listener, which will be alerted when sync requests complete
     *
     * @param listener the listener to register
     * @return {@code true} if it was successfully registered (i.e. it wasn't previously registered), {@code false} otherwise
     */
    public boolean registerListener(@NonNull SyncListener listener);

    /**
     * Un-registers a listener, so it will NOT be alerted when sync requests complete
     *
     * @param listener the listener to un-register
     * @return {@code true} if it was successfully un-registered (i.e. it was previously registered), {@code false} otherwise
     */
    public boolean unregisterListener(@NonNull SyncListener listener);

    /**
     * Determines if we can use this provider to support this particular category of synchronization operations
     *
     * @param request - the request to check
     * @return {@code true} if this is supported. {@code false} otherwise.
     */
    public boolean supportsSynchronization(@NonNull SyncRequest request);

    /**
     * Submits a synchronization request to be uploaded to our back-end
     *
     * @param syncRequest - the {@link SyncRequest} to upload
     * @return {@code true} if at {@link SyncProvider} is registered that supports this request type
     */
    public boolean submitSyncRequest(@NonNull SyncRequest syncRequest);

}
