package com.lanit_tercom.dogfriendly_studproject.data.geofire;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.firebase.geofire.LocationCallback;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserGeoFire {

    private static final String CHILD_LOCATIONS = "Locations";
    private static GeoFire geoFire;
    private static DatabaseReference referenceDatabase;

    public UserGeoFire(){
        referenceDatabase = FirebaseDatabase.getInstance().getReference().child(CHILD_LOCATIONS);
        initialize();
    }

    public void initialize(){
        if (geoFire == null) geoFire = new GeoFire(referenceDatabase);
    }

    public void userSetLocation(String userId, Double latitude, Double longitude, UserLocationCallback userLocationCallback){
        geoFire.setLocation(userId, new GeoLocation(latitude, longitude), new GeoFire.CompletionListener() {
            @Override
            public void onComplete(String key, DatabaseError error) {
                // onSuccess / onFailure listener'ы не нашел
                if (error != null)
                    userLocationCallback.onError(error.toException());
                else
                    userLocationCallback.onLocationSet();
            }
        });
    }

    public void userQueryAtLocation(String userId, double radius, UserQueryAtLocationCallback userQueryAtLocationCallback){
        geoFire.getLocation(userId, new LocationCallback() {
            @Override
            public void onLocationResult(String key, GeoLocation location) {
                GeoQuery geoQuery = geoFire.queryAtLocation(location, radius);
                geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
                    @Override
                    public void onKeyEntered(String key, GeoLocation location) {
                        userQueryAtLocationCallback.onQueryLoaded(key, location.latitude, location.longitude);
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
                        //not yet implemented
                    }

                    @Override
                    public void onGeoQueryError(DatabaseError error) {
                        userQueryAtLocationCallback.onError(error.toException());
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                userQueryAtLocationCallback.onError(databaseError.toException());
            }
        });
    }

    interface Error{
        void onError(Exception exception);
    }

    public interface UserLocationCallback extends Error{
        void onLocationLoaded();
        void onLocationSet();
    }

    public interface UserQueryAtLocationCallback extends Error{
        void onQueryLoaded(String key, Double latitude, Double longitude);
    }
}