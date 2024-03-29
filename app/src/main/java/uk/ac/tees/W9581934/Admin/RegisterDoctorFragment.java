package uk.ac.tees.W9581934.Admin;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import uk.ac.tees.W9581934.Adapters.CallBackTwice;
import uk.ac.tees.W9581934.Models.DoctorListModel;
import uk.ac.tees.W9581934.Models.PatientModel;
import uk.ac.tees.W9581934.Models.Validation;
import uk.ac.tees.W9581934.R;
import uk.ac.tees.W9581934.databinding.FragmentFeedbackBinding;
import uk.ac.tees.W9581934.databinding.FragmentRegisterDoctorBinding;


public class RegisterDoctorFragment extends Fragment implements CallBackTwice {
    public static String selectedValue;
    FragmentRegisterDoctorBinding binding;
    private CallBackTwice mAdapterCallback;
    int pictureCode = 101;
    int cameracode = 100;
    ProgressDialog progressDoalog;
    Uri filePath=null;
    private ProgressBar progressbar;
    // private FirebaseAuth mAuth;
    String mediaPath = null;

    String encodedImage = "";
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private Bitmap bitmapProfile = null;
    private static int RESULT_LOAD_IMAGE = 1;
    private String userChosenTask;

    int newWidth = 200;
    String gen;
    int newHeight = 200;

    Matrix matrix;

    Bitmap resizedBitmap;

    private int mYear, mMonth, mDay, mHour, mMinute;
    String loc;
    float scaleWidth;

    float scaleHeight;

