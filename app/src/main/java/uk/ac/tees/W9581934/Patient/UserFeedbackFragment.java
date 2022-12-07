package uk.ac.tees.W9581934.Patient;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import uk.ac.tees.W9581934.Models.DeptModel;
import uk.ac.tees.W9581934.Models.FeedbackModel;
import uk.ac.tees.W9581934.R;
import uk.ac.tees.W9581934.databinding.FragmentUserBookingBinding;
import uk.ac.tees.W9581934.databinding.FragmentUserFeedbackBinding;


public class UserFeedbackFragment extends Fragment {
    FragmentUserFeedbackBinding binding;
  //  FirebaseStorage firebaseStorage;
   // StorageReference storageReference;
    FirebaseFirestore db;
    ProgressDialog progressDoalog;
    private ProgressBar progressbar;
   // private FirebaseAuth mAuth;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback( this,new OnBackPressedCallback(true){
            @Override
            public void handleOnBackPressed() {
                Toast.makeText(getContext(), "please logout to Exit",Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentUserFeedbackBinding.inflate(getLayoutInflater(),container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.etFeedback.getText().toString().isEmpty())
                    binding.etFeedback.setError("Enter your complaints/feedbacks");
                else{
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                     sendfeedback();
                    binding.etFeedback.setText("");
                }
            }
        });
    }
    private void sendfeedback() {
        progressDoalog = new ProgressDialog(requireContext());
        progressDoalog.setMessage("Submitting....");
        progressDoalog.setTitle("Please wait");
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        String feedback;
        SharedPreferences sp = getContext().getSharedPreferences("logDetails", Context.MODE_PRIVATE);

        feedback = binding.etFeedback.getText().toString();
        fireStoreDatabase:
        FirebaseFirestore.getInstance();
        FeedbackModel obj = new FeedbackModel(sp.getString("name",""), feedback);
        db = FirebaseFirestore.getInstance();
        db.collection("Feedback").add(obj).
                addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        binding.etFeedback.setText("");
                        progressDoalog.dismiss();
                        Snackbar.make(requireView(), "Feedback send Successfully", Snackbar.LENGTH_LONG).show();
                    }
                }).
                addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireContext(), "Creation failed", Toast.LENGTH_SHORT).show();
                    }
                });


    }

}