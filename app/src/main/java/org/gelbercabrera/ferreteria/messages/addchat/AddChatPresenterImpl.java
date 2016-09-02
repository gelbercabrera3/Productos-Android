package org.gelbercabrera.ferreteria.messages.addchat;

import org.gelbercabrera.ferreteria.lib.EventBus;
import org.gelbercabrera.ferreteria.lib.GreenRobotEventBus;
import org.gelbercabrera.ferreteria.messages.addchat.events.AddChatEvent;
import org.gelbercabrera.ferreteria.messages.addchat.ui.AddChatView;
import org.greenrobot.eventbus.Subscribe;

public class AddChatPresenterImpl implements AddChatPresenter {
    private AddChatView view;
    private EventBus eventBus;
    private AddChatInteractor interactor;

    public AddChatPresenterImpl(AddChatView view) {
        this.view = view;
        this.eventBus = GreenRobotEventBus.getInstance();
        this.interactor = new AddChatInteractorImpl();
    }

    @Override
    public void onShow() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        view =null;
        eventBus.unregister(this);
    }

    @Override
    public void addContact(String email) {
        if (view != null) {
        }
        interactor.execute(email);
    }

    @Override
    @Subscribe
    public void onEventMainThread(AddChatEvent event) {
        if (view != null) {

            if (event.isError()) {
                view.contactAdded();
            } else {
                view.contactAdded();
            }
        }
    }
}
