package co.smartreceipts.android.sync.model;

import android.support.annotation.NonNull;

/**
 * Identifies a particular object as being able to be synced to our backend.
 */
public interface Syncable {

    /**
     * Gets the metadata information related to the current sync status of this object
     *
     * @return the {@link co.smartreceipts.android.sync.model.SyncMetadata}
     */
    @NonNull
    SyncMetadata getSyncMetadata();
}
