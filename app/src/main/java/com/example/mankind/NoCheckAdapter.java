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

public class NoCheckAdapter extends RecyclerView.Adapter<NoCheckAdapter.MyViewHolder> {
    private Context mContext;
    private List<Tasks> mDatas;

    public NoCheckAdapter(Context mContext, List<Tasks> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @Override
    public NoCheckAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
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

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView item_content_tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            item_content_tv = (TextView) itemView.findViewById(R.id.item_task);
        }
    }
}