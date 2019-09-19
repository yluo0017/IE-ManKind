package com.example.mankind;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * The type No check recycler view config.
 */
public class NoCheckRecyclerView_Config {
    private Context mcontext;
    private NoCheckAdapter mcheckAdapter;

    /**
     * Set config.
     *
     * @param recyclerView   the recycler view
     * @param context        the context
     * @param noCheckAdapter the no check adapter
     */
    public void setConfig(RecyclerView recyclerView, Context context, NoCheckAdapter noCheckAdapter){
        mcontext = context;
        mcheckAdapter = noCheckAdapter;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mcheckAdapter);
    }
}
