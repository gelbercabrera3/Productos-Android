package org.gelbercabrera.ferreteria.posts.postlist;


public interface PostsRepo {
    void destroyListener();
    void subscribe();
    void unSubscribe();
}
