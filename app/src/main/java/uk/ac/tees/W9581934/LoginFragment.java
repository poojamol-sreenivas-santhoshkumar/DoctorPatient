package uk.ac.tees.W9581934;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;


import java.util.List;

import uk.ac.tees.W9581934.databinding.FragmentLoginBinding;


public class LoginFragment extends Fragment {
    FragmentLoginBinding binding;

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(getLayoutInflater(), container, false);
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
        requestPermission();
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

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
                    String username = binding.etEmail.getText().toString();
                    String password = binding.etPassword.getText().toString();
                    FirebaseFirestore db;
                    db=FirebaseFirestore.getInstance();

                    if (username.equals("admin") && password.equals("admin")) {
                        sp = getContext().getSharedPreferences("logDetails", Context.MODE_PRIVATE);
                        editor = sp.edit();
                        editor.putString("userType", "admin");
                        editor.commit();
                        binding.etEmail.setText("");
                        binding.etPassword.setText("");
                        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_adminHomeFragment);
                        progressDoalog.dismiss();
                    } else {

                        try {
                            db.collection("User").whereEqualTo("username", username).whereEqualTo("password", password)
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            try {

                                                if (queryDocumentSnapshots.getDocuments().isEmpty()) {

                                                    Toast.makeText(requireContext(), "invalid  credentials", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    sp = getContext().getSharedPreferences("logDetails", Context.MODE_PRIVATE);
                                                    editor = sp.edit();

                                                    Log.d("##", queryDocumentSnapshots.getDocuments().get(0).getString("userId") + "");
                                                        editor.putString("userType", queryDocumentSnapshots.getDocuments().get(0).getString("type"));
                                                        editor.putString("name", queryDocumentSnapshots.getDocuments().get(0).getString("name"));
                                                        editor.putString("mobile", queryDocumentSnapshots.getDocuments().get(0).getString("phone"));
                                                        editor.putString("userId", queryDocumentSnapshots.getDocuments().get(0).getString("userId"));
                                                    editor.commit();

                                                    if (queryDocumentSnapshots.getDocuments().get(0).getString("type").equals("patient")) {

                                                        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_patientHome);
                                                        progressDoalog.dismiss();
                                                    } else if (queryDocumentSnapshots.getDocuments().get(0).getString("type").equals("doctor")) {

                                                         Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_doctorDashboard);
                                                        progressDoalog.dismiss();
                                                    }

                                                    binding.etEmail.setText("");
                                                    binding.etPassword.setText("");
                                                }
                                                progressDoalog.dismiss();
                                            } catch (Exception e) {
                                                progressDoalog.dismiss();
                                                Log.d("exception: ", e.toString());
                                            }


                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDoalog.dismiss();

                                            Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } catch (Exception e) {
                            Log.d("exception: ", e.toString());
                        }
                    }
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
    public void requestPermission() {

        //Multiple permission request
        Dexter.withActivity(getActivity())
                .withPermissions(
                        Manifest.permission.SEND_SMS,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(
                            MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            // Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }



                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }


    /**
     * Showing Alert Dialog with Settings option in case of deny any permission
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getContext().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

}