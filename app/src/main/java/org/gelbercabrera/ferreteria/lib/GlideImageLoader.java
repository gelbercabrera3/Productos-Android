package org.gelbercabrera.ferreteria.lib;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import de.hdodenhof.circleimageview.CircleImageView;


public class GlideImageLoader implements ImageLoader{
    private RequestManager requestManager;

    public GlideImageLoader(Context context) {
        this.requestManager = Glide.with(context);
    }

    @Override
    public void load(CircleImageView imgAvatar, String url) {
        requestManager.load(url).into(imgAvatar);
    }
}
