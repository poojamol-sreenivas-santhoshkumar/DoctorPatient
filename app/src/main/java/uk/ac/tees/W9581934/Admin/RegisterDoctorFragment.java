package uk.ac.tees.W9581934.Admin;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.ac.tees.W9581934.R;
import uk.ac.tees.W9581934.databinding.FragmentFeedbackBinding;
import uk.ac.tees.W9581934.databinding.FragmentRegisterDoctorBinding;


public class RegisterDoctorFragment extends Fragment {
    FragmentRegisterDoctorBinding binding;

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
        binding = FragmentRegisterDoctorBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.etDocId.getText().toString().isEmpty())
                binding.etDocId.setError("Enter a Unique Id for this new Doctor");
                else if(binding.etDocName.getText().toString().isEmpty())
                    binding.etDocName.setError("Enter doctor name");
                else if(binding.etDocAge.getText().toString().isEmpty())
                    binding.etDocAge.setError("Enter doctor age");
                else if(binding.etDocDob.getText().toString().isEmpty())
                    binding.etDocDob.setError("Enter a doctor date of birth");
                else if(binding.etDepartment.getText().toString().isEmpty())
                    binding.etDepartment.setError("Enter department name");
                else if(binding.etSpecialization.getText().toString().isEmpty())
                    binding.etSpecialization.setError("Enter Specialization");
                else if(binding.etExperience.getText().toString().isEmpty())
                    binding.etExperience.setError("Enter no of working experience");
                else if(binding.etDocMobile.getText().toString().isEmpty())
                    binding.etDocMobile.setError("Enter doctor mobile number");
                else if(binding.Time.getText().toString().isEmpty())
                    binding.Time.setError("Enter doctor consultation start time");
                else if(binding.endTime.getText().toString().isEmpty())
                    binding.endTime.setError("Enter doctor consultation end time");
                else if(binding.etDocDays.getText().toString().isEmpty())
                    binding.etDocDays.setError("Enter doctor Available days");
                else if(binding.etDocUsername.getText().toString().isEmpty())
                    binding.etDocUsername.setError("Enter doctor username");
                else if(binding.etDocPassword.getText().toString().isEmpty())
                    binding.etDocPassword.setError("Enter doctor password");
                else{

                }
            }
        });
    }
}