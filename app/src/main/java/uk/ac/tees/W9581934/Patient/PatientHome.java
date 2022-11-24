package uk.ac.tees.W9581934.Patient;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.ac.tees.W9581934.R;
import uk.ac.tees.W9581934.databinding.FragmentUserHomeBinding;

public class PatientHome extends Fragment {
    FragmentUserHomeBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentUserHomeBinding.inflate(getLayoutInflater(),container,false);
        return binding.getRoot();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback( this,new OnBackPressedCallback(true){
            @Override
            public void handleOnBackPressed() {
                Log.d("@@", "handleOnBackPressed: click");
                AlertDialog.Builder alertbox=new AlertDialog.Builder(requireContext());
                alertbox.setMessage("Do you really wants to logout from this app?");
                alertbox.setTitle("Logout!!");

                alertbox.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Navigation.findNavController(getView()).navigateUp();

                    }
                });
                alertbox.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alertbox.show();


            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavHostFragment NavHostFragment=binding.HomeContainer.getFragment();
        NavInflater inflater=NavHostFragment.getNavController().getNavInflater();
        setBottomsheetIcons();
        binding.ivlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertbox=new AlertDialog.Builder(requireContext());
                alertbox.setMessage("Do you really wants to logout from this app?");
                alertbox.setTitle("Logout!!");

                alertbox.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Navigation.findNavController(getView()).navigateUp();

                    }
                });
                alertbox.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alertbox.show();
            }
        });
        binding.iconDr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //progressDoalog.dismiss();
                NavGraph graph=inflater.inflate(R.navigation.homenav);
                graph.setStartDestination(R.id.bookDoctor);
                NavHostFragment.getNavController().setGraph(graph);
                binding.iconDr.setBackgroundResource(R.drawable.ic_baseline_search_24);
                binding.iconChat.setBackgroundResource(R.drawable.chat_black);
                binding.iconHome.setBackgroundResource(R.drawable.home);
                binding.iconBookings.setBackgroundResource(R.drawable.clipboard_black);
                binding.iconFeedback.setBackgroundResource(R.drawable.feedback);

            }
        });
        binding.iconChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NavGraph graph=inflater.inflate(R.navigation.homenav);
                graph.setStartDestination(R.id.chatWithdoctor);
                NavHostFragment.getNavController().setGraph(graph);
                binding.iconDr.setBackgroundResource(R.drawable.search_black);
                binding.iconChat.setBackgroundResource(R.drawable.chat);
                binding.iconHome.setBackgroundResource(R.drawable.home);
                binding.iconBookings.setBackgroundResource(R.drawable.clipboard_black);
                binding.iconFeedback.setBackgroundResource(R.drawable.feedback);

            }
        });
        binding.iconHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NavGraph graph=inflater.inflate(R.navigation.homenav);
                graph.setStartDestination(R.id.dashboardFragment);
                NavHostFragment.getNavController().setGraph(graph);
                binding.iconDr.setBackgroundResource(R.drawable.search_black);
                binding.iconChat.setBackgroundResource(R.drawable.chat_black);
                binding.iconHome.setBackgroundResource(R.drawable.home_white);
                binding.iconBookings.setBackgroundResource(R.drawable.clipboard_black);
                binding.iconFeedback.setVisibility(View.VISIBLE);
                binding.iconFeedback.setBackgroundResource(R.drawable.feedback);

            }
        });
        binding.iconBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NavGraph graph=inflater.inflate(R.navigation.homenav);
                graph.setStartDestination(R.id.myBookingsFragment);
                NavHostFragment.getNavController().setGraph(graph);
                binding.iconDr.setBackgroundResource(R.drawable.search_black);
                binding.iconChat.setBackgroundResource(R.drawable.chat_black);
                binding.iconHome.setBackgroundResource(R.drawable.home);
                binding.iconBookings.setBackgroundResource(R.drawable.clipboard);
                binding.iconFeedback.setBackgroundResource(R.drawable.feedback);

            }
        });
        binding.iconFeedback.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                binding.iconDr.setBackgroundResource(R.drawable.search_black);
                binding.iconChat.setBackgroundResource(R.drawable.chat_black);
                binding.iconHome.setBackgroundResource(R.drawable.home);
                binding.iconBookings.setBackgroundResource(R.drawable.clipboard_black);
                binding.iconFeedback.setBackgroundResource(R.drawable.feedback_white);
              //  binding.iconFeedback.setImageTintList(ColorStateList.valueOf(R.color.orange));
                NavGraph graph=inflater.inflate(R.navigation.homenav);
                graph.setStartDestination(R.id.userFeedbackFragment);
                NavHostFragment.getNavController().setGraph(graph);

            }
        });

    }
public void setBottomsheetIcons(){
    binding.iconDr.setVisibility(View.VISIBLE);
    binding.iconDr.setBackgroundResource(R.drawable.search_black);
    binding.iconChat.setVisibility(View.VISIBLE);
    binding.iconChat.setBackgroundResource(R.drawable.chat_black);
    binding.iconFeedback.setVisibility(View.VISIBLE);
    binding.iconFeedback.setBackgroundResource(R.drawable.feedback);
    binding.iconHome.setVisibility(View.VISIBLE);
    binding.iconHome.setBackgroundResource(R.drawable.home_white);
    binding.iconBookings.setVisibility(View.VISIBLE);
    binding.iconBookings.setBackgroundResource(R.drawable.clipboard_black);
}
}