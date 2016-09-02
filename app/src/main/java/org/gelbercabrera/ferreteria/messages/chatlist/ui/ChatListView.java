package org.gelbercabrera.ferreteria.messages.chatlist.ui;

import org.gelbercabrera.ferreteria.entities.User;

public interface ChatListView {
    void onChatAdded(User user);
    void onContactChanged(User user);
    void onChatRemoved(User user);
}
