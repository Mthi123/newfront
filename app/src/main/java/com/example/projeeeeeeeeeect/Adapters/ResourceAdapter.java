package com.example.projeeeeeeeeeect.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projeeeeeeeeeect.Models.Resource;
import com.example.projeeeeeeeeeect.R;
import java.util.List;

public class ResourceAdapter extends RecyclerView.Adapter<ResourceAdapter.ResourceViewHolder> {

    private List<Resource> resourceList;

    public ResourceAdapter(List<Resource> resourceList) {
        this.resourceList = resourceList;
    }

    @NonNull
    @Override
    public ResourceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.resource_list_item, parent, false);
        return new ResourceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResourceViewHolder holder, int position) {
        Resource resource = resourceList.get(position);

        holder.name.setText(resource.getName());
        holder.type.setText(resource.getType());
        holder.description.setText(resource.getDescription());

        // Handle optional fields
        holder.phone.setText("Phone: " + (resource.getPhoneNumber() != null ? resource.getPhoneNumber() : "N/A"));
        holder.email.setText("Email: " + (resource.getEmail() != null ? resource.getEmail() : "N/A"));
        holder.website.setText("Website: " + (resource.getWebsite() != null ? resource.getWebsite() : "N/A"));
        holder.address.setText("Address: " + (resource.getAddress() != null ? resource.getAddress() : "N/A"));
    }

    @Override
    public int getItemCount() {
        return resourceList.size();
    }

    // --- ViewHolder Class ---
    public static class ResourceViewHolder extends RecyclerView.ViewHolder {
        TextView name, type, description, phone, email, address, website;

        public ResourceViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.resourceName);
            type = itemView.findViewById(R.id.resourceType);
            description = itemView.findViewById(R.id.resourceDescription);
            phone = itemView.findViewById(R.id.resourcePhone);
            email = itemView.findViewById(R.id.resourceEmail);
            address = itemView.findViewById(R.id.resourceAddress);
            website = itemView.findViewById(R.id.resourceWebsite);
        }
    }
}