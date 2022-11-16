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

import uk.ac.tees.W9581934.Adapters.DoctorListAdapter;
import uk.ac.tees.W9581934.Models.DoctorListModel;
import uk.ac.tees.W9581934.R;
import uk.ac.tees.W9581934.databinding.FragmentDoctorListBinding;


public class DoctorListFragment extends Fragment {
    FragmentDoctorListBinding binding;
    DoctorListAdapter adapter=new DoctorListAdapter();
    List<DoctorListModel> DoctorList = new ArrayList();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
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
        binding = FragmentDoctorListBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       for(int i=0;i<10;i++) {
           DoctorList.add(new DoctorListModel("Nithin", "Cardiolgy", "Cardiac Surgon", "10/04/1985", "35", "Morning 11:am to 02:00 pm", "Monday-friday", "nithin@cardiology", "9747062356", R.drawable.doctor));
       }
       binding.rvDoctors.setLayoutManager(new LinearLayoutManager(requireContext()));
       adapter.doctorList=DoctorList;
       binding.rvDoctors.setAdapter(adapter);
       adapter.notifyDataSetChanged();
    }
}