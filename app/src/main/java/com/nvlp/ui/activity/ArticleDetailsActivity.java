package com.nvlp.ui.activity;


import android.os.Bundle;

import com.nvlp.R;
import com.nvlp.databinding.ActivityArticledetailsBinding;
import com.nvlp.model.response.Article;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class ArticleDetailsActivity extends AppCompatActivity {
    ActivityArticledetailsBinding activityArticledetailsBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityArticledetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_articledetails);
        activityArticledetailsBinding.setArticle((Article) getIntent().getSerializableExtra("data"));

    }
}
