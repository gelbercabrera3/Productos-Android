package org.gelbercabrera.ferreteria.posts.postlist;


import org.gelbercabrera.ferreteria.entities.Post;

public interface PostsInteractor {
    void subscribe();
    void unsubscribe();
    void destroyListener();
}
