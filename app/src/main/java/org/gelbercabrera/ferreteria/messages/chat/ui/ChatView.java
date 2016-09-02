package org.gelbercabrera.ferreteria.messages.chat.ui;

import org.gelbercabrera.ferreteria.entities.ChatMessage;

public interface ChatView {
    void sendMessage();
    void sendLocation();
    void onMessageReceived(ChatMessage msg);
}
