package com.example.mankind;

import android.content.Context;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerView_Config {
    private Context mcontext;
    private CheckAdapter mcheckAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, CheckAdapter checkAdapter){
        mcontext = context;
        mcheckAdapter = checkAdapter;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mcheckAdapter);
    }
}
