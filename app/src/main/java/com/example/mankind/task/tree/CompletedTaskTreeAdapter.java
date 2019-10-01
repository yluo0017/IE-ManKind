package com.example.mankind.task.tree;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.mankind.R;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class CompletedTaskTreeAdapter extends RecyclerView.Adapter<CompletedTaskTreeAdapter.ViewHolder> implements TaskTreeStateChangeListener {
    private final static int ITEM_STATE_CLOSE = 0;
    private final static int ITEM_STATE_OPEN = 1;
    private Context mContext;
    private List<TaskTreeItem> mList;
    private Map<Integer, Boolean> checkStatus = new HashMap<>();
    private CheckItemListener mCheckListener;

    public List<TaskTreeItem> getmList() {
        return mList;
    }

    public CompletedTaskTreeAdapter(Context context, List<TaskTreeItem> list, CheckItemListener mCheckListener) {
        this.mList = new LinkedList<>(list);
        initList(list, 0);
        this.mContext = context;
        this.mCheckListener = mCheckListener;
        initCheck(false);
    }

    private void initList(List<TaskTreeItem> list, int level) {
        if (list == null || list.size() <= 0) return;
        for (TaskTreeItem item: list) {
            item.itemLevel = level;
            if (item.child != null && item.child.size() > 0) {
                initList(item.child, level + 1);
                //////
                mList.addAll(mList.indexOf(item) + 1, item.child);
                item.itemState = ITEM_STATE_OPEN;
                notifyItemRangeInserted(mList.indexOf(item) + 1, item.child.size());
                notifyItemChanged(mList.indexOf(item));
            }
        }
    }

    public void initCheck(boolean flag) {
        for (int i = 0; i < mList.size(); i++) {
            checkStatus.put(i, flag);
            mList.get(i).isChecked = false;        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_tasktree, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final TaskTreeItem treeItem = mList.get(i);
        viewHolder.mTextView.setText(treeItem.title);
        viewHolder.mTextView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        if (i == mList.size() - 1) {
            viewHolder.mDivider.setVisibility(View.VISIBLE);
        } else if (mList.get(i + 1).itemLevel == 0) {
            viewHolder.mDivider.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mDivider.setVisibility(View.INVISIBLE);
        }

        if (treeItem.child != null && treeItem.child.size() > 0) {
            viewHolder.tvState.setVisibility(View.VISIBLE);
            if (treeItem.itemState == ITEM_STATE_OPEN) {
                viewHolder.tvState.setText("-");
            } else {
                viewHolder.tvState.setText("+");
            }
        } else {
            viewHolder.tvState.setVisibility(View.INVISIBLE);
        }

        ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams)viewHolder.mIndicator.getLayoutParams();
        lp.width = 32;
        lp.height = 32;
        viewHolder.mIndicator.setLayoutParams(lp);
        viewHolder.checkBox.setOnCheckedChangeListener(null);
        viewHolder.checkBox.setChecked(checkStatus.get(i));
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkStatus.put(i, isChecked);
                if (null != mCheckListener) {
                    mList.get(i).isChecked = isChecked;
                    mCheckListener.itemRestored(mList.get(i), viewHolder.checkBox.isChecked());
                    for(int a = 0; a < mList.get(i).child.size(); a++){
                        mCheckListener.itemRestored(mList.get(i).child.get(a),isChecked);
                        mList.get(i).child.get(a).isChecked = isChecked;
                        checkStatus.put(i+1+a, isChecked);
                    }
                }
                if(isChecked)
                    checkChecked();
                else
                    checkUncheked();
                notifyDataSetChanged();
            }
        });
    }

    private void checkUncheked() {
        for(TaskTreeItem t : mList){
            boolean flag = true;
            for(TaskTreeItem i : t.child){
                if(!i.isChecked){
                    flag = false;
                    break;
                }
            }
            if(t.child.size() == 0)
                break;
            if (!flag){
                t.isChecked = false;
                checkStatus.put(mList.indexOf(t),false);

            }

        }
    }


    private void checkChecked() {
        for(TaskTreeItem t : mList){
            boolean flag = true;
            for(TaskTreeItem i : t.child){
                if(!i.isChecked){
                    flag = false;
                    break;
                }
            }
            if(t.child.size() == 0)
                break;
            if (flag){
                t.isChecked = true;
                checkStatus.put(mList.indexOf(t),true);
                mCheckListener.itemRestored(t, true);

            }

        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onOpen(TaskTreeItem treeItem, int position) {
        if (treeItem.child != null && treeItem.child.size() > 0) {
            mList.addAll(position + 1, treeItem.child);
            treeItem.itemState = ITEM_STATE_OPEN;
            notifyItemRangeInserted(position + 1, treeItem.child.size());
            notifyItemChanged(position);
            initCheck(false);
        }
    }

    @Override
    public void onClose(TaskTreeItem treeItem, int position) {
        if (treeItem.child != null && treeItem.child.size() > 0) {
            int nextSameOrHigherLevelNodePosition = mList.size() - 1;
            if (mList.size() > position + 1) {
                for (int i = position + 1; i < mList.size(); i++) {
                    if (mList.get(i).itemLevel <= mList.get(position).itemLevel) {
                        nextSameOrHigherLevelNodePosition = i - 1;
                        break;
                    }
                }
                closeChild(mList.get(position));
                if (nextSameOrHigherLevelNodePosition > position) {
                    mList.subList(position + 1, nextSameOrHigherLevelNodePosition + 1).clear();
                    treeItem.itemState = ITEM_STATE_CLOSE;
                    notifyItemRangeRemoved(position + 1, nextSameOrHigherLevelNodePosition - position);
                    notifyItemChanged(position);
                }
            }
            initCheck(false);
        }
    }

    private void closeChild(TaskTreeItem treeItem) {
        if (treeItem.child != null) {
            for (TaskTreeItem child: treeItem.child) {
                child.itemState = ITEM_STATE_CLOSE;
                closeChild(child);
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View mIndicator;
        TextView tvState;
        TextView mTextView;
        CheckBox checkBox;
        View mDivider;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mIndicator = itemView.findViewById(R.id.vIndicator);
            tvState = itemView.findViewById(R.id.tvState);
            mTextView = itemView.findViewById(R.id.tvTitle);
            checkBox = itemView.findViewById(R.id.checkBox);
            mDivider = itemView.findViewById(R.id.vDivider);
        }

    }

    /**
     * The interface Check item listener.
     */
    public interface CheckItemListener {

        /**
         * Item checked.
         * @param isChecked the is checked
         */
        void itemRestored(TaskTreeItem treeItem, boolean isChecked);
    }
}
