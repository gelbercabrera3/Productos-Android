package org.gelbercabrera.ferreteria.main;

import org.gelbercabrera.ferreteria.lib.domain.FirebaseHelper;

public class MainRepositoryImpl implements MainRepository {
    private FirebaseHelper firebaseHelper;

    public MainRepositoryImpl() {
        this.firebaseHelper = FirebaseHelper.getInstance();
    }

    @Override
    public void signOff() {
        firebaseHelper.signOff();
    }

    @Override
    public String getCurrentEmail() {
        return firebaseHelper.getAuthUserEmail();
    }

    @Override
    public void changeUserConnectionStatus(boolean online) {

    }
}
