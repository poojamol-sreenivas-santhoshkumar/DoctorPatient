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
import uk.ac.tees.W9581934.Adapters.DoctorListAdapter;
import uk.ac.tees.W9581934.Models.BookingModel;
import uk.ac.tees.W9581934.Models.DoctorListModel;
import uk.ac.tees.W9581934.R;
import uk.ac.tees.W9581934.databinding.FragmentDashboardBinding;
import uk.ac.tees.W9581934.databinding.FragmentMyBookingsBinding;


public class MyBookingsFragment extends Fragment {
    FragmentMyBookingsBinding binding;
    BookingAdapter adapter;
    List<BookingModel> bookingList = new ArrayList();
    String name;
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
        binding= FragmentMyBookingsBinding.inflate(getLayoutInflater(),container,false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sp = getContext().getSharedPreferences("logDetails", Context.MODE_PRIVATE);
        name = sp.getString("name", "error");
        adapter = new BookingAdapter(sp.getString("userType","error"),getContext());
        binding.rcMyBooking.setLayoutManager(new LinearLayoutManager(requireContext()));
        showData();

    }

    private void showData() {
        //Log.d("@", "showData: Called")
        bookingList.clear();
        final ProgressDialog progressDoalog = new ProgressDialog(requireContext());
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Bookings").whereEqualTo("patient_name",name)
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
                            bookingList.add(new BookingModel(
                                    queryDocumentSnapshots.getDocuments().get(i).getId(),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("doc_name"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("patient_name"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("tokenNo"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("patient_phone"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("bookingDate"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("bookingType"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("dept_name"),""

                            ));
                        }
                        progressDoalog.dismiss();
                        adapter.bookingList=bookingList;
                        binding.rcMyBooking.setAdapter(adapter);
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