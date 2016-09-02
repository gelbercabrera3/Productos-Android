package org.gelbercabrera.ferreteria.messages.chatlist;

import org.gelbercabrera.ferreteria.entities.User;
import org.gelbercabrera.ferreteria.lib.EventBus;
import org.gelbercabrera.ferreteria.lib.GreenRobotEventBus;
import org.gelbercabrera.ferreteria.messages.chatlist.events.ChatListEvent;
import org.gelbercabrera.ferreteria.messages.chatlist.ui.ChatListView;
import org.greenrobot.eventbus.Subscribe;

public class ChatListPresenterImpl implements ChatListPresenter {
    private EventBus eventBus;
    private ChatListView view;
    private ChatListInteractor interactor;

    public ChatListPresenterImpl(ChatListView view) {
        this.view = view;
        this.eventBus = GreenRobotEventBus.getInstance();
        this.interactor = new ChatListInteractorImpl();
    }

    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        interactor.destroyListener();
        view = null;
    }

    @Override
    public void onPause() {
        interactor.unsubscribe();
    }

    @Override
    public void onResume() {
        interactor.subscribe();
    }

    @Override
    public void removeChat(String email) {
        interactor.removeChat(email);
    }

    @Override
    @Subscribe
    public void onEventMainThread(ChatListEvent event) {
        User user = event.getUser();
        switch (event.getEventType()) {
            case ChatListEvent.onChatAdded:
                onChatAdded(user);
                break;
            case ChatListEvent.onContactChanged:
                onContactChanged(user);
                break;
            case ChatListEvent.onChatRemoved:
                onChatRemoved(user);
                break;
        }
    }

    public void onChatAdded(User user) {
        if (view != null) {
            view.onChatAdded(user);
        }
    }

    public void onContactChanged(User user) {
        if (view != null) {
            view.onContactChanged(user);
        }
    }

    public void onChatRemoved(User user) {
        if (view != null) {
            view.onChatRemoved(user);
        }
    }
}
