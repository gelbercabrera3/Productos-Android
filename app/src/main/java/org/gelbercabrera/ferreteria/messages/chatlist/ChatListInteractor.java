package org.gelbercabrera.ferreteria.messages.chatlist;

public interface ChatListInteractor {
    void subscribe();
    void unsubscribe();
    void destroyListener();
    void removeChat(String email);
}
