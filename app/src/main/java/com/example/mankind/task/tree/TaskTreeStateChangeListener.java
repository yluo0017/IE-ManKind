package com.example.mankind.task.tree;

public interface TaskTreeStateChangeListener {
    void onOpen(TaskTreeItem treeItem, int position);
    void onClose(TaskTreeItem treeItem, int position);
}