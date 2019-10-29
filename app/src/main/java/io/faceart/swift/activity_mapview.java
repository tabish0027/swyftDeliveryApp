package io.faceart.swift;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
import androidx.navigation.ui.AppBarConfiguration;

import java.util.ArrayList;
import java.util.List;

import io.faceart.swift.interface_retrofit.DeliveryParcel;
import io.faceart.swift.interface_retrofit.RiderActivity;
import io.faceart.swift.interface_retrofit.*;
import mumayank.com.airlocationlibrary.AirLocation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import io.faceart.swift.interface_retrofit_delivery.*;


public class activity_mapview extends Activity implements OnMapReadyCallback {
    GoogleMap mMapServiceView;
    MapView mMapView;
    ImageView img_rider_activity_button,btn_navigation;

    ConstraintLayout offlineTag = null;
    ConstraintLayout Task1,Task2,Task3,Task4,Task5 = null;
    ConstraintLayout btn_walled,btn_earning = null;
    TextView tx_username,tx_rating = null;
    ProgressBar progressBar = null;


    ImageView btn_slider_menu;
    private AppBarConfiguration mAppBarConfiguration;
    NavigationView navigationView;
    private DrawerLayout mDrawerLayout;
    ConstraintLayout pendingTask;
    private AirLocation airLocation;
    ImageView btn_get_current_locationc,profile_image2;
    TextView tx_parcels_status_count;
    Marker marker_destination_location = null;

    ArrayList<MarkerOptions> markers = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_drawer);
        mMapView =  findViewById(R.id.ridermapView);
        offlineTag = findViewById(R.id.img_rider_activity_button_State);
        img_rider_activity_button = findViewById(R.id.img_rider_activity_button);
        navigationView = findViewById(R.id.nav_view);
        pendingTask = findViewById(R.id.pendingTask);
        btn_get_current_locationc = findViewById(R.id.btn_get_current_locationc);
        profile_image2 = findViewById(R.id.profile_image2);
        Task1= findViewById(R.id.item1);
        Task2= findViewById(R.id.item2);
        Task3= findViewById(R.id.item3);
        Task4= findViewById(R.id.item4);
        Task5= findViewById(R.id.item5);
        btn_navigation = findViewById(R.id.btn_navigation);
        tx_username = findViewById(R.id.tx_username_slider);
        progressBar = (ProgressBar)findViewById(R.id.url_loading_animation);
        btn_walled= findViewById(R.id.btn_wallet);
        btn_earning= findViewById(R.id.btn_earning);
        tx_rating = findViewById(R.id.tx_rating);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.slider_menu);
        btn_slider_menu = findViewById(R.id.btn_slider_menu);
        tx_parcels_status_count = findViewById(R.id.tx_parcels_status_count);
        progressBar.setVisibility(View.GONE);

       // generate_test_Data_for_daily();

        if(Databackbone.getinstance().ar_orders_daily.size() > 0)
        {
           // check_parcel_scanning_complete = true;
            //tx_parcels_status_count.setText(Integer.toString(Databackbone.getinstance().ar_orders_daily.size()) + " Pickups Remaining");
        }
        else
        {
            //check_parcel_scanning_complete = false;
           // tx_parcels_status_count.setText(Integer.toString(0) + " Scanning Parcels");

        }
        Task1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Task2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openaactivity = new Intent(activity_mapview.this,activity_help.class);
                activity_mapview.this.startActivity(openaactivity);
            }
        });
        Task3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openaactivity = new Intent(activity_mapview.this,activity_faq.class);
                activity_mapview.this.startActivity(openaactivity);
            }
        });
        Task4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openaactivity = new Intent(activity_mapview.this, activity_settings.class);
                activity_mapview.this.startActivity(openaactivity);
            }
        });
        Task5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity_mapview.this.finish();
            }
        });
        btn_get_current_locationc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentLocation();
            }
        });
        btn_walled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openaactivity = new Intent(activity_mapview.this,activity_wallet_orders.class);
                activity_mapview.this.startActivity(openaactivity);
            }
        });

        btn_earning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openaactivity = new Intent(activity_mapview.this,activity_earning.class);
                activity_mapview.this.startActivity(openaactivity);
            }
        });
        //mMapView.onResume();

        try {
            MapsInitializer.initialize(this.getApplicationContext());
            mMapView.onCreate(savedInstanceState);
            mMapView.getMapAsync(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        img_rider_activity_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnableLoading();
                if(Databackbone.getinstance().rider.getUser().getIsOnline())
                     change_Activity_status(false);
                else
                    change_Activity_status(true);



            }
        });


        mAppBarConfiguration = new AppBarConfiguration.Builder()
                .setDrawerLayout(mDrawerLayout)
                .build();

        btn_slider_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDrawerLayout.openDrawer(Gravity.LEFT);
                navigationView.bringToFront();

            }
        });
        profile_image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openBarCode = new Intent(activity_mapview.this,activity_profile.class);
                activity_mapview.this.startActivity(openBarCode);
                //overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );


            }
        });
        pendingTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Databackbone.getinstance().rider.getUser().getIsOnline()){
                    Intent pendingtask = null;
                    if (Databackbone.getinstance().check_parcel_scanning_complete) {
                        pendingtask = new Intent(activity_mapview.this, activity_daily_order_status.class);
                        pendingtask.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        pendingtask.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        activity_mapview.this.startActivity(pendingtask);
                        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                    } else {
                        pendingtask = new Intent(activity_mapview.this, activity_barcode_scanner.class);
                        pendingtask.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        pendingtask.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        activity_mapview.this.startActivity(pendingtask);
                        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                    }
                }else{
                    Databackbone.getinstance().showAlsertBox(activity_mapview.this,"Error","Your Are Not Online");
                }


            }
        });

        btn_navigation.setVisibility(View.GONE);

        // data attributes set from server
        tx_username.setText("Hi "+Databackbone.getinstance().rider.getUser().getFirstName());
        tx_rating.setText(Databackbone.getinstance().rider.getUser().getType());
        btn_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(marker_destination_location != null){
                    new AlertDialog.Builder(activity_mapview.this)
                            .setTitle("Navigation Request")
                            .setMessage("Activate Navigation for " + marker_destination_location.getTitle() )

                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    Offlice_Activity(marker_destination_location.getPosition());

                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })

                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });
        check_status_of_rider_activity();


    }
    // override and call airLocation object's method by the same name
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        airLocation.onActivityResult(requestCode, resultCode, data);

    }

    // override and call airLocation object's method by the same name
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        airLocation.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    public void getCurrentLocation(){
        airLocation = new AirLocation(this, true, true, new AirLocation.Callbacks() {
            @Override
            public void onSuccess( Location location) {
                if(location == null || mMapServiceView == null)
                    return ;
                LatLng current_location = new LatLng(location.getLatitude(),location.getLongitude());
                Databackbone.getinstance().current_location = current_location;
                //mMapServiceView.addMarker(new MarkerOptions().position(current_location).title("Current Location"));
                //mMapServiceView.moveCamera(CameraUpdateFactory.newLatLngZoom(current_location, 15));
                CameraUpdate location_animation = CameraUpdateFactory.newLatLngZoom(current_location, 15);
                mMapServiceView.animateCamera(location_animation);
                //mMapServiceView.moveCamera(CameraUpdateFactory.newLatLng(current_location));
            }

            @Override
            public void onFailed( AirLocation.LocationFailedEnum locationFailedEnum) {
                // do something
                String message = locationFailedEnum.name();
                System.out.println(message);
            }
        });

}
    public void ActivateRider(){
        img_rider_activity_button.setImageResource(R.drawable.icon_rider_event_start);
        offlineTag.setVisibility(View.GONE);
    }

    public void DeactivateRider(){
        img_rider_activity_button.setImageResource(R.drawable.icon_rider_event_stop);
        offlineTag.setVisibility(View.VISIBLE);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMapServiceView = googleMap;
        mMapServiceView.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if(marker.getTitle().contains("Pickup"))
                {
                    //btn_navigation.setVisibility(View.VISIBLE);
                    marker_destination_location = marker;
                }
                return false;
            }
        });
        mMapServiceView.setMyLocationEnabled(true);
        mMapServiceView.getUiSettings().setMyLocationButtonEnabled(false);

        getCurrentLocation();
        /*
        if(Databackbone.getinstance().rider.getUser().getType().equalsIgnoreCase("delivery"))
            LoadParcelsForDelivery();
        else
            LoadParcels();

         */




    }
    @Override
    public void onResume() {

       mMapView.onResume();
        super.onResume();
        if(Databackbone.getinstance().rider != null && Databackbone.getinstance().rider.getUser().getIsOnline())
        {
            ActivateRider();

        }else{
            DeactivateRider();
        }
        if(Databackbone.getinstance().rider.getUser().getType().equalsIgnoreCase("delivery"))
            LoadParcelsForDelivery();
        else
            LoadParcels();



    }

    @Override
    public void onPause() {
        super.onPause();
       mMapView.onPause();
        mDrawerLayout.closeDrawer(Gravity.LEFT);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
       mMapView.onLowMemory();
    }




    ////////////////////////////////////////////////////////////////////////////////////////////
        public void LoadParcels(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Databackbone.getinstance().Base_URL).addConverterFactory(GsonConverterFactory.create()).build();
        swift_api riderapi = retrofit.create(swift_api.class);
        EnableLoading();
        Call<List<DeliveryParcel>>  call = riderapi.getParcelsByRiders(Databackbone.getinstance().rider.getId(),Databackbone.getinstance().rider.getUserId());
        call.enqueue(new Callback<List<DeliveryParcel>>() {
            @Override
            public void onResponse(Call<List<DeliveryParcel>> call, Response<List<DeliveryParcel>> response) {
                if(response.isSuccessful()){

                    List<DeliveryParcel> parcels = response.body();
                   // System.out.println(parcels.size());
                    LoadLocation(parcels);
                    Databackbone.getinstance().parcels = parcels;
                    tx_parcels_status_count.setText(Integer.toString(parcels.size())+" Task Pending");
                    DisableLoading();

                }
                else{
                    DisableLoading();
                }

            }

            @Override
            public void onFailure(Call<List<DeliveryParcel>> call, Throwable t) {
                System.out.println(t.getCause());
                tx_parcels_status_count.setText("0 Task Pending");
                DisableLoading();
            }
        });


    }
    public void check_status_of_rider_activity(){
        if(Databackbone.getinstance().rider != null){
            if(Databackbone.getinstance().rider.getUser().getIsOnline()){

                ActivateRider();


            }
            else{

                DeactivateRider();

            }
        }
    }
    public void change_Activity_status(Boolean status){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Databackbone.getinstance().Base_URL).addConverterFactory(GsonConverterFactory.create()).build();
        swift_api riderapi = retrofit.create(swift_api.class);

        Call<RiderActivity> call = riderapi.setRiderOnlineStatus(Databackbone.getinstance().rider.getId(),Databackbone.getinstance().rider.getUserId(),new online(status));
        call.enqueue(new Callback<RiderActivity>() {
            @Override
            public void onResponse(Call<RiderActivity> call, Response<RiderActivity> response) {
                if(response.isSuccessful()){

                    RiderActivity riderActivity = response.body();
                    Databackbone.getinstance().riderActivity = riderActivity;
                     if(Databackbone.getinstance().riderActivity.getIsOnline()) {
                         ActivateRider();
                         Databackbone.getinstance().rider.getUser().setIsOnline(true);
                         Databackbone.getinstance().showAlsertBox(activity_mapview.this,"Rider App","Online");

                     }
                     else {

                         Databackbone.getinstance().rider.getUser().setIsOnline(Databackbone.getinstance().riderActivity.getIsOnline());
                         DeactivateRider();
                         Databackbone.getinstance().showAlsertBox(activity_mapview.this,"Rider App","Offline");

                     }
                    DisableLoading();

                }
                else{
                    DisableLoading();
                    //DeactivateRider();
                    Databackbone.getinstance().showAlsertBox(activity_mapview.this,"Error","Error Connecting To Server Error Code 33");
                }

            }

            @Override
            public void onFailure(Call<RiderActivity> call, Throwable t) {
                System.out.println(t.getCause());
                DisableLoading();
                Databackbone.getinstance().showAlsertBox(activity_mapview.this,"Error","Error Connecting To Server Error Code 34");

                //DeactivateRider();
            }
        });




    }
    public void AddMarkers(Double lat,Double lng,final String title){

        MarkerOptions marker = new MarkerOptions().position(new LatLng(lat, lng)).title("Pickup : "+title);
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.icon_pickup);

        marker.icon(icon);
        markers.add(marker);
        mMapServiceView.addMarker(marker);
    }
    public void Offlice_Activity(LatLng location){
        String location_to_string = Double.toString(location.latitude) + ","+Double.toString(location.longitude);
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr="+location_to_string));
        startActivity(intent);
    }
    public void DisableLoading(){
        img_rider_activity_button.setEnabled(true);
        progressBar.setVisibility(View.GONE);
    }
    public void EnableLoading(){
        img_rider_activity_button.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
    }
    public void LoadLocation(List<DeliveryParcel> parcels){
        markers.clear();
        btn_navigation.setVisibility(View.GONE);
        for(int i =0;i<parcels.size();i++){
            AddMarkers(parcels.get(i).getLocation().getGeoPoints().getLat(),parcels.get(i).getLocation().getGeoPoints().getLng(),parcels.get(i).getName());
        }

        LoadAllMarkers();


    }
    public void LoadAllMarkers(){
        if(Databackbone.getinstance().current_location == null) {
            //Databackbone.getinstance().showAlsertBox(activity_mapview.this, "Error", "Activate your GPS");
            return;
        }
        MarkerOptions currentmarker = new MarkerOptions().position(new LatLng(Databackbone.getinstance().current_location.latitude, Databackbone.getinstance().current_location.longitude));
        markers.add(currentmarker);
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (MarkerOptions marker : markers) {
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds = builder.build();
        int padding = 100; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        mMapServiceView.moveCamera(cu);
        mMapServiceView.animateCamera(cu);

    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void LoadParcelsForDelivery(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Databackbone.getinstance().Base_URL).addConverterFactory(GsonConverterFactory.create()).build();
        swift_api_delivery riderapidata = retrofit.create(swift_api_delivery.class);
        EnableLoading();
        Call<List<RiderActivityDelivery>> call = riderapidata.manageTaskfordelivery(Databackbone.getinstance().rider.getId(),(Databackbone.getinstance().rider.getUserId()));
        call.enqueue(new Callback<List<RiderActivityDelivery>>() {
            @Override
            public void onResponse(Call<List<RiderActivityDelivery>> call, Response<List<RiderActivityDelivery>> response) {
                if(response.isSuccessful()){

                    List<RiderActivityDelivery> parcels = response.body();
                    // System.out.println(parcels.size());
                    if(parcels == null)
                    {
                        tx_parcels_status_count.setText("0 Task Pending");
                    }
                    else{
                        Databackbone.getinstance().parcelsdelivery = parcels;
                        tx_parcels_status_count.setText(Integer.toString(parcels.size()) + " Task Pending");
                    }
                    DisableLoading();

                }
                else{
                    DisableLoading();
                }

            }

            @Override
            public void onFailure(Call<List<RiderActivityDelivery>> call, Throwable t) {
                System.out.println(t.getCause());
                tx_parcels_status_count.setText("0 Task Pending");
                DisableLoading();
            }
        });


    }

}
