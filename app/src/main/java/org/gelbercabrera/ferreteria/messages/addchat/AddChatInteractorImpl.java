package org.gelbercabrera.ferreteria.messages.addchat;

public class AddChatInteractorImpl implements AddChatInteractor {
    AddChatRepository repository;

    public AddChatInteractorImpl() {
        repository = new AddChatRepositoryImpl();
    }

    @Override
    public void execute(String email) {
        repository.addContact(email);
    }
}
