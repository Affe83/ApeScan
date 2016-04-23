package co.smartreceipts.android.cognito;

import android.support.annotation.NonNull;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * An interface that we can use in conjunction with network requests
 */
public interface LoginService {

    @POST("users/sign_in")
    Call<UserIdResponse> logIn(@NonNull @Body LoginPayload loginPayload);
}
