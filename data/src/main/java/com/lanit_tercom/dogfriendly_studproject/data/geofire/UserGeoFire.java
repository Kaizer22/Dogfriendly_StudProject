package com.lanit_tercom.dogfriendly_studproject.data.geofire;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.firebase.geofire.LocationCallback;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.AuthManager;

import java.util.HashMap;
import java.util.Map;

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
                Map<String, Double[]> query = new HashMap<>();
                geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
                    @Override
                    public void onKeyEntered(String key, GeoLocation location) {
                        Double[] coordinates = new Double[]{location.latitude, location.longitude};
                        query.put(key, coordinates);
                    }

                    @Override
                    public void onKeyExited(String key) {
                        //not yet implemented
                    }

                    @Override
                    public void onKeyMoved(String key, GeoLocation location) {
                        //not yet implemented
                    }

                    @Override
                    public void onGeoQueryReady() {
                        userQueryAtLocationCallback.onQueryLoaded(query);
                    }

                    @Override
                    public void onGeoQueryError(DatabaseError error) {
                        userQueryAtLocationCallback.onError(error);
                    }
                });
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
        void onQueryLoaded(Map<String, Double[]> query);
    }
}