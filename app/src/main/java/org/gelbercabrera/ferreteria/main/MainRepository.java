package org.gelbercabrera.ferreteria.main;

public interface MainRepository {
    void signOff();
    String getCurrentEmail();
    void changeUserConnectionStatus(boolean online);
}
