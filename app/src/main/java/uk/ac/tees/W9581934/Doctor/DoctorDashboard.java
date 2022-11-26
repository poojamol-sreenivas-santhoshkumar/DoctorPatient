package uk.ac.tees.W9581934.Doctor;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import uk.ac.tees.W9581934.Adapters.BookingAdapter;
import uk.ac.tees.W9581934.Models.BookingModel;
import uk.ac.tees.W9581934.R;
import uk.ac.tees.W9581934.databinding.FragmentDoctorDashboardBinding;
import uk.ac.tees.W9581934.databinding.FragmentDoctorHomeBinding;


public class DoctorDashboard extends Fragment {
    FragmentDoctorDashboardBinding binding;
    BookingAdapter adapter;
    private int mYear, mMonth, mDay, mHour, mMinute;
    String loc;
    DatePickerDialog datePickerDialog;
    List<BookingModel> bookingList = new ArrayList();
    String dic_id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                AlertDialog.Builder alertbox = new AlertDialog.Builder(requireContext());
                alertbox.setMessage("Do you really wants to logout from this app?");
                alertbox.setTitle("Logout!!");

                alertbox.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Navigation.findNavController(getView()).navigate(R.id.action_doctorDashboard_to_loginFragment);

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
        binding = FragmentDoctorDashboardBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sp = getContext().getSharedPreferences("logDetails", Context.MODE_PRIVATE);
        dic_id = sp.getString("userId", "error");
        adapter = new BookingAdapter(sp.getString("userType", "error"), getContext());
        binding.rvBookings.setLayoutManager(new LinearLayoutManager(requireContext()));
        getProfile();
        showData();
        binding.icOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.etDate.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "please choose a date", Toast.LENGTH_SHORT).show();
                } else {
                    Bundle b=new Bundle();
                            b.putString("cdate",binding.etDate.getText().toString());
                    Navigation.findNavController(view).navigate(R.id.action_doctorDashboard_to_chatFragmentDoctor,b);
                }
            }
        });
        binding.etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker();
            }
        });
        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFilterBookingByDate();
            }
        });
    }

    private void getFilterBookingByDate() {
        final ProgressDialog progressDoalog = new ProgressDialog(requireContext());
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        //Log.d("@", "showData: Called")
        bookingList.clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Bookings").whereEqualTo("bookingDate", binding.etDate.getText().toString().trim())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d("@", queryDocumentSnapshots + "");
                        int i;
                        for (i = 0; i < queryDocumentSnapshots.getDocuments().size(); i++) {
                            /*Log.d("!", queryDocumentSnapshots.getDocuments().get(i).getId());
                            Log.d("!", queryDocumentSnapshots.getDocuments().get(i).getString("foodName"));
                            Log.d("!", queryDocumentSnapshots.getDocuments().get(i).getString("foodPrice"));*/
                            bookingList.add(new BookingModel(queryDocumentSnapshots.getDocuments().get(i).getId(),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("doc_name"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("patient_name"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("tokenNo")
                                    , queryDocumentSnapshots.getDocuments().get(i).getString("patient_phone")
                                    , queryDocumentSnapshots.getDocuments().get(i).getString("bookingDate")
                                    , queryDocumentSnapshots.getDocuments().get(i).getString("bookingType")
                                    , queryDocumentSnapshots.getDocuments().get(i).getString("dept_name"),""

                            ));
                        }
                        progressDoalog.dismiss();
                        adapter.bookingList = bookingList;
                        binding.rvBookings.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void showData() {
        final ProgressDialog progressDoalog = new ProgressDialog(requireContext());
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        //Log.d("@", "showData: Called")
        bookingList.clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Bookings").whereEqualTo("doc_id", dic_id)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d("@", queryDocumentSnapshots + "");
                        int i;
                        for (i = 0; i < queryDocumentSnapshots.getDocuments().size(); i++) {
                            /*Log.d("!", queryDocumentSnapshots.getDocuments().get(i).getId());
                            Log.d("!", queryDocumentSnapshots.getDocuments().get(i).getString("foodName"));
                            Log.d("!", queryDocumentSnapshots.getDocuments().get(i).getString("foodPrice"));*/
                            bookingList.add(new BookingModel(queryDocumentSnapshots.getDocuments().get(i).getId(),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("doc_name"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("patient_name"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("tokenNo")
                                    , queryDocumentSnapshots.getDocuments().get(i).getString("patient_phone")
                                    , queryDocumentSnapshots.getDocuments().get(i).getString("bookingDate")
                                    , queryDocumentSnapshots.getDocuments().get(i).getString("bookingType")
                                    , queryDocumentSnapshots.getDocuments().get(i).getString("dept_name"),""

                            ));
                        }
                        progressDoalog.dismiss();
                        adapter.bookingList = bookingList;
                        binding.rvBookings.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void getProfile() {
        final ProgressDialog progressDoalog = new ProgressDialog(requireContext());
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        //Log.d("@", "showData: Called")
        bookingList.clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Doctors").whereEqualTo("userId", dic_id)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d("@", queryDocumentSnapshots + "");
                        binding.tvDocName.setText(queryDocumentSnapshots.getDocuments().get(0).getString("name"));
                        binding.tvDocDepartment.setText(queryDocumentSnapshots.getDocuments().get(0).getString("departmentName"));
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        byte[] imageBytes = baos.toByteArray();
                        imageBytes = Base64.decode(queryDocumentSnapshots.getDocuments().get(0).getString("doctorImage"), Base64.DEFAULT);
                        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                        binding.image.setImageBitmap(decodedImage);
                        progressDoalog.dismiss();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                });

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
                    binding.etDate.setText("0" + dayOfMonth + "-0" + (monthOfYear + 1) + "-" + year);
                }
                if (monthOfYear < 10 && dayOfMonth >= 10) {
                    binding.etDate.setText(dayOfMonth + "-0" + (monthOfYear + 1) + "-" + year);
                }
                if (monthOfYear >= 10 && dayOfMonth < 10) {
                    binding.etDate.setText("0" + dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                }
                if (monthOfYear >= 10 && dayOfMonth >= 10) {
                    binding.etDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                }
            }

        }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }
}