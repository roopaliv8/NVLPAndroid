package com.nvlp.model.response;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.nvlp.ui.activity.ArticleDetailsActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import androidx.databinding.BindingAdapter;

public class Article implements Serializable {

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstractval() {
        return abstractval;
    }

    public void setAbstractval(String abstractval) {
        this.abstractval = abstractval;
    }

    public String getPublished_date() {
        return published_date;
    }

    public void setPublished_date(String published_date) {
        this.published_date = published_date;
    }

    public List<Media> getMedia() {
        return media;
    }

    public void setMedia(List<Media> media) {
        this.media = media;
    }

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("abstract")
    @Expose
    private String abstractval;

    @SerializedName("section")
    @Expose
    private String section;

    @SerializedName("published_date")
    @Expose
    private String published_date;

    @SerializedName("media")
    @Expose
    private List<Media> media;

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public class Media implements Serializable {
        public List<MediaMeta> getMediaMetas() {
            return mediaMetas;
        }

        public void setMediaMetas(List<MediaMeta> mediaMetas) {
            this.mediaMetas = mediaMetas;
        }

        @SerializedName("media-metadata")
        @Expose
        List<MediaMeta> mediaMetas;

        public String getCaption() {
            return caption;
        }

        public void setCaption(String caption) {
            this.caption = caption;
        }

        @SerializedName("caption")
        @Expose
        String caption;
    }

    public class MediaMeta implements Serializable {
        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @SerializedName("url")
        @Expose
        private String url;

    }

    @BindingAdapter("mediaImage")
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl).apply(new RequestOptions().circleCrop())
                .into(view);
    }

    @BindingAdapter("loaddetailsImage")
    public static void loaddetailsImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .into(view);
    }

    @BindingAdapter("showDetails")
    public void showDetails(View view, Article data) {
        Intent intent = new Intent(view.getContext(), ArticleDetailsActivity.class);
        intent.putExtra("data", data);
        view.getContext().startActivity(intent);


    }
}
