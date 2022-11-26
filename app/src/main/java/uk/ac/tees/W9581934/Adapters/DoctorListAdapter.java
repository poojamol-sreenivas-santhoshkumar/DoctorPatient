package uk.ac.tees.W9581934.Adapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import uk.ac.tees.W9581934.Models.DoctorListModel;
import uk.ac.tees.W9581934.R;
import uk.ac.tees.W9581934.databinding.DoctorCardBinding;

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.MyviewHolder> implements Filterable {

    public List<DoctorListModel> exampleListFull;
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
        holder.tvdocname.setText(dm.getName());
        holder.dept_name.setText(dm.getDepartmentName());
        holder.streamName.setText(dm.getSpecializedStream());
        holder.dob.setText("DOB: "+dm.getDob());
        holder.age.setText("\tAge :\t" + dm.getAge());
        holder.time.setText(dm.getConsultingTime() + " To " + dm.getEndtime());
        holder.days.setText(dm.getAvailabilityDays());
        holder.username.setText(dm.getUsername());
        holder.mobile.setText(dm.getPhone());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] imageBytes = baos.toByteArray();
        imageBytes = Base64.decode(dm.getDoctorImage(), Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        holder.ivphoto.setImageBitmap(decodedImage);
        holder.btnbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("##", dm.getName());
                Log.d("##", dm.getDepartmentName());
                Log.d("##", dm.getUserId());
                SharedPreferences doctor=ctx.getSharedPreferences("dr",Context.MODE_PRIVATE);
                SharedPreferences.Editor ed= doctor.edit();
                ed.putString("drname",dm.getName());
                ed.putString("drdpt",dm.getDepartmentName());
                ed.putString("docid",dm.getUserId());
                ed.commit();
                Navigation.findNavController(view).navigate(R.id.action_bookDoctor_to_bookingPageFragment2);
                //  mAdapterCallback.onMethodCallback();
            }
        });
        holder.icdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertbox=new AlertDialog.Builder(view.getRootView().getContext());
                alertbox.setMessage("Do you really wants to Delete this Doctor from your hospital?");
                alertbox.setTitle("Delete!!");

                alertbox.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteDoctor(dm.getUserId(), view);

                    }
                });
                alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alertbox.show();
            }
        });
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
            holder.icdelete.setVisibility(View.GONE);
        }
        else {
            holder.btnbook.setVisibility(View.GONE);
            holder.age.setVisibility(View.VISIBLE);
            holder.dob.setVisibility(View.VISIBLE);
            holder.icdelete.setVisibility(View.GONE);
            holder.username.setVisibility(View.VISIBLE);holder.labelusername.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView tvdocname, dept_name, streamName, dob, age, time, days, username, mobile, btnbook,labelusername;
        ImageView ivphoto,icdelete;
        ConstraintLayout root;


        public MyviewHolder(@NonNull DoctorCardBinding binding) {
            super(binding.getRoot());
            root = binding.root;
            icdelete=binding.btndelete;
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
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<DoctorListModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (DoctorListModel item : exampleListFull) {
                    try {
                        if (item.getDepartmentName().toLowerCase().contains(filterPattern) ||
                                item.getName().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }catch (Exception e){}

                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            doctorList.clear();
            doctorList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
    private void deleteDoctor(String doc_name, View view) {
        //Log.d("@", "showData: Called")

        final ProgressDialog progressDoalog = new ProgressDialog(view.getRootView().getContext());
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Doctors").document(doc_name).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Navigation.findNavController(view).navigate(R.id.action_doctorListFragment_self);
                        Toast.makeText(view.getRootView().getContext(), "Doctor removed successfully",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(view.getRootView().getContext(), "Technical error occured",Toast.LENGTH_SHORT).show();

                    }
                });

        progressDoalog.dismiss();

}

}
