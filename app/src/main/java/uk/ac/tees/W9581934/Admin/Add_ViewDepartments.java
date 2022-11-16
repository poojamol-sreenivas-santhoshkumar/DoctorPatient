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
import uk.ac.tees.W9581934.Adapters.DoctorListAdapter;
import uk.ac.tees.W9581934.Models.DeptModel;
import uk.ac.tees.W9581934.Models.DoctorListModel;
import uk.ac.tees.W9581934.R;
import uk.ac.tees.W9581934.databinding.FragmentAddViewDepartmentsBinding;
import uk.ac.tees.W9581934.databinding.FragmentDoctorListBinding;


public class Add_ViewDepartments extends Fragment {
    FragmentAddViewDepartmentsBinding binding;
    DeptAdapter adapter=new DeptAdapter();
    List<DeptModel> deptlist = new ArrayList();
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
        binding = FragmentAddViewDepartmentsBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        for(int i=0;i<10;i++) {
            deptlist.add(new DeptModel("Cardiology", i));
        }
        binding.rvDepartments.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter.deptlist=deptlist;
        binding.rvDepartments.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        binding.btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.etDname.getText().toString().isEmpty())
                    binding.etDname.setError("enter a department name");
                else{

                }
            }
        });
    }
}