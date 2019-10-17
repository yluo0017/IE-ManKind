package com.example.mankind;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.mankind.Entity.Tasks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.RecyclerView;

public class FinalAdapter extends RecyclerView.Adapter<FinalAdapter.ViewHolder> {
    //context
    private Context mContext;
    //ongoing tasks list
    private List<Tasks> mDatas;
    //listener
    private FinalAdapter.ItemCheckedListener mCheckListener;
    //map to store status of each checkbox
    private Map<Integer, Boolean> checkStatus = new HashMap<>();


    /**
     * Instantiates a new Check adapter.
     *
     * @param mContext       the context
     * @param mDatas         the datas
     * @param mCheckListener the check listener
     */
    public FinalAdapter(Context mContext, List<Tasks> mDatas, FinalAdapter.ItemCheckedListener mCheckListener) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.mCheckListener = mCheckListener;
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
    public FinalAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
        FinalAdapter.ViewHolder viewHolder = new FinalAdapter.ViewHolder(view);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        initCheck();
        holder.stage.setText("s" + mDatas.get(position).getStage());
        holder.stage.setVisibility(View.VISIBLE);
        holder.item_content_tv.setText(mDatas.get(position).getDes());
        holder.item_cb.setOnCheckedChangeListener(null);
        holder.item_cb.setChecked(checkStatus.get(position));
        if(checkStatus.get(position)){
            holder.item_content_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else
            holder.item_content_tv.getPaint().setFlags( holder.item_content_tv.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        holder.item_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkStatus.put(position, isChecked);
                if (null != mCheckListener) {
                    mCheckListener.checked(mDatas.get(position), holder.item_cb.isChecked());
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
        private TextView stage;
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
            stage = itemView.findViewById(R.id.stage);
            item_cb = itemView.findViewById(R.id.item_cb);
            item_content_tv = itemView.findViewById(R.id.item_task);
        }
    }

    public interface ItemCheckedListener {

        /**
         * Item checked.
         *
         * @param task      the task
         * @param isChecked the is checked
         */
        void checked(Tasks task, boolean isChecked);
    }
}
