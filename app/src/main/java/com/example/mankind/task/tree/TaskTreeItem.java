package com.example.mankind.task.tree;

import java.util.List;

/**
 * Created by KaelLi on 2018/11/26.
 */
public class TaskTreeItem {
    public String title;
    public boolean isChecked;
    public String type;
    public int itemLevel;
    public int itemState;
    public List<TaskTreeItem> child;
}