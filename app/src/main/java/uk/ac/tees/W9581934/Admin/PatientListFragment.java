package uk.ac.tees.W9581934.Admin;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import uk.ac.tees.W9581934.Adapters.DeptAdapter;
import uk.ac.tees.W9581934.Adapters.PatientAdapter;
import uk.ac.tees.W9581934.Models.DeptModel;
import uk.ac.tees.W9581934.Models.PatientModel;
import uk.ac.tees.W9581934.R;
import uk.ac.tees.W9581934.databinding.FragmentFeedbackBinding;
import uk.ac.tees.W9581934.databinding.FragmentPatientListBinding;


public class PatientListFragment extends Fragment {
    FragmentPatientListBinding binding;
    PatientAdapter adapter=new PatientAdapter();
    List<PatientModel> PatientList = new ArrayList();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback( this,new OnBackPressedCallback(true){
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(getView()).navigateUp();
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
        for(int i=0;i<10;i++) {
            PatientList.add(new PatientModel("manu","22","9747062356" ,"no.20,rosevilla"));
        }
        binding.rvPatients.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter.patientList=PatientList;
        binding.rvPatients.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}