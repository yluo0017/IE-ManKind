package com.example.mankind.Entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * The type Record.
 */
@Entity(tableName = "Record")
public class Record {
    @NonNull
    @PrimaryKey
    private String time;
    private int result;

    /**
     * Instantiates a new Record.
     *
     * @param time   the time
     * @param result the result
     */
    public Record(String time, int result) {
        this.time = time;
        this.result = result;
    }

    /**
     * Gets time.
     *
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * Gets result.
     *
     * @return the result
     */
    public int getResult() {
        return result;
    }
}
