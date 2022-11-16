package uk.ac.tees.W9581934.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.ac.tees.W9581934.Models.DeptModel;
import uk.ac.tees.W9581934.Models.DoctorListModel;
import uk.ac.tees.W9581934.databinding.DepartmentCardBinding;
import uk.ac.tees.W9581934.databinding.DoctorCardBinding;

public class DeptAdapter extends RecyclerView.Adapter<DeptAdapter.MyviewHolder> {
    public List<DeptModel> deptlist;
    DepartmentCardBinding binding;

    @NonNull
    @Override
    public DeptAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = DepartmentCardBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new DeptAdapter.MyviewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull DeptAdapter.MyviewHolder holder, int position) {
        DeptModel dm = deptlist.get(position);
        holder.tvdocname.setText(dm.getDname());

    }

    @Override
    public int getItemCount() {
        return deptlist.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView tvdocname, dept_name, streamName, dob, age, time, days, username, mobile, btnbook;
        ImageView ivphoto;

        public MyviewHolder(@NonNull DepartmentCardBinding binding) {
            super(binding.getRoot());
            tvdocname = binding.tvdname;

        }
    }
}

