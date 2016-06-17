package co.smartreceipts.android.persistence.database.tables.adapters;

import android.content.ContentValues;
import android.database.Cursor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.TimeZone;

import co.smartreceipts.android.model.Distance;
import co.smartreceipts.android.model.Price;
import co.smartreceipts.android.model.Trip;
import co.smartreceipts.android.model.WBCurrency;
import co.smartreceipts.android.model.factory.DistanceBuilderFactory;
import co.smartreceipts.android.persistence.database.tables.Table;
import co.smartreceipts.android.persistence.database.tables.keys.PrimaryKey;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(RobolectricGradleTestRunner.class)
public class DistanceDatabaseAdapterTest {

    private static final int ID = 5;
    private static final int PRIMARY_KEY_ID = 11;
    private static final String PARENT = "Trip";
    private static final double DISTANCE = 12.55d;
    private static final String LOCATION = "Location";
    private static final long DATE = 1409703721000L;
    private static final String TIMEZONE = TimeZone.getDefault().getID();
    private static final String COMMENT = "Comment";
    private static final double RATE = 0.33d;
    private static final String CURRENCY_CODE = "USD";


    // Class under test
    DistanceDatabaseAdapter mDistanceDatabaseAdapter;

    @Mock
    Table<Trip, String> mTripsTable;

    @Mock
    Trip mTrip;

    @Mock
    Cursor mCursor;

    @Mock
    Distance mDistance;

    @Mock
    Price mPrice;

    @Mock
    PrimaryKey<Distance, Integer> mPrimaryKey;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        final int idIndex = 1;
        final int parentIndex = 2;
        final int distanceIndex = 3;
        final int locationIndex = 4;
        final int dateIndex = 5;
        final int timezoneIndex = 6;
        final int commentIndex = 7;
        final int rateIndex = 8;
        final int rateCurrencyIndex = 9;
        when(mCursor.getColumnIndex("id")).thenReturn(idIndex);
        when(mCursor.getColumnIndex("parent")).thenReturn(parentIndex);
        when(mCursor.getColumnIndex("distance")).thenReturn(distanceIndex);
        when(mCursor.getColumnIndex("location")).thenReturn(locationIndex);
        when(mCursor.getColumnIndex("date")).thenReturn(dateIndex);
        when(mCursor.getColumnIndex("timezone")).thenReturn(timezoneIndex);
        when(mCursor.getColumnIndex("comment")).thenReturn(commentIndex);
        when(mCursor.getColumnIndex("rate")).thenReturn(rateIndex);
        when(mCursor.getColumnIndex("rate_currency")).thenReturn(rateCurrencyIndex);

        when(mCursor.getInt(idIndex)).thenReturn(ID);
        when(mCursor.getString(parentIndex)).thenReturn(PARENT);
        when(mCursor.getDouble(distanceIndex)).thenReturn(DISTANCE);
        when(mCursor.getString(locationIndex)).thenReturn(LOCATION);
        when(mCursor.getLong(dateIndex)).thenReturn(DATE);
        when(mCursor.getString(timezoneIndex)).thenReturn(TIMEZONE);
        when(mCursor.getString(commentIndex)).thenReturn(COMMENT);
        when(mCursor.getDouble(rateIndex)).thenReturn(RATE);
        when(mCursor.getString(rateCurrencyIndex)).thenReturn(CURRENCY_CODE);

        when(mDistance.getTrip()).thenReturn(mTrip);
        when(mDistance.getLocation()).thenReturn(LOCATION);
        when(mDistance.getDistance()).thenReturn(new BigDecimal(DISTANCE));
        when(mDistance.getTimeZone()).thenReturn(TimeZone.getTimeZone(TIMEZONE));
        when(mDistance.getDate()).thenReturn(new Date(DATE));
        when(mDistance.getRate()).thenReturn(new BigDecimal(RATE));
        when(mDistance.getPrice()).thenReturn(mPrice);
        when(mDistance.getComment()).thenReturn(COMMENT);

        when(mTrip.getName()).thenReturn(PARENT);
        when(mPrice.getCurrencyCode()).thenReturn(CURRENCY_CODE);
        when(mPrice.getCurrency()).thenReturn(WBCurrency.getInstance(CURRENCY_CODE));
        when(mTripsTable.findByPrimaryKey(PARENT)).thenReturn(mTrip);
        when(mPrimaryKey.getPrimaryKeyValue(mDistance)).thenReturn(PRIMARY_KEY_ID);

        mDistanceDatabaseAdapter = new DistanceDatabaseAdapter(mTripsTable);
    }

    @Test
    public void read() throws Exception {
        final Distance distance = new DistanceBuilderFactory(ID).setTrip(mTrip).setLocation(LOCATION).setDistance(DISTANCE).setDate(DATE).setTimezone(TIMEZONE).setRate(RATE).setCurrency(CURRENCY_CODE).setComment(COMMENT).build();
        assertEquals(distance, mDistanceDatabaseAdapter.read(mCursor));
    }

    @Test
    public void write() throws Exception {
        final ContentValues contentValues = mDistanceDatabaseAdapter.write(mDistance);
        assertEquals(PARENT, contentValues.getAsString("parent"));
        assertEquals(DISTANCE, contentValues.getAsDouble("distance"), 0.0001d);
        assertEquals(LOCATION, contentValues.getAsString("location"));
        assertEquals(DATE, (long) contentValues.getAsLong("date"));
        assertEquals(TIMEZONE, contentValues.getAsString("timezone"));
        assertEquals(COMMENT, contentValues.getAsString("comment"));
        assertEquals(RATE, contentValues.getAsDouble("rate"), 0.0001d);
        assertEquals(CURRENCY_CODE, contentValues.getAsString("rate_currency"));
        assertFalse(contentValues.containsKey("id"));
    }

    @Test
    public void build() throws Exception {
        final Distance distance = new DistanceBuilderFactory(PRIMARY_KEY_ID).setTrip(mTrip).setLocation(LOCATION).setDistance(DISTANCE).setDate(DATE).setTimezone(TIMEZONE).setRate(RATE).setCurrency(CURRENCY_CODE).setComment(COMMENT).build();
        assertEquals(distance, mDistanceDatabaseAdapter.build(mDistance, mPrimaryKey));
    }

}