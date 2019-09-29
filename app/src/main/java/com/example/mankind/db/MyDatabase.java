package com.example.mankind.db;

import com.example.mankind.Entity.Record;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * The type My database.
 */
@Database(entities = {Record.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {
    /**
     * Gets dao.
     *
     * @return the dao
     */
    abstract public myDao getDao();
}
