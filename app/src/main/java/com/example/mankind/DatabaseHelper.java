package com.example.mankind;

import com.example.mankind.Entity.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;


/**
 * The type Database helper.
 */
public class DatabaseHelper {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    /**
     * The Tasks.
     */
    List<Tasks> tasks;

    /**
     * The interface Data status.
     */
    public interface DataStatus{
        /**
         * Data is loaded.
         *
         * @param tasks the tasks
         * @param key   the key
         */
        void DataIsLoaded(List<Tasks> tasks, List<String> key);
    }

    /**
     * Instantiates a new Database helper.
     *
     * @param path the path
     */
    public DatabaseHelper(String path) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(path);
        tasks = new ArrayList<>();
    }

    /**
     * Read task list.
     *
     * @param dataStatus the data status
     * @return the list
     */
    public List<Tasks> readTask(final DataStatus dataStatus){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tasks.clear();
                List<String> key = new ArrayList<>();
                for(DataSnapshot node : dataSnapshot.getChildren()){
                    key.add(node.getKey());
                    tasks.add(node.getValue(Tasks.class));
                }
                dataStatus.DataIsLoaded(tasks, key);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return  tasks;
    }
}
