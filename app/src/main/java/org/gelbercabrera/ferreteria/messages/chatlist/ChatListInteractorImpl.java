package org.gelbercabrera.ferreteria.messages.chatlist;

public class ChatListInteractorImpl implements ChatListInteractor {
    ChatListRepository repository;

    public ChatListInteractorImpl() {
        this.repository = new ChatListRepositoryImpl();
    }

    @Override
    public void subscribe() {
        repository.subscribeForChatListUpdates();
    }

    @Override
    public void unsubscribe() {
        repository.unSubscribeForChatListUpdates();
    }

    @Override
    public void destroyListener() {
        repository.destroyChatListListener();
    }

    @Override
    public void removeChat(String email) {
        repository.removeChat(email);
    }
}
