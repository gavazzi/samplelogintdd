package nz.co.zzi.samplelogintdd.infrastructure.data.services;

import nz.co.zzi.samplelogintdd.infrastructure.data.models.LoginResponse;
import retrofit.Callback;

/**
 * Created by joao.gavazzi on 9/10/15.
 */
public interface AuthenticationService {
    void authenticate(final String username, final String password, final Callback<LoginResponse> callback);
}
