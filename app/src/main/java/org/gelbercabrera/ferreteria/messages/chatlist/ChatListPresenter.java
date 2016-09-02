package org.gelbercabrera.ferreteria.messages.chatlist;


import org.gelbercabrera.ferreteria.messages.chatlist.events.ChatListEvent;

public interface ChatListPresenter {
    void onCreate();
    void onDestroy();
    void onResume();
    void onPause();

    void removeChat(String email);
    void onEventMainThread(ChatListEvent event);
}
