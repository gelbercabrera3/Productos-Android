package org.gelbercabrera.ferreteria.messages.chat;

public interface ChatInteractor {
    void sendMessage(String msg);
    void setRecipient(String recipient);

    void subscribe();
    void unSubscribe();
    void destroyListener();
}
