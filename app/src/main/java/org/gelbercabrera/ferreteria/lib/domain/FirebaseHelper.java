package org.gelbercabrera.ferreteria.lib.domain;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.gelbercabrera.ferreteria.entities.Post;
import org.gelbercabrera.ferreteria.entities.User;

import java.util.HashMap;
import java.util.Map;

public class FirebaseHelper {
    private Firebase dataReference;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://ferreteria-android.appspot.com");
    StorageReference imagesRef = storageRef.child("imagenes/");

    private final static String SEPARATOR = "___";
    private final static String CHATS_PATH = "chats";
    private final static String USERS_PATH = "users";
    private final static String CONTACTS_PATH = "contacts";
    private final static String POSTS_PATH = "posts";
    private final static String FRIENDS_PATH = "friends";
    private final static String FIREBASE_URL = "https://ferreteria-android.firebaseIO.com";

    private static class SingletonHolder {
        private static final FirebaseHelper INSTANCE = new FirebaseHelper();
    }

    public static FirebaseHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public FirebaseHelper() {
        this.dataReference = new Firebase(FIREBASE_URL);
    }

    public Firebase getDataReference() {
        return dataReference;
    }

    public String getAuthUserEmail() {
        AuthData authData = dataReference.getAuth();
        String email = null;
        if (authData != null) {
            Map<String, Object> providerData = authData.getProviderData();
            email = providerData.get("email").toString();
        }
        return email;
    }

    public Firebase getUserReference(String email) {
        Firebase userReference = null;
        if(email !=null ) {
            String emailKey = email.replace(".","_");
            userReference = getDataReference().getRoot().child(USERS_PATH).child(emailKey);
        }
        return userReference;
    }

    public Firebase getUsersReference(){
        return getDataReference().getRoot().child(USERS_PATH);
    }

    public Firebase getPostsReference(){
        return getDataReference().getRoot().child(POSTS_PATH);
    }

    public Firebase getMyUserReference () {
        return getUserReference(getAuthUserEmail());
    }

    public Firebase getMyContactsReference(){
        return getContactsReference(getAuthUserEmail());
    }

    public  Firebase getContactsReference(String email) {
        return getUserReference(email).child(CONTACTS_PATH);
    }

    public Firebase getOneContactReference(String mainEmail, String childEmail){
        String childKey = childEmail.replace(".","_");
        return getUserReference(mainEmail).child(CONTACTS_PATH).child(childKey);
    }

    public Firebase getChatsReference(String receiver) {
        String keySender = getAuthUserEmail().replace(".", "_");
        String keyReceiver = receiver.replace(".", "_");

        String keyChat = keySender + SEPARATOR + keyReceiver;
        if (keySender.compareTo(keyReceiver) > 0) {
            keyChat = keyReceiver + SEPARATOR + keySender;
        }
        return dataReference.getRoot().child(CHATS_PATH).child(keyChat);
    }

    public Firebase getMyFriendsReference(){
        return getFriendsReference(getAuthUserEmail());
    }

    public Firebase getFriendsReference(String email) {
        return getUserReference(email).child(FRIENDS_PATH);
    }

    public Firebase getOneFriendReference(String mainEmail, String childEmail){
        String childKey = childEmail.replace(".","_");
        return getUserReference(mainEmail).child(FRIENDS_PATH).child(childKey);
    }

    public void changeUserConnectionStatus(boolean online) {
        if (getMyUserReference() != null) {
            Map<String, Object> updates = new HashMap<String, Object>();
            updates.put("online", online);
            getMyUserReference().updateChildren(updates);

            notifyContactsOfConnectionChange(online);
        }
    }

    public void createUser(String email,String name, Boolean online){
        getUserReference(email).child("name").setValue(name);
        getUserReference(email).child("online").setValue(online);
    }

    public void notifyContactsOfConnectionChange(final boolean online, final boolean signoff) {
        final String myEmail = getAuthUserEmail();
        getMyContactsReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    String email = child.getKey();
                    Firebase reference = getOneContactReference(email, myEmail);
                    reference.setValue(online);
                }
                if (signoff){
                    dataReference.unauth();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });
    }


    public void notifyContactsOfConnectionChange(boolean online) {
        notifyContactsOfConnectionChange(online, false);
    }

    public void signOff(){
        notifyContactsOfConnectionChange(User.OFFLINE, true);
        changeUserConnectionStatus(User.OFFLINE);
    }

    public Firebase getUserPostRefernce(String email){
        return dataReference.child(USERS_PATH).child(email).child(POSTS_PATH);
    }

    public Firebase getOnePostReference(Post post){
        return dataReference.child(USERS_PATH).child(post.getEmail_poster()).child(POSTS_PATH).child(post.getDate().toString());
    }

    public FirebaseStorage getStorage() {
        return storage;
    }

    public StorageReference getStorageRef() {
        return storageRef;
    }

    public StorageReference getImagesRef() {
        return imagesRef;
    }
}
