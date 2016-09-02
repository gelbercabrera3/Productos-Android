package org.gelbercabrera.ferreteria.posts.postlist;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.gelbercabrera.ferreteria.entities.Post;
import org.gelbercabrera.ferreteria.lib.EventBus;
import org.gelbercabrera.ferreteria.lib.GreenRobotEventBus;
import org.gelbercabrera.ferreteria.lib.domain.FirebaseHelper;
import org.gelbercabrera.ferreteria.posts.postlist.events.PostsListEvent;

public class PostRepoImpl implements PostsRepo {
    private FirebaseHelper helper;
    private ChildEventListener listener;
    private ValueEventListener likeListener;
    private ChildEventListener friendsListener;
    private EventBus eventBus;
    private final static String DATE = "date";

    public PostRepoImpl() {
        this.eventBus = GreenRobotEventBus.getInstance();
        this.helper = FirebaseHelper.getInstance();
    }

    @Override
    public void destroyListener() {
        listener = null;
    }

    @Override
    public void subscribe() {
        listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                handlePost(dataSnapshot, PostsListEvent.onPostAdded);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                handlePost(dataSnapshot, PostsListEvent.onPostChanged);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                handlePost(dataSnapshot, PostsListEvent.onPostRemoved);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };
        helper.getUsersReference().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               try {
                   helper.getUserPostRefernce(dataSnapshot.getKey()).addChildEventListener(listener);
               }catch (NullPointerException nu){}
           }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        helper.getMyUserReference().child("posts").addChildEventListener(listener);
    }

    @Override
    public void unSubscribe() {

    }

    private void handlePost(DataSnapshot dataSnapshot,final int type){
        final Post post = dataSnapshot.getValue(Post.class);
        postEvent(type, post);

    }

    private void postEvent(int type, Post post) {
        PostsListEvent postListEvent = new PostsListEvent();
        postListEvent.setEventType(type);
        postListEvent.setPost(post);
        eventBus.post(postListEvent);
    }
}
