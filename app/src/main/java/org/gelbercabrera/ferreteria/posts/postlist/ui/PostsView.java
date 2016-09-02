package org.gelbercabrera.ferreteria.posts.postlist.ui;


import org.gelbercabrera.ferreteria.entities.Post;

public interface PostsView {
    void addPost(Post post);
    void removePost(Post post);

    void updatePost(Post post);

    void makePost();
}
