package co.smartreceipts.android.sync.request;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import co.smartreceipts.android.sync.model.Syncable;

/**
 * Implementations of this class can handle how synchronization requests are transmitted
 * 
 * @author Will Baumann
 */
public interface SyncRequest<T extends Syncable> extends Parcelable {

	/**
	 * @return the {@link SyncRequestType} for this upload request
	 */
    @NonNull
    SyncRequestType getSyncRequestType();

	/**
	 * @return the desired payload or {@code} null if nothing is being uploaded
	 */
    @NonNull
    T getRequestData();

    /**
     * @return the {@link java.lang.Class} type of {@link #getRequestData()}
     */
    @NonNull
    Class<T> getRequestDataClass();
}
