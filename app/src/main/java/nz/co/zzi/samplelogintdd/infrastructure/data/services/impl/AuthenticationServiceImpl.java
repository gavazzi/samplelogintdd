package nz.co.zzi.samplelogintdd.infrastructure.data.services.impl;

import nz.co.zzi.samplelogintdd.infrastructure.data.models.LoginRequest;
import nz.co.zzi.samplelogintdd.infrastructure.data.models.LoginResponse;
import nz.co.zzi.samplelogintdd.infrastructure.data.services.AuthenticationService;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by joao.gavazzi on 9/10/15.
 */
public class AuthenticationServiceImpl implements AuthenticationService {

    //It would be a retrofit API
    public interface AuthenticationAPI {
        @POST(value = "/address/myservice")
        void authenticate(@Body LoginRequest request, Callback<LoginResponse> callback);
    }

    private AuthenticationAPI mAuthenticationAPI;

    public AuthenticationServiceImpl(final AuthenticationAPI AuthenticationAPI) {
        mAuthenticationAPI = AuthenticationAPI;
    }

    @Override
    public void authenticate(final String username, final String password, final Callback<LoginResponse> callback) {
        final LoginRequest loginRequest = new LoginRequest();
        loginRequest.username = username;
        loginRequest.password = password;

        mAuthenticationAPI.authenticate(loginRequest, callback);
    }
}
