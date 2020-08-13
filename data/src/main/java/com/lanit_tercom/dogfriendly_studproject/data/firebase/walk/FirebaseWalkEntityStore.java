package com.lanit_tercom.dogfriendly_studproject.data.firebase.walk;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lanit_tercom.dogfriendly_studproject.data.entity.WalkEntity;
import com.lanit_tercom.dogfriendly_studproject.data.exception.RepositoryErrorBundle;
import com.lanit_tercom.dogfriendly_studproject.data.firebase.cache.WalkCache;
import com.lanit_tercom.domain.exception.ErrorBundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseWalkEntityStore implements WalkEntityStore {

    private static final String CHILD_WALKS = "Walks";
    private WalkCache walkCache;
    private DatabaseReference databaseReference;
    private WalkEntity walkEntity = new WalkEntity();

    public FirebaseWalkEntityStore(WalkCache walkCache){
        this.walkCache = walkCache;
        //TODO создать сущности
        this.databaseReference = FirebaseDatabase.getInstance().getReference().child(CHILD_WALKS);
    }

    public FirebaseWalkEntityStore(){
        this.databaseReference = FirebaseDatabase.getInstance().getReference().child(CHILD_WALKS);
        getWalk("ZtVM6D0rX6Vygni8NgyGz6kf9dB3", new GetWalkCallback() {
            @Override
            public void onWalkLoaded(WalkEntity walkEntity) {
                System.out.println("Walk was loaded");
            }

            @Override
            public void onError(ErrorBundle errorBundle) {
                System.out.println("ERROR!!!");
            }
        });
    }


    @Override
    public void getWalk(String userId, GetWalkCallback getWalkCallback) {

        //TODO подкорректировать метод для вывода одной прогулки!!!
        //databaseReference.child(userId).addValueEventListener(new ValueEventListener() {
        List<WalkEntity> walks = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> snapshots = snapshot.child(userId).getChildren();
                for (DataSnapshot keyNode: snapshots){
                    WalkEntity walkEntity = keyNode.getValue(WalkEntity.class);
                    walkEntity.setWalkId(keyNode.getKey());
                    walks.add(walkEntity);
                }


                //walkEntity = snapshot.getValue(WalkEntity.class);
                //walkEntity.setWalkId(walkId);
                getWalkCallback.onWalkLoaded(walkEntity);
                //TODO for test
                for (WalkEntity walkEntityList: walks) {
                    System.out.println(walkEntityList.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                getWalkCallback.onError(new RepositoryErrorBundle(error.toException()));
            }
        });
    }

    @Override
    public void addWalk(WalkEntity walkEntity, AddWalkCallback addWalkCallback) {
        String firebaseId = databaseReference.push().getKey();
        walkEntity.setWalkId(firebaseId);
        Map<String, Object> map = new HashMap<>();
        map.put(walkEntity.getWalkId(), walkEntity);
        databaseReference.updateChildren(map)
                .addOnSuccessListener(aVoid -> addWalkCallback.onWalkAdded())
                .addOnFailureListener(e -> addWalkCallback.onError(new RepositoryErrorBundle(e)));
    }

    @Override
    public void editWalk(WalkEntity walkEntity, EditWalkCallback editWalkCallback) {
        Map<String, Object> map = new HashMap<>();
        map.put(walkEntity.getWalkId(), walkEntity);
        databaseReference.updateChildren(map)
                .addOnSuccessListener(aVoid -> editWalkCallback.onWalkEdited())
                .addOnFailureListener(e -> editWalkCallback.onError(new RepositoryErrorBundle(e)));
    }

    @Override
    public void deleteWalk(WalkEntity walkEntity, DeleteWalkCallback deleteWalkCallback) {
        String id = walkEntity.getWalkId();
        databaseReference.child(CHILD_WALKS).child(id).removeValue()
                .addOnSuccessListener(aVoid -> deleteWalkCallback.onWalkDeleted())
                .addOnFailureListener(e -> deleteWalkCallback.onError(new RepositoryErrorBundle(e)));
    }
}
