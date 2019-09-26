package com.example.mankind.db;

import android.content.Context;

import com.example.mankind.Entity.Record;

import java.util.List;

import androidx.room.Room;

/**
 * The type Db facade.
 */
public class DBFacade {
    private myDao dao;
    private DBFacade(){};

    /**
     * Get instance db facade.
     *
     * @return the db facade
     */
    public static DBFacade getInstance(){
        return Initializer.INSTANCE;
    }

    /**
     * Init.
     *
     * @param context the context
     */
    public void init(Context context){
        MyDatabase myDatabase = Room.databaseBuilder(
                context,
                MyDatabase.class,
                "my_db"
        ).allowMainThreadQueries().build();
        dao = myDatabase.getDao();
    }

    /**
     * Get all record list.
     *
     * @return the list
     */
    public List<Record> getAllRecord(){
        return dao.getAllRecord();
    }

    /**
     * Insert.
     *
     * @param record the record
     */
    public void insert(Record record){
        dao.insert(record);
    }

    /**
     * Delete.
     *
     * @param record the record
     */
    public void delete(Record record){
        dao.delete(record);
    }

    private static class Initializer{
        private static final DBFacade INSTANCE = new DBFacade();
    }
}
