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

import com.example.mankind.Entity.Record;
import com.example.mankind.Entity.Tasks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * The type Record adapter.
 */
public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder>{
    private Context mContext;
    private List<Record> mDatas;

    /**
     * Instantiates a new Record adapter.
     *
     * @param mContext the m context
     * @param mDatas   the m datas
     */
    public RecordAdapter(Context mContext, List<Record> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @NonNull
    @Override
    public RecordAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.record, parent, false);
        return new RecordAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecordAdapter.ViewHolder holder, final int position) {
        Record record = mDatas.get(position);
        holder.time.setText(record.getTime());
        holder.result.setText("" + record.getResult());
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /**
     * The type View holder.
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView time;
        private TextView result;

        private ViewHolder(View itemView) {
            super(itemView);
           time = itemView.findViewById(R.id.time);
           result = itemView.findViewById(R.id.result);
        }
    }


}
