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

import android.text.Editable;
import android.text.TextWatcher;
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

import java.util.ArrayList;
import java.util.List;

import uk.ac.tees.W9581934.Adapters.DoctorListAdapter;
import uk.ac.tees.W9581934.Models.DoctorListModel;
import uk.ac.tees.W9581934.databinding.FragmentUserBookingBinding;


public class BookDoctor extends Fragment {
    FragmentUserBookingBinding binding;
    DoctorListAdapter adapter;
    List<DoctorListModel> DoctorList = new ArrayList();
    public List<DoctorListModel> exampleListFull= new ArrayList();
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
        binding=FragmentUserBookingBinding.inflate(getLayoutInflater(),container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sp = getContext().getSharedPreferences("logDetails", Context.MODE_PRIVATE);
        adapter=new DoctorListAdapter(sp.getString("userType","error"),getContext());
        binding.rcDoctors.setLayoutManager(new LinearLayoutManager(requireContext()));
        showData();

        binding.btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                Log.d("@", "showData: Called"+binding.etSearch.getText().toString());
               // showOnSearch(binding.etSearch.getText().toString());
            }
        });
    }

    private void showData() {
        //Log.d("@", "showData: Called")

        final ProgressDialog progressDoalog = new ProgressDialog(requireContext());
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait");
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Doctors")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d("@", queryDocumentSnapshots + "");
                        int i;
                        DoctorList.clear();
                        exampleListFull.clear();
                        for (i = 0; i < queryDocumentSnapshots.getDocuments().size(); i++) {

                            DoctorList.add(new DoctorListModel(
                                    queryDocumentSnapshots.getDocuments().get(i).getId(),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("userId"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("name"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("departmentName"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("specializedStream"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("dob"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("age"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("consultingTime"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("availabilityDays"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("username"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("phone"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("type"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("doctorImage"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("password"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("experience"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("endtime")
                            ));
                        }
                        if (DoctorList.isEmpty()){
                            binding.rcDoctors.setVisibility(View.GONE);
                            binding.labelNoData.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            binding.rcDoctors.setVisibility(View.VISIBLE);
                            binding.labelNoData.setVisibility(View.INVISIBLE);
                        }
                        progressDoalog.dismiss();
                    //    Log.d("@",  DoctorList+"");
                        adapter.doctorList=DoctorList;
                        exampleListFull=DoctorList;
                        adapter.exampleListFull=exampleListFull;
                        binding.rcDoctors.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        binding.etSearch.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if(s.toString().isEmpty()){
                                    showData();
                                }
                                //after the change calling the method and passing the search input
                                adapter.getFilter().filter(s.toString());

                            }
                        });


                }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                });

    }
//    private void showOnSearch(String s) {
//        Log.d("@", "showData: Called"+binding.etSearch.getText().toString());
//        DoctorList.clear();
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//        db.collection("Doctors").whereEqualTo("doctorName",binding.etSearch.getText().toString())
//                .get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        if(queryDocumentSnapshots.getDocuments().size()>0) {
//
//
//                            Log.d("@", queryDocumentSnapshots + "");
//                            int i;
//                            for (i = 0; i < queryDocumentSnapshots.getDocuments().size(); i++) {
//                            /*Log.d("!", queryDocumentSnapshots.getDocuments().get(i).getId());
//                            Log.d("!", queryDocumentSnapshots.getDocuments().get(i).getString("foodName"));
//                            Log.d("!", queryDocumentSnapshots.getDocuments().get(i).getString("foodPrice"));*/
//                                DoctorList.add(new DoctorListModel(
//                                        queryDocumentSnapshots.getDocuments().get(i).getString("doc_id"),
//                                        queryDocumentSnapshots.getDocuments().get(i).getString("doctorName"),
//                                        queryDocumentSnapshots.getDocuments().get(i).getString("departmentName"),
//                                        queryDocumentSnapshots.getDocuments().get(i).getString("specializedStream"),
//                                        queryDocumentSnapshots.getDocuments().get(i).getString("dob"),
//                                        queryDocumentSnapshots.getDocuments().get(i).getString("age"),
//                                        queryDocumentSnapshots.getDocuments().get(i).getString("consultingTime"),
//                                        queryDocumentSnapshots.getDocuments().get(i).getString("availabilityDays"),
//                                        queryDocumentSnapshots.getDocuments().get(i).getString("userName"),
//                                        queryDocumentSnapshots.getDocuments().get(i).getString("mobileNumber"),
//                                        queryDocumentSnapshots.getDocuments().get(i).getString("type"),
//                                        queryDocumentSnapshots.getDocuments().get(i).getString("doctorImage"),
//                                        queryDocumentSnapshots.getDocuments().get(i).getString("password"),
//                                        queryDocumentSnapshots.getDocuments().get(i).getString("experience"),
//                                        queryDocumentSnapshots.getDocuments().get(i).getString("endtime")
//                                ));
//                            }
//
//                            adapter.doctorList = DoctorList;
//                            binding.rcDoctors.setAdapter(adapter);
//                            adapter.notifyDataSetChanged();
//                        }
//                        else
//                        {
//                            Toast.makeText(getContext(), "No data Available", Toast.LENGTH_SHORT).show();
//
//                        }
//                        }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(getContext(), "No data Available", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//    }
}