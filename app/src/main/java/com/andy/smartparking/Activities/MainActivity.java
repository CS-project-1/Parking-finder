package com.andy.smartparking.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.andy.smartparking.Adapter.ViewPagerAdapter;
import com.andy.smartparking.Model.User;
import com.andy.smartparking.R;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private ViewPager viewPager ;
    private CircularImageView circularImageView;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();
        mStorageRef = FirebaseStorage.getInstance().getReference().child("profile_images");

        viewPager =findViewById(R.id.view_pager);
//

        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        viewPager =findViewById(R.id.view_pager);

        if (savedInstanceState == null) {
            setUpViewpager();
        }
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.home:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.spots:
                        viewPager.setCurrentItem(1);
                        break;

                    case R.id.wallet:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.qrScanner:
                        viewPager.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });
        navigationView = findViewById(R.id.navi_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);

        circularImageView = header.findViewById(R.id.userProfilePicture);
        circularImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        drawer = findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        reference.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                TextView firstNameTextView1 = header.findViewById(R.id.fName);
                TextView surnameTextView1 = header.findViewById(R.id.lName);
                TextView emailTextView1 = header.findViewById(R.id.userEmail);
                CircularImageView circularImageView1 = header.findViewById(R.id.userProfilePicture);
//                circularImageView = header.findViewById(R.id.userProfilePicture);

                if(userProfile != null){
                    String firstname = userProfile.firstName;
                    String surname = userProfile.surName;
                    String email = userProfile.email;
//                    current_user_db.child("Image").setValue("Default");

                    firstNameTextView1.setText(firstname);
                    surnameTextView1.setText(surname);
                    emailTextView1.setText(email);

//                    find a way of getting the image uri and loading it into glide
                    Glide.with(MainActivity.this).load("https://firebasestorage.googleapis.com/v0/b/bold-zoo-303509.appspot.com/o/profile_images%2Fprofile_images%2Fimage%3A417?alt=media&token=47a76900-3bbb-4687-a544-c705530346b0").into(circularImageView1);
                }else{
                    Toast.makeText(MainActivity.this,"Where is user profile",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this,"An error occured",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setUpViewpager(){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.spots).setChecked(true);
                        break;

                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.wallet).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.qrScanner).setChecked(true);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }



    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        if(item.getItemId() == R.id.home){
            Toast.makeText(this, "Home btn Clicked.", Toast.LENGTH_SHORT).show();
        }else if(item.getItemId() == R.id.contact_us){
            Uri myUri = Uri.parse("tel: 0724413288");
            Intent intent = new Intent(Intent.ACTION_DIAL, myUri);
            startActivity(intent);
        }else if(item.getItemId() == R.id.location){
            Uri googleMapsIntentUri = Uri.parse("https://www.google.com/maps/place/Panari+Group/@-1.3288466,36.8537058,17z/data=!4m8!3m7!1s0x182f10ef7e2b0aa1:0xe91e93be849a121b!5m2!4m1!1i2!8m2!3d-1.328852!4d36.8558998");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, googleMapsIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);

        }else if(item.getItemId()== R.id.share_app){
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String Body = "Download this App";
            String Sub = "http://play.google.com";
            intent.putExtra(Intent.EXTRA_TEXT,Body);
            intent.putExtra(Intent.EXTRA_TEXT,Sub);
            startActivity(Intent.createChooser(intent,"Share using"));

        }else if(item.getItemId()==R.id.logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this, Login.class));
        }
        return false;
    }
}