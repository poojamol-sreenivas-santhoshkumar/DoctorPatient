package uk.ac.tees.W9581934.Admin;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import uk.ac.tees.W9581934.Adapters.CallBackTwice;
import uk.ac.tees.W9581934.Adapters.DataAdapter;
import uk.ac.tees.W9581934.Models.DataModel;
import uk.ac.tees.W9581934.databinding.FragmentBottomSheetBinding;


public class BottomSheetFragment extends BottomSheetDialogFragment implements CallBackTwice {
    FragmentBottomSheetBinding binding;
    DataAdapter adapter;
    List<DataModel> routeList = new ArrayList();
    private CallBackTwice mAdapterCallback;
    String trainNumber, sheetType;
    Context ctx;

    public BottomSheetFragment(Context context, CallBackTwice callback, String s, String stop) {
        this.mAdapterCallback = callback;
        this.trainNumber = s;
        this.ctx = context;
        this.sheetType = stop;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBottomSheetBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.rvData.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new DataAdapter(this);

            showData();
    }


    private void showData() {
        //Log.d("@", "showData: Called")

        routeList.clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Department")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.getDocuments().size() > 0) {
                            Log.d("@", queryDocumentSnapshots.getDocuments().size() + "");
                            int i;
                            for (i = 0; i < queryDocumentSnapshots.getDocuments().size(); i++) {
                                routeList.add(new DataModel(
                                        queryDocumentSnapshots.getDocuments().get(i).getString("dname")

                                ));
                                Log.d("@", queryDocumentSnapshots.getDocuments().get(i).getString("dname") + "");
                            }
                            adapter.routeList = routeList;
                            binding.rvData.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        } else {
                            Snackbar.make(requireView(), "No Department available", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("@q", e + "");
                        Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onStopCallback(String routeName) {
      //  RegisterDoctorFragment.selectedValue = routeName;
        mAdapterCallback.onStopCallback(routeName);
        dismiss();
        //  Toast.makeText(requireActivity(),"click",Toast.LENGTH_LONG).show();
    }
}