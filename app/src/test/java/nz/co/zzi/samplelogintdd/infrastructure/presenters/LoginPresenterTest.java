package nz.co.zzi.samplelogintdd.infrastructure.presenters;

import android.content.Context;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import nz.co.zzi.samplelogintdd.R;
import nz.co.zzi.samplelogintdd.infrastructure.data.models.LoginRequest;
import nz.co.zzi.samplelogintdd.infrastructure.data.models.LoginResponse;
import nz.co.zzi.samplelogintdd.infrastructure.data.services.AuthenticationService;
import nz.co.zzi.samplelogintdd.infrastructure.data.services.impl.AuthenticationServiceImpl;
import retrofit.Callback;

import static nz.co.zzi.samplelogintdd.infrastructure.data.services.impl.AuthenticationServiceImpl.AuthenticationAPI;
import static nz.co.zzi.samplelogintdd.infrastructure.presenters.LoginPresenter.Listener;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by joao.gavazzi on 9/10/15.
 */
public class LoginPresenterTest {

    @Rule
    public ExpectedException mExpectedException = ExpectedException.none();

    private LoginPresenter mPresenter;
    private Listener mMockListener;
    private Context mMockContext;

    //this is a boundary in our test environment,
    //we don't care about the server we just care
    //about interpret right the response it gives
    private AuthenticationAPI mMockAuthenticationAPI;

    @Before
    public void setUp() throws Exception {
        mMockListener = mock(Listener.class);
        mMockContext = mock(Context.class);
        mMockAuthenticationAPI = mock(AuthenticationAPI.class);

        final AuthenticationService authenticationService =
                new AuthenticationServiceImpl(mMockAuthenticationAPI);

        mPresenter = new LoginPresenter(mMockContext, authenticationService);
        mPresenter.setListener(mMockListener);
    }

    @Test
    public void testNullPointerIsThrown_whenNullListenerIsGiven() {
        mExpectedException.expect(NullPointerException.class);

        mPresenter.setListener(null);
    }

    @Test
    public void testLoadingIsShown_whenLoginButtonIsClicked() {
        // # Arrange #
        mPresenter.onUsernameEntered("jgavazzisp");
        mPresenter.onPasswordEntered("jgavazzisp");

        // # Act #
        mPresenter.onLoginButtonClicked();

        // # Assert #
        verify(mMockListener, times(1)).showLoading();
    }

    @Test
    public void testErrorIsShown_whenUsernameIsEmpty_andLoginButtonIsClicked() {
        // # Act #
        mPresenter.onLoginButtonClicked();

        // # Assert #
        verify(mMockListener, times(1)).showUsernameError();
        verify(mMockListener, never()).showLoading();
    }

    @Test
    public void testErrorIsShown_whenUsernameIsGivenPasswordIsEmpty_andLoginButtonIsClicked() {
        // # Arrange #
        mPresenter.onUsernameEntered("jgavazzisp");

        // # Act #
        mPresenter.onLoginButtonClicked();

        // # Assert #
        verify(mMockListener, times(1)).showPasswordError();
        verify(mMockListener, never()).showLoading();
    }

    @Test
    public void testErrorIsShown_whenAuthenticationFails() {
        // # Arrange #
        mockAuthenticationResponse(null);

        when(mMockContext.getString(R.string.authentication_fail_message)).thenReturn("Invalid username / password");

        mPresenter.onUsernameEntered("jgavazzisp");
        mPresenter.onPasswordEntered("1234");

        // # Act #
        mPresenter.onLoginButtonClicked();

        // # Assert #
        verify(mMockListener, times(1)).showAuthenticationError("Invalid username / password");
    }

    @Test
    public void testMainActivityIsShown_whenAuthenticationSucceed() {
        // # Arrange #
        final LoginResponse loginResponse = new LoginResponse();
        loginResponse.success = true;
        mockAuthenticationResponse(loginResponse);

        mPresenter.onUsernameEntered("jgavazzisp");
        mPresenter.onPasswordEntered("1234");

        // # Act #
        mPresenter.onLoginButtonClicked();

        // # Assert #
        verify(mMockListener, times(1)).showMainActivity();
    }

    @SuppressWarnings("unchecked")
    private void mockAuthenticationResponse(final LoginResponse loginResponse) {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                //get the callback given and mock up a response from server
                final Callback<LoginResponse> callback = (Callback) invocation.getArguments()[1];

                if (loginResponse != null) {
                    //authentication responded (can be success or fail)
                    callback.success(loginResponse, null);
                } else {
                    //network fails
                    callback.failure(null);
                }

                return null;
            }
        }).when(mMockAuthenticationAPI).authenticate(any(LoginRequest.class), any(Callback.class));
    }
}