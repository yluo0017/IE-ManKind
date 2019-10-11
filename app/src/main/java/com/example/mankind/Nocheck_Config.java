package com.example.mankind;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Nocheck_Config {
    private Context mcontext;

    /**
     * Set config.
     *
     * @param recyclerView the recycler view
     * @param context      the context

     */
    public void setConfig(RecyclerView recyclerView, Context context, RecyclerView.Adapter noCheckAdapter){
        mcontext = context;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(noCheckAdapter);
    }
}
