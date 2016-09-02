package org.gelbercabrera.ferreteria.posts.postlist;


import org.gelbercabrera.ferreteria.entities.Post;

public class PostsInteractorImpl implements PostsInteractor {
    private PostsRepo repo;

    public PostsInteractorImpl() {
        this.repo = new PostRepoImpl();
    }

    @Override
    public void subscribe() {
        repo.subscribe();
    }

    @Override
    public void unsubscribe() {
        repo.unSubscribe();
    }

    @Override
    public void destroyListener() {
        repo.destroyListener();
    }

}
