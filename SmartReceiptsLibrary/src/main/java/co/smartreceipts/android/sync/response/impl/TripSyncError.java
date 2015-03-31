package co.smartreceipts.android.sync.response.impl;

import android.os.Parcel;
import android.support.annotation.NonNull;

import co.smartreceipts.android.model.Trip;
import co.smartreceipts.android.sync.request.SyncRequest;
import co.smartreceipts.android.sync.response.SyncErrorType;

/**
 * An implementation of the {@link co.smartreceipts.android.sync.response.SyncResponse} interface for
 * {@link co.smartreceipts.android.model.Trip} objects.
 *
 * @author williambaumann
 */
public final class TripSyncError extends AbstractSyncError<Trip> {

    public TripSyncError(@NonNull SyncErrorType syncErrorType, @NonNull SyncRequest<Trip> syncRequest) {
        super(syncErrorType, syncRequest);
    }

    private TripSyncError(@NonNull Parcel in) {
        super(in);
    }

    public static final Creator<TripSyncError> CREATOR = new Creator<TripSyncError>() {
        public TripSyncError createFromParcel(Parcel source) {
            return new TripSyncError(source);
        }

        public TripSyncError[] newArray(int size) {
            return new TripSyncError[size];
        }
    };
}
