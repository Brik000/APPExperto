package com.example.appexperto2020.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appexperto2020.R;
import com.example.appexperto2020.holder.ServiceViewHolder;
import com.example.appexperto2020.model.Expert;
import com.example.appexperto2020.model.Job;
import com.example.appexperto2020.model.Service;
import com.example.appexperto2020.util.HTTPSWebUtilDomi;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.util.ArrayList;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ServiceRecyclerViewAdapter  extends RecyclerView.Adapter<ServiceViewHolder> {

    private ArrayList<Service> services  = new ArrayList<>();
    private Context context;


    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_service,parent,false);
        ServiceViewHolder holder = new ServiceViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {

        Query query = FirebaseDatabase.getInstance().getReference().child("experts").
                child(services.get(position).getExpertId());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String jobs = "";
                Expert expert = dataSnapshot.getValue(Expert.class);
                for (String key : expert.getJobList().keySet()){
                    jobs += expert.getJobList().get(key).getName() + "-";
                }
                holder.getJobServiceTV().setText(jobs);
                holder.getUserServiceTV().setText(expert.getFirstName() + " " + expert.getLastName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Obtener las imagenes

        File imageFile = new File( context.getExternalFilesDir(null)+"/"+services.get(position).getExpertId());
        if(imageFile.exists())
        {
            loadImage(holder.getServiceCV(), imageFile);
        }else
        {

            FirebaseStorage storage = FirebaseStorage.getInstance();

            try {
                storage.getReference().child("profilePictures").child(services.get(position).getExpertId()).getDownloadUrl().
                        addOnSuccessListener(
                                uri -> {

                                    File file = new File(context.getExternalFilesDir(null) + "/" + services.get(position).getExpertId());
                                    new Thread(
                                            () ->
                                            {
                                                HTTPSWebUtilDomi utilDomi = new HTTPSWebUtilDomi();
                                                utilDomi.saveURLImageOnFile(uri.toString(), file);
                                                Log.e("---->", "se guarda");
                                                loadImage(holder.getServiceCV(), file);
                                            }
                                    ).start();
                                }
                        );

            } catch (Exception e) {
                Log.e(">>>", "There is no profile picture for: " + services.get(position).getExpertId());
            }
        }
        holder.getStatusServiceTV().setText(services.get(position).getStatus());
        holder.getBodyServiceTV().setText(services.get(position).getTitle());
        holder.getServiceContainerCL().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,services.get(position).getDescription(),Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public void addService(Service service){
        services.add(service);
        notifyDataSetChanged();
    }

    private void loadImage(ImageView expertIV, File file) {
        Bitmap bitmap = BitmapFactory.decodeFile(file.toString());
        expertIV.setImageBitmap(bitmap);
    }

}
