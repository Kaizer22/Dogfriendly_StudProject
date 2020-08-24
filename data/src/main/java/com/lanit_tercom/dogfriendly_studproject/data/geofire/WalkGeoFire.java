package com.lanit_tercom.dogfriendly_studproject.data.geofire;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.firebase.geofire.LocationCallback;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WalkGeoFire {

    private static final String CHILD_LOCATIONS = "Locations";
    private static GeoFire geoFire;
    private static DatabaseReference referenceDatabase;

    public WalkGeoFire(){
        referenceDatabase = FirebaseDatabase.getInstance().getReference().child(CHILD_LOCATIONS);
        initialize();
    }

    interface Error{
        void onError(Exception exception);
    }

    public interface WalkLocationCallback extends Error{
        void onLocationLoaded();
        void onLocationSet();
    }

    public interface WalkQueryAtLocationCallback extends Error {
        void onQueryLoaded(String key, Double latitude, Double longitude);
    }


    public void initialize(){
        if (geoFire == null) geoFire = new GeoFire(referenceDatabase);
    }

    public void walkSetLocation(String walkId,  Double latitude, Double longitude, WalkLocationCallback walkLocationCallback){
        geoFire.setLocation(walkId, new GeoLocation(latitude, longitude), new GeoFire.CompletionListener() {
            @Override
            public void onComplete(String key, DatabaseError error) {
                if (error != null)
                    walkLocationCallback.onError(error.toException());
                else
                    walkLocationCallback.onLocationSet();
            }
        });
    }

    public void walkQueryAtLocation(String walkId, double radius, WalkQueryAtLocationCallback walkQueryAtLocationCallback){
        geoFire.getLocation(walkId, new LocationCallback() {
            @Override
            public void onLocationResult(String key, GeoLocation location) {
                GeoQuery geoQuery = geoFire.queryAtLocation(location, radius);
                geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
                    @Override
                    public void onKeyEntered(String key, GeoLocation location) {
                        walkQueryAtLocationCallback.onQueryLoaded(key, location.latitude, location.longitude);
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
                        walkQueryAtLocationCallback.onError(error.toException());
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                walkQueryAtLocationCallback.onError(databaseError.toException());
            }
        });
    }
}
