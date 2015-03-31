package co.smartreceipts.android.sync.provider.parse;

import android.support.annotation.NonNull;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;

import co.smartreceipts.android.model.Trip;
import co.smartreceipts.android.model.factory.TripBuilderFactory;
import co.smartreceipts.android.persistence.DatabaseHelper;
import co.smartreceipts.android.sync.model.SyncSource;
import co.smartreceipts.android.sync.model.SyncStatus;
import co.smartreceipts.android.sync.model.factory.SyncableBuilderFactory;
import co.smartreceipts.android.sync.request.SyncRequest;
import co.smartreceipts.android.sync.response.SyncError;
import co.smartreceipts.android.sync.response.SyncResponse;
import co.smartreceipts.android.sync.response.impl.SyncErrorTypesEnum;
import co.smartreceipts.android.sync.response.impl.TripSyncError;
import co.smartreceipts.android.sync.response.impl.TripSyncResponse;
import co.smartreceipts.android.sync.response.listener.SyncListenersManager;
import wb.android.storage.StorageManager;

/**
 * Handles the process of submitting a {@link co.smartreceipts.android.sync.request.SyncRequest} to the Parse
 * backend for a {@link co.smartreceipts.android.model.Trip} object
 *
 * @author williambaumann
 */
public class ParseTripSyncHelper extends AbstractParseSyncHelper<Trip> {

    private static final String TRIPS = "Trips";

    private final StorageManager mStorageManager;

    public ParseTripSyncHelper(@NonNull SyncListenersManager syncListenersManager, @NonNull StorageManager storageManager) {
        super(syncListenersManager);
        mStorageManager = storageManager;
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
        final ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>(TRIPS);
        // TODO: Add where x is > last request time
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e == null) {
                    final int size = parseObjects.size();
                    for (int i = 0; i < size; i++) {
                        final ParseObject parseObject = parseObjects.get(i);
                        final SyncableBuilderFactory syncableBuilderFactory = new SyncableBuilderFactory();
                        syncableBuilderFactory.setIdentifier(parseObject.getObjectId());
                        syncableBuilderFactory.setSource(SyncSource.Parse);
                        syncableBuilderFactory.setStatus(SyncStatus.Synced);
                        syncableBuilderFactory.setCreationDate(parseObject.getCreatedAt());
                        syncableBuilderFactory.setLastModificationDate(parseObject.getUpdatedAt());
                        final TripBuilderFactory tripBuilder = new TripBuilderFactory();
                        tripBuilder.setDirectory(mStorageManager.getFile(parseObject.getString(DatabaseHelper.TripsTable.COLUMN_NAME)));
                        tripBuilder.setStartDate(parseObject.getDate(DatabaseHelper.TripsTable.COLUMN_FROM));
                        tripBuilder.setEndDate(parseObject.getDate(DatabaseHelper.TripsTable.COLUMN_TO));
                        tripBuilder.setStartTimeZone(parseObject.getString(DatabaseHelper.TripsTable.COLUMN_TO_TIMEZONE));
                        tripBuilder.setEndTimeZone(parseObject.getString(DatabaseHelper.TripsTable.COLUMN_FROM_TIMEZONE));
                        tripBuilder.setComment(parseObject.getString(DatabaseHelper.TripsTable.COLUMN_COMMENT));
                        tripBuilder.setCostCenter(parseObject.getString(DatabaseHelper.TripsTable.COLUMN_COST_CENTER));
                        tripBuilder.setSyncMetaData(syncableBuilderFactory.build());
                        final SyncResponse<Trip> syncResponse = new TripSyncResponse(tripBuilder.build(), request);
                        notifySyncSuccess(request.getSyncRequestType(), syncResponse);
                    }
                } else {
                    // TODO: Get error codes from parse
                    final SyncError<Trip> syncError = new TripSyncError(SyncErrorTypesEnum.Unknown, request);
                    notifySyncError(request.getSyncRequestType(), syncError);
                }
            }
        });
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
                    final SyncError<Trip> syncError = new TripSyncError(SyncErrorTypesEnum.Unknown, request);
                    notifySyncError(request.getSyncRequestType(), syncError);
                }
            }
        });
    }

    private void submitDeleteRequest(@NonNull final SyncRequest<Trip> request) {
        final ParseObject parseObject = new ParseObject(TRIPS);
        final Trip trip = request.getRequestData();
        parseObject.setObjectId(trip.getSyncMetadata().getId().getId());
        parseObject.deleteInBackground(new DeleteCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    final SyncableBuilderFactory syncableBuilderFactory = new SyncableBuilderFactory();
                    syncableBuilderFactory.setIdentifier(parseObject.getObjectId());
                    syncableBuilderFactory.setSource(SyncSource.Parse);
                    syncableBuilderFactory.setStatus(SyncStatus.Deleted);
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
                    final SyncError<Trip> syncError = new TripSyncError(SyncErrorTypesEnum.Unknown, request);
                    notifySyncError(request.getSyncRequestType(), syncError);
                }
            }
        });
    }

}
