package co.smartreceipts.android.model.impl.columns.distance;

import android.support.annotation.NonNull;

import co.smartreceipts.android.model.Distance;
import co.smartreceipts.android.model.impl.columns.AbstractColumnImpl;

public final class DistanceCommentColumn extends AbstractColumnImpl<Distance> {

    public DistanceCommentColumn(int id, @NonNull String name) {
        super(id, name);
    }

    @Override
    public String getValue(@NonNull Distance distance) {
        return distance.getComment();
    }

}
