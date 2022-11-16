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

import uk.ac.tees.W9581934.Adapters.BookingAdapter;
import uk.ac.tees.W9581934.Models.BookingModel;
import uk.ac.tees.W9581934.Models.DoctorListModel;
import uk.ac.tees.W9581934.R;
import uk.ac.tees.W9581934.databinding.FragmentBookingListBinding;

public class BookingListFragment extends Fragment {
    FragmentBookingListBinding binding;
    BookingAdapter adapter=new BookingAdapter();
    List<BookingModel> bookingList = new ArrayList();
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
        binding = FragmentBookingListBinding.inflate(getLayoutInflater(), container, false);
                return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        for(int i=0;i<10;i++) {
            bookingList.add(new BookingModel("Nithin", "Manu", "5", "9747062356", "13/11/2022", "Online", "Cardiology", "nithin@cardiology", "11:00 am"));
        }
        binding.rvBooking.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter.bookingList=bookingList;
        binding.rvBooking.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}