package uk.ac.tees.W9581934.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.ac.tees.W9581934.Models.DoctorListModel;
import uk.ac.tees.W9581934.Models.PatientModel;
import uk.ac.tees.W9581934.databinding.DoctorCardBinding;
import uk.ac.tees.W9581934.databinding.PatientCardBinding;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.MyviewHolder> {
    public List<PatientModel> patientList;
    PatientCardBinding binding;

    @NonNull
    @Override
    public PatientAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = PatientCardBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new PatientAdapter.MyviewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull PatientAdapter.MyviewHolder holder, int position) {
        PatientModel dm = patientList.get(position);
        holder.name.setText(dm.getName());
        holder.age.setText(dm.getAge());
        holder.phone.setText(dm.getPhone());
        holder.address.setText(dm.getAddress());


    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView name, age, phone, address;


        public MyviewHolder(@NonNull PatientCardBinding binding) {
            super(binding.getRoot());
            name = binding.tvPatientName;
            address = binding.tvPatientAddress;
            age = binding.tvPatientAge;
            phone = binding.tvPatientPhone;

        }
    }
}
