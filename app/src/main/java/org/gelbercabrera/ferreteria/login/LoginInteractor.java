package org.gelbercabrera.ferreteria.login;

public interface LoginInteractor {
    void checkSession();
    void doSignIn(String email, String password);
    void doSignUp(String name, String email, String password);
}
