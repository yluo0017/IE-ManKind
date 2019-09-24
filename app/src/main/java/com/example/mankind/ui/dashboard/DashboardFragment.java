package com.example.mankind.ui.dashboard;

import android.content.Context;
import android.content.Intent;
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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.example.mankind.CheckAdapter;
import com.example.mankind.DatabaseHelper;
import com.example.mankind.Entity.Tasks;
import com.example.mankind.MyApplication;
import com.example.mankind.NavigationActivity;
import com.example.mankind.NoCheckAdapter;
import com.example.mankind.NoCheckRecyclerView_Config;
import com.example.mankind.Question1Activity;
import com.example.mankind.Question2_1Activity;
import com.example.mankind.R;
import com.example.mankind.RecyclerView_Config;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * The type Dashboard fragment.
 */
public class DashboardFragment extends Fragment implements CheckAdapter.CheckItemListener, NoCheckAdapter.CheckTaskListener {

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
    private List<Tasks> newRestoreList = new ArrayList<>();
    private FirebaseFirestore db;
    private ProgressBar pb;
    private Switch aSwitch;
    private Button button;
    private Button undo;
    private TextView tv;
    private CircleProgressBar progressBar;
    private NoCheckAdapter noCheckAdapter;
    /**
     * The Ongoing tasks.
     */
    private final String ongoingTasks = "ongoingTasks";
    /**
     * The Remained tasks.
     */
    private final String remainedTasks = "remainedTasks";
    /**
     * The Completed tasks.
     */
    private final String completedTasks = "completedTasks";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard , container, false);
        spinner = root.findViewById(R.id.spinner);
        pb = root.findViewById(R.id.progressBar);
        recyclerView = root.findViewById(R.id.recycler_view);
        checkedView = root.findViewById(R.id.recycler_view_checked);
        tv = root.findViewById(R.id.congratulation);
        initSwitch(root);
        initSpinner();
        initList(root);
        initCheckedTasks();
        initButton(root);
        initText();
        infoDisplay(root);
        return root;
    }

    private void initProgressBar(View root) {
        progressBar = root.findViewById(R.id.custom_progress5);
        progressBar.setProgressFormatter(new CircleProgressBar.ProgressFormatter() {
            @Override
            public CharSequence format(int progress, int max) {
                return progress + "%";
            }
        });
        simulateProgress();
    }


    private void simulateProgress() {
        progressBar.setProgress(100*checkedList.size()/(checkedList.size()+displayList.size()+remainedList.size()));
    }


    private void initText() {
        if(checkedList.size() == 10)
            tv.setVisibility(View.VISIBLE);
    }

    private void initList(View root) {
        readFile();
        if(displayList.size() == 0 && checkedList.size() == 0 && remainedList.size() == 0)
            initTasks(root);
        else{
            initType();
            String temptype = "";
            if(displayList.size() != 0)
                temptype = displayList.get(0).getType();
            else if(checkedList.size() != 0)
                temptype = checkedList.get(0).getType();
            else
                temptype = remainedList.get(0).getType();

            if(!temptype.equals(type))
                initTasks(root);
            else{
                pb.setVisibility(View.GONE);
                mCheckAdapter = new CheckAdapter(getActivity(), displayList, DashboardFragment.this);
                new RecyclerView_Config().setConfig(recyclerView, getActivity(),mCheckAdapter);
                noCheckAdapter = new NoCheckAdapter(getActivity(), checkedList, DashboardFragment.this);
                new NoCheckRecyclerView_Config().setConfig(checkedView, getActivity(),noCheckAdapter);
                initProgressBar(root);
            }
        }
    }

    private void readFile() {
        try{
            FileInputStream fileInputStream= getActivity().openFileInput(ongoingTasks);
            if (fileInputStream!=null){
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                displayList = (List<Tasks>) objectInputStream.readObject();
                if(displayList == null)
                    displayList = new ArrayList<>();
                objectInputStream.close();
                fileInputStream.close();
            }
        }catch (IOException io){
            io.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try{
            FileInputStream fileInputStream= getActivity().openFileInput(remainedTasks);
            if (fileInputStream!=null){
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                remainedList = (List<Tasks>) objectInputStream.readObject();
                if(remainedList == null)
                    remainedList = new ArrayList<>();
                objectInputStream.close();
                fileInputStream.close();
            }
        }catch (IOException io){
            io.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try{
            FileInputStream fileInputStream= getActivity().openFileInput(completedTasks);
            if (fileInputStream!=null){
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                checkedList = (List<Tasks>) objectInputStream.readObject();
                if (checkedList == null)
                    checkedList = new ArrayList<>();
                objectInputStream.close();
                fileInputStream.close();
            }
        }catch (IOException io){
            io.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initCheckedTasks() {
        noCheckAdapter = new NoCheckAdapter(getActivity(), checkedList, DashboardFragment.this);
        new NoCheckRecyclerView_Config().setConfig(checkedView, getActivity(),noCheckAdapter);
    }

    private void initUndoButton(View root) {
        undo = root.findViewById(R.id.undo);
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedList.removeAll(newRestoreList);
                Set<Tasks> set = new HashSet<>(newRestoreList);
                for (Tasks t : set) {
                    displayList.add(0, t);
                }
                while (displayList.size() > num) {
                    remainedList.add(0, displayList.remove(displayList.size() - 1));
                }
                if (checkedList.size() != 10)
                    tv.setVisibility(View.GONE);
                newRestoreList.clear();
                mCheckAdapter.initCheck(false);
                noCheckAdapter.initCheck(false);
                mCheckAdapter.notifyDataSetChanged();
                noCheckAdapter.notifyDataSetChanged();
                simulateProgress();
                writeFile();
            }
        });
    }

    private void initButton(View root) {
        initUndoButton(root);
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
                if(checkedList.size() == 10)
                    tv.setVisibility(View.VISIBLE);
                newCheckedList.clear();
                mCheckAdapter.initCheck(false);
                noCheckAdapter.initCheck(false);
                mCheckAdapter.notifyDataSetChanged();
                noCheckAdapter.notifyDataSetChanged();
                simulateProgress();
                writeFile();
            }
        });
    }

    private void writeFile() {
        try{
            FileOutputStream fileOutputStream = getActivity().openFileOutput(ongoingTasks,
                    Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(displayList);
            objectOutputStream.close();
            fileOutputStream.close();
        }catch (IOException io){
            io.printStackTrace();
        }
        try{
            FileOutputStream fileOutputStream = getActivity().openFileOutput(remainedTasks,
                    Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(remainedList);
            objectOutputStream.close();
            fileOutputStream.close();
        }catch (IOException io){
            io.printStackTrace();
        }
        try{
            FileOutputStream fileOutputStream = getActivity().openFileOutput(completedTasks,
                    Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(checkedList);
            objectOutputStream.close();
            fileOutputStream.close();
        }catch (IOException io){
            io.printStackTrace();
        }
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

    private void initTasks(final View root){
        initType();
//        displayList.add(new Tasks("2","2"));
//        displayList.add(new Tasks("3","3"));
//        displayList.add(new Tasks("4","4"));
//        remainedList.add(new Tasks("5","5"));
//        remainedList.add(new Tasks("6","6"));
//        remainedList.add(new Tasks("7","7"));
//        remainedList.add(new Tasks("8","8"));
//        remainedList.add(new Tasks("9","9"));
//        remainedList.add(new Tasks("10","10"));
//        checkedList.add(new Tasks("1","1"));
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
                            Tasks atask = new Tasks(doc.getString("des"), type);
                            tasks.add(atask);
                        }
                        if(tasks.size() > num){
                            displayList = new ArrayList<>(tasks.subList(0,num));
                            remainedList = new ArrayList<>(tasks.subList(num, tasks.size()));
                        }
                        mCheckAdapter = new CheckAdapter(getActivity(), displayList, DashboardFragment.this);
                        new RecyclerView_Config().setConfig(recyclerView, getActivity(),mCheckAdapter);
                        initProgressBar(root);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pb.setVisibility(View.GONE);
                    }
                });
    }

    private void initType() {
        type = ((MyApplication)(getActivity().getApplication())).getType();

    }

    private void initSpinner() {
        num = 3;
        final String[] spinnerItems = {"3 tasks/time", "5 tasks/time"};
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
                mCheckAdapter.initCheck(false);
            }
            else
                break;
        }
        while(displayList.size() > num){
            remainedList.add(0,displayList.remove(displayList.size()-1));
            mCheckAdapter.initCheck(false);
        }
        mCheckAdapter.notifyDataSetChanged();
        noCheckAdapter.notifyDataSetChanged();
        }

    private void infoDisplay(View root) {
        Button info = root.findViewById(R.id.info_icon);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                dialogBuilder.setMessage("In curriculum a series of tasks are designed for you." + "\n" +
                            "\n" + "You can track and manage your tasks by clicking buttons and checkboxes.");
                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public void itemChecked(Tasks task, boolean isChecked) {
        if(isChecked){
            newCheckedList.add(task);
        }
        else{
            if(newCheckedList.contains(task))
                newCheckedList.remove(task);
        }

    }

    @Override
    public void TaskChecked(Tasks task, boolean isChecked) {
        if(isChecked){
            newRestoreList.add(task);
        }
        else{
            if(newRestoreList.contains(task))
                newRestoreList.remove(task);
        }
    }
}