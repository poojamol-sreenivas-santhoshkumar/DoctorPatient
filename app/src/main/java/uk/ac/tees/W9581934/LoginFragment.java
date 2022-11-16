package uk.ac.tees.W9581934;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.ac.tees.W9581934.databinding.FragmentLoginBinding;


public class LoginFragment extends Fragment {
    FragmentLoginBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.etEmail.getText().toString().isEmpty())
                    binding.etEmail.setError("please enter your username");
                else if (binding.etPassword.getText().toString().isEmpty())
                    binding.etPassword.setError("plaese enter your password");
                else {

                    final ProgressDialog progressDoalog = new ProgressDialog(requireContext());
                    progressDoalog.setMessage("Checking....");
                    progressDoalog.setTitle("Please wait");
                    progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDoalog.show();

                    if (binding.etEmail.getText().toString().equals("admin"))
                        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_adminHomeFragment);
                    else if (binding.etEmail.getText().toString().equals("doctor"))
                        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_doctorHomeFragment);
                    else if (binding.etEmail.getText().toString().equals("patient"))
                        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_userHomeFragment);
                    progressDoalog.dismiss();
                    binding.etEmail.setText("");
                    binding.etPassword.setText("");
                }
            }
        });
        binding.tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });
    }
}