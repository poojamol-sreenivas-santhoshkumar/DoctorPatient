package uk.ac.tees.W9581934.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.ac.tees.W9581934.Models.DataModel;
import uk.ac.tees.W9581934.databinding.DataCardBinding;


public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyviewHolder> {
    public List<DataModel> routeList;
    DataCardBinding binding;
    private CallBackTwice mAdapterCallback;

    public DataAdapter(CallBackTwice callback) {
        this.mAdapterCallback = callback;
    }
    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = DataCardBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new MyviewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        DataModel dm = routeList.get(position);
        holder.itemName.setText(dm.getRouteName());
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterCallback.onStopCallback(dm.getRouteName());
            }
        });
        //holder.image.setImageBitmap(dm.getImage());

       /* holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapterCallback.onMethodCallback();
            }
        });*/


    }

    @Override
    public int getItemCount() {
        return routeList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
ConstraintLayout root;
        public MyviewHolder(@NonNull DataCardBinding binding) {
            super(binding.getRoot());
            itemName= binding.tvItem;
root=binding.clRoot;
        }
    }
}

