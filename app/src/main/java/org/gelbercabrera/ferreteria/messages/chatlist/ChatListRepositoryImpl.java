package org.gelbercabrera.ferreteria.messages.chatlist;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.gelbercabrera.ferreteria.entities.User;
import org.gelbercabrera.ferreteria.lib.EventBus;
import org.gelbercabrera.ferreteria.lib.GreenRobotEventBus;
import org.gelbercabrera.ferreteria.lib.domain.FirebaseHelper;
import org.gelbercabrera.ferreteria.messages.chatlist.events.ChatListEvent;

public class ChatListRepositoryImpl implements ChatListRepository {
    private FirebaseHelper firebaseHelper;
    private ChildEventListener contactEventListener;
    private EventBus eventBus;

    public ChatListRepositoryImpl() {
        this.firebaseHelper = FirebaseHelper.getInstance();
        this.eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void removeChat(String email) {
        String currentUserEmail = firebaseHelper.getAuthUserEmail();
        firebaseHelper.getOneContactReference(currentUserEmail, email).removeValue();
        firebaseHelper.getOneContactReference(email, currentUserEmail).removeValue();
    }

    @Override
    public void destroyChatListListener() {
        contactEventListener = null;
    }

    @Override
    public void subscribeForChatListUpdates() {
        if (contactEventListener == null) {
            contactEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String previousChildKey) {
                    handleChat(dataSnapshot, ChatListEvent.onChatAdded);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String previousChildKey) {
                    handleChat(dataSnapshot, ChatListEvent.onContactChanged);
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    handleChat(dataSnapshot, ChatListEvent.onChatRemoved);
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

                @Override
                public void onCancelled(FirebaseError firebaseError) {}
            };
        }
        firebaseHelper.getMyContactsReference().addChildEventListener(contactEventListener);
    }

    @Override
    public void unSubscribeForChatListUpdates() {

    }

    private void handleChat(DataSnapshot dataSnapshot,final int type) {
        String email = dataSnapshot.getKey();
        email = email.replace("_",".");
        boolean online = ((Boolean)dataSnapshot.getValue()).booleanValue();
        final User user = new User("Name",email, online, null);
        FirebaseHelper.getInstance().getUserReference(user.getEmail()).child("name").
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        user.setName(dataSnapshot.getValue().toString());
                        postEvent(type, user);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
    }

    private void postEvent(int type, User user) {
        ChatListEvent chatListEvent = new ChatListEvent();
        chatListEvent.setEventType(type);
        chatListEvent.setUser(user);
        eventBus.post(chatListEvent);
    }
}
