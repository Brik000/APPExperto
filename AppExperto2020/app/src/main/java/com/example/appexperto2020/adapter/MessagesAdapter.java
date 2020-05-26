package com.example.appexperto2020.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appexperto2020.R;
import com.example.appexperto2020.model.Message;
import com.example.appexperto2020.util.Constants;
import com.example.appexperto2020.util.HTTPSWebUtilDomi;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.util.ArrayList;

public class MessagesAdapter extends BaseAdapter {
    private ArrayList<Message> messages;

    private String userId = "";

    public MessagesAdapter(){
        messages = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root;
        if(userId.equals(messages.get(position).getUserid())) {
             root =inflater.inflate(R.layout.messages_row_mine, null);
        }
        else{
            root = inflater.inflate(R.layout.messages_row_others,null);
        }
        TextView messagesRow = root.findViewById(R.id.message_row);
        messagesRow.setText(messages.get(position).getBody());

        if(messages.get(position).getType() == Constants.MESSAGE_TYPE_IMAGE){
            ImageView imageRow = root.findViewById(R.id.imageRow);
            imageRow.setVisibility(View.VISIBLE);

            String nameImage = messages.get(position).getId();

            //1. Preguntar si nameImage está descargada
            File imageFile = new File(parent.getContext().getExternalFilesDir(null) + "/" + nameImage);

            if(imageFile.exists()){
                //1.b Si ya lo está, mostrarla
                loadImage(imageRow,imageFile);
            }
            else{
                // 1.a Si el archivo no existe, descargar y mostrar
                FirebaseStorage storage = FirebaseStorage.getInstance();
                storage.getReference().child("chats").child(nameImage)
                        .getDownloadUrl().addOnSuccessListener(
                        uri -> {
                            Log.e(">>>",""+ uri.toString());
                            //Almacenar la foto en un archivo
                            File f = new File(parent.getContext().getExternalFilesDir(null) +"/"+nameImage );

                            new Thread(
                                    ()->{
                                        HTTPSWebUtilDomi utilDomi = new HTTPSWebUtilDomi();
                                        utilDomi.saveURLImageOnFile(uri.toString(),f);

                                        //runOnUiThread -> Actividad

                                        //post -> view

                                        root.post(
                                                ()->{
                                                    //Ejecución en hilo principal;
                                                    loadImage(imageRow,f);
                                                }
                                        );

                                    }
                            ).start();

                        }
                );
            }



        }

        return root;
    }

    public void loadImage(ImageView imageRow, File f) {
        Bitmap bitmap = BitmapFactory.decodeFile(f.toString());
        imageRow.setImageBitmap(bitmap);
    }

    public void addMessage(Message message){
        messages.add(message);
        notifyDataSetChanged();
    }

    public void setUserId(String userId) {
        this.userId = userId;
        notifyDataSetChanged();
    }
}
