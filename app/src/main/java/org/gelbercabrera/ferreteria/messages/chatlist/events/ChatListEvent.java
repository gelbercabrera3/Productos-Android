package org.gelbercabrera.ferreteria.messages.chatlist.events;


import org.gelbercabrera.ferreteria.entities.User;

public class ChatListEvent {
    private User user;
    private int eventType;

    public final static int onChatAdded = 0;
    public final static int onContactChanged = 1;
    public final static int onChatRemoved = 2;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }
}
