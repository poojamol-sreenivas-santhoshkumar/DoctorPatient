package uk.ac.tees.W9581934.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.List;

import uk.ac.tees.W9581934.Models.DoctorListModel;
import uk.ac.tees.W9581934.R;
import uk.ac.tees.W9581934.databinding.DoctorCardBinding;

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.MyviewHolder> {
    public List<DoctorListModel> doctorList;
    DoctorCardBinding binding;
    private AdapterCallback mAdapterCallback;
    String type;
    Context ctx;

    public DoctorListAdapter(String type, Context context) {
        this.type = type;
        this.ctx=context;
    }

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
        holder.dob.setText("DOB: "+dm.getDob());
        holder.age.setText("\tAge :\t" + dm.getAge());
        holder.time.setText(dm.getConsultingTime() + " To " + dm.getEndtime());
        holder.days.setText(dm.getAvailabilityDays());
        holder.username.setText(dm.getUserName());
        holder.mobile.setText(dm.getMobileNumber());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] imageBytes = baos.toByteArray();
        imageBytes = Base64.decode(dm.getDoctorImage(), Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        holder.ivphoto.setImageBitmap(decodedImage);
        if (type.equals("admin")) {
            holder.btnbook.setVisibility(View.GONE);
            holder.age.setVisibility(View.VISIBLE);
            holder.dob.setVisibility(View.VISIBLE);
            holder.username.setVisibility(View.VISIBLE);
            holder.labelusername.setVisibility(View.VISIBLE);
        }
        else if (type.equals("patient")) {
            holder.btnbook.setVisibility(View.VISIBLE);
            holder.age.setVisibility(View.GONE);
            holder.dob.setVisibility(View.GONE);
            holder.username.setVisibility(View.GONE);
            holder.labelusername.setVisibility(View.GONE);
        }
        else {
            holder.btnbook.setVisibility(View.GONE);
            holder.age.setVisibility(View.VISIBLE);
            holder.dob.setVisibility(View.VISIBLE);
            holder.username.setVisibility(View.VISIBLE);holder.labelusername.setVisibility(View.VISIBLE);
        }
        holder.btnbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences doctor=ctx.getSharedPreferences("dr",Context.MODE_PRIVATE);
                SharedPreferences.Editor ed= doctor.edit();
                ed.putString("drname",dm.getDoctorName());
                ed.putString("drdpt",dm.getDepartmentName());
                ed.commit();
                Navigation.findNavController(view).navigate(R.id.action_bookDoctor_to_bookingPageFragment2);
                //  mAdapterCallback.onMethodCallback();
            }
        });

    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView tvdocname, dept_name, streamName, dob, age, time, days, username, mobile, btnbook,labelusername;
        ImageView ivphoto;
        ConstraintLayout root;

        public MyviewHolder(@NonNull DoctorCardBinding binding) {
            super(binding.getRoot());
            root = binding.root;
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
            labelusername=binding.labelUsername;
        }
    }
}
