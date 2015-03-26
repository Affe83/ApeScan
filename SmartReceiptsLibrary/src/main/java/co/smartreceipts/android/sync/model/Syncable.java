package co.smartreceipts.android.sync.model;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Date;

import co.smartreceipts.android.sync.id.Identifier;
import co.smartreceipts.android.sync.model.impl.ImmutableSyncableImpl;

/**
 * Identifies a particular object as being able to be synced to our backend.
 */
public interface Syncable extends Parcelable {

    /**
     * Defines a specific implementation for data points that are not and have never been synced.
     */
    public static final Syncable NEVER_SYNCED = new ImmutableSyncableImpl(Identifier.NONE, SyncSource.Undefined, SyncStatus.NeverSynced, null, null);

    /**
     * Gets the id for this particular item. {@link co.smartreceipts.android.sync.id.Identifier#NONE} should
     * be returned if this item has never been synced or otherwise has an undefined id.
     *
     * @return the {@link co.smartreceipts.android.sync.id.Identifier}
     */
    @NonNull
    Identifier getId();

    /**
     * Gets the source of this synchronized object
     *
     * @return the {@link co.smartreceipts.android.sync.model.SyncSource}
     */
    @NonNull
    SyncSource getSource();

    /**
     * Gets the current sync status for this item
     *
     * @return the {@link co.smartreceipts.android.sync.model.SyncStatus}
     */
    @NonNull
    SyncStatus getStatus();

    /**
     * Gets the date timestamp for when this item was created in the remote backend
     *
     * @return the {@link java.util.Date} of creation or {@code null} if this item has never been synced
     */
    Date getCreationDate();

    /**
     * Gets the date timestamp for when this item was last modified in the remote backend
     *
     * @return the {@link java.util.Date} of last modification or {@code null} if this item has never been synced
     */
    Date getLastModificationDate();
}
