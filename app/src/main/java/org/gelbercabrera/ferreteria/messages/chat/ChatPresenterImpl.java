package org.gelbercabrera.ferreteria.messages.chat;

import org.gelbercabrera.ferreteria.entities.User;
import org.gelbercabrera.ferreteria.lib.EventBus;
import org.gelbercabrera.ferreteria.lib.GreenRobotEventBus;
import org.gelbercabrera.ferreteria.messages.chat.events.ChatEvent;
import org.gelbercabrera.ferreteria.messages.chat.ui.ChatView;
import org.greenrobot.eventbus.Subscribe;

public class ChatPresenterImpl implements ChatPresenter {
    private EventBus eventBus;
    private ChatView chatView;
    private ChatInteractor chatInteractor;
    private ChatSessionInteractor chatSessionInteractor;

    public ChatPresenterImpl(ChatView view) {
        this.chatView = view;
        this.eventBus = GreenRobotEventBus.getInstance();

        this.chatInteractor = new ChatInteractorImpl();
        this.chatSessionInteractor = new ChatSessionInteractorImpl();
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onResume() {
        chatInteractor.subscribe();
        chatSessionInteractor.changeConnectionStatus(User.ONLINE);
    }

    @Override
    public void onPause() {
        chatInteractor.unSubscribe();
        chatSessionInteractor.changeConnectionStatus(User.OFFLINE);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        chatInteractor.destroyListener();
        chatView = null;
    }

    @Override
    public void setChatRecipient(String recipient) {
        this.chatInteractor.setRecipient(recipient);
    }

    @Override
    public void sendMessage(String msg) {
        chatInteractor.sendMessage(msg);
    }

    @Override
    @Subscribe
    public void onEventMainThread(ChatEvent event) {
        if (chatView != null) {
            chatView.onMessageReceived(event.getMessage());
        }
    }
}
