package co.smartreceipts.android.sync.response.impl;

import android.os.Parcel;
import android.support.annotation.NonNull;

import co.smartreceipts.android.sync.response.SyncErrorType;

public enum SyncErrorTypesEnum implements SyncErrorType {

    Unknown(0, "Unknown error occurred");

    private final int mCode;
    private final String mMessage;

    private SyncErrorTypesEnum(int code, @NonNull String message) {
        mCode = code;
        mMessage = message;
    }

    @Override
    public int getCode() {
        return mCode;
    }

    @NonNull
    @Override
    public String getMessage() {
        return mMessage;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ordinal());
    }


    public static final Creator<SyncErrorTypesEnum> CREATOR = new Creator<SyncErrorTypesEnum>() {

        public SyncErrorTypesEnum createFromParcel(Parcel source) {
            return SyncErrorTypesEnum.values()[source.readInt()];
        }

        public SyncErrorTypesEnum[] newArray(int size) {
            return new SyncErrorTypesEnum[size];
        }
    };
}
