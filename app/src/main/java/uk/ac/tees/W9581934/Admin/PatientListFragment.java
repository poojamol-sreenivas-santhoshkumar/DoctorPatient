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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import uk.ac.tees.W9581934.Adapters.AdapterCallback;
import uk.ac.tees.W9581934.Adapters.DeptAdapter;
import uk.ac.tees.W9581934.Adapters.PatientAdapter;
import uk.ac.tees.W9581934.Models.DeptModel;
import uk.ac.tees.W9581934.Models.PatientModel;
import uk.ac.tees.W9581934.R;
import uk.ac.tees.W9581934.databinding.FragmentFeedbackBinding;
import uk.ac.tees.W9581934.databinding.FragmentPatientListBinding;


public class PatientListFragment extends Fragment implements AdapterCallback {
    FragmentPatientListBinding binding;
    PatientAdapter adapter=new PatientAdapter();
    List<PatientModel> PatientList = new ArrayList();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback( this,new OnBackPressedCallback(true){
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(getView()).navigate(R.id.action_patientListFragment_to_adminHomeFragment);
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPatientListBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        for(int i=0;i<10;i++) {
//            PatientList.add(new PatientModel("manu","22","9747062356" ,"no.20,rosevilla"));
//        }
        binding.rvPatients.setLayoutManager(new LinearLayoutManager(requireContext()));
       showData();

    }
    private void showData() {
        final ProgressDialog progressDoalog = new ProgressDialog(requireContext());
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait");
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        //Log.d("@", "showData: Called")
        PatientList.clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Patients")
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
                            PatientList.add(new PatientModel(
                                    queryDocumentSnapshots.getDocuments().get(i).getString("id"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("type"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("name"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("age"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("address"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("phone"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("username"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("password")
                            ));
                        }
                        if (PatientList.isEmpty()){
                            binding.rvPatients.setVisibility(View.GONE);
                            binding.labelNoData.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            binding.rvPatients.setVisibility(View.VISIBLE);
                            binding.labelNoData.setVisibility(View.INVISIBLE);
                        }
                        progressDoalog.dismiss();
                        adapter.patientList=PatientList;
                        binding.rvPatients.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onMethodCallback() {

    }
}