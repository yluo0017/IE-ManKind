package com.example.mankind.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mankind.Entity.Links;
import com.example.mankind.MyApplication;
import com.example.mankind.R;
import com.example.mankind.TreeAdapter;
import com.example.mankind.TreeItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The type Home fragment.
 */
public class HomeFragment extends Fragment implements TreeAdapter.ClickItemListener {

    //violence type
    private String type;
    //title of the screen
    private TextView textView;
    /**
     * The Organization.
     */
    private final String link = "_link";
    private final String organization = "Organisation";
    private final String court = "Article";
    private final String lawyer = "lawyer";
    private ProgressBar pb;
    private List<Links> organizations;
    private List<Links> courts;
    private List<Links> lawyers;
    private RecyclerView rvTree;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        textView = root.findViewById(R.id.home_title);
        rvTree = root.findViewById(R.id.rvTree);
        initType();
        init();
        initProgressBar(root);

        return root;
    }

    private void initProgressBar(View root) {
        pb = root.findViewById(R.id.progressBar);
    }

    private void initType() {
        type = ((MyApplication)getActivity().getApplication()).getType();
    }

    private void init() {
        String title = "Websites to get rid of " + type + " abuse";
        textView.setText(title);
        organizations = new ArrayList<>();
        courts = new ArrayList<>();
        lawyers = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
        db.collection(type + link)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot doc : task.getResult()){
                            String type = (String)doc.get("type");
                            if(type.equals(organization))
                                organizations.add(new Links((String)doc.get("name"), (String)doc.get("link"), (String)doc.get("type")));
                            if(type.equals(court))
                                courts.add(new Links((String)doc.get("name"), (String)doc.get("link"), (String)doc.get("type")));
                            if(type.equals(lawyer))
                                lawyers.add(new Links((String)doc.get("name"), (String)doc.get("link"), (String)doc.get("type")));
                        }
                        pb.setVisibility(View.GONE);
                        TreeAdapter treeAdapter = new TreeAdapter(getActivity(), initList(),HomeFragment.this);
                        rvTree.setLayoutManager(new LinearLayoutManager(getActivity()));
                        rvTree.setAdapter(treeAdapter);
                        rvTree.setVisibility(View.VISIBLE);
                    }
                });
    }
    private List<TreeItem> initList() {
        List<TreeItem> list = new ArrayList<>();

        TreeItem item_0_0 = new TreeItem();
        item_0_0.title = "Organization Resouces";
        item_0_0.child = new ArrayList<>();
        for(Links l : organizations){
            TreeItem item = new TreeItem();
            item.title = l.getName();
            item.link = l.getLink();
            item_0_0.child.add(item);
        }
        list.add(item_0_0);
        TreeItem item_0_1 = new TreeItem();
        item_0_1.title = "Articles";
        item_0_1.child = new ArrayList<>();
        for(Links l : courts){
            TreeItem item = new TreeItem();
            item.title = l.getName();
            item.link = l.getLink();
            item_0_1.child.add(item);
        }
        list.add(item_0_1);
        return list;
    }

    @Override
    public void itemClicked(TreeItem treeItem) {
        Uri webpage = Uri.parse(treeItem.link);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}