package co.smartreceipts.android.sync.request;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Provides an {@link java.lang.Enum} implementation of the {@link SyncRequestType}
 * interface
 *
 * @author williambaumann
 */
public enum SyncRequestType implements Parcelable {
    Get("Get"), Insert("Insert"), Update("Update"), Delete("Delete");

    private final String mType;

    private SyncRequestType(String type) {
        mType = type;
    }

    public String getType() {
        return mType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mType);
    }

    public static final Creator<SyncRequestType> CREATOR = new Creator<SyncRequestType>() {
        public SyncRequestType createFromParcel(Parcel source) {
            return getEnumFromType(source.readString());
        }

        public SyncRequestType[] newArray(int size) {
            return new SyncRequestType[size];
        }
    };


    public static SyncRequestType getEnumFromType(String requestType) {
        for (SyncRequestType syncRequestTypeEnum : SyncRequestType.values()) {
            if (syncRequestTypeEnum.getType().equals(requestType)) {
                return syncRequestTypeEnum;
            }
        }
        throw new IllegalArgumentException("Invalud Request Type was provided: " + requestType);
    }
}
