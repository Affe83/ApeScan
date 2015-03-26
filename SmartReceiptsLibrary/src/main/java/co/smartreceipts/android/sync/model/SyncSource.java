package co.smartreceipts.android.sync.model;

import android.support.annotation.NonNull;

import co.smartreceipts.android.model.Value;

/**
 * Tracks the backend source that was used for synchronization purposes
 */
public enum SyncSource implements Value {

    /**
     * Identifies that this source has not been defined
     */
    Undefined("undefined"),

    /**
     * Identifies that we're using an implementation from the parse.com APIs
     */
    Parse("parse");

    private final String mValue;

    private SyncSource(@NonNull String value) {
        mValue = value;
    }

    @NonNull
    @Override
    public String getValue() {
        return mValue;
    }

    public static SyncSource getSourceFromValue(@NonNull String value) {
        final SyncSource[] syncSources = SyncSource.values();
        for (int i = 0; i < syncSources.length; i++) {
            final SyncSource syncSource = syncSources[i];
            if (syncSource.getValue().equals(value)) {
                return syncSource;
            }
        }
        throw new IllegalArgumentException("Undefined value: " + value);
    }
}
