package org.gelbercabrera.ferreteria.posts.postlist.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;

import org.gelbercabrera.ferreteria.R;
import org.gelbercabrera.ferreteria.entities.Post;
import org.gelbercabrera.ferreteria.lib.ImageLoader;
import org.gelbercabrera.ferreteria.lib.domain.AvatarHelper;
import org.gelbercabrera.ferreteria.lib.domain.FirebaseHelper;
import org.gelbercabrera.ferreteria.posts.postlist.helper.DateManager;

import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class PostsAdapter extends RecyclerView.Adapter <PostsAdapter.ViewHolder> {
    private List<Post> postsList;
    private ImageLoader imageLoader;
    private OnItemClickListener clickListener;
    private Context mContext;

    public PostsAdapter(List<Post> postsList, ImageLoader imageLoader,
                        OnItemClickListener clickListener, Context mContext) {
        this.postsList = postsList;
        this.imageLoader = imageLoader;
        this.clickListener = clickListener;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_post_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Post post =  postsList.get(position);

        holder.txtName.setText(post.getName_poster());
        holder.txtDescription.setText(post.getDescripction());
        holder.txtNamePost.setText(post.getName());
        holder.txtPrice.setText("Q " + post.getPrice());
        holder.txtName.setText(post.getName_poster());


        holder.txtDate.setText(DateManager.calculateTime(post.getDate()));

        imageLoader.load(holder.imgAvatar, AvatarHelper.getAvatarUrl(post.getEmail_poster()));

        StorageReference islandRef = FirebaseHelper.getInstance().getImagesRef().child(post.getUrlImage());

        final long ONE_MEGABYTE = 1024 * 1024;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                holder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(bytes,0, bytes.length));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });

        holder.toolbarCard.getMenu().clear();

        if (!post.getEmail_poster().replace("_",".").equals(
                FirebaseHelper.getInstance().getAuthUserEmail().replace("_","."))){
            holder.toolbarCard.inflateMenu(R.menu.menu_posts_item);
            holder.toolbarCard.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_message:
                            clickListener.onAddMessage(post.getEmail_poster());
                    }
                    return true;
                }
            });
        }
        holder.imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onProfileClick(post);
            }
        });
    }



    @Override
    public int getItemCount() {
        return postsList.size();
    }

    public int getPositionByDate(Date date) {
        int position = 0;
        for (Post post : postsList) {
            if (post.getDate().equals(date)) {
                break;
            }
            position++;
        }

        return position;
    }

    private boolean alreadyInAdapter(Post _post){
        boolean alreadyInAdapter = false;
        for (Post post : this.postsList) {
            if (post.getEmail_poster().equals(_post.getEmail_poster())
                    && post.getDate().equals(_post.getDate())) {
                alreadyInAdapter = true;
                break;
            }
        }

        return alreadyInAdapter;
    }

    public void add(Post post) {
        if (!alreadyInAdapter(post)) {
            this.postsList.add(post);
            this.notifyDataSetChanged();
        }
    }

    public void update(Post post) {
        int pos = getPositionByDate(post.getDate());
        postsList.set(pos, post);
        this.notifyDataSetChanged();
    }

    public void remove(Post post) {
        int pos = getPositionByDate(post.getDate());
        try {
            postsList.remove(pos);
        }catch(IndexOutOfBoundsException ix){}
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.imgAvatar)
        CircleImageView imgAvatar;
        @Bind(R.id.txtName)
        TextView txtName;
        @Bind(R.id.txtPrice)
        TextView txtPrice;
        @Bind(R.id.txtDescription)
        TextView txtDescription;
        @Bind(R.id.txtNamePost)
        TextView txtNamePost;
        @Bind(R.id.txtDate)
        TextView txtDate;

        @Bind(R.id.imageVIew)
        ImageView imageView;
        @Bind(R.id.toolbarCard)
        Toolbar toolbarCard;

        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
