package uk.ac.tees.W9581934.Admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import uk.ac.tees.W9581934.Adapters.AdapterCallback;
import uk.ac.tees.W9581934.Adapters.DoctorListAdapter;
import uk.ac.tees.W9581934.Models.DoctorListModel;
import uk.ac.tees.W9581934.R;
import uk.ac.tees.W9581934.databinding.FragmentDoctorListBinding;


public class DoctorListFragment extends Fragment implements AdapterCallback {
    FragmentDoctorListBinding binding;
    DoctorListAdapter adapter;
    List<DoctorListModel> DoctorList = new ArrayList();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(getView()).navigate(R.id.action_doctorListFragment_to_adminHomeFragment);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDoctorListBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sp = getContext().getSharedPreferences("logDetails", Context.MODE_PRIVATE);
        adapter = new DoctorListAdapter(sp.getString("userType", "error"), getContext());
//       for(int i=0;i<10;i++) {
//           DoctorList.add(new DoctorListModel("Nithin", "Cardiolgy", "Cardiac Surgon", "10/04/1985", "35", "Morning 11:am to 02:00 pm", "Monday-friday", "nithin@cardiology", "9747062356", "R.drawable.doctor","doctor","","fff","fff","fff"));
//       }
        binding.rvDoctors.setLayoutManager(new LinearLayoutManager(requireContext()));
        showData();
    }

    @Override
    public void onMethodCallback() {

    }

    private void showData() {
        final ProgressDialog progressDoalog = new ProgressDialog(requireContext());
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait");
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        //Log.d("@", "showData: Called")
        DoctorList.clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Doctors")
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
                            DoctorList.add(new DoctorListModel("",
                                    queryDocumentSnapshots.getDocuments().get(i).getId(),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("name"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("departmentName"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("specializedStream"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("dob"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("age"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("consultingTime"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("availabilityDays"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("username"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("phone"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("type"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("doctorImage"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("password"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("experience"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("endtime")
                            ));
                        }
                        if (DoctorList.isEmpty()) {
                            binding.rvDoctors.setVisibility(View.GONE);
                            binding.labelNoData.setVisibility(View.VISIBLE);
                        } else {
                            binding.rvDoctors.setVisibility(View.VISIBLE);
                            binding.labelNoData.setVisibility(View.INVISIBLE);
                        }
                        progressDoalog.dismiss();
                        adapter.doctorList = DoctorList;
                        binding.rvDoctors.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                });

    }

}