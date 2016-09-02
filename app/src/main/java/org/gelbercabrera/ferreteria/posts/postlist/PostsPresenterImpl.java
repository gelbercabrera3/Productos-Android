package org.gelbercabrera.ferreteria.posts.postlist;

import org.gelbercabrera.ferreteria.entities.Post;
import org.gelbercabrera.ferreteria.lib.EventBus;
import org.gelbercabrera.ferreteria.lib.GreenRobotEventBus;
import org.gelbercabrera.ferreteria.posts.postlist.events.PostsListEvent;
import org.gelbercabrera.ferreteria.posts.postlist.ui.PostsView;
import org.greenrobot.eventbus.Subscribe;

public class PostsPresenterImpl implements  PostsPresenter{
    private EventBus eventBus;
    private PostsView view;
    private PostsInteractor interactor;

    public PostsPresenterImpl(PostsView view) {
        this.eventBus = GreenRobotEventBus.getInstance();
        this.view = view;
        this.interactor = new PostsInteractorImpl();
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
    @Subscribe
    public void onEventMainThread(PostsListEvent event) {
        Post post = event.getPost();
        switch (event.getEventType()){
            case PostsListEvent.onPostAdded:
                onPostAdded(post);
                break;
            case PostsListEvent.onPostChanged:
                onPostChanged(post);
                break;
            case PostsListEvent.onPostRemoved:
                onPostRemoved(post);
                break;
        }
    }

    public void onPostAdded(Post post) {
        if (view != null) {
            view.addPost(post);
        }
    }

    public void onPostChanged(Post post) {
        if (view != null) {
            view.updatePost(post);
        }
    }

    public void onPostRemoved(Post post) {
        if (view != null) {
            view.removePost(post);
        }
    }
}
