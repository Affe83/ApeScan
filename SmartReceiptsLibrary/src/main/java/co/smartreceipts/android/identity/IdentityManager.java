package co.smartreceipts.android.identity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import co.smartreceipts.android.apis.hosts.ServiceManager;

public class IdentityManager {

    private final Context mContext;
    private final ServiceManager mServiceManager;
    private final IdentityStore mIdentityStore;

    public IdentityManager(@NonNull Context context, @NonNull ServiceManager serviceManager) {
        this(context, serviceManager, new IdentityStore(context));
    }

    public IdentityManager(@NonNull Context context, @NonNull ServiceManager serviceManager, @NonNull IdentityStore identityStore) {
        mContext = context.getApplicationContext();
        mServiceManager = serviceManager;
        mIdentityStore = identityStore;
    }

    public boolean isLoggedIn() {
        return getEmail() != null && getToken() != null;
    }

    @Nullable
    public EmailAddress getEmail() {
        return mIdentityStore.getEmail();
    }

    @Nullable
    public Token getToken() {
        return mIdentityStore.getToken();
    }
}
