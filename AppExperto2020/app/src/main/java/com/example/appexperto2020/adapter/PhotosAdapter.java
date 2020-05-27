package com.example.appexperto2020.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appexperto2020.R;
import com.example.appexperto2020.util.Constants;
import com.example.appexperto2020.util.HTTPSWebUtilDomi;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.util.ArrayList;

import static com.example.appexperto2020.util.Constants.FOLDER_EXPERTS;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder>  {


    public ArrayList<String> uris;

    public PhotosAdapter( )
    {
       uris = new ArrayList<>();
    }

    public void setData(ArrayList<String> uris)
    {
        this.uris = uris;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.e("", "VIEW HOLDER");

      /**  View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.p, parent, false);
        ViewHolder holder = new ViewHolder(v);**/
    return null;
       // return holder;
    }

    public String getItem(int position)
    {
        return uris.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.e("", "BIND");

        holder.custom(uris.get(position));
    }

    @Override
    public int getItemCount() {

        return uris.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView photoIV;
        private View view;

        public ViewHolder(View view) {
            super(view);
            Log.e("", "VIEW HOLDER");

            this. view = view;
            photoIV = view.findViewById(R.id.phoneTV);
            view.setTag(this);
        }


        // Personalizamos un ViewHolder a partir de un lugar
        public void custom(String photo) {
            Log.e("", "CUSTOM");

            File imageFile = new File(view.getContext().getExternalFilesDir(null)+"/"+photo);
            if(imageFile.exists())
            {
                loadImage(photoIV, imageFile);
            }else{

                FirebaseStorage storage = FirebaseStorage.getInstance();

                try{
                    storage.getReference().child(FOLDER_EXPERTS).child(photo).getDownloadUrl().
                            addOnSuccessListener(
                                    uri ->{

                                        File file = new File(view.getContext().getExternalFilesDir(null) +"/"+photo);
                                        new Thread(
                                                () ->
                                                {
                                                    HTTPSWebUtilDomi utilDomi = new HTTPSWebUtilDomi();
                                                    utilDomi.saveURLImageOnFile(uri.toString(), file);
                                                    Log.e("---->","se guarda");
                                                    loadImage(photoIV, file);
                                                }
                                        ).start();
                                    }
                            );

                }catch(Exception e)
                {
                }


            }
        }



        private void loadImage(ImageView photoIV, File file) {
            Bitmap bitmap = BitmapFactory.decodeFile(file.toString());
            photoIV.setImageBitmap(bitmap);
        }
    }

}
