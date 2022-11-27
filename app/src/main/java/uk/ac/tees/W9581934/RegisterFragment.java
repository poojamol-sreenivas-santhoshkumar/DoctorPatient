package uk.ac.tees.W9581934;

import android.app.ProgressDialog;
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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Random;

import uk.ac.tees.W9581934.Models.PatientModel;
import uk.ac.tees.W9581934.Models.Validation;
import uk.ac.tees.W9581934.databinding.FragmentRegisterBinding;


public class RegisterFragment extends Fragment {
    FragmentRegisterBinding binding;
   // FirebaseStorage firebaseStorage;
   // StorageReference storageReference;
    FirebaseFirestore db;
     ProgressDialog progressDoalog;
    private ProgressBar progressbar;
   // private FirebaseAuth mAuth;

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
       // firebaseStorage = FirebaseStorage.getInstance();
      //  storageReference = FirebaseStorage.getInstance().getReference();
        binding = FragmentRegisterBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      //  mAuth = FirebaseAuth.getInstance();
        Random random = new Random();
        int number = random.nextInt(1000000);
        binding.tvUid.setText("UserId : " + number + "");
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.etName.getText().toString().isEmpty() ||
                        !binding.etName.getText().toString().matches(Validation.text))
                    binding.etName.setError("Enter your name");
                else if (binding.etAge.getText().toString().isEmpty()|| binding.etAge.getText().toString().length()>2)
                    binding.etAge.setError("Enter your Age");
                else if (binding.etPhone.getText().toString().isEmpty() || binding.etPhone.getText().toString().length()>10
                || binding.etPhone.getText().toString().length()<10)
                    binding.etPhone.setError("Enter your valid 10 digit phone number");
                else if (binding.etAddress.getText().toString().isEmpty())
                    binding.etAddress.setError("Enter your address");
                else if (binding.etUsername.getText().toString().isEmpty())
                    binding.etUsername.setError("Enter your username");
                else if (binding.etPassword.getText().toString().isEmpty())
                    binding.etPassword.setError("Enter your password");
                else {
                    db = FirebaseFirestore.getInstance();
                    try {
                        progressDoalog = new ProgressDialog(requireContext());
                        progressDoalog.setMessage("Checking....");
                        progressDoalog.setTitle("Please wait");
                        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDoalog.show();
                        db.collection("User").whereEqualTo("username", binding.etUsername.getText().toString()).get().
                                addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        if (queryDocumentSnapshots.getDocuments().isEmpty()) {
                                            userRegistration(number);
                                        } else {
                                            Toast.makeText(requireContext(), "Please Take Another Username", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }).
                                addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        //userRegistration();
                                        Toast.makeText(requireContext(), "Creation failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                        progressDoalog.dismiss();
                    } catch (Exception e) {
                        progressDoalog.dismiss();
                        Log.d("exception", "Exception" + e.toString());
                    }
                }
            }
        });

    }

    private void userRegistration(int number) {
        progressDoalog = new ProgressDialog(requireContext());
        progressDoalog.setMessage("Checking....");
        progressDoalog.setTitle("Please wait");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        String username, name, mobile, password,age,address;
        username = binding.etUsername.getText().toString();
        password = binding.etPassword.getText().toString();
        name = binding.etName.getText().toString();
        mobile = binding.etPhone.getText().toString();
        age = binding.etAge.getText().toString();
        address = binding.etAddress.getText().toString();
      FirebaseFirestore db2;
        PatientModel obj = new PatientModel(number + "", "patient", name,age,address, mobile, username, password);
        db2 = FirebaseFirestore.getInstance();
        db2.collection("User").add(obj).
                addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        progressDoalog.dismiss();
                        binding.tvUid.setText("");
                        binding.etName.setText("");
                        binding.etPhone.setText("");
                        binding.etUsername.setText("");
                        binding.etPassword.setText("");
                        Snackbar.make(requireView(), "Registration Successfull", Snackbar.LENGTH_LONG).show();
                        //;
                    }
                }).
                addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireContext(), "Creation failed", Toast.LENGTH_SHORT).show();
                    }
                });
        db.collection("Patients").add(obj).
                addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "onSuccess: Success");
                        //Navigation.findNavController(getView()).navigateUp();
                        Navigation.findNavController(getView()).navigate(R.id.action_registerFragment_to_loginFragment);
                    }
                }).
                addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "onSuccess: Fail");
                    }
                });

    }


}