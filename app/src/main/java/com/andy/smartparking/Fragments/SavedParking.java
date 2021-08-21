package com.andy.smartparking.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.andy.smartparking.LoadingDialog;
import com.andy.smartparking.R;
import com.andy.smartparking.SavedParkingInterface;
import com.andy.smartparking.SavedPlaceModel;
import com.andy.smartparking.databinding.FragmentWalletBinding;
import com.andy.smartparking.databinding.SavedParkingBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class SavedParking extends Fragment implements SavedParkingInterface {
    private FragmentWalletBinding binding;
    private FirebaseAuth firebaseAuth;
    private ArrayList<SavedPlaceModel> savedPlaceModelArrayList;
    private LoadingDialog loadingDialog;
    private FirebaseRecyclerAdapter<String, ViewHolder> firebaseRecyclerAdapter;
    private SavedParkingInterface savedLocationInterface;


    public SavedParking() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWalletBinding.inflate(inflater, container, false);
        savedLocationInterface = this;
        firebaseAuth = FirebaseAuth.getInstance();
        savedPlaceModelArrayList = new ArrayList<>();

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Saved Parking slots");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.savedRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(binding.savedRecyclerView);
        getSavedParking();

    }

    private void getSavedParking() {
        Query query = FirebaseDatabase.getInstance().getReference("Users")
                .child(firebaseAuth.getUid()).child("Saved Locations");

        FirebaseRecyclerOptions<String> options = new FirebaseRecyclerOptions.Builder<String>()
                .setQuery(query, String.class).build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<String, ViewHolder>(options) {
            @NonNull
            @NotNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                SavedParkingBinding binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()),
                        R.layout.saved_parking, parent, false);
                return new ViewHolder(binding);
            }

            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull String savePlaceId) {

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ParkingLocation").child(savePlaceId);
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {

                            SavedPlaceModel savedPlaceModel = snapshot.getValue(SavedPlaceModel.class);
                            holder.binding.setSavedPlaceModel(savedPlaceModel);
                            holder.binding.setListener(savedLocationInterface);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }


        };
        binding.savedRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }
    @Override
    public void onResume() {
        super.onResume();
        firebaseRecyclerAdapter.startListening();
    }

    @Override
    public void onPause() {
        super.onPause();
        firebaseRecyclerAdapter.stopListening();
    }

    @Override
    public void onLocationClick(SavedPlaceModel savedPlaceModel) {
        Toast.makeText(requireContext(),"Parking Clicked",Toast.LENGTH_SHORT).show();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private SavedParkingBinding binding;

        public ViewHolder(@NonNull SavedParkingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}