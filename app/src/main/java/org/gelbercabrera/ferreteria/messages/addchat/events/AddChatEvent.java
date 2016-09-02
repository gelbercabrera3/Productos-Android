package org.gelbercabrera.ferreteria.messages.addchat.events;

public class AddChatEvent {
    boolean error = false;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
