package uk.ac.tees.W9581934.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.ac.tees.W9581934.Models.BookingModel;
import uk.ac.tees.W9581934.Models.PatientModel;
import uk.ac.tees.W9581934.databinding.BookingCardBinding;
import uk.ac.tees.W9581934.databinding.PatientCardBinding;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.MyviewHolder> {
    public List<BookingModel> bookingList;
    BookingCardBinding binding;

    @NonNull
    @Override
    public BookingAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = BookingCardBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new BookingAdapter.MyviewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull BookingAdapter.MyviewHolder holder, int position) {
        BookingModel dm = bookingList.get(position);
        holder.tvrname.setText(dm.getDoc_name());
        holder.tvPatientName.setText(dm.getPatient_name());
        holder.tvPatientPhone.setText(dm.getPatient_phone());
        holder.tvbookingDate.setText("Booking To "+dm.getBookingDate());
        holder.tvbookingType.setText(dm.getBookingType());
        holder.tvbookingDpt.setText("["+dm.getDept_name()+"]");
        holder.token.setText(dm.getTokenNo());


    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView tvrname, tvPatientPhone, tvPatientName, tvbookingDate, tvbookingDpt, tvbookingType,token;
        ImageView docImageView;

        public MyviewHolder(@NonNull BookingCardBinding binding) {
            super(binding.getRoot());
            tvrname = binding.tvDr;
            tvPatientPhone = binding.tvPatientPhone;
            tvPatientName = binding.tvPatientName;
            tvbookingDate = binding.tvBookingDate;
            tvbookingDpt = binding.tvDptName;
            tvbookingType = binding.tvBookingType;
            docImageView=binding.image;
            token=binding.tvToken;

        }
    }
}
