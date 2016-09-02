package org.gelbercabrera.ferreteria.posts.postlist.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.gelbercabrera.ferreteria.R;
import org.gelbercabrera.ferreteria.entities.Post;
import org.gelbercabrera.ferreteria.lib.GlideImageLoader;
import org.gelbercabrera.ferreteria.lib.ImageLoader;
import org.gelbercabrera.ferreteria.lib.domain.AvatarHelper;
import org.gelbercabrera.ferreteria.lib.domain.FirebaseHelper;
import org.gelbercabrera.ferreteria.addpost.AddPostActivity;
import org.gelbercabrera.ferreteria.messages.addchat.AddChatPresenter;
import org.gelbercabrera.ferreteria.messages.addchat.AddChatPresenterImpl;
import org.gelbercabrera.ferreteria.messages.addchat.ui.AddChatView;
import org.gelbercabrera.ferreteria.messages.chat.ui.ChatActivity;
import org.gelbercabrera.ferreteria.posts.postlist.PostsPresenter;
import org.gelbercabrera.ferreteria.posts.postlist.PostsPresenterImpl;
import org.gelbercabrera.ferreteria.posts.postlist.ui.adapters.OnItemClickListener;
import org.gelbercabrera.ferreteria.posts.postlist.ui.adapters.PostsAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class PostsFragment extends Fragment implements PostsView, OnItemClickListener, AddChatView {
    @Bind(R.id.recyclerPosts)
    RecyclerView recyclerView;
    @Bind(R.id.imgAvatar)
    CircleImageView imgAvatar;

    private PostsPresenter presenter;
    private AddChatPresenter addMessagePresenter;
    private PostsAdapter adapter;
    ImageLoader loader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        ButterKnife.bind(this, view);

        loader = new GlideImageLoader(this.getActivity().getApplicationContext());
        loader.load(imgAvatar, AvatarHelper.getAvatarUrl(FirebaseHelper.getInstance().getAuthUserEmail()));

        presenter = new PostsPresenterImpl(this);
        addMessagePresenter = new AddChatPresenterImpl(this);
        presenter.onCreate();

        setupAdapter();
        setupRecyclerView();
        return view;
    }

    private void setupAdapter() {
        adapter = new PostsAdapter(new ArrayList<Post>(), loader, this, this.getActivity());
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onPause() {
        presenter.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void addPost(Post post) {
        adapter.add(post);
    }

    @Override
    public void removePost(Post post) {
        adapter.remove(post);
    }

    @Override
    public void updatePost(Post post){
        adapter.update(post);
    }

    @OnClick(R.id.cardNewPost)
    @Override
    public void makePost() {
        startActivity(new Intent(this.getActivity(), AddPostActivity.class));
    }

    @Override
    public void onProfileClick(Post post) {

    }

    @Override
    public void onAddMessage(String email){
        addMessagePresenter.addContact(email);
        Intent intent = new Intent(this.getActivity(), ChatActivity.class);
        intent.putExtra(ChatActivity.EMAIL_KEY, email);
        intent.putExtra(ChatActivity.ONLINE_KEY, false);
        startActivity(intent);
    }

    @Override
    public void contactAdded() {
        Toast.makeText(getActivity(), R.string.addcontact_message_contactadded, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void contactNotAdded() {
        Toast.makeText(getActivity(), "Imposible contactar con el vendedor", Toast.LENGTH_SHORT).show();
    }
}
