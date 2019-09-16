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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.RecyclerView;

public class CheckAdapter extends RecyclerView.Adapter<CheckAdapter.ViewHolder> {
    private Context mContext;
    private List<Tasks> mDatas;
    private CheckItemListener mCheckListener;
    private Map<Integer, Boolean> checkStatus = new HashMap<>();


    public CheckAdapter(Context mContext, List<Tasks> mDatas, CheckItemListener mCheckListener) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.mCheckListener = mCheckListener;
        initData();
    }

    private void initData() {
        initCheck(false);
    }

    public void initCheck(boolean flag) {
        for (int i = 0; i < mDatas.size(); i++) {
            //更改指定位置的数据
            checkStatus.put(i, flag);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.item_content_tv.setText(mDatas.get(position).getDes());
        //清除监听器
        holder.item_cb.setOnCheckedChangeListener(null);
        //设置选中状态
        holder.item_cb.setChecked(checkStatus.get(position));
        //再设置一次CheckBox的选中监听器，当CheckBox的选中状态发生改变时，把改变后的状态储存在Map中
        holder.item_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkStatus.put(position, isChecked);
                if (null != mCheckListener) {
                    mCheckListener.itemChecked(mDatas.get(position), holder.item_cb.isChecked());
                }
                notifyDataSetChanged();
                //check状态一旦改变，保存的check值也要发生相应的变化
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
