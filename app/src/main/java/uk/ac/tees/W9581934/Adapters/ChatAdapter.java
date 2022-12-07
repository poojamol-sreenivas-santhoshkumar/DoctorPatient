package uk.ac.tees.W9581934.Adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import uk.ac.tees.W9581934.Models.ChatModel;
import uk.ac.tees.W9581934.R;
import uk.ac.tees.W9581934.databinding.ChatboxBinding;


public class ChatAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int viewLEFT = 0;
    public static final int viewRIGHT = 1;
    public ArrayList<ChatModel> chatlist = new ArrayList<>();


    Context ctx;
    public String senderid, genid;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == viewLEFT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.receiver, parent, false);
            return new ViewLeftHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sender, parent, false);
            return new ViewrighttHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatModel item = chatlist.get(position);
        if (getItemViewType(position) == viewLEFT) {
            //pass array list
            ((ViewLeftHolder) holder).bind(item);
        } else {
            ((ViewrighttHolder) holder).bind(item);
        }
    }

    @Override
    public int getItemCount() {
        return chatlist.size();
    }

    @Override
    public int getItemViewType(int position) {
        //check sender or receiver
        if (chatlist.get(position).getSender().equals(senderid)) {
            return viewRIGHT;
        } else {
            return viewLEFT;
        }
    }

    class ViewLeftHolder extends RecyclerView.ViewHolder {
        ImageView imageL;
        TextView tvMsgL;

        public ViewLeftHolder(@NonNull View itemView) {
            super(itemView);
            imageL = itemView.findViewById(R.id.imageL);
            tvMsgL = itemView.findViewById(R.id.tvMsgL);
        }

        public void bind(ChatModel item) {
            imageL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(imageL.getContext());
                    dialog.setContentView(R.layout.fragment_image_display);
                    ImageView closeBtn =(ImageView)  dialog.findViewById(R.id.ivClose);
                    ImageView display =(ImageView)  dialog.findViewById(R.id.ivDisplay);
                    Glide.with(imageL.getContext()).load(item.getImage()).into(display);
                    // if button is clicked, close the custom dialog
                    closeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();

                        }
                    });
                    dialog.show();

                }
            });
            if (item.getType().equals("image")) {
                imageL.setVisibility(View.VISIBLE);
                tvMsgL.setVisibility(View.GONE);
                Glide.with(imageL.getContext()).load(item.getImage()).into(imageL);
            } else if (item.getType().equals("imagetext")) {
                imageL.setVisibility(View.VISIBLE);
                tvMsgL.setVisibility(View.VISIBLE);
                tvMsgL.setText(item.getMessage());
                Glide.with(imageL.getContext()).load(item.getImage()).into(imageL);
            } else {
                imageL.setVisibility(View.GONE);
                tvMsgL.setVisibility(View.VISIBLE);
                tvMsgL.setText(item.getMessage());

            }

        }
    }

    class ViewrighttHolder extends RecyclerView.ViewHolder {
        ImageView imageR;
        TextView tvMsgR;

        public ViewrighttHolder(@NonNull View itemView) {

            super(itemView);
            imageR = itemView.findViewById(R.id.imageR);
            tvMsgR = itemView.findViewById(R.id.tvMsgR);
        }

        public void bind(ChatModel item) {
            imageR.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    // custom dialog
                    final Dialog dialog = new Dialog(imageR.getContext());
                    dialog.setContentView(R.layout.fragment_image_display);
                    ImageView closeBtn =(ImageView)  dialog.findViewById(R.id.ivClose);
                    ImageView display =(ImageView)  dialog.findViewById(R.id.ivDisplay);
                    Glide.with(imageR.getContext()).load(item.getImage()).into(display);
                    // if button is clicked, close the custom dialog
                    closeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();

                        }
                    });
                    dialog.show();
                }

            });
            if (item.getType().equals("image")) {
                imageR.setVisibility(View.VISIBLE);
                tvMsgR.setVisibility(View.GONE);
                Glide.with(imageR.getContext()).load(item.getImage()).into(imageR);
            } else if (item.getType().equals("imagetext")) {
                imageR.setVisibility(View.VISIBLE);
                tvMsgR.setVisibility(View.VISIBLE);
                tvMsgR.setText(item.getMessage());
                Glide.with(imageR.getContext()).load(item.getImage()).into(imageR);
            } else {
                imageR.setVisibility(View.GONE);
                tvMsgR.setVisibility(View.VISIBLE);
                tvMsgR.setText(item.getMessage());

            }


        }
    }
}

