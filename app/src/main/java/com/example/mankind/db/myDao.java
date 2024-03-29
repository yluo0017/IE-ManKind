package com.example.mankind.db;

import com.example.mankind.Entity.Record;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

/**
 * The interface My dao.
 */
@Dao
public interface myDao {

    /**
     * Gets all record.
     *
     * @return the all record
     */
    @Query("SELECT * FROM Record ORDER BY time desc")
    List<Record> getAllRecord();

    /**
     * Insert.
     *
     * @param record the record
     */
    @Insert
    void insert(Record record);

    /**
     * Delete.
     *
     * @param record the record
     */
    @Delete
    void delete(Record record);
}
