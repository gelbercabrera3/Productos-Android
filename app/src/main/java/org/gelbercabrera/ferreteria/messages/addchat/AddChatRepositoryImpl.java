package org.gelbercabrera.ferreteria.messages.addchat;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.gelbercabrera.ferreteria.entities.User;
import org.gelbercabrera.ferreteria.lib.EventBus;
import org.gelbercabrera.ferreteria.lib.GreenRobotEventBus;
import org.gelbercabrera.ferreteria.lib.domain.FirebaseHelper;
import org.gelbercabrera.ferreteria.messages.addchat.events.AddChatEvent;

public class AddChatRepositoryImpl implements AddChatRepository {
    private EventBus eventBus;
    private FirebaseHelper helper;

    public AddChatRepositoryImpl() {
        this.eventBus = GreenRobotEventBus.getInstance();
        this.helper = FirebaseHelper.getInstance();
    }

    @Override
    public void addContact(final String email) {
        final String key = email.replace(".","_");
        Firebase userReference = helper.getUserReference(email);
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = new User(dataSnapshot.child("name").getValue().toString(),
                        dataSnapshot.getKey(),
                        (Boolean)dataSnapshot.child("online").getValue(),null);
                if (user != null) {
                    Firebase myContactReference = helper.getMyContactsReference();
                    myContactReference.child(key).setValue(user.isOnline());

                    String currentUserEmailKey = helper.getAuthUserEmail();
                    currentUserEmailKey = currentUserEmailKey.replace(".","_");

                    Firebase reverseContactReference = helper.getContactsReference(email);
                    reverseContactReference.child(currentUserEmailKey).setValue(User.ONLINE);

                    postSuccess();
                } else {
                    postError();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                postError();
            }
        });
    }

    private void postError() {
        post(true);
    }

    private void postSuccess() {
        post(false);
    }

    private void post(boolean error) {
        AddChatEvent event = new AddChatEvent();
        event.setError(error);
        eventBus.post(event);
    }
}
