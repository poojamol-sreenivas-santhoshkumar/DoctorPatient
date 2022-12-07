package uk.ac.tees.W9581934.Doctor;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import uk.ac.tees.W9581934.Adapters.BookingAdapter;
import uk.ac.tees.W9581934.Models.BookingModel;
import uk.ac.tees.W9581934.R;
import uk.ac.tees.W9581934.databinding.BookingCardBinding;

public class OnlineBookingAdapter extends RecyclerView.Adapter<OnlineBookingAdapter.MyviewHolder> {
    public List<BookingModel> bookingList;
    BookingCardBinding binding;
    Context ctx;
    String type;

    public OnlineBookingAdapter(String type, Context context) {
        this.ctx = context;
        this.type = type;
    }


    @NonNull
    @Override
    public OnlineBookingAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = BookingCardBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new OnlineBookingAdapter.MyviewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull OnlineBookingAdapter.MyviewHolder holder, int position) {
        BookingModel dm = bookingList.get(position);
        if (type.equals("admin")) {
            holder.btncancel.setText("Delete\n Booking");

        }
        else if (type.equals("doctor")) {
            holder.btncancel.setText("Remove from\n Queue");
            if (dm.getBookingType().equals("Online")) {
                binding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle b=new Bundle();
                        b.putString("dataid",dm.getDoc_id());
                        b.putString("dataname",dm.getPatient_name());
                        Navigation.findNavController(view).navigate(R.id.action_chatFragmentDoctor_to_doctorChatHome,b);
                    }
                });
            }
            //holder.btncancel.setVisibility(View.GONE);

        }


        holder.tvrname.setText(dm.getDoc_name());
        holder.tvPatientName.setText(dm.getPatient_name());
        holder.tvPatientPhone.setText(dm.getPatient_phone());
        holder.tvbookingDate.setText("Booking on " + dm.getBookingDate());
        holder.tvbookingType.setText(dm.getBookingType());
        holder.tvbookingDpt.setText("[" + dm.getDept_name() + "]");
        holder.token.setText(dm.getTokenNo());
        holder.btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertbox = new AlertDialog.Builder(view.getRootView().getContext());
                alertbox.setMessage("Do you really wants to cancel/remove this booking?");
                alertbox.setTitle("Cancel!!");

                alertbox.setPositiveButton("Cancel Booking", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteBooking(dm.getDoc_id(), view);

                    }
                });
                alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
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
    public int getItemCount() {
        return bookingList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView tvrname, tvPatientPhone, tvPatientName, tvbookingDate, tvbookingDpt, tvbookingType, token;
        ImageView docImageView;
        Button btncancel;

        public MyviewHolder(@NonNull BookingCardBinding binding) {
            super(binding.getRoot());
            tvrname = binding.tvDr;
            btncancel = binding.btncancel;
            tvPatientPhone = binding.tvPatientPhone;
            tvPatientName = binding.tvPatientName;
            tvbookingDate = binding.tvBookingDate;
            tvbookingDpt = binding.tvDptName;
            tvbookingType = binding.tvBookingType;
            docImageView = binding.image;
            token = binding.tvToken;

        }
    }

    private void deleteBooking(String doc_name, View view) {
        final ProgressDialog progressDoalog = new ProgressDialog(view.getRootView().getContext());
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Please wait");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Bookings").document(doc_name).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        if (type.equals("admin")) {
                            Navigation.findNavController(view).navigate(R.id.action_bookingListFragment_self);
                            Toast.makeText(view.getRootView().getContext(), "Booking Cancelled successfully", Toast.LENGTH_SHORT).show();

                        }
                        else  if (type.equals("doctor")) {
                            Navigation.findNavController(view).navigate(R.id.action_chatFragmentDoctor_self);
                            Toast.makeText(view.getRootView().getContext(), "Booking Removed from Queue", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            Navigation.findNavController(view).navigate(R.id.action_myBookingsFragment_self);
                            Toast.makeText(view.getRootView().getContext(), "Booking Cancelled successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(view.getRootView().getContext(), "Technical error occured", Toast.LENGTH_SHORT).show();

                    }
                });

        progressDoalog.dismiss();
    }
}

