package com.example.mankind;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * The type Recycler view config.
 */
public class RecyclerView_Config {
    private Context mcontext;
    private CheckAdapter mcheckAdapter;

    /**
     * Set config.
     *
     * @param recyclerView the recycler view
     * @param context      the context
     * @param checkAdapter the check adapter
     */
    public void setConfig(RecyclerView recyclerView, Context context, CheckAdapter checkAdapter){
        mcontext = context;
        mcheckAdapter = checkAdapter;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mcheckAdapter);
    }
}
