package com.example.mankind;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NoCheckRecyclerView_Config {
    private Context mcontext;
    private NoCheckAdapter mcheckAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, NoCheckAdapter noCheckAdapter){
        mcontext = context;
        mcheckAdapter = noCheckAdapter;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mcheckAdapter);
    }
}
