package org.gelbercabrera.ferreteria.login;

import org.gelbercabrera.ferreteria.lib.GreenRobotEventBus;
import org.gelbercabrera.ferreteria.login.events.LoginEvent;
import org.gelbercabrera.ferreteria.login.ui.LoginView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class LoginPresenterImpl implements  LoginPresenter{
    private GreenRobotEventBus eventBus;
    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        this.loginInteractor = new LoginInteractorImpl();
        this.eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void onCreate() {


    }

    @Override
    public void onDestroy() {
        loginView= null;
        eventBus.unregister(this);
    }

    @Override
    public void onPause() {
        eventBus.unregister(this);
    }

    @Override
    public void onResume() {
        eventBus.register(this);
    }

    @Override
    public void checkForAuthenticatedUser() {
        if (loginView != null) {
            loginView.disableInputs();
            loginView.showProgress();
        }

        loginInteractor.checkSession();
    }

    @Override
    public void validateLogin(String email, String password) {
        if (loginView != null) {
            loginView.disableInputs();
            loginView.showProgress();
        }
        loginInteractor.doSignIn(email, password);
    }

    @Override
    public void registerNewUser(String name, String email, String password) {
        if (loginView != null) {
            loginView.disableInputs();
            loginView.showProgress();
        }
        loginInteractor.doSignUp(name, email, password);
    }

    @Override
    @Subscribe
    public void onEventMainThread(LoginEvent event) {
        switch (event.getEventType()) {
            case LoginEvent.onSignInSuccess:
                onSigninSuccess();
                break;
            case LoginEvent.onSignUpSuccess:
                onSignupSuccess();
                break;
            case LoginEvent.onSignInError:
                onSigninError(event.getErrorMesage());
                break;
            case LoginEvent.onSignUpError:
                onSignupError(event.getErrorMesage());
            case LoginEvent.onFailedToRecoverSession:
                onFailedToRecoverSession();
                break;
        }
    }

    private void onFailedToRecoverSession() {
        if (loginView != null) {
            loginView.hideProgress();
            loginView.enableInputs();
        }

    }

    private void onSigninSuccess(){
        if (loginView != null) {
            loginView.navigateToMainScreen();
        }
    }

    private void onSignupSuccess(){
        if (loginView != null) {
            loginView.newUserSuccess();
        }
    }

    private void onSigninError(String error){
        if (loginView != null) {
            loginView.hideProgress();
            loginView.enableInputs();
            loginView.loginError(error);
        }
    }

    private void onSignupError(String error){
        if (loginView != null) {
            loginView.hideProgress();
            loginView.enableInputs();
            loginView.newUserError(error);
        }
    }

}
