package com.example.mankind;

import com.example.mankind.Entity.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;


public class DatabaseHelper {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    List<Task> tasks;

    public DatabaseHelper() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Task");
        tasks = new ArrayList<>();
    }

    public List<Task> readTask(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tasks.clear();
                List<String> key = new ArrayList<>();
                for(DataSnapshot node : dataSnapshot.getChildren()){
                    key.add(node.getKey());
                    tasks.add(node.getValue(Task.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return  tasks;
    }
}
