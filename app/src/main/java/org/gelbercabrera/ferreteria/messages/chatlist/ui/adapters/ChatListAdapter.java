package org.gelbercabrera.ferreteria.messages.chatlist.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.gelbercabrera.ferreteria.R;
import org.gelbercabrera.ferreteria.entities.User;
import org.gelbercabrera.ferreteria.lib.ImageLoader;
import org.gelbercabrera.ferreteria.lib.domain.AvatarHelper;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatListAdapter extends RecyclerView.Adapter <ChatListAdapter.ViewHolder> {
    private List<User> contactList;
    private ImageLoader imageLoader;
    private OnItemClickListener clickListener;
    private Context mContext;

    public ChatListAdapter(List<User> contactList,
                           ImageLoader imageLoader,
                           OnItemClickListener clickListener, Context mContext) {
        this.contactList = contactList;
        this.imageLoader = imageLoader;
        this.clickListener = clickListener;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_chat_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final User user = contactList.get(position);
        holder.setClickListener(user, clickListener);
        boolean online = user.isOnline();
        String status = online ? "online" : "offline";

        holder.txtUser.setText(user.getName());
        holder.txtStatus.setText(status);

        if (online) {
            holder.txtStatus.setTextColor(Color.GREEN);
        }
        imageLoader.load(holder.imgAvatar, AvatarHelper.getAvatarUrl(user.getEmail()));

        holder.toolbarCard.inflateMenu(R.menu.menu_chatitem);
        holder.toolbarCard.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_remove_chat:
                        clickListener.onMenuClick(user);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }


    public int getPositionByUsername(String username) {
        int position = 0;
        for (User user : contactList) {
            if (user.getEmail().equals(username)) {
                break;
            }
            position++;
        }

        return position;
    }

    private boolean alreadyInAdapter(User newUser){
        boolean alreadyInAdapter = false;
        for (User user : this.contactList) {
            if (user.getEmail().equals(newUser.getEmail())) {
                alreadyInAdapter = true;
                break;
            }
        }

        return alreadyInAdapter;
    }

    public void add(User user) {
        if (!alreadyInAdapter(user)) {
            this.contactList.add(user);
            this.notifyDataSetChanged();
        }
    }

    public void update(User user) {
        int pos = getPositionByUsername(user.getEmail());
        try{
            contactList.set(pos, user);}
        catch (IndexOutOfBoundsException ex){}
        this.notifyDataSetChanged();
    }

    public void remove(User user) {
        int pos = getPositionByUsername(user.getEmail());
        try {
            contactList.remove(pos);
        }catch(IndexOutOfBoundsException ix){}
        this.notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.imgAvatar)
        CircleImageView imgAvatar;
        @Bind(R.id.txtStatus)
        TextView txtStatus;
        @Bind(R.id.txtUser)
        TextView txtUser;
        View view;
        @Bind(R.id.toolbarCard)
        Toolbar toolbarCard;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }

        public void setClickListener(final User user,
                                     final OnItemClickListener listener) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(user);
                }
            });
        }
    }
}