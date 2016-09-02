package org.gelbercabrera.ferreteria.addpost.events;

public class AddPostEvent {
    boolean error = false;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
            this.error = error;
        }
}
