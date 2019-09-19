package com.example.mankind;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mankind.Entity.Tasks;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * The type No check adapter.
 */
public class NoCheckAdapter extends RecyclerView.Adapter<NoCheckAdapter.MyViewHolder> {
    private Context mContext;
    private List<Tasks> mDatas;

    /**
     * Instantiates a new No check adapter.
     *
     * @param mContext the m context
     * @param mDatas   the m datas
     */
    public NoCheckAdapter(Context mContext, List<Tasks> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @Override
    public NoCheckAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_checked, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final NoCheckAdapter.MyViewHolder holder, final int position) {
        final Tasks bean = mDatas.get(position);
        holder.item_content_tv.setText(bean.getDes());
        holder.item_content_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG );
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /**
     * The type My view holder.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {

        /**
         * The Item content tv.
         */
        TextView item_content_tv;

        /**
         * Instantiates a new My view holder.
         *
         * @param itemView the item view
         */
        public MyViewHolder(View itemView) {
            super(itemView);
            item_content_tv = (TextView) itemView.findViewById(R.id.item_task);
        }
    }
}