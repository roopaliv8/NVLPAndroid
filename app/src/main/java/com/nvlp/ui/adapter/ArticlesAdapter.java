package com.nvlp.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.nvlp.databinding.ItemBinding;
import com.nvlp.model.response.Article;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.MyViewHolder> {

    private List<Article> itemList;

    public ArticlesAdapter(List<Article> itemList) {
        this.itemList = itemList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        ItemBinding itemBinding =
                ItemBinding.inflate(layoutInflater, parent, false);
        return new MyViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Article item = itemList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private final ItemBinding binding;

        MyViewHolder(ItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Article item) {
            binding.setArticle(item);
            binding.executePendingBindings();
        }
    }

    public void animateTo(List<Article> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<Article> newModels) {
        for (int i = itemList.size() - 1; i >= 0; i--) {
            final Article model = itemList.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void removeItem(int position) {
        itemList.remove(position);
        notifyItemRemoved(position);
    }

    public void addItem(int position, Article model) {
        boolean position_notexist = itemList.indexOf(model) < 0;
        if (position_notexist) {
            itemList.add(position, model);
            notifyItemInserted(position);
        }

    }

    private void applyAndAnimateAdditions(List<Article> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final Article model = newModels.get(i);
            addItem(i, model);


        }
    }

    private void applyAndAnimateMovedItems(List<Article> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final Article model = newModels.get(toPosition);
            final int fromPosition = itemList.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Article model = itemList.remove(fromPosition);
        boolean position_notexist = itemList.indexOf(model) < 0;
        if (position_notexist) {
            itemList.add(toPosition, model);
            notifyItemMoved(fromPosition, toPosition);
        }
        notifyItemChanged(toPosition);

    }
}