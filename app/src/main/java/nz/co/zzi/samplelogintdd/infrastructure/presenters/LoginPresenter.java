package nz.co.zzi.samplelogintdd.infrastructure.presenters;

import android.content.Context;

import nz.co.zzi.samplelogintdd.R;
import nz.co.zzi.samplelogintdd.infrastructure.data.models.LoginResponse;
import nz.co.zzi.samplelogintdd.infrastructure.data.services.AuthenticationService;
import nz.co.zzi.samplelogintdd.infrastructure.utils.Utility;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by joao.gavazzi on 9/10/15.
 */
public class LoginPresenter {

    private Context mContext;
    private AuthenticationService mAuthenticationService;
    private Listener mListener;
    private String mUsername;
    private String mPassword;

    public LoginPresenter(final Context context, final AuthenticationService authenticationService) {
        mContext = context;
        mAuthenticationService = authenticationService;
    }

    public void setListener(final Listener listener) {
        mListener = Utility.requireNonNull(listener);
    }

    public void onUsernameEntered(final String username) {
        mUsername = username;
    }

    public void onPasswordEntered(final String password) {
        mPassword = password;
    }

    public void onLoginButtonClicked() {
        if(Utility.isEmpty(mUsername)) {
            mListener.showUsernameError();
        } else if(Utility.isEmpty(mPassword)) {
            mListener.showPasswordError();
        } else {
            mListener.showLoading();
            mAuthenticationService.authenticate(mUsername, mPassword, new Callback<LoginResponse>() {
                @Override
                public void success(LoginResponse loginResponse, Response response) {
                    mListener.showMainActivity();
                }

                @Override
                public void failure(RetrofitError error) {
                    mListener.showAuthenticationError(mContext.getString(R.string.authentication_fail_message));
                }
            });
        }
    }

    public interface Listener {
        void showLoading();
        void showUsernameError();
        void showPasswordError();
        void showAuthenticationError(final String message);
        void showMainActivity();
    }
}
