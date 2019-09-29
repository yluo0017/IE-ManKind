package com.example.mankind;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * The type Records config.
 */
public class Records_Config {
    private Context mcontext;
    private RecordAdapter recordAdapter;


    /**
     * Set config.
     *
     * @param recyclerView  the recycler view
     * @param context       the context
     * @param recordAdapter the record adapter
     */
    public void setConfig(RecyclerView recyclerView, Context context, RecordAdapter recordAdapter){
        mcontext = context;
        recordAdapter = recordAdapter;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(recordAdapter);
    }
}