    ByteArrayOutputStream outputStream;
    DatePickerDialog datePickerDialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(getView()).navigate(R.id.action_registerDoctorFragment_to_adminHomeFragment);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // firebaseStorage = FirebaseStorage.getInstance();
        // storageReference = FirebaseStorage.getInstance().getReference();
        binding = FragmentRegisterDoctorBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapterCallback = this;
        Random random = new Random();
        int number = random.nextInt(1000000);
        binding.etDocId.setText("UserId : " + number + "");
        binding.cameraImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PselectImage();
            }
        });

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.etDocId.getText().toString().isEmpty())
                    binding.etDocId.setError("Enter a Unique Id for this new Doctor");
                else if (binding.etDocName.getText().toString().isEmpty())
                    binding.etDocName.setError("Enter doctor name");
                else if (binding.etDocAge.getText().toString().isEmpty() || binding.etDocAge.getText().toString().length() < 2)
                    binding.etDocAge.setError("Enter doctor age");
                else if (binding.etDocDob.getText().toString().isEmpty())
                    binding.etDocDob.setError("Enter a doctor date of birth");
                else if (binding.etDepartment.getText().toString().isEmpty())
                    binding.etDepartment.setError("Enter department name");
                else if (binding.etSpecialization.getText().toString().isEmpty())
                    binding.etSpecialization.setError("Enter Specialization");
                else if (binding.etExperience.getText().toString().isEmpty())
                    binding.etExperience.setError("Enter no of working experience");
                else if (binding.etDocMobile.getText().toString().isEmpty() ||
                        !binding.etDocMobile.getText().toString().matches(Validation.mobile))
                    binding.etDocMobile.setError("Enter doctor mobile number");
                else if (binding.Time.getText().toString().isEmpty())
                    binding.Time.setError("Enter doctor consultation start time");
                else if (binding.endTime.getText().toString().isEmpty())
                    binding.endTime.setError("Enter doctor consultation end time");
                else if (binding.etDocDays.getText().toString().isEmpty())
                    binding.etDocDays.setError("Enter doctor Available days");
                else if (binding.etDocUsername.getText().toString().isEmpty())
                    binding.etDocUsername.setError("Enter doctor username");
                else if (binding.etDocPassword.getText().toString().isEmpty())
                    binding.etDocPassword.setError("Enter doctor password");
                else if (encodedImage.isEmpty())
                    Toast.makeText(requireContext(), "Choose Image", Toast.LENGTH_LONG).show();
                else {
                    FirebaseFirestore db;
                    db = FirebaseFirestore.getInstance();
                    //checking username
                    try {
                      /*  progressDoalog = new ProgressDialog(requireContext());
                        progressDoalog.setMessage("Checking....");
                        progressDoalog.setTitle("Please wait");
                        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDoalog.show();*/
                        db.collection("User").whereEqualTo("username", binding.etDocUsername.getText().toString()).get().
                                addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        if (queryDocumentSnapshots.getDocuments().isEmpty()) {
                                            DoctorRegistration(number);
                                            Log.d("call", "fn-called");
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

                    } catch (Exception e) {

                        Log.d("exception", "Exception" + e.toString());
                    }

                }
            }
        });

        binding.etDocDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker();
            }
        });


        binding.Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tiemPicker();
            }
        });

        binding.endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endtimePicker();
            }
        });

        binding.etDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetFragment bottomSheet = new BottomSheetFragment(requireContext(), mAdapterCallback, "", "");
                bottomSheet.show(getChildFragmentManager(), "BottomSheet");
            }
        });
    }


    private void DoctorRegistration(int number) {
        final ProgressDialog progressDoalog = new ProgressDialog(requireContext());
        progressDoalog.setMessage("Creating....");
        progressDoalog.setTitle("Please wait");
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        Log.d("call", "fn-inside ");
        String doc_username, doc_name, doc_mobile, doc_password, doc_age, doc_stream, doc_image, doc_dept, doc_dob, doc_exp, doc_stime, doc_etime, doc_availability;
        doc_username = binding.etDocUsername.getText().toString();
        doc_password = binding.etDocPassword.getText().toString();
        doc_name = binding.etDocName.getText().toString();
        doc_mobile = binding.etDocMobile.getText().toString();
        doc_age = binding.etDocAge.getText().toString();
        doc_stream = binding.etSpecialization.getText().toString();
        doc_dept = binding.etDepartment.getText().toString();
        doc_dob = binding.etDocDob.getText().toString();
        doc_exp = binding.etExperience.getText().toString();
        doc_stime = binding.Time.getText().toString();
        doc_etime = binding.endTime.getText().toString();
        doc_availability = binding.etDocDays.getText().toString();
        doc_image = encodedImage;
        DoctorListModel obj = new DoctorListModel("",number + "", doc_name, doc_dept,
                doc_stream, doc_dob, doc_age, doc_stime, doc_availability, doc_username, doc_mobile, "doctor", doc_image,
                doc_password, doc_exp, doc_etime);
        FirebaseFirestore db2 = FirebaseFirestore.getInstance();
        db2.collection("Doctors").add(obj).
                addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                         @Override
                                         public void onSuccess(DocumentReference documentReference) {

                                             Log.d("TAG", "onSuccess: Success");
                                             //progressDoalog.dismiss();
                                             //Snackbar.make(requireView(), "Doctor added Successfully", Snackbar.LENGTH_LONG).show();

                                             DoctorListModel obj1 = new DoctorListModel("",number + "", doc_name, doc_dept,
                                                     doc_stream, doc_dob, doc_age, doc_stime, doc_availability, doc_username, doc_mobile, "doctor", "",
                                                     doc_password, doc_exp, doc_etime);
                                             db2.collection("User").add(obj1).
                                                     addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                         @Override
                                                         public void onSuccess(DocumentReference documentReference) {
                                                             binding.etDocId.setText("");
                                                             binding.etDocAge.setText("");
                                                             binding.etDocDob.setText("");
                                                             binding.etDocName.setText("");
                                                             binding.etDocMobile.setText("");
                                                             binding.etDocUsername.setText("");
                                                             binding.etDocPassword.setText("");
                                                             binding.etDepartment.setText("");
                                                             binding.etDocAge.setText("");
                                                             binding.etDocDays.setText("");
                                                             binding.endTime.setText("");
                                                             binding.Time.setText("");
                                                             binding.etExperience.setText("");
                                                             progressDoalog.dismiss();
                                                             Snackbar.make(requireView(), "Doctor added Successfully", Snackbar.LENGTH_LONG).show();
                                                             Navigation.findNavController(getView()).navigateUp();
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
                ).
                addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "onRegistration Try: Fail");
                    }
                });


    }

    private void PselectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        //final CharSequence[] items = {"Take Photo", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Upload your documents");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                // boolean result = Utility.checkPermission(Register.this);
                if (items[item].equals("Take Photo")) {
                    userChosenTask = "Take Photo";
                    // if (result)
                    // cameraIntent();
                    selectCamera();
                } else if (items[item].equals("Choose from Library")) {
                    userChosenTask = "Choose from Library";
                    // if (result)
                    // galleryIntent();
                    selectImage();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                    Log.d("dialog dismiss ", "true");
                }
            }
        });
        builder.show();
    }
    private void selectImage(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Pic"),pictureCode);
    }
    private void selectCamera(){
        Intent intent=new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,cameracode);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == pictureCode && resultCode == Activity.RESULT_OK) {

            if(data==null ||data.getData().equals(null)){
                return;
            }
            filePath = data.getData();
            try {
                Bitmap bitmap=MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),filePath);
                binding.image.setImageBitmap(bitmap);
                uploadImage();
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }
        else if(requestCode == cameracode && resultCode == Activity.RESULT_OK){
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            try {
                binding.image.setImageBitmap(thumbnail);
                filePath=  getImageUri(getContext(),thumbnail);

                uploadImage();
            }
            catch (Exception e){

            }
        }
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void uploadImage(){
        Log.d("## filepath: inside", filePath+"");
        if(filePath != null){
            progressDoalog = new ProgressDialog(requireContext());
            progressDoalog.setMessage("Uploading....");
            progressDoalog.setTitle("Please wait");
            progressDoalog.setCancelable(false);
            progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDoalog.show();
            Log.d("## filepath:", filePath+"");
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReference();
            storageReference = storageReference.child("doctor_Images/" + UUID.randomUUID().toString());
            UploadTask uploadTask=storageReference.putFile(filePath);
            // encodedImage= storageReference.toString();
            Log.d("## encodedImage:", encodedImage+"");
            // Toast.makeText(requireContext(), encodedImage, Toast.LENGTH_SHORT).show();
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            String uploadedImageUrl = task.getResult().toString();
                            Log.d("##", uploadedImageUrl);
                            encodedImage=uploadedImageUrl;
                            progressDoalog.dismiss();
                        }
                    });
                }
            });

        }else{
            Toast.makeText(requireContext(), "Please Upload an Image", Toast.LENGTH_SHORT).show();

        }
    }

    private void datePicker() {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if (monthOfYear < 10 && dayOfMonth < 10) {
                    binding.etDocDob.setText("0" + dayOfMonth + "-0" + (monthOfYear + 1) + "-" + year);
                }
                if (monthOfYear < 10 && dayOfMonth >= 10) {
                    binding.etDocDob.setText(dayOfMonth + "-0" + (monthOfYear + 1) + "-" + year);
                }
                if (monthOfYear >= 10 && dayOfMonth < 10) {
                    binding.etDocDob.setText("0" + dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                }
                if (monthOfYear >= 10 && dayOfMonth >= 10) {
                    binding.etDocDob.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                }
            }
        }, mYear, mMonth, mDay);
        //  datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private void tiemPicker() {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        // loc=c.get(Calendar.AM_PM);
        TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mHour = hourOfDay;
                mMinute = minute;
                if (hourOfDay >= 12) {
                    loc = "Pm";
                } else {
                    loc = "Am";
                }
                binding.Time.setText(hourOfDay + ":" + minute + " " + loc);
            }
        }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    private void endtimePicker() {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mHour = hourOfDay;
                mMinute = minute;
                if (hourOfDay >= 12) {
                    loc = "Pm";
                } else {
                    loc = "Am";
                }
                binding.endTime.setText(hourOfDay + ":" + minute + " " + loc);
            }
        }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    @Override
    public void onStopCallback(String routeName) {
        binding.etDepartment.setText(routeName);
    }


}