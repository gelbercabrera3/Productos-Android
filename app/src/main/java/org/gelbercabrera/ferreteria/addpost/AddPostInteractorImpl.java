package org.gelbercabrera.ferreteria.addpost;


import org.gelbercabrera.ferreteria.entities.Post;

public class AddPostInteractorImpl implements AddPostInteractor {
    AddPostRepo repo;

    public AddPostInteractorImpl() {
        this.repo = new AddPostRepoImpl();
    }

    @Override
    public void execute(Post post) {
        repo.addPost(post);
    }
}
