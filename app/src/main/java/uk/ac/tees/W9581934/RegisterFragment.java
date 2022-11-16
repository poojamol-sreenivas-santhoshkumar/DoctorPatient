package uk.ac.tees.W9581934;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.ac.tees.W9581934.databinding.FragmentRegisterBinding;


public class RegisterFragment extends Fragment {
    FragmentRegisterBinding binding;

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
        binding = FragmentRegisterBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.etName.getText().toString().isEmpty())
                    binding.etName.setError("Enter your name");
                else if (binding.etAge.getText().toString().isEmpty())
                    binding.etAge.setError("Enter your Age");
                else if (binding.etPhone.getText().toString().isEmpty())
                    binding.etPhone.setError("Enter your valid phone number");
                else if (binding.etAddress.getText().toString().isEmpty())
                    binding.etAddress.setError("Enter your address");
                else if (binding.etUsername.getText().toString().isEmpty())
                    binding.etUsername.setError("Enter your username");
                else if (binding.etPassword.getText().toString().isEmpty())
                    binding.etPassword.setError("Enter your password");
                else
                    Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment);
            }
        });

    }



}