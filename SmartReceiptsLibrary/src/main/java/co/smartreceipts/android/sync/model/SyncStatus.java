package co.smartreceipts.android.sync.model;

import android.support.annotation.NonNull;

import co.smartreceipts.android.model.Value;

/**
 * Tracks different synchronization statuses
 */
public enum SyncStatus implements Value {

    /**
     * Acts as a default value for items that have never been synced before
     */
    NeverSynced("never_synced"),

    /**
     * Indicates that we've successfully synced an item
     */
    Synced("synced"),

    /**
     * Indicates that we've updated an item that was previously synced (and hence it's out of date)
     */
    NotSynced("not_synced"),

    /**
     * Indicates that we've deleted an item
     */
    Deleted("deleted");

    private final String mValue;

    private SyncStatus(@NonNull String value) {
        mValue = value;
    }

    @NonNull
    @Override
    public String getValue() {
        return mValue;
    }

    public static SyncStatus getStatusFromValue(@NonNull String value) {
        final SyncStatus[] syncStatuses = SyncStatus.values();
        for (int i = 0; i < syncStatuses.length; i++) {
            final SyncStatus syncStatus = syncStatuses[i];
            if (syncStatus.getValue().equals(value)) {
                return syncStatus;
            }
        }
        throw new IllegalArgumentException("Undefined value: " + value);
    }

}
