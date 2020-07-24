package com.lanit_tercom.dogfriendly_studproject.data.geofire;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.LocationCallback;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.AuthManager;

public class UserGeoFire {

    private AuthManager authManager;
    private static final String CHILD_LOCATIONS = "Locations";
    private static GeoFire geoFire;
    private static DatabaseReference referenceDatabase;

    public UserGeoFire(AuthManager authManager){
        this.authManager = authManager;
        referenceDatabase = FirebaseDatabase.getInstance().getReference(CHILD_LOCATIONS);
        initialize();
    }

    public void initialize(){
        if (geoFire == null) geoFire = new GeoFire(referenceDatabase);
    }

    public void currentUserSetLocation(Double latitude, Double longitude, UserLocationCallback userLocationCallback){
        String userId = authManager.getCurrentUserId();
        geoFire.setLocation(userId, new GeoLocation(latitude, longitude), new GeoFire.CompletionListener() {
            @Override
            public void onComplete(String key, DatabaseError error) {
                // onSuccess / onFailure listener'ы не нашел
                if (error != null)
                    userLocationCallback.onError(error);
                else
                    userLocationCallback.onLocationSet();
            }
        });
    }

    public void currentUserQueryAtLocation(double radius, UserQueryAtLocationCallback userQueryAtLocationCallback){
        String userId = authManager.getCurrentUserId();
        geoFire.getLocation(userId, new LocationCallback() {
            @Override
            public void onLocationResult(String key, GeoLocation location) {
                GeoQuery geoQuery = geoFire.queryAtLocation(location, radius);
                userQueryAtLocationCallback.onQueryCreated(geoQuery);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                userQueryAtLocationCallback.onError(databaseError);
            }
        });
    }

    interface Error{
        void onError(DatabaseError databaseError);
    }

    interface UserLocationCallback extends Error{
        void onLocationLoaded(GeoLocation geoLocation);
        void onLocationSet();
    }

    interface UserQueryAtLocationCallback extends Error{
        void onQueryCreated(GeoQuery geoQuery);
    }
}