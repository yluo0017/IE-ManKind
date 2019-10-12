package com.example.mankind;

public interface TreeStateChangeListener {

    //open node
    void onOpen(TreeItem treeItem, int position);
    //close node
    void onClose(TreeItem treeItem, int position);
}