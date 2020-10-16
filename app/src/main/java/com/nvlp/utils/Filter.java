package com.nvlp.utils;

import com.nvlp.model.response.Article;

import java.util.ArrayList;
import java.util.List;

public class Filter {

    public static List<Article> search(String searchKey, ArrayList<Article> articles) {
        ArrayList<Article> searchlist = new ArrayList<>();
        for (Article article : articles) {
            if (article.getTitle().toLowerCase().contains(searchKey.toLowerCase())) {
                searchlist.add(article);
            }
        }
        return searchlist;
    }
}
