package uk.ac.tees.W9581934.Patient;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import uk.ac.tees.W9581934.Adapters.ChatAdapter;
import uk.ac.tees.W9581934.Adapters.ChatLayoutChangeadapter;
import uk.ac.tees.W9581934.Models.BookingModel;
import uk.ac.tees.W9581934.Models.ChatModel;
import uk.ac.tees.W9581934.Models.DoctorListModel;
import uk.ac.tees.W9581934.R;
import uk.ac.tees.W9581934.databinding.FragmentChatBinding;
import uk.ac.tees.W9581934.databinding.FragmentDoctorListBinding;

public class ChatFragment extends Fragment {

    FragmentChatBinding binding;
    ChatLayoutChangeadapter adapter= new ChatLayoutChangeadapter();
    List<ChatModel> ChatList = new ArrayList();
    String senderId = "", receiverId = "",drname="";
    String conversationId = "";
    String generateId = "";
    DocumentReference myref;
    FirebaseDatabase database;
    ChatFragmentArgs args;
    String teacherEncodedstring = "nil";
    int pictureCode = 101;
    int cameracode = 100;
    private String userChosenTask;
    Uri filePath = null;

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
        binding = FragmentChatBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sp = getContext().getSharedPreferences("logDetails", Context.MODE_PRIVATE);
        senderId = sp.getString("userDocID", "error").toString();
        receiverId = getArguments().getString("dataid");
        drname = getArguments().getString("dataname");
        binding.labelUserName.setText(drname);
        binding.attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PselectImage();
            }
        });
        int c = senderId.compareTo(receiverId);
        System.out.println(senderId.compareTo(receiverId));
        if (c > 0) {
            //make a conversationId to filter
            conversationId = senderId + "||" + receiverId;
        } else {
            conversationId = receiverId + "||" + senderId;
        }

        //fn call to set adapter


        //send button
        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                uploadImage();
            }
        });
        adapter.senderid=senderId;
        adapter.genid=generateId;
        binding.rvChat.setAdapter(adapter);
        fetchData();
    }

    private void fetchData() {
       // ChatList.clear();
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        //Log.d("##", "argid-${args.dataid.toString()}")
//        db.collection("Chat")
//                .whereEqualTo("receiver", conversationId)
//                //.whereEqualTo("receiver",generateId)
//                // .whereEqualTo("sender",args.dataid.toString())
//                //  .whereEqualTo("receiver",senderId)
//                .orderBy("time")
//                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        for (int i = 0; i < queryDocumentSnapshots.size(); i++) {
//                            // Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
//                            ChatList.add(
//                                    new ChatModel(
//                                            queryDocumentSnapshots.getDocuments().get(i).getString("message"),
//                                            queryDocumentSnapshots.getDocuments().get(i).getString("sender"),
//                                            queryDocumentSnapshots.getDocuments().get(i).getString("receiver"),
//                                            queryDocumentSnapshots.getDocuments().get(i).getString("time"),
//                                            queryDocumentSnapshots.getDocuments().get(i).getString("type"),
//                                            queryDocumentSnapshots.getDocuments().get(i).getString("image")));
//                        }
//
//                        try {
//                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//                           // linearLayoutManager.getStackFromEnd();
//                            //linearLayoutManager.setReverseLayout(true);
//                            binding.rvChat.setLayoutManager(linearLayoutManager);
//                            adapter.chatlist = ChatList;
//                            binding.rvChat.setAdapter(adapter);
//                            adapter.notifyDataSetChanged();
//                        } catch (Exception e) {
//                            Log.d("TAG", e.toString());
//                        }
//
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//
//                    }
//                });
        FirebaseFirestore.getInstance().collection("Chat")
                .whereEqualTo("receiver", conversationId)
                .orderBy("time")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "listen:error", e);
                            return;
                        }

                        for (DocumentChange dc : snapshots.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                Log.d(TAG, "New city: " + dc.getDocument().getData());
                                int sizeq=0;
                                if(adapter.chatlist.size()>0){
                                    sizeq=adapter.chatlist.size();
                                }
                                adapter.chatlist.add(
                                        sizeq,
                                        new ChatModel(
                                                dc.getDocument().getData().get("message").toString(),
                                                dc.getDocument().getData().get("sender").toString(),
                                                dc.getDocument().getData().get("receiver").toString(),
                                                dc.getDocument().getData().get("time").toString(),
                                                dc.getDocument().getData().get("type").toString(),
                                                dc.getDocument().getData().get("image").toString()));
                                adapter.notifyItemInserted(sizeq);
                                binding.rvChat.getLayoutManager().scrollToPosition((sizeq));

                            }
                        }

                    }
                });
    }

    private void uploadImage() {
        if (filePath != null) {
           ProgressDialog progressDoalog = new ProgressDialog(requireContext());
            progressDoalog.setMessage("Uploading....");
            progressDoalog.setTitle("Please wait");
            progressDoalog.setCancelable(false);
            progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDoalog.show();
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReference();
            storageReference = storageReference.child("Chat_Images/" + UUID.randomUUID().toString());
            UploadTask uploadTask = storageReference.putFile(filePath);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            String uploadedImageUrl = task.getResult().toString();
                            Log.d("##", uploadedImageUrl);
                            teacherEncodedstring=uploadedImageUrl;
                            Log.d("url", "Chat-uploadImage:${filePath} || ${ref} ->${uploadTask} ");
                            // teacherEncodedstring = storageReference.toString();
                            Toast.makeText(requireContext(), "Picture Uploaded", Toast.LENGTH_SHORT).show();

                            String msg = binding.etText.getText().toString();
                            if (!msg.isEmpty() && teacherEncodedstring.equals("nil")) {
                                Log.d("@", "im here1");
                                // sendMessage(sender = senderId!!, args.dataid.toString(), msg)
                                sendMessage(senderId, conversationId, msg, "text", teacherEncodedstring);
                                binding.etText.setText("");
                                filePath = null;
                                teacherEncodedstring = "nil";
                            } else if (!msg.isEmpty() && !teacherEncodedstring.equals("nil")) {
                                Log.d("@", "im here2");
                                sendMessage(senderId, conversationId, msg, "imagetext", teacherEncodedstring);
                                binding.etText.setText("");
                                teacherEncodedstring = "nil";
                                filePath = null;
                            } else if (msg.isEmpty() && !teacherEncodedstring.equals("nil")) {
                                Log.d("@", "im here3");
                                sendMessage(senderId, conversationId, "", "image", teacherEncodedstring);
                                binding.etText.setText("");
                                teacherEncodedstring = "nil";
                                filePath = null;
                            } else {
                                Toast.makeText(requireContext(), "Invalid", Toast.LENGTH_SHORT).show();
                            }

                            progressDoalog.dismiss();
                        }
                    });
                }
            });

            //binding.successText.alpha=1f
            //binding.btnUpload.alpha=0f

        } else {
            String msg = binding.etText.getText().toString();
            if (!msg.isEmpty() && teacherEncodedstring.equals("nil")) {
                Log.d("@", "im here1");
                // sendMessage(sender = senderId!!, args.dataid.toString(), msg)
                sendMessage(senderId, conversationId, msg, "text", teacherEncodedstring);
                binding.etText.setText("");
                filePath = null;
                teacherEncodedstring = "nil";
            } else if (!msg.isEmpty() && !teacherEncodedstring.equals("nil")) {
                Log.d("@", "im here2");
                sendMessage(senderId, conversationId, msg, "imagetext", teacherEncodedstring);
                binding.etText.setText("");
                teacherEncodedstring = "nil";
                filePath = null;
            } else if (msg.isEmpty() && !teacherEncodedstring.equals("nil")) {
                Log.d("@", "im here3");
                sendMessage(senderId, conversationId, "", "image", teacherEncodedstring);
                binding.etText.setText("");
                teacherEncodedstring = "nil";
                filePath = null;
            } else {
                Toast.makeText(requireContext(), "Invalid", Toast.LENGTH_SHORT).show();
            }
            // Toast.makeText(requireContext(), "Please Upload an Image", Toast.LENGTH_SHORT).show()
            //binding.btnUpload.alpha=1f
        }
    }

    private void sendMessage(String sender, String receiver, String message, String type, String image) {
        binding.imagePreview.setVisibility(View.GONE);
        if (teacherEncodedstring.equals("nil")) {
            Date date = new Date();
            Timestamp timestamp2 = new Timestamp(date);
            String createdAt = String.valueOf(timestamp2.getSeconds());
            Toast.makeText(requireContext(), "click", Toast.LENGTH_SHORT).show();
            Log.d("##", "userId=${sender}/${message}/${receiver}/${createdAt}");
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            ChatModel obj = new ChatModel(message, sender, receiver, createdAt, type, image);
            db.collection("Chat").add(obj)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("@", "FailGet");
                        }
                    });

        } else {
            Date date = new Date();
            Timestamp timestamp2 = new Timestamp(date);
            String createdAt = String.valueOf(timestamp2.getSeconds());
            Toast.makeText(requireContext(), "click", Toast.LENGTH_SHORT).show();
            Log.d("##", "userId=${sender}/${message}/${receiver}/${createdAt}");
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            ChatModel obj = new ChatModel(message, sender, receiver, createdAt.toString(), type, image);
            db.collection("Chat").add(obj).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("@", "FailGet");
                }
            });


        }


    }

    private void PselectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        //final CharSequence[] items = {"Take Photo", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Upload your documents");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                // boolean result = Utility.checkPermission(Register.this);
                if (items[item].equals("Take Photo")) {
                    userChosenTask = "Take Photo";
                    // if (result)
                    // cameraIntent();
                    selectCamera();
                } else if (items[item].equals("Choose from Library")) {
                    userChosenTask = "Choose from Library";
                    // if (result)
                    // galleryIntent();
                    selectImage();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                    Log.d("dialog dismiss ", "true");
                }
            }
        });
        builder.show();
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pic"), pictureCode);
    }

    private void selectCamera() {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, cameracode);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == pictureCode && resultCode == Activity.RESULT_OK) {

            if (data == null || data.getData().equals(null)) {
                return;
            }
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);
                 binding.imagePreview.setImageBitmap(bitmap);
                 binding.imagePreview.setVisibility(View.VISIBLE);
                //uploadImage();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (requestCode == cameracode && resultCode == Activity.RESULT_OK) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            try {
                // binding.image.setImageBitmap(thumbnail);
                filePath = getImageUri(getContext(), thumbnail);
                binding.imagePreview.setImageBitmap(thumbnail);
                binding.imagePreview.setVisibility(View.VISIBLE);
                //uploadImage();
            } catch (Exception e) {

            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

}