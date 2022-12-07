package uk.ac.tees.W9581934;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.ac.tees.W9581934.databinding.FragmentChatWithdoctorBinding;
import uk.ac.tees.W9581934.databinding.FragmentChooseBinding;


public class ChooseFragment extends Fragment {

  FragmentChooseBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentChooseBinding.inflate(getLayoutInflater(),container,false);
        return binding.getRoot();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        });
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnUserLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle type=new Bundle();
                type.putString("loginType","User");
                Navigation.findNavController(view).navigate(R.id.action_chooseFragment_to_loginFragment,type);
            }
        });

        binding.btnDoctorLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle type=new Bundle();
                type.putString("loginType","Doctor");
                Navigation.findNavController(view).navigate(R.id.action_chooseFragment_to_loginFragment,type);
            }
        });
    }
}