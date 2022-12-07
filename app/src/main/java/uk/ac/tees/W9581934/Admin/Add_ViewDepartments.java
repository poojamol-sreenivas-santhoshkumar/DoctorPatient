package uk.ac.tees.W9581934.Admin;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import uk.ac.tees.W9581934.Adapters.DeptAdapter;
import uk.ac.tees.W9581934.Adapters.DoctorListAdapter;
import uk.ac.tees.W9581934.Models.DeptModel;
import uk.ac.tees.W9581934.Models.DoctorListModel;
import uk.ac.tees.W9581934.Models.FeedbackModel;
import uk.ac.tees.W9581934.Models.PatientModel;
import uk.ac.tees.W9581934.R;
import uk.ac.tees.W9581934.databinding.FragmentAddViewDepartmentsBinding;
import uk.ac.tees.W9581934.databinding.FragmentDoctorListBinding;


public class Add_ViewDepartments extends Fragment {
    FragmentAddViewDepartmentsBinding binding;
    DeptAdapter adapter = new DeptAdapter();
    List<DeptModel> deptlist = new ArrayList();
    ProgressDialog progressDoalog;
    private ProgressBar progressbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(getView()).navigate(R.id.action_add_ViewDepartments_to_adminHomeFragment);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddViewDepartmentsBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.rvDepartments.setLayoutManager(new LinearLayoutManager(requireContext()));
        showData();
        binding.btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.etDname.getText().toString().isEmpty())
                    binding.etDname.setError("enter a department name");
                else {
                    AddDepartment();
                }
            }
        });
    }

    private void AddDepartment() {
        progressDoalog = new ProgressDialog(requireContext());
        progressDoalog.setMessage("Adding Data....");
        progressDoalog.setTitle("Please wait");
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        String deptname;
        deptname = binding.etDname.getText().toString();
        fireStoreDatabase:
        FirebaseFirestore.getInstance();
        DeptModel obj = new DeptModel(deptname, "Dpt"+deptname);
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        db.collection("Department").add(obj).
                addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        binding.etDname.setText("");
                        progressDoalog.dismiss();
                        Snackbar.make(requireView(), "Department added Successfully", Snackbar.LENGTH_LONG).show();
                        showData();
                        binding.etDname.setText("");
                    }
                }).
                addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireContext(), "Creation failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showData() {
        progressDoalog = new ProgressDialog(requireContext());
        progressDoalog.setMessage("Checking....");
        progressDoalog.setTitle("Please wait");
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        //Log.d("@", "showData: Called")
        deptlist.clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        try {
            db.collection("Department")
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            Log.d("@", queryDocumentSnapshots + "");
                            int i;
                            for (i = 0; i < queryDocumentSnapshots.getDocuments().size(); i++) {
                            /*Log.d("!", queryDocumentSnapshots.getDocuments().get(i).getId());
                            Log.d("!", queryDocumentSnapshots.getDocuments().get(i).getString("foodName"));
                            Log.d("!", queryDocumentSnapshots.getDocuments().get(i).getString("foodPrice"));*/
                                deptlist.add(new DeptModel(
                                        queryDocumentSnapshots.getDocuments().get(i).getString("dname"),
                                        queryDocumentSnapshots.getDocuments().get(i).getId()
                                ));
                            }
                            progressDoalog.dismiss();
                            adapter.deptlist = deptlist;
                            binding.rvDepartments.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                        }
                    });

        } catch (Exception e) {

        }

    }
}