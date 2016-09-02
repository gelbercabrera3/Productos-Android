package org.gelbercabrera.ferreteria.addpost;

import org.gelbercabrera.ferreteria.entities.Post;
import org.gelbercabrera.ferreteria.lib.EventBus;
import org.gelbercabrera.ferreteria.lib.GreenRobotEventBus;
import org.gelbercabrera.ferreteria.addpost.events.AddPostEvent;
import org.greenrobot.eventbus.Subscribe;

public class AddPostPresenterImpl implements AddPostPresenter {
    private AddPostView view;
    private EventBus eventBus;
    private AddPostInteractor interactor;

    public AddPostPresenterImpl(AddPostView view) {
        this.view = view;
        this.eventBus = GreenRobotEventBus.getInstance();
        this.interactor = new AddPostInteractorImpl();
    }

    @Override
    public void addPost(Post post) {
        if (view != null) {
            view.hideInput();
            view.showProgress();
        }
        interactor.execute(post);
    }

    @Override
    public void onShow() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        view = null;
        eventBus.unregister(this);
    }

    @Override
    @Subscribe
    public void onEventMainThread(AddPostEvent event) {
        if (view != null) {
            view.hideProgress();
            view.showInput();

            if (event.isError()) {
                view.postAdded();
            } else {
                view.postAdded();
            }
        }
    }
}
