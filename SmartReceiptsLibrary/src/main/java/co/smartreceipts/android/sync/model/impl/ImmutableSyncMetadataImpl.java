package co.smartreceipts.android.sync.model.impl;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Date;

import co.smartreceipts.android.sync.model.Identifier;
import co.smartreceipts.android.sync.model.SyncSource;
import co.smartreceipts.android.sync.model.SyncStatus;
import co.smartreceipts.android.sync.model.SyncMetadata;

/**
 * Provides an immutable implementation of the {@link co.smartreceipts.android.sync.model.SyncMetadata} contract
 * so that other objects have a standard means of consuming this information
 */
public final class ImmutableSyncMetadataImpl implements SyncMetadata {

    private final Identifier mIdentifier;
    private final SyncSource mSyncSource;
    private final SyncStatus mSyncStatus;
    private final Date mCreationDate;
    private final Date mLastModificationDate;

    public ImmutableSyncMetadataImpl(@NonNull Identifier identifier, @NonNull SyncSource syncSource, @NonNull SyncStatus syncStatus,
                                     Date creationDate, Date lastModificationDate) {
        mIdentifier = identifier;
        mSyncSource = syncSource;
        mSyncStatus = syncStatus;
        mCreationDate = creationDate;
        mLastModificationDate = lastModificationDate;
    }

    private ImmutableSyncMetadataImpl(Parcel in) {
        this.mIdentifier = in.readParcelable(Identifier.class.getClassLoader());
        this.mSyncSource = SyncSource.values()[in.readInt()];
        this.mSyncStatus = SyncStatus.values()[in.readInt()];
        final long creationDateTime = in.readLong();
        final long lastModificationDateTime = in.readLong();
        this.mCreationDate = creationDateTime >= 0 ? new Date(creationDateTime) : null;
        this.mLastModificationDate = lastModificationDateTime >= 0 ? new Date(lastModificationDateTime) : null;
    }


    @NonNull
    @Override
    public Identifier getId() {
        return mIdentifier;
    }

    @NonNull
    @Override
    public SyncSource getSource() {
        return mSyncSource;
    }

    @NonNull
    @Override
    public SyncStatus getStatus() {
        return mSyncStatus;
    }

    @NonNull
    @Override
    public Date getCreationDate() {
        return mCreationDate;
    }

    @NonNull
    @Override
    public Date getLastModificationDate() {
        return mLastModificationDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImmutableSyncMetadataImpl that = (ImmutableSyncMetadataImpl) o;

        if (mCreationDate != null ? !mCreationDate.equals(that.mCreationDate) : that.mCreationDate != null)
            return false;
        if (!mIdentifier.equals(that.mIdentifier)) return false;
        if (mLastModificationDate != null ? !mLastModificationDate.equals(that.mLastModificationDate) : that.mLastModificationDate != null)
            return false;
        if (mSyncSource != that.mSyncSource) return false;
        if (mSyncStatus != that.mSyncStatus) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mIdentifier.hashCode();
        result = 31 * result + mSyncSource.hashCode();
        result = 31 * result + mSyncStatus.hashCode();
        result = 31 * result + (mCreationDate != null ? mCreationDate.hashCode() : 0);
        result = 31 * result + (mLastModificationDate != null ? mLastModificationDate.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mIdentifier, 0);
        dest.writeInt(this.mSyncSource.ordinal());
        dest.writeInt(this.mSyncStatus.ordinal());
        dest.writeLong(mCreationDate != null ? mCreationDate.getTime() : -1L);
        dest.writeLong(mLastModificationDate != null ? mLastModificationDate.getTime() : -1L);
    }

    public static final Parcelable.Creator<ImmutableSyncMetadataImpl> CREATOR = new Parcelable.Creator<ImmutableSyncMetadataImpl>() {
        public ImmutableSyncMetadataImpl createFromParcel(Parcel source) {
            return new ImmutableSyncMetadataImpl(source);
        }

        public ImmutableSyncMetadataImpl[] newArray(int size) {
            return new ImmutableSyncMetadataImpl[size];
        }
    };
}
