package uk.ac.tees.W9581934.Adapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import uk.ac.tees.W9581934.Models.DeptModel;
import uk.ac.tees.W9581934.Models.DoctorListModel;
import uk.ac.tees.W9581934.R;
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
        holder.ddelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertbox=new AlertDialog.Builder(view.getRootView().getContext());
                alertbox.setMessage("Do you really wants to Delete this Department from your hospital?");
                alertbox.setTitle("Delete!!");

                alertbox.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteDepartment(dm.getDept_id(), view);

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
    }

    @Override
    public int getItemCount() {
        return deptlist.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView tvdocname, dept_name, streamName, dob, age, time, days, username, mobile, btnbook;
        ImageView ddelete;

        public MyviewHolder(@NonNull DepartmentCardBinding binding) {
            super(binding.getRoot());
            tvdocname = binding.tvdname;
            ddelete = binding.dDelete;
        }
    }

    private void deleteDepartment(String doc_name, View view) {
        //Log.d("@", "showData: Called")

        final ProgressDialog progressDoalog = new ProgressDialog(view.getRootView().getContext());
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Department").document(doc_name).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Navigation.findNavController(view).navigate(R.id.action_add_ViewDepartments_self);
                        Toast.makeText(view.getRootView().getContext(), "Department removed successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(view.getRootView().getContext(), "Technical error occured", Toast.LENGTH_SHORT).show();

                    }
                });

        progressDoalog.dismiss();

    }

}

