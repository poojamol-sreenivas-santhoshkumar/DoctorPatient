package uk.ac.tees.W9581934.Admin;

import android.app.ProgressDialog;
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
import uk.ac.tees.W9581934.Adapters.FeedbackAdapter;
import uk.ac.tees.W9581934.Models.FeedbackModel;
import uk.ac.tees.W9581934.R;
import uk.ac.tees.W9581934.databinding.FragmentFeedbackBinding;

public class FeedbackFragment extends Fragment {
    FragmentFeedbackBinding binding;
    FeedbackAdapter adapter=new FeedbackAdapter();
    List<FeedbackModel> feedbackList = new ArrayList();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback( this,new OnBackPressedCallback(true){
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(getView()).navigate(R.id.action_feedbackFragment_to_adminHomeFragment);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFeedbackBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        for(int i=0;i<10;i++) {
//            feedbackList.add(new FeedbackModel("manu","Good Service"));
//        }
        binding.rvFeedbacks.setLayoutManager(new LinearLayoutManager(requireContext()));
         showData();
    }
    private void showData() {
        final ProgressDialog progressDoalog = new ProgressDialog(requireContext());
        progressDoalog.setMessage("Loading....");
        progressDoalog.setCancelable(false);
        progressDoalog.setTitle("Please wait");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        //Log.d("@", "showData: Called")
        feedbackList.clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Feedback")
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
                            feedbackList.add(new FeedbackModel(
                                    queryDocumentSnapshots.getDocuments().get(i).getString("pname"),
                                    queryDocumentSnapshots.getDocuments().get(i).getString("feedback")
                            ));
                        }
                        if (feedbackList.isEmpty()){
                            binding.rvFeedbacks.setVisibility(View.GONE);
                            binding.labelNoData.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            binding.rvFeedbacks.setVisibility(View.VISIBLE);
                            binding.labelNoData.setVisibility(View.INVISIBLE);
                        }
                        progressDoalog.dismiss();
                        adapter.feedList=feedbackList;
                        binding.rvFeedbacks.setAdapter(adapter);
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