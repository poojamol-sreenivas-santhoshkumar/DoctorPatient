package uk.ac.tees.W9581934.Admin;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import uk.ac.tees.W9581934.Adapters.FeedbackAdapter;
import uk.ac.tees.W9581934.Adapters.PatientAdapter;
import uk.ac.tees.W9581934.Models.FeedbackModel;
import uk.ac.tees.W9581934.Models.PatientModel;
import uk.ac.tees.W9581934.R;
import uk.ac.tees.W9581934.databinding.FragmentBookingListBinding;
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
                Navigation.findNavController(getView()).navigateUp();
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
        for(int i=0;i<10;i++) {
            feedbackList.add(new FeedbackModel("manu","Good Service"));
        }
        binding.rvFeedbacks.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter.feedList=feedbackList;
        binding.rvFeedbacks.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}