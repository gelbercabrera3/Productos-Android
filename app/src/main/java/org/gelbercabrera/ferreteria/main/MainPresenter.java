package org.gelbercabrera.ferreteria.main;

public interface MainPresenter {
    void onDestroy();
    void onPause();
    void onResume();
    void signOff();
    String getCurrentUserEmail();
}
