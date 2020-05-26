package com.example.appexperto2020.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appexperto2020.R;
import com.example.appexperto2020.holder.ServiceViewHolder;
import com.example.appexperto2020.model.Service;

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

        //Obtener las imagenes
        holder.getJobServiceTV().setText("Carpintero");
        holder.getStatusServiceTV().setText("Activao");
        holder.getUserServiceTV().setText("Manuel Quintero");
        holder.getBodyServiceTV().setText("Se formo esta mielda");
        //holder.getServiceCV().setImageBitmap(Bitmap);
        holder.getServiceContainerCL().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Ojala funcionara ya",Toast.LENGTH_LONG).show();
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
}
