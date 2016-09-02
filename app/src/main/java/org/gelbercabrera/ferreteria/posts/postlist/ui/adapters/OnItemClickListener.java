package org.gelbercabrera.ferreteria.posts.postlist.ui.adapters;


import org.gelbercabrera.ferreteria.entities.Post;

public interface OnItemClickListener {
    void onProfileClick(Post post);
    void onAddMessage(String email);
}
