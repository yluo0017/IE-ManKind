package com.example.mankind.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * The type Home fragment.
 */
public class HomeFragment extends Fragment implements TreeAdapter.ClickItemListener {

    //violence type
    private String type;
    //title of the screen
    private TextView textView;
    //link
    private final String link = "_link";
    //organization
    private final String organization = "Organisation";
    //article
    private final String ARTICLE = "Article";
    //internal
    private final String internal = "Internal";
    //progress bar
    private ProgressBar pb;
    //organization links
    private List<Links> organizations;
    //article links
    private List<Links> articles;
    //internal data links
    private List<Links> internals;
    //recycler view to contain links
    private RecyclerView rvTree;
    //tree node
    private TreeItem treeItem;

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

    //init progress bar
    private void initProgressBar(View root) {
        pb = root.findViewById(R.id.progressBar);
    }

    //init type
    private void initType() {
        type = ((MyApplication)getActivity().getApplication()).getType();
    }

    //init resources
    private void init() {
        String title = "Websites to get rid of " + type + " abuse";
        textView.setText(title);
        organizations = new ArrayList<>();
        articles = new ArrayList<>();
        internals = new ArrayList<>();
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
                                organizations.add(new Links((String)doc.get("name"), (String)doc.get("link"), (String)doc.get("type"), (String)doc.get("des")));
                            if(type.equals(ARTICLE))
                                articles.add(new Links((String)doc.get("name"), (String)doc.get("link"), (String)doc.get("type"), (String)doc.get("des")));
                            if(type.equals(internal))
                                internals.add(new Links((String)doc.get("name"), (String)doc.get("link"), (String)doc.get("type"), (String)doc.get("des")));
                        }
                        pb.setVisibility(View.GONE);
                        TreeAdapter treeAdapter = new TreeAdapter(getActivity(), initList(),HomeFragment.this);
                        treeAdapter.onOpen(treeItem,0);
                        rvTree.setLayoutManager(new LinearLayoutManager(getActivity()));
                        rvTree.setAdapter(treeAdapter);
                        rvTree.setVisibility(View.VISIBLE);
                    }
                });
    }

    //init tree
    private List<TreeItem> initList() {
        List<TreeItem> list = new ArrayList<>();

        TreeItem item_0_0 = new TreeItem();
        item_0_0.title = "Organization Resources";
        item_0_0.des = "Find a specially curated list of formal organisations that help people in your situation!";
        item_0_0.child = new ArrayList<>();
        for(Links l : organizations){
            TreeItem item = new TreeItem();
            item.title = l.getName();
            item.link = l.getLink();
            item.des = l.getDes();
            item_0_0.child.add(item);
        }
        list.add(item_0_0);
        this.treeItem = item_0_0;
        TreeItem item_0_1 = new TreeItem();
        item_0_1.title = "Articles";
        item_0_1.des = "Read through a collection of stories, cases and information pertaining to your situation ";
        item_0_1.child = new ArrayList<>();
        for(Links l : articles){
            TreeItem item = new TreeItem();
            item.title = l.getName();
            item.link = l.getLink();
            item.des = l.getDes();
            item_0_1.child.add(item);
        }
        list.add(item_0_1);
        TreeItem item_0_2 = new TreeItem();
        item_0_2.title = "Internal Resources";
        item_0_2.des = "Find our website with the latest update of the app and also other open Data used in the app";
        item_0_2.child = new ArrayList<>();
        for(Links l : internals){
            TreeItem item = new TreeItem();
            item.title = l.getName();
            item.link = l.getLink();
            item.des = l.getDes();
            item_0_2.child.add(item);
        }
        list.add(item_0_2);
        return list;
    }

    @Override
    public void itemClicked(TreeItem treeItem) {
        Log.e("", "itemClicked: " + treeItem.title );
        Uri webpage = Uri.parse(treeItem.link);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}