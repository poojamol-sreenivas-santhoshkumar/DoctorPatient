package uk.ac.tees.W9581934.Patient;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import uk.ac.tees.W9581934.Adapters.FeedbackAdapter;
import uk.ac.tees.W9581934.Adapters.ViewPagerAdapter;
import uk.ac.tees.W9581934.Models.FeedbackModel;
import uk.ac.tees.W9581934.Models.ViewPagerModel;
import uk.ac.tees.W9581934.R;
import uk.ac.tees.W9581934.databinding.FragmentDashboardBinding;


public class DashboardFragment extends Fragment {
    FragmentDashboardBinding binding;
    ViewPagerAdapter adapter = new ViewPagerAdapter();
    List<ViewPagerModel> feedbackList = new ArrayList();

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
        binding = FragmentDashboardBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        feedbackList.add(new ViewPagerModel("https://i.pinimg.com/236x/78/75/e3/7875e393a9b01d3c26fb6dcee69f9591.jpg",
                "https://i.pinimg.com/236x/7e/28/e5/7e28e596533e8f617c4090c1679da254.jpg",
                "https://i.pinimg.com/236x/c9/2d/e8/c92de88331ea792f7e10a05753aa8fa2.jpg"));

        feedbackList.add(new ViewPagerModel("https://i.pinimg.com/236x/c9/2d/e8/c92de88331ea792f7e10a05753aa8fa2.jpg",
                "https://i.pinimg.com/236x/78/75/e3/7875e393a9b01d3c26fb6dcee69f9591.jpg",
                "https://i.pinimg.com/236x/76/27/44/7627442d867d8e013cb0d8e6f49fe026.jpg"));

        feedbackList.add(new ViewPagerModel("https://i.pinimg.com/236x/97/c9/02/97c902ad27c8dc4a57e8eb50abd43a87.jpg",
                "https://i.pinimg.com/236x/cb/2c/16/cb2c1682098d4c052aee6f3a23413ef6.jpg",
                "https://i.pinimg.com/236x/76/27/44/7627442d867d8e013cb0d8e6f49fe026.jpg"));

        feedbackList.add(new ViewPagerModel("https://i.pinimg.com/236x/cb/2c/16/cb2c1682098d4c052aee6f3a23413ef6.jpg",
                "https://i.pinimg.com/236x/76/27/44/7627442d867d8e013cb0d8e6f49fe026.jpg",
                ""));
        //binding.viewPager.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter.feedList = feedbackList;
        binding.viewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            // This method is triggered when there is any scrolling activity for the current page
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            // triggered when you select a new page
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }

            // triggered when there is
            // scroll state will be changed
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }
}