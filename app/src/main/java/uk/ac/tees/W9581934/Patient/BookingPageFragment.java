package uk.ac.tees.W9581934.Patient;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;

import uk.ac.tees.W9581934.Models.BookingModel;
import uk.ac.tees.W9581934.R;
import uk.ac.tees.W9581934.databinding.FragmentBookingPageBinding;

public class BookingPageFragment extends Fragment {
    FragmentBookingPageBinding binding;
    private int mYear, mMonth, mDay, mHour, mMinute;
    String loc;
    DatePickerDialog datePickerDialog;
    String selectedVal, date, drname, drdpt, name, mobile;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBookingPageBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //getting dr details
        SharedPreferences doctor = getContext().getSharedPreferences("dr", Context.MODE_PRIVATE);
        drname = doctor.getString("drname", "error");
        drdpt = doctor.getString("drdpt", "error");
        binding.etDate.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                datePicker();
            }
        });


        //getting user details
        SharedPreferences sp = getContext().getSharedPreferences("logDetails", Context.MODE_PRIVATE);
        name = sp.getString("name", "error");
        mobile = sp.getString("mobile", "error");

        binding.btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.radioOnline.isChecked()) {
                    selectedVal = binding.radioOnline.getText().toString();
                }
                else if (binding.radioOffline.isChecked()) {
                    selectedVal = binding.radioOffline.getText().toString();
                }
                if (binding.etDate.getText().toString().equals("")){
                    Toast.makeText(requireContext(),"Select date & type",Toast.LENGTH_LONG).show();
                }
                else {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("Bookings").whereEqualTo("doc_name", drname)
                            .whereEqualTo("bookingDate", binding.etDate.getText().toString()).get().
                            addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    int size = queryDocumentSnapshots.getDocuments().size();
                                    Log.d("##", size+"");
                               /* if (queryDocumentSnapshots.getDocuments().size() > 0 ) {
                                    Toast.makeText(requireContext(), "Sorry, Already booked", Toast.LENGTH_SHORT).show();
                                    //addfirstuser

                                }*/
                                    if (queryDocumentSnapshots.getDocuments().size() == 10) {
                                        Toast.makeText(requireContext(), "Sorry, Booking Full", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        db.collection("Bookings").whereEqualTo("doc_name", drname).
                                                whereEqualTo("patient_name", name)
                                                .whereEqualTo("bookingDate", binding.etDate.getText().toString()).get().
                                                addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                        if (queryDocumentSnapshots.getDocuments().size() >0) {
                                                            Toast.makeText(requireContext(), "Sorry,  Already booked", Toast.LENGTH_SHORT).show();
                                                        }
                                                        else{
                                                            firstBooking(size);
                                                            Log.d("call", "fn-called");
                                                        }
                                                    }
                                                });

                                    }
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

    private void firstBooking(int size) {
        String val=size+1+"";
        binding.tvToken.setText("Token Number :-"+val);
        final ProgressDialog progressDoalog = new ProgressDialog(requireContext());
        progressDoalog.setMessage("Checking....");
        progressDoalog.setTitle("Please wait");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        Log.d("call", "fn-inside ");
        BookingModel obj1 = new BookingModel(drname, name, val, mobile, binding.etDate.getText().toString(), selectedVal, drdpt);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Bookings").add(obj1).
                addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                         @Override
                                         public void onSuccess(DocumentReference documentReference) {
                                             Log.d("TAG", "onSuccess: Success");
                                             progressDoalog.dismiss();
                                             Snackbar.make(requireView(), "Booking Successfull", Snackbar.LENGTH_LONG).show();
                                             Navigation.findNavController(getView()).navigate(R.id.action_bookingPageFragment2_to_myBookingsFragment);
                                         }
                                     }
                ).
                addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "onbooking Try: Fail");
                    }
                });

    }

   /* private void Booking(int token) {
        final ProgressDialog progressDoalog = new ProgressDialog(requireContext());
        progressDoalog.setMessage("Checking....");
        progressDoalog.setTitle("Please wait");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        Log.d("call", "fn-inside ");
        BookingModel obj1 = new BookingModel(drname, name, token+1+"", mobile, date, selectedVal, drdpt);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Bookings").add(obj1).
                addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                         @Override
                                         public void onSuccess(DocumentReference documentReference) {
                                             Log.d("TAG", "onSuccess: Success");
                                             progressDoalog.dismiss();
                                             Snackbar.make(requireView(), "Booking Successfull", Snackbar.LENGTH_LONG).show();
                                         }
                                     }
                ).
                addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "onbooking Try: Fail");
                    }
                });

    }*/
}