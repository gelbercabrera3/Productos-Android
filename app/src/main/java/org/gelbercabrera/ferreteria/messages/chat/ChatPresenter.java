package org.gelbercabrera.ferreteria.messages.chat;


import org.gelbercabrera.ferreteria.messages.chat.events.ChatEvent;

public interface ChatPresenter {
    void onPause();
    void onResume();
    void onCreate();
    void onDestroy();

    void setChatRecipient(String recipient);

    void sendMessage(String msg);
    void onEventMainThread(ChatEvent event);
}
