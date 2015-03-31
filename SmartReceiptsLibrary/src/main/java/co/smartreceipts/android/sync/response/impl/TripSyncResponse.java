package co.smartreceipts.android.sync.response.impl;

import android.os.Parcel;
import android.support.annotation.NonNull;

import co.smartreceipts.android.model.Trip;
import co.smartreceipts.android.sync.request.SyncRequest;

/**
 * An implementation of the {@link co.smartreceipts.android.sync.response.SyncResponse} interface for
 * {@link co.smartreceipts.android.model.Trip} objects.
 *
 * @author williambaumann
 */
public final class TripSyncResponse extends AbstractSyncResponse<Trip> {

    public TripSyncResponse(@NonNull Trip response, @NonNull SyncRequest<Trip> request) {
        super(response, request);
    }

    private TripSyncResponse(@NonNull Parcel in) {
        super(in);
    }

    public static final Creator<TripSyncResponse> CREATOR = new Creator<TripSyncResponse>() {
        public TripSyncResponse createFromParcel(Parcel source) {
            return new TripSyncResponse(source);
        }

        public TripSyncResponse[] newArray(int size) {
            return new TripSyncResponse[size];
        }
    };
}
