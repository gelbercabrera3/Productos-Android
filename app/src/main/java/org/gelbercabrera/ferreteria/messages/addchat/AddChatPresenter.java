package org.gelbercabrera.ferreteria.messages.addchat;


import org.gelbercabrera.ferreteria.messages.addchat.events.AddChatEvent;

public interface AddChatPresenter {
    void onShow();
    void onDestroy();

    void addContact(String email);
    void onEventMainThread(AddChatEvent event);
}
