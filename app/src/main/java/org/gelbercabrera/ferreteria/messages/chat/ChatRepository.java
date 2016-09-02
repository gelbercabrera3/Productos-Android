package org.gelbercabrera.ferreteria.messages.chat;

public interface ChatRepository {
    void sendMessage(String msg);
    void setReceiver(String receiver);

    void destroyListener();
    void subscribe();
    void unSubscribe();

    void changeUserConnectionStatus(boolean online);
}
