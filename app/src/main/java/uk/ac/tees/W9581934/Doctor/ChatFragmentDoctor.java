package uk.ac.tees.W9581934.Doctor;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import uk.ac.tees.W9581934.Adapters.BookingAdapter;
import uk.ac.tees.W9581934.Models.BookingModel;
import uk.ac.tees.W9581934.R;
import uk.ac.tees.W9581934.databinding.FragmentChatDoctorBinding;
import uk.ac.tees.W9581934.databinding.FragmentDoctorDashboardBinding;

public class ChatFragmentDoctor extends Fragment {
    String dic_id,cdate;
    FragmentChatDoctorBinding binding;
    BookingAdapter adapter;
    List<BookingModel> bookingList = new ArrayList();
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
        binding = FragmentChatDoctorBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sp = getContext().getSharedPreferences("logDetails", Context.MODE_PRIVATE);
        dic_id = sp.getString("userId", "error");
        adapter = new BookingAdapter(sp.getString("userType", "error"), getContext());
        binding.rvBookings.setLayoutManager(new LinearLayoutManager(requireContext()));
        cdate=getArguments().getString("cdate");
        Log.d("##",dic_id );
        Log.d("##",cdate );
        showData();
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
                .whereEqualTo("bookingDate",cdate)
                .whereEqualTo("bookingType","Online")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        int i;
                        for (i = 0; i < queryDocumentSnapshots.getDocuments().size(); i++) {
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
                        Log.d("##", bookingList.size() + "");
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
}