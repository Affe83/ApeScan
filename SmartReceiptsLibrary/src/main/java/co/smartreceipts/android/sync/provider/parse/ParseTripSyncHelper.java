package co.smartreceipts.android.sync.provider.parse;

import android.support.annotation.NonNull;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import co.smartreceipts.android.model.Trip;
import co.smartreceipts.android.model.factory.TripBuilderFactory;
import co.smartreceipts.android.persistence.DatabaseHelper;
import co.smartreceipts.android.sync.model.SyncSource;
import co.smartreceipts.android.sync.model.SyncStatus;
import co.smartreceipts.android.sync.model.factory.SyncableBuilderFactory;
import co.smartreceipts.android.sync.request.SyncRequest;
import co.smartreceipts.android.sync.request.SyncRequestType;
import co.smartreceipts.android.sync.response.SyncError;
import co.smartreceipts.android.sync.response.SyncResponse;
import co.smartreceipts.android.sync.response.impl.TripSyncResponse;
import co.smartreceipts.android.sync.response.listener.SyncListenersManager;

/**
 * Handles the process of submitting a {@link co.smartreceipts.android.sync.request.SyncRequest} to the Parse
 * backend for a {@link co.smartreceipts.android.model.Trip} object
 *
 * @author williambaumann
 */
public class ParseTripSyncHelper extends AbstractParseSyncHelper<Trip> {

    private static final String TRIPS = "Trips";

    public ParseTripSyncHelper(@NonNull SyncListenersManager syncListenersManager) {
        super(syncListenersManager);
    }

    @Override
    protected void onSubmitSyncRequestWithNetwork(@NonNull SyncRequest<Trip> request) {
        switch (request.getSyncRequestType()) {
            case Get:
                submitGetRequest(request);
                break;
            case Insert:
            case Update:
                submitInsertUpdateRequest(request);
                break;
            case Delete:
                submitDeleteRequest(request);
                break;
        }
    }


    private void submitGetRequest(@NonNull final SyncRequest<Trip> request) {
        
    }


    private void submitInsertUpdateRequest(@NonNull final SyncRequest<Trip> request) {
        final ParseObject parseObject = new ParseObject(TRIPS);
        final Trip trip = request.getRequestData();
        parseObject.put(DatabaseHelper.TripsTable.COLUMN_NAME, trip.getName());
        parseObject.put(DatabaseHelper.TripsTable.COLUMN_FROM, trip.getStartDate());
        parseObject.put(DatabaseHelper.TripsTable.COLUMN_TO, trip.getEndDate());
        parseObject.put(DatabaseHelper.TripsTable.COLUMN_FROM_TIMEZONE, trip.getStartTimeZone().getID());
        parseObject.put(DatabaseHelper.TripsTable.COLUMN_TO_TIMEZONE, trip.getEndTimeZone().getID());
        parseObject.put(DatabaseHelper.TripsTable.COLUMN_COMMENT, trip.getComment());
        parseObject.put(DatabaseHelper.TripsTable.COLUMN_COST_CENTER, trip.getCostCenter());
        parseObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    final SyncableBuilderFactory syncableBuilderFactory = new SyncableBuilderFactory();
                    syncableBuilderFactory.setIdentifier(parseObject.getObjectId());
                    syncableBuilderFactory.setSource(SyncSource.Parse);
                    syncableBuilderFactory.setStatus(SyncStatus.Synced);
                    syncableBuilderFactory.setCreationDate(parseObject.getCreatedAt());
                    syncableBuilderFactory.setLastModificationDate(parseObject.getUpdatedAt());
                    final TripBuilderFactory tripBuilder = new TripBuilderFactory();
                    tripBuilder.setDirectory(trip.getDirectory());
                    tripBuilder.setStartDate(trip.getStartDate());
                    tripBuilder.setEndDate(trip.getEndDate());
                    tripBuilder.setStartTimeZone(trip.getStartTimeZone());
                    tripBuilder.setEndTimeZone(trip.getEndTimeZone());
                    tripBuilder.setComment(trip.getComment());
                    tripBuilder.setCostCenter(trip.getCostCenter());
                    tripBuilder.setSyncMetaData(syncableBuilderFactory.build());
                    final SyncResponse<Trip> syncResponse = new TripSyncResponse(tripBuilder.build(), request);
                    notifySyncSuccess(request.getSyncRequestType(), syncResponse);
                } else {
                    // TODO: Get error codes from parse
                    // TODO: Return sync error
                }
            }
        });
    }

    private void submitDeleteRequest(@NonNull final SyncRequest<Trip> request) {

    }

}
