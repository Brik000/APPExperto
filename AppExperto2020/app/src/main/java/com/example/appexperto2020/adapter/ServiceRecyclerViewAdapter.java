package com.example.appexperto2020.adapter;

import android.content.Intent;
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
import com.example.appexperto2020.model.Client;
import com.example.appexperto2020.model.Expert;
import com.example.appexperto2020.model.Service;
import com.example.appexperto2020.util.HTTPSWebUtilDomi;
import com.example.appexperto2020.view.AcceptServiceActivity;
import com.example.appexperto2020.view.ChatActivity;
import com.example.appexperto2020.view.MyServicesFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.util.ArrayList;

import lombok.AllArgsConstructor;

import static com.example.appexperto2020.util.Constants.MORE;

@AllArgsConstructor
public class ServiceRecyclerViewAdapter  extends RecyclerView.Adapter<ServiceViewHolder> {

    private ArrayList<Service> services  = new ArrayList<>();
    private MyServicesFragment context;

    public ServiceRecyclerViewAdapter(MyServicesFragment context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_service,parent,false);
        ServiceViewHolder holder = new ServiceViewHolder(view);
        return holder;
    }

    public void initializeServices() {
        services = new ArrayList<>();
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        boolean client = validateExpertOrClient(FirebaseAuth.getInstance().getCurrentUser().getUid(),services.get(position));

        final String[] clientName = new String[1];
        if(client){
            //Obtener las imagenes
            File imageFile = new File( context.getActivity().getExternalFilesDir(null)+"/"+services.get(position).getExpertId());
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

                                            File file = new File(context.getActivity().getExternalFilesDir(null) + "/" + services.get(position).getExpertId());
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
        }
        else{
            //Obtener las imagenes
            File imageFile = new File( context.getActivity().getExternalFilesDir(null)+"/"+services.get(position).getClientId());
            if(imageFile.exists())
            {
                loadImage(holder.getServiceCV(), imageFile);
            }else {
                FirebaseStorage storage = FirebaseStorage.getInstance();
                Log.e("Before Query", "Here");
                try {
                    storage.getReference().child("profilePictures").child(services.get(position).getClientId()).getDownloadUrl().
                            addOnSuccessListener(
                                    uri -> {

                                        File file = new File(context.getActivity().getExternalFilesDir(null) + "/" + services.get(position).getClientId());
                                        new Thread(
                                                () ->
                                                {
                                                    HTTPSWebUtilDomi utilDomi = new HTTPSWebUtilDomi();
                                                    utilDomi.saveURLImageOnFile(uri.toString(), file);
                                                    Log.e("---->", "se guarda");
                                                    context.getActivity().runOnUiThread(
                                                            ()->{
                                                                loadImage(holder.getServiceCV(), file);
                                                            }
                                                    );
                                                }
                                        ).start();
                                    }
                            );

                } catch (Exception e) {
                    Log.e(">>>", "There is no profile picture for: " + services.get(position).getExpertId());
                }
            }
                Query query = FirebaseDatabase.getInstance().getReference().child("clients").
                        child(services.get(position).getClientId());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Client client = dataSnapshot.getValue(Client.class);

                        holder.getJobServiceTV().setText("Cliente");
                         clientName[0] = client.getFirstName().toString();
                        holder.getUserServiceTV().setText(client.getFirstName() + " " + client.getLastName());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

        holder.getStatusServiceTV().setText(services.get(position).getStatus());
        holder.getBodyServiceTV().setText(services.get(position).getTitle());
        holder.getServiceContainerCL().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context.getActivity(),services.get(position).getDescription(),Toast.LENGTH_LONG).show();
            }
        });
        String userId = client ? services.get(position).getClientId(): services.get(position).getExpertId();
        String userRoot = client ? "clients":"experts";
        String state = services.get(position).getStatus();
        if(state.equals(context.getActivity().getString(R.string.service_pending)) || state.equals(context.getActivity().getString(R.string.service_declined)))
        {
            if(client){
                holder.getChatServiceBtn().setVisibility(View.GONE);
            }else
            {
                holder.getChatServiceBtn().setText(MORE);
            }
        }else {
            if(!client)
            {
                holder.getChatServiceBtn().setText(("Chatea con tu cliente"));
            }
        }

        holder.getChatServiceBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.getChatServiceBtn().getText().toString().equals(MORE))
                {
                    Intent intent = new Intent(context.getActivity(), AcceptServiceActivity.class);
                    intent.putExtra("service",services.get(position).getId());
                    intent.putExtra("clientName",clientName[0]);

                    context.startActivity(intent);
                }else
                {
                    Intent intent = new Intent(context.getActivity(), ChatActivity.class);
                    intent.putExtra("username", userId);
                    intent.putExtra("chatroom",services.get(position).getId());
                    intent.putExtra("userRoot",userRoot);
                    context.startActivity(intent);
                }


            /**
             * DEBERÍA HACERSE UN CONDICIONAL
             * SI EL TEXTO DEL BOTON DICE (VER MAS) IR AL FRAGMENT DE ACEPTAR SERVICIO
             * SI EL TEXTO NO DICE ESO HACER LO DE ARRIBA DEL INTENT
             */


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
        int pos = services.size()-1;
        FirebaseDatabase.getInstance().getReference().child("services").child(service.getId())
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("actualiza", dataSnapshot.getKey());
                services.set(pos, dataSnapshot.getValue(Service.class));
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadImage(ImageView expertIV, File file) {
        Bitmap bitmap = BitmapFactory.decodeFile(file.toString());
        expertIV.setImageBitmap(bitmap);
    }

    private boolean validateExpertOrClient(String userId, Service temp){
        if(userId.equals(temp.getClientId())){
            return true;
        }
        else{
            return false;
        }
    }
}
