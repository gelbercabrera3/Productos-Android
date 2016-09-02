package org.gelbercabrera.ferreteria.messages.chatlist;

public interface ChatListRepository {
    void removeChat(String email);
    void destroyChatListListener();
    void subscribeForChatListUpdates();
    void unSubscribeForChatListUpdates();
}
