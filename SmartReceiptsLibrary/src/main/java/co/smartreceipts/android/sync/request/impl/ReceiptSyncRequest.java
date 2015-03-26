package co.smartreceipts.android.sync.request.impl;

import android.os.Parcel;
import android.os.Parcelable;

import co.smartreceipts.android.model.Receipt;
import co.smartreceipts.android.sync.request.SyncRequestType;

/**
 * An implementation of the {@link co.smartreceipts.android.sync.request.SyncRequest} interface for
 * {@link co.smartreceipts.android.model.Receipt} objects.
 *
 * @author williambaumann
 */
public class ReceiptSyncRequest {

    public ReceiptSyncRequest(Receipt requestData, SyncRequestType requestType) {
        // super(requestData, requestType, Receipt.class);
    }

    private ReceiptSyncRequest(Parcel in) {
        // super(in);
    }

    public static final Parcelable.Creator<ReceiptSyncRequest> CREATOR = new Parcelable.Creator<ReceiptSyncRequest>() {
        public ReceiptSyncRequest createFromParcel(Parcel source) {
            return new ReceiptSyncRequest(source);
        }

        public ReceiptSyncRequest[] newArray(int size) {
            return new ReceiptSyncRequest[size];
        }
    };
}
