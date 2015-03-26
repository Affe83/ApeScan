package co.smartreceipts.android.model;

import android.support.annotation.NonNull;

/**
 * Used to track items that have a specific value component (e.g. enums that need to be stored in the database)
 */
public interface Value {

    /**
     * Gets the value associated with this object
     *
     * @return the value as a {@link java.lang.String}
     */
    @NonNull
    String getValue();
}
