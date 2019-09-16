package com.example.mankind;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mankind.Entity.Tasks;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class CheckAdapter extends RecyclerView.Adapter<CheckAdapter.ViewHolder> {
    private Context mContext;
    private List<Tasks> mDatas;
    private CheckItemListener mCheckListener;

    public CheckAdapter(Context mContext, List<Tasks> mDatas, CheckItemListener mCheckListener) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.mCheckListener = mCheckListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Tasks bean = mDatas.get(position);
        holder.item_content_tv.setText(bean.getDes());
        holder.item_content_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 if(!holder.item_cb.isChecked()){
                    holder.item_cb.setChecked(true);
                    holder.item_cb.setBackgroundResource(R.drawable.sel_true);
                    if (null != mCheckListener) {
                        mCheckListener.itemChecked(bean, holder.item_cb.isChecked());
                    }
                    notifyDataSetChanged();
                }
                else{
                    holder.item_cb.setChecked(false);
                    holder.item_cb.setBackground(null);
                    if (null != mCheckListener) {
                        mCheckListener.itemChecked(bean, holder.item_cb.isChecked());
                    }
                    notifyDataSetChanged();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CheckBox item_cb;
        private LinearLayout item_content_ll;

        TextView item_content_tv;

        public ViewHolder(View itemView) {
            super(itemView);
            item_cb = (CheckBox) itemView.findViewById(R.id.item_cb);
            item_content_tv = (TextView) itemView.findViewById(R.id.item_task);
            item_content_ll = (LinearLayout) itemView.findViewById(R.id.item_content_ll);
        }
    }

    public interface CheckItemListener {

        void itemChecked(Tasks task, boolean isChecked);
    }
}
