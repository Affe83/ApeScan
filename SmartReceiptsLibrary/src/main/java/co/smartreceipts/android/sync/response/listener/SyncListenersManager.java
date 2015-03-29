package co.smartreceipts.android.sync.response.listener;

import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import co.smartreceipts.android.sync.model.Syncable;
import co.smartreceipts.android.sync.request.SyncRequestType;
import co.smartreceipts.android.sync.response.SyncError;
import co.smartreceipts.android.sync.response.SyncResponse;

/**
 * Tracks a series of {@link co.smartreceipts.android.sync.response.listener.SyncListener} objects to simplify the process
 * of informing each about the results
 */
public final class SyncListenersManager {

    private final Map<Class<? extends Syncable>, List<SyncListener>> mListenerMap;

    public SyncListenersManager() {
        mListenerMap = new ConcurrentHashMap<Class<? extends Syncable>, List<SyncListener>>();
    }

    /**
     * Registers a listener, which will be alerted when sync requests complete
     *
     * @param listener the listener to register
     * @return {@code true} if it was successfully registered (i.e. it wasn't previously registered), {@code false} otherwise
     */
    public boolean registerListener(@NonNull SyncListener listener) {
        final Class<? extends Syncable> classType = listener.getListenerType();
        final List<SyncListener> listeners = mListenerMap.get(classType);
        if (listeners == null) {
            mListenerMap.put(classType, new CopyOnWriteArrayList<SyncListener>(Arrays.asList(listener)));
            return true;
        } else {
            return listeners.add(listener);
        }
    }

    /**
     * Un-registers a listener, so it will NOT be alerted when sync requests complete
     *
     * @param listener the listener to un-register
     * @return {@code true} if it was successfully un-registered (i.e. it was previously registered), {@code false} otherwise
     */
    public boolean unregisterListener(@NonNull SyncListener listener) {
        final Class<? extends Syncable> classType = listener.getListenerType();
        final List<SyncListener> listeners = mListenerMap.get(classType);
        if (listeners == null) {
            return false;
        } else {
            return listeners.remove(listener);
        }
    }

    /**
     * Notifies all listeners that we've completed a successful sync
     *
     * @param type     the {@link co.smartreceipts.android.sync.request.SyncRequestType} of the request
     * @param response the {@link co.smartreceipts.android.sync.response.SyncResponse} that was received
     */
    public void notifySyncSuccess(@NonNull SyncRequestType type, @NonNull SyncResponse response) {
        final Class<? extends Syncable> classType = response.getRequest().getRequestDataClass();
        final List<SyncListener> listeners = mListenerMap.get(classType);
        if (listeners != null) {
            for (final SyncListener listener : listeners) {
                listener.onSyncSuccess(type, response);
            }
        }
    }

    /**
     * Notifies all listeners that we've completed a successful sync
     *
     * @param type  the {@link co.smartreceipts.android.sync.request.SyncRequestType} of the request
     * @param error the {@link co.smartreceipts.android.sync.response.SyncError}, which details why the sync failed
     */
    public void notifySyncError(@NonNull SyncRequestType type, @NonNull SyncError error) {
        final Class<? extends Syncable> classType = error.getRequest().getRequestDataClass();
        final List<SyncListener> listeners = mListenerMap.get(classType);
        if (listeners != null) {
            for (final SyncListener listener : listeners) {
                listener.onSyncError(type, error);
            }
        }
    }
}
