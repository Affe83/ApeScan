package co.smartreceipts.android.model.impl;

import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.math.BigDecimal;
import java.util.Collections;

import co.smartreceipts.android.model.WBCurrency;
import co.smartreceipts.android.model.factory.ExchangeRateBuilderFactory;
import co.smartreceipts.android.model.gson.ExchangeRate;
import co.smartreceipts.android.model.utils.ModelUtils;

/**
 * In order to ensure that I'm keeping the same (albeit broken for multi-currencies) price behavior
 * for {@link co.smartreceipts.android.model.Trip} objects, I built this behavior. It should be
 * removed once we get real cross-currency conversions
 *
 * @author williambaumann
 */
public class LegacyTripPriceImpl extends AbstractPriceImpl {

    private final BigDecimal mPrice;
    private final WBCurrency mCurrency;
    private final ExchangeRate mExchangeRate;

    /**
     * Default constructor
     *
     * @param price the price as a {@link java.math.BigDecimal}
     * @param currency the {@link co.smartreceipts.android.model.WBCurrency}. If {@code null}, we assume it's mixed currencies
     */
    public LegacyTripPriceImpl(@NonNull BigDecimal price, @Nullable WBCurrency currency) {
        mPrice = price;
        mCurrency = currency;
        mExchangeRate = new ExchangeRateBuilderFactory().build();
    }

    private LegacyTripPriceImpl(@NonNull Parcel in) {
        mPrice = new BigDecimal(in.readFloat());
        final String currencyCode = in.readString();
        mCurrency = !TextUtils.isEmpty(currencyCode) ? WBCurrency.getInstance(currencyCode) : null;
        mExchangeRate = (ExchangeRate) in.readSerializable();
    }

    @Override
    public float getPriceAsFloat() {
        return mPrice.floatValue();
    }

    @Override
    @NonNull
    public BigDecimal getPrice() {
        return mPrice;
    }

    @Override
    @NonNull
    public String getDecimalFormattedPrice() {
        return ModelUtils.getDecimalFormattedValue(mPrice);
    }

    @Override
    @NonNull
    public String getCurrencyFormattedPrice() {
        if (mCurrency != null) {
            return ModelUtils.getCurrencyCodeFormattedValue(mPrice, mCurrency);
        } else {
            return "Mixed";
        }
    }

    @NonNull
    @Override
    public String getCurrencyCodeFormattedPrice() {
        if (mCurrency != null) {
            return ModelUtils.getCurrencyFormattedValue(mPrice, mCurrency);
        } else {
            return "Mixed";
        }
    }

    @Override
    @NonNull
    public WBCurrency getCurrency() {
        if (mCurrency != null) {
            return mCurrency;
        } else {
            return WBCurrency.MISSING_CURRENCY;
        }
    }

    @Override
    @NonNull
    public String getCurrencyCode() {
        if (mCurrency != null) {
            return mCurrency.getCurrencyCode();
        } else {
            return WBCurrency.MISSING_CURRENCY_CODE;
        }
    }

    @NonNull
    @Override
    public ExchangeRate getExchangeRate() {
        return mExchangeRate;
    }

    @Override
    public String toString() {
        return getCurrencyFormattedPrice();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(getPriceAsFloat());
        dest.writeString(getCurrencyCode());
        dest.writeSerializable(mExchangeRate);
    }

    public static final Creator<LegacyTripPriceImpl> CREATOR = new Creator<LegacyTripPriceImpl>() {
        public LegacyTripPriceImpl createFromParcel(Parcel source) {
            return new LegacyTripPriceImpl(source);
        }

        public LegacyTripPriceImpl[] newArray(int size) {
            return new LegacyTripPriceImpl[size];
        }
    };
}
