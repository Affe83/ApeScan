package co.smartreceipts.android.sync.request.impl;

import android.os.Parcel;
import android.os.Parcelable;

import co.smartreceipts.android.model.Distance;
import co.smartreceipts.android.sync.request.SyncRequestType;

/**
 * An implementation of the {@link co.smartreceipts.android.sync.request.SyncRequest} interface for
 * {@link co.smartreceipts.android.model.Distance} objects.
 *
 * @author williambaumann
 */
public class DistanceSyncRequest  {

    public DistanceSyncRequest(Distance requestData, SyncRequestType requestType) {
        // super(requestData, requestType, Distance.class);
    }

    private DistanceSyncRequest(Parcel in) {
        // super(in);
    }

    public static final Parcelable.Creator<DistanceSyncRequest> CREATOR = new Parcelable.Creator<DistanceSyncRequest>() {
        public DistanceSyncRequest createFromParcel(Parcel source) {
            return new DistanceSyncRequest(source);
        }

        public DistanceSyncRequest[] newArray(int size) {
            return new DistanceSyncRequest[size];
        }
    };

}
