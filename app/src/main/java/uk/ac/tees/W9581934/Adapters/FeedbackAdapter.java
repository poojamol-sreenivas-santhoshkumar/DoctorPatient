package uk.ac.tees.W9581934.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.ac.tees.W9581934.Models.DeptModel;
import uk.ac.tees.W9581934.Models.FeedbackModel;
import uk.ac.tees.W9581934.databinding.DepartmentCardBinding;
import uk.ac.tees.W9581934.databinding.FeedbackTileBinding;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.MyviewHolder> {
    public List<FeedbackModel> feedList;
    FeedbackTileBinding binding;

    @NonNull
    @Override
    public FeedbackAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = FeedbackTileBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new FeedbackAdapter.MyviewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackAdapter.MyviewHolder holder, int position) {
        FeedbackModel dm = feedList.get(position);
        holder.pname.setText(dm.getPname());
        holder.feedback.setText(dm.getFeedback());

    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView pname, feedback;
        public MyviewHolder(@NonNull FeedbackTileBinding binding) {
            super(binding.getRoot());
            pname = binding.tvPatientName;
            feedback = binding.tvFeedback;

        }
    }
}

