package uk.ac.tees.W9581934.Doctor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.ac.tees.W9581934.R;
import uk.ac.tees.W9581934.databinding.FragmentDoctorHomeBinding;


public class DoctorDashboard extends Fragment {
    FragmentDoctorHomeBinding binding;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback( this,new OnBackPressedCallback(true){
            @Override
            public void handleOnBackPressed() {
                AlertDialog.Builder alertbox=new AlertDialog.Builder(requireContext());
                alertbox.setMessage("Do you really wants to logout from this app?");
                alertbox.setTitle("Logout!!");

                alertbox.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Navigation.findNavController(getView()).navigateUp();

                    }
                });
                alertbox.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentDoctorHomeBinding.inflate(getLayoutInflater(),container,false);
        return binding.getRoot();
    }
}