package co.smartreceipts.android.sync.id;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import co.smartreceipts.android.sync.id.impl.UniqueId;

/**
 * Operates as a unique identifier for synchronization data
 */
public interface Identifier extends Parcelable {

    /**
     * Defines a "default" identifier for items without one. This should be used in favor or {@code null}.
     */
    public static final Identifier NONE = new UniqueId("");

    /**
     * @return - the {@link java.lang.String} representation of this id
     */
    @NonNull
    String getId();
}
