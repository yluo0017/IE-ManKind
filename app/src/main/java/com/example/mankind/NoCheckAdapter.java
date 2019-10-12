package com.example.mankind;


import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mankind.Entity.Tasks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.RecyclerView;

/**
 * The type Check adapter.
 */
public class NoCheckAdapter extends RecyclerView.Adapter<NoCheckAdapter.ViewHolder> {
    //context
    private Context mContext;
    //ongoing tasks list
    private List<Tasks> mDatas;
    //map to store status of each checkbox
    private Map<Integer, Boolean> checkStatus = new HashMap<>();


    /**
     * Instantiates a new Check adapter.
     *
     * @param mContext       the context
     * @param mDatas         the datas
     */
    public NoCheckAdapter(Context mContext, List<Tasks> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        initData();
    }

    private void initData() {
        initCheck();
    }

    /**
     * Init the check map.
     *
     */
    public void initCheck() {
        for (int i = 0; i < mDatas.size(); i++) {
            checkStatus.put(i, mDatas.get(i).isChecked());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_checked, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.item_content_tv.setText(mDatas.get(position).getDes());
        if(checkStatus.get(position)){
            holder.item_content_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else
            holder.item_content_tv.getPaint().setFlags( holder.item_content_tv.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /**
     * The type View holder.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView item_content_tv;

        /**
         * Instantiates a new View holder.
         *
         * @param itemView the item view
         */
        public ViewHolder(View itemView) {
            super(itemView);
            item_content_tv = itemView.findViewById(R.id.item_task);
        }
    }
}
