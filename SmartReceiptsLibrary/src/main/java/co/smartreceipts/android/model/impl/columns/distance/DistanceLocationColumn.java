package co.smartreceipts.android.model.impl.columns.distance;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import co.smartreceipts.android.R;
import co.smartreceipts.android.model.Distance;
import co.smartreceipts.android.model.impl.columns.AbstractColumnImpl;

public final class DistanceLocationColumn extends AbstractColumnImpl<Distance> {

    private final Context mContext;

    public DistanceLocationColumn(int id, @NonNull String name, @NonNull Context context) {
        super(id, name);
        mContext = context;
    }

    @Override
    public String getValue(@NonNull Distance distance) {
        return distance.getLocation();
    }

    @Override
    public String getFooter(@NonNull List<Distance> distances) {
        return mContext.getString(R.string.total);
    }
}
