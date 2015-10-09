package nz.co.zzi.samplelogintdd.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import nz.co.zzi.samplelogintdd.R;
import nz.co.zzi.samplelogintdd.infrastructure.presenters.LoginPresenter;

/**
 * Created by joao.gavazzi on 9/10/15.
 */
public class LoginActivity extends AppCompatActivity implements LoginPresenter.Listener, View.OnClickListener {

    //TODO should be inject using dagger
    private LoginPresenter mLoginPresenter;

    private EditText mUsernameEditText;
    private EditText mPasswordEditText;

    private Button mLoginButton;
    private ProgressBar mProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLoginPresenter.setListener(this);

        //TODO init the class
    }

    @Override
    public void showLoading() {
        //disable things here
        mProgressBar.setVisibility(View.VISIBLE);
        mLoginButton.setVisibility(View.GONE);
    }

    @Override
    public void showUsernameError() {
        mUsernameEditText.setError("Username required");
    }

    @Override
    public void showPasswordError() {
        mPasswordEditText.setError("Password required");
    }

    @Override
    public void showAuthenticationError(String message) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(R.string.dialog_ok_button_text, null)
                .show();

        //restore the screen here
        mProgressBar.setVisibility(View.GONE);
        mLoginButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                handleLoginButtonClick();
                break;
        }
    }

    private void handleLoginButtonClick() {
        mLoginPresenter.onUsernameEntered(mUsernameEditText.getText().toString());
        mLoginPresenter.onPasswordEntered(mPasswordEditText.getText().toString());
        mLoginPresenter.onLoginButtonClicked();
    }
}
