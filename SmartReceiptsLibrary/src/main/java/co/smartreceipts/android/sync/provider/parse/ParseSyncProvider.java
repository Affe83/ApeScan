package co.smartreceipts.android.sync.provider.parse;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import co.smartreceipts.android.model.Trip;
import co.smartreceipts.android.sync.network.NetworkProvider;
import co.smartreceipts.android.sync.provider.SyncProvider;
import co.smartreceipts.android.sync.request.SyncRequest;
import co.smartreceipts.android.sync.response.listener.SyncListener;
import co.smartreceipts.android.sync.response.listener.SyncListenersManager;

/**
 * Defines a parse.com implementation of the {@link co.smartreceipts.android.sync.provider.SyncProvider}
 */
public final class ParseSyncProvider implements SyncProvider {

    private final NetworkProvider mNetworkProvider;
    private final List<SyncRequest> mOutstandingRequests;
    private final ParseTripSyncHelper mParseSyncTripProvider;
    private final SyncListenersManager mSyncListenersManager;

    public ParseSyncProvider(@NonNull NetworkProvider networkProvider) {
        mNetworkProvider = networkProvider;
        mOutstandingRequests = new CopyOnWriteArrayList<SyncRequest>();
        mSyncListenersManager = new SyncListenersManager();
        mParseSyncTripProvider = new ParseTripSyncHelper(mSyncListenersManager);
    }


    @Override
    public boolean registerListener(@NonNull SyncListener listener) {
        return mSyncListenersManager.registerListener(listener);
    }

    @Override
    public boolean unregisterListener(@NonNull SyncListener listener) {
        return mSyncListenersManager.unregisterListener(listener);
    }

    @Override
    public boolean supportsSynchronization(@NonNull SyncRequest request) {
        return true;
    }

    @Override
    public boolean submitSyncRequest(@NonNull SyncRequest request) {
        if (mNetworkProvider.isNetworkAvailable()) {
            if (request.getRequestDataClass().equals(Trip.class)) {
                mParseSyncTripProvider.onSubmitSyncRequestWithNetwork(request);
            }
        } else {
            mOutstandingRequests.add(request);
        }

        return true;
    }

    @Override
    public void onNetworkConnectivityLost() {

    }

    @Override
    public void onNetworkConnectivityGained() {
        if (!mOutstandingRequests.isEmpty()) {
            final List<SyncRequest> listSnapshot = new ArrayList<SyncRequest>(mOutstandingRequests);
            mOutstandingRequests.clear();
            for (SyncRequest request : listSnapshot) {
                submitSyncRequest(request);
            }
        }
    }
}
