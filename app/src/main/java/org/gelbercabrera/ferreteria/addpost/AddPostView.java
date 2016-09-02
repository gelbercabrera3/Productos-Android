package org.gelbercabrera.ferreteria.addpost;

public interface AddPostView {
    void showInput();
    void hideInput();
    void showProgress();
    void hideProgress();

    void postAdded();
}
