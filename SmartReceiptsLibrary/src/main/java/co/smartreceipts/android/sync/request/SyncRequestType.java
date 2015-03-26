package co.smartreceipts.android.sync.request;

import android.os.Parcelable;

/**
 * Tracks different available request types
 *
 * @author Will Baumann
 */
public interface SyncRequestType extends Parcelable {

    /**
     * Gets the type of the request
     *
     * @return the type as a {@link java.lang.String}
     */
    String getType();
}
