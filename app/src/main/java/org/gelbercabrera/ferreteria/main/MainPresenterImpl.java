package org.gelbercabrera.ferreteria.main;

import org.gelbercabrera.ferreteria.entities.User;
import org.gelbercabrera.ferreteria.main.ui.MainActivity;

public class MainPresenterImpl implements MainPresenter {
    private MainActivity view;
    private MainSessionInteractor mainInteractor;

    public MainPresenterImpl(MainActivity view) {
        this.view = view;
        this.mainInteractor = new MainSessionInteractorImpl();
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void onPause() {
        mainInteractor.changeConnectionStatus(User.OFFLINE);
    }

    @Override
    public void onResume() {
        mainInteractor.changeConnectionStatus(User.ONLINE);
    }

    @Override
    public void signOff() {
        mainInteractor.changeConnectionStatus(User.OFFLINE);
        mainInteractor.signOff();
    }

    @Override
    public String getCurrentUserEmail() {
        return mainInteractor.getCurrentUserEmail();
    }

}
