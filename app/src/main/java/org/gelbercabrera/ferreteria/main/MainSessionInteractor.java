package org.gelbercabrera.ferreteria.main;

public interface MainSessionInteractor {
    void changeConnectionStatus(boolean offline);

    void signOff();

    String getCurrentUserEmail();
}
