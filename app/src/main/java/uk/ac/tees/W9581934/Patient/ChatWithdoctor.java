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
import uk.ac.tees.W9581934.Adapters.ChatDoctorAdapter;
import uk.ac.tees.W9581934.Models.BookingModel;
import uk.ac.tees.W9581934.R;
import uk.ac.tees.W9581934.databinding.FragmentChatDoctorBinding;
import uk.ac.tees.W9581934.databinding.FragmentChatWithdoctorBinding;


public class ChatWithdoctor extends Fragment {
FragmentChatWithdoctorBinding binding;
    String dic_id,cdate,userId;

    ChatDoctorAdapter adapter;
    List<BookingModel> bookingList = new ArrayList();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentChatWithdoctorBinding.inflate(getLayoutInflater(),container,false);
        return binding.getRoot();
    }
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sp = getContext().getSharedPreferences("logDetails", Context.MODE_PRIVATE);
        userId = sp.getString("userDocID", "error");
        adapter = new ChatDoctorAdapter(sp.getString("userType", "error"), getContext());
        binding.rvBookings.setLayoutManager(new LinearLayoutManager(requireContext()));
//        cdate=getArguments().getString("cdate");
        showData();
    }
    private void showData() {
        final ProgressDialog progressDoalog = new ProgressDialog(requireContext());
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait");
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        //Log.d("@", "showData: Called")
        bookingList.clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Bookings")
                .whereEqualTo("bookingType","Online")
                .whereEqualTo("userId",userId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        int i;
                        for (i = 0; i < queryDocumentSnapshots.getDocuments().size(); i++) {
                            bookingList.add(new BookingModel(queryDocumentSnapshots.getDocuments().get(i).getString("doc_id"),
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