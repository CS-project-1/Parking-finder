package com.andy.smartparking.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.andy.smartparking.GooglePlaceModel;
import com.andy.smartparking.NearbyParkingInterface;
import com.andy.smartparking.R;
import com.andy.smartparking.databinding.ParkingSlotsLayoutBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ParkingAdapter extends RecyclerView.Adapter<ParkingAdapter.ViewHolder>{

    private List<GooglePlaceModel> googlePlaceModels;
    private NearbyParkingInterface nearbyParkingInterface;

    public ParkingAdapter(NearbyParkingInterface nearbyParkingInterface) {
        this.nearbyParkingInterface = nearbyParkingInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ParkingSlotsLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.parking_slots_layout, parent, false);
        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        if (googlePlaceModels != null) {
            GooglePlaceModel placeModel = googlePlaceModels.get(position);
            holder.binding.setGooglePlaceModel(placeModel);
            holder.binding.setListener(nearbyParkingInterface);

        }

    }

    @Override
    public int getItemCount() {
        if (googlePlaceModels != null)
            return googlePlaceModels.size();
        else
            return 0;
    }
    public void setGooglePlaceModels(List<GooglePlaceModel> googlePlaceModels) {
        this.googlePlaceModels = googlePlaceModels;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ParkingSlotsLayoutBinding binding;
        public ViewHolder(@NonNull @NotNull ParkingSlotsLayoutBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
