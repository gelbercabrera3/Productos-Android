package org.gelbercabrera.ferreteria.login;

public interface LoginRepository {
    void signUp(String name, String email, String password);
    void signIn(String email, String password);
    void checkSession();
}
