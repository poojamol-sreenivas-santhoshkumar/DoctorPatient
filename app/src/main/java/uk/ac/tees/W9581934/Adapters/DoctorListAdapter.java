package uk.ac.tees.W9581934.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.ac.tees.W9581934.Models.DoctorListModel;
import uk.ac.tees.W9581934.databinding.DoctorCardBinding;

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.MyviewHolder> {
    public List<DoctorListModel> doctorList;
    DoctorCardBinding binding;

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = DoctorCardBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new MyviewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        DoctorListModel dm = doctorList.get(position);
        holder.tvdocname.setText(dm.getDoctorName());
        holder.dept_name.setText(dm.getDepartmentName());
        holder.streamName.setText(dm.getSpecializedStream());
        holder.dob.setText(dm.getDob());
        holder.age.setText("\tAge :\t"+dm.getAge());
        holder.time.setText(dm.getConsultingTime());
        holder.days.setText(dm.getAvailabilityDays());
        holder.username.setText(dm.getUserName());
        holder.mobile.setText(dm.getMobileNumber());
        holder.ivphoto.setImageResource(dm.getDoctorImage());
        holder.btnbook.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView tvdocname, dept_name, streamName, dob, age, time, days, username, mobile, btnbook;
        ImageView ivphoto;

        public MyviewHolder(@NonNull DoctorCardBinding binding) {
            super(binding.getRoot());
            tvdocname = binding.tvDocName;
            dept_name = binding.tvDocDepartment;
            streamName = binding.tvDocSpecialization;
            dob = binding.tvDob;
            age = binding.tvAge;
            time = binding.tvConsultingTime;
            days = binding.tvAvailable;
            username = binding.tvUsername;
            mobile = binding.tvMobile;
            ivphoto = binding.image;
            btnbook = binding.btnFind;
        }
    }
}
