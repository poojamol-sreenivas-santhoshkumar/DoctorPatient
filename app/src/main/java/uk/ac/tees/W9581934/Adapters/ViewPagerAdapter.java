package uk.ac.tees.W9581934.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import uk.ac.tees.W9581934.Models.FeedbackModel;
import uk.ac.tees.W9581934.Models.ViewPagerModel;
import uk.ac.tees.W9581934.databinding.FeedbackTileBinding;
import uk.ac.tees.W9581934.databinding.SpotsBinding;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.MyviewHolder> {
    public List<ViewPagerModel> feedList;
    SpotsBinding binding;

    @NonNull
    @Override
    public ViewPagerAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = SpotsBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new ViewPagerAdapter.MyviewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewPagerAdapter.MyviewHolder holder, int position) {
        ViewPagerModel dm = feedList.get(position);

        Glide.with(holder.img1.getContext())
                .load(dm.getImage())
                .into(holder.img1);
        Glide.with(holder.img2.getContext())
                .load(dm.getImage2())
                .into(holder.img2);
        Glide.with(holder.img3.getContext())
                .load(dm.getImage3())
                .into(holder.img3);

    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        ImageView img1, img2,img3;
        public MyviewHolder(@NonNull SpotsBinding binding) {
            super(binding.getRoot());
            img1 = binding.iv1;
            img2 = binding.iv2;
            img3 = binding.iv3;

        }
    }
}

