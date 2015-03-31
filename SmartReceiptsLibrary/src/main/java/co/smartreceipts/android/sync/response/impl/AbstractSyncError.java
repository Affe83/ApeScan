package co.smartreceipts.android.sync.response.impl;

import android.os.Parcel;
import android.support.annotation.NonNull;

import co.smartreceipts.android.sync.model.Syncable;
import co.smartreceipts.android.sync.request.SyncRequest;
import co.smartreceipts.android.sync.response.SyncError;
import co.smartreceipts.android.sync.response.SyncErrorType;

/**
 * An abstract implementation of the {@link co.smartreceipts.android.sync.response.SyncResponse} interface
 * in order to provide a base implementation for common data types.
 *
 * @author williambaumann
 */
class AbstractSyncError<T extends Syncable> implements SyncError<T> {

    private final SyncErrorType mSyncErrorType;
    private final SyncRequest<T> mSyncRequest;

    protected AbstractSyncError(@NonNull SyncErrorType errorType, @NonNull SyncRequest<T> syncRequest) {
        mSyncErrorType = errorType;
        mSyncRequest = syncRequest;
    }

    /**
     * Stepping stone constructor to ensure we're using an efficient class loader (i.e. framework vs APK) for re-loading the parcel
     *
     * @param in - the {@link android.os.Parcel} to load
     */
    protected AbstractSyncError(@NonNull Parcel in) {
        this((SyncErrorType) in.readParcelable(SyncErrorType.class.getClassLoader()), (SyncRequest<T>) in.readParcelable(SyncRequest.class.getClassLoader()));
    }

    @NonNull
    @Override
    public final SyncRequest<T> getRequest() {
        return mSyncRequest;
    }

    @NonNull
    @Override
    public SyncErrorType getErrorType() {
        return mSyncErrorType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mSyncErrorType, flags);
        dest.writeParcelable(mSyncRequest, flags);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractSyncError that = (AbstractSyncError) o;

        if (!mSyncErrorType.equals(that.mSyncErrorType)) return false;
        if (!mSyncRequest.equals(that.mSyncRequest)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mSyncErrorType.hashCode();
        result = 31 * result + mSyncRequest.hashCode();
        return result;
    }
}
