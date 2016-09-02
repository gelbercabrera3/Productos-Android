package org.gelbercabrera.ferreteria.login;

import org.gelbercabrera.ferreteria.login.events.LoginEvent;

public interface LoginPresenter {
    void onCreate();
    void onDestroy();
    void onPause();
    void onResume();
    void checkForAuthenticatedUser();
    void validateLogin(String email, String password);
    void onEventMainThread(LoginEvent event);
    void registerNewUser(String name, String email, String password);
}
