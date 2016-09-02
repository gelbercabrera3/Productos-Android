package org.gelbercabrera.ferreteria.messages.chatlist.ui.adapters;

import org.gelbercabrera.ferreteria.entities.User;

public interface OnItemClickListener {
    void onItemClick(User user);
    void onMenuClick(User user);
}
