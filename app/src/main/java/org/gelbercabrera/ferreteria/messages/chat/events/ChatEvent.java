package org.gelbercabrera.ferreteria.messages.chat.events;


import org.gelbercabrera.ferreteria.entities.ChatMessage;

public class ChatEvent {
    ChatMessage message;

    public ChatMessage getMessage() {
        return message;
    }

    public void setMessage(ChatMessage message) {
        this.message = message;
    }
}
