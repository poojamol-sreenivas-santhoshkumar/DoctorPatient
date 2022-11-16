package uk.ac.tees.W9581934.Patient;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.ac.tees.W9581934.R;
import uk.ac.tees.W9581934.databinding.FragmentMyBookingsBinding;
import uk.ac.tees.W9581934.databinding.FragmentUserBookingBinding;

public class UserBookingFragment extends Fragment {
    FragmentUserBookingBinding binding;
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
        binding= FragmentUserBookingBinding.inflate(getLayoutInflater(),container,false);
        return binding.getRoot();
    }
}