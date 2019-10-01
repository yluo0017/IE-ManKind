package com.example.mankind;

import android.content.Context;
import android.graphics.Paint;
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

/**
 * The type No check adapter.
 */
public class NoCheckAdapter extends RecyclerView.Adapter<NoCheckAdapter.ViewHolder> {
    //context
    private Context mContext;
    //completed tasks
    private List<Tasks> mDatas;
    //listener
    private NoCheckAdapter.CheckTaskListener mCheckListener;
    //map to store status of each ccheckbox
    private Map<Integer, Boolean> checkStatus = new HashMap<>();


    /**
     * Instantiates a new Check adapter.
     *
     * @param mContext       the context
     * @param mDatas         the datas
     * @param mCheckListener the check listener
     */
    public NoCheckAdapter(Context mContext, List<Tasks> mDatas, NoCheckAdapter.CheckTaskListener mCheckListener) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.mCheckListener = mCheckListener;
        initData();
    }

    private void initData() {
        initCheck(false);
    }

    /**
     * Init the check map.
     *
     * @param flag the flag
     */
    public void initCheck(boolean flag) {
        for (int i = 0; i < mDatas.size(); i++) {
            checkStatus.put(i, flag);
        }
    }

    @Override
    public NoCheckAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
        NoCheckAdapter.ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.item_content_tv.setText(mDatas.get(position).getDes());
        holder.item_content_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.item_cb.setOnCheckedChangeListener(null);
        holder.item_cb.setChecked(checkStatus.get(position));
        holder.item_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkStatus.put(position, isChecked);
                if (null != mCheckListener) {
                    mCheckListener.TaskChecked(mDatas.get(position), holder.item_cb.isChecked());
                }
                notifyDataSetChanged();
            }
        });
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /**
     * The type View holder.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        private CheckBox item_cb;
        private LinearLayout item_content_ll;

        /**
         * The Item content tv.
         */
        TextView item_content_tv;

        /**
         * Instantiates a new View holder.
         *
         * @param itemView the item view
         */
        public ViewHolder(View itemView) {
            super(itemView);
            item_cb = itemView.findViewById(R.id.item_cb);
            item_content_tv = itemView.findViewById(R.id.item_task);
            item_content_ll = itemView.findViewById(R.id.item_content_ll);
        }
    }

    /**
     * The interface Check item listener.
     */
    public interface CheckTaskListener {

        /**
         * Item checked.
         *
         * @param task      the task
         * @param isChecked the is checked
         */
        void TaskChecked(Tasks task, boolean isChecked);
    }
}