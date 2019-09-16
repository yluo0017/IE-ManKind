package com.example.mankind.ui.dashboard;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mankind.CheckAdapter;
import com.example.mankind.DatabaseHelper;
import com.example.mankind.Entity.Tasks;
import com.example.mankind.MyApplication;
import com.example.mankind.NoCheckAdapter;
import com.example.mankind.NoCheckRecyclerView_Config;
import com.example.mankind.R;
import com.example.mankind.RecyclerView_Config;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class DashboardFragment extends Fragment implements CheckAdapter.CheckItemListener {

    private Spinner spinner;
    private int num;
    private RecyclerView recyclerView;
    private RecyclerView checkedView;
    private String type;
    private CheckAdapter mCheckAdapter;
    private List<Tasks> tasks = new ArrayList<>();
    private List<Tasks> newCheckedList = new ArrayList<>();
    private List<Tasks> checkedList = new ArrayList<>();
    private List<Tasks> displayList = new ArrayList<>();
    private List<Tasks> remainedList = new ArrayList<>();
    private FirebaseFirestore db;
    private ProgressBar pb;
    private Switch aSwitch;
    private Button button;
    private NoCheckAdapter noCheckAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard , container, false);
        spinner = root.findViewById(R.id.spinner);
        pb = root.findViewById(R.id.progressBar);
        recyclerView = root.findViewById(R.id.recycler_view);
        checkedView = root.findViewById(R.id.recycler_view_checked);
        initSwitch(root);
        initSpinner();
        initTasks();
        initCheckedTasks();
        initButton(root);
        return root;
    }

    private void initCheckedTasks() {
        checkedList = new ArrayList<>();
        noCheckAdapter = new NoCheckAdapter(getActivity(), checkedList);
        new NoCheckRecyclerView_Config().setConfig(checkedView, getActivity(),noCheckAdapter);
    }

    private void initButton(View root) {
        button = root.findViewById(R.id.submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayList.removeAll(newCheckedList);
                Set<Tasks> set = new HashSet<>(newCheckedList);
                checkedList.addAll(new ArrayList<Tasks>(set));
                while(displayList.size() < num){
                    if(!remainedList.isEmpty()){
                        displayList.add(remainedList.remove(0));
                    }
                    else
                        break;
                }
                mCheckAdapter.notifyDataSetChanged();
                noCheckAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initSwitch(final View root) {
        aSwitch = root.findViewById(R.id.switch1);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    root.findViewById(R.id.checked_task).setVisibility(View.VISIBLE);
                    checkedView.setVisibility(View.VISIBLE);
                }

                else{
                    root.findViewById(R.id.checked_task).setVisibility(View.GONE);
                    checkedView.setVisibility(View.GONE);
                }

            }
        });
    }

    private void initTasks(){
        initType();
        tasks = new ArrayList<>();
        tasks.add(new Tasks("1", "1"));
        tasks.add(new Tasks("2", "1"));
        tasks.add(new Tasks("3", "1"));
        tasks.add(new Tasks("4", "1"));
        tasks.add(new Tasks("5", "1"));
        tasks.add(new Tasks("6", "1"));
        tasks.add(new Tasks("7", "1"));
        tasks.add(new Tasks("8", "1"));
        tasks.add(new Tasks("9", "1"));
        tasks.add(new Tasks("10", "1"));
        remainedList.add(new Tasks("11", "1"));
        remainedList.add(new Tasks("12", "1"));
        remainedList.add(new Tasks("13", "1"));
        remainedList.add(new Tasks("14", "1"));
        remainedList.add(new Tasks("15", "1"));
        checkedList = new ArrayList<>();
        newCheckedList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
        db.collection(type)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        pb.setVisibility(View.GONE);
                        for(DocumentSnapshot doc : task.getResult()){
                            tasks.add(new Tasks(doc.getString("des"), type));
                        }
                        displayList = new ArrayList<>(tasks.subList(0,num));
                        remainedList = new ArrayList<>(tasks.subList(num, tasks.size()));
                        mCheckAdapter = new CheckAdapter(getActivity(), displayList, DashboardFragment.this);
                        new RecyclerView_Config().setConfig(recyclerView, getActivity(),mCheckAdapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pb.setVisibility(View.GONE);
                        Log.e("fail", "fail");
                    }
                });
    }

    private void initType() {
        type = ((MyApplication)(getActivity().getApplication())).getType();
    }

    private void initSpinner() {
        num = 3;
        final String[] spinnerItems = {"3 tasks/week", "5 tasks/week", "10 tasks/week"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, spinnerItems);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(0, true);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    num = 3;
                }
                if(position == 1){
                    num = 5;
                }
                if(position == 2){
                    num = 10;
                }
                setTasks();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setTasks() {
        while(displayList.size() < num){
            if(!remainedList.isEmpty()){
                displayList.add(remainedList.remove(0));
            }
            else
                break;
        }
        while(displayList.size() > num){
            remainedList.add(0,displayList.remove(displayList.size()-1));
        }
        mCheckAdapter.notifyDataSetChanged();
        noCheckAdapter.notifyDataSetChanged();
        }


    @Override
    public void itemChecked(Tasks task, boolean isChecked) {
        if(isChecked){
            newCheckedList.add(task);
            Log.e("", "itemChecked: " + task.getDes() );
        }
        else{
            if(newCheckedList.contains(task))
                newCheckedList.remove(task);
        }

    }
}