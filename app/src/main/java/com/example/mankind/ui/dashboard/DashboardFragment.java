package com.example.mankind.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mankind.CheckAdapter;
import com.example.mankind.Entity.Tasks;
import com.example.mankind.MyApplication;
import com.example.mankind.NoCheckAdapter;
import com.example.mankind.NoCheckRecyclerView_Config;
import com.example.mankind.R;
import com.example.mankind.RecyclerView_Config;
import com.example.mankind.node_progress.NodeProgressView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import me.zhouzhuo.zzhorizontalprogressbar.ZzHorizontalProgressBar;


/**
 * The type Dashboard fragment.
 */
public class DashboardFragment extends Fragment implements CheckAdapter.CheckItemListener, NoCheckAdapter.CheckTaskListener {

    //goal
    private final int num = 3;
    //container for display ongoing tasks
    private RecyclerView recyclerView;
    //container for displaying completed tasks
    private RecyclerView checkedView;
    //violence type
    private String type;
    // adapter for ongoing tasks
    private CheckAdapter mCheckAdapter;
    //totoal tasks
    private List<Tasks> tasks = new ArrayList<>();
    //temp list
    private List<Tasks> newCheckedList = new ArrayList<>();
    //completed task list
    private List<Tasks> checkedList = new ArrayList<>();
    //ongoing task list
    private List<Tasks> displayList = new ArrayList<>();
    //remained task list
    private List<Tasks> remainedList = new ArrayList<>();
    //temp list
    private List<Tasks> newRestoreList = new ArrayList<>();
    // progress bar to be displayed while loading
    private ProgressBar pb;
    //textview to display congratulation
    private TextView tv;
    //text view to display progress
    private TextView tv_progress;
    //progress bar to display current progress
    private NodeProgressView nodeProgressView;
    //textview to display stage
    private TextView stageView;
    //current stage
    private int stage;
    //adapter for completed tasks
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
        stageView = root.findViewById(R.id.stage);
        pb = root.findViewById(R.id.progressBar);
        recyclerView = root.findViewById(R.id.recycler_view);
        checkedView = root.findViewById(R.id.recycler_view_checked);
        tv = root.findViewById(R.id.congratulation);
        initSwitch(root);
        initList(root);
        initCheckedTasks();
        initButton(root);
        infoDisplay(root);
        return root;
    }

    private void initStage() {
       stage = (4*checkedList.size())/(checkedList.size()+displayList.size()+remainedList.size()) + 1;
    }

    //init progress bar
    private void initProgressBar(View root) {
        nodeProgressView = root.findViewById(R.id.pb);
        nodeProgressView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tv_progress = root.findViewById(R.id.tv_progress);
        setStage();
        simulateProgress();
    }

    private void setStage() {
        int num = checkedList.size()+displayList.size()+remainedList.size();
        int currentStage;
        if(num == 0)
            currentStage = 0;
        else{
            currentStage = (4*checkedList.size())/(checkedList.size()+displayList.size()+remainedList.size()) + 1;
        }
        if(currentStage > stage){
            initText();
            stage = currentStage;
        }
        else{
            tv.setVisibility(View.GONE);
            stage = currentStage;
        }

        stageView.setText("You are at stage " + stage);
    }

    //update progree bar
    private void simulateProgress() {
        int num = checkedList.size()+displayList.size()+remainedList.size();
        if(num == 0 || stage < 1)
            nodeProgressView.setCurentNode(0);
        else{
            nodeProgressView.setCurentNode(stage-1);
        }
        nodeProgressView.reDraw();
        if (num == 0)
            tv_progress.setText("0");
        else
            tv_progress.setText(checkedList.size() + "/" + num);
    }

    //display text
    private void initText() {
        tv.setText("Congratulation! You have completed stage " + stage + ". Please re-attempt the questions.");
        tv.setVisibility(View.VISIBLE);
    }

    //init tasks
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
                initStage();
                initProgressBar(root);
            }
        }
    }

    //read tasks locally
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

    //init completed tasks
    private void initCheckedTasks() {
        noCheckAdapter = new NoCheckAdapter(getActivity(), checkedList, DashboardFragment.this);
        new NoCheckRecyclerView_Config().setConfig(checkedView, getActivity(),noCheckAdapter);
    }

    //init undo button
    private void initUndoButton(View root) {
        Button undo = root.findViewById(R.id.undo);
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
                sortList();
                setStage();
                simulateProgress();
            }
        });
    }

    private void sortList() {
        Collections.sort(displayList, new Comparator<Tasks>() {
            @Override
            public int compare(Tasks o1, Tasks o2) {
                return o1.getId()-o2.getId();
            }
        });
        Collections.sort(checkedList, new Comparator<Tasks>() {
            @Override
            public int compare(Tasks o1, Tasks o2) {
                return o1.getId()-o2.getId();
            }
        });
    }

    //init confirm button
    private void initButton(View root) {
        initUndoButton(root);
        Button button = root.findViewById(R.id.submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayList.removeAll(newCheckedList);
                int max = findMaxStage(newCheckedList);
                for(Tasks t : displayList){
                    if(t.getStage()<max){
                        Toast.makeText(getActivity(), "Please complete all the tasks before this one", Toast.LENGTH_SHORT).show();
                        displayList.addAll(newCheckedList);
                        newCheckedList.clear();
                        sortList();
                        mCheckAdapter.notifyDataSetChanged();
                        mCheckAdapter.initCheck(false);
                        return;
                    }
                }
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
                sortList();
                setStage();
                simulateProgress();
            }
        });
    }

    //get max stage
    private int findMaxStage(List<Tasks> newCheckedList) {
        if(newCheckedList.isEmpty())
            return 0;
        int max = newCheckedList.get(0).getStage();
        for (Tasks t : newCheckedList){
            if(t.getStage()>max)
                max = t.getStage();
        }
        return max;

    }

    //write current state
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

    //init switch
    private void initSwitch(final View root) {
        Switch aSwitch = root.findViewById(R.id.switch1);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    root.findViewById(R.id.checked_task).setVisibility(View.VISIBLE);
                    checkedView.setVisibility(View.VISIBLE);
                }

                else{
                    root.findViewById(R.id.checked_task).setVisibility(View.INVISIBLE);
                    checkedView.setVisibility(View.INVISIBLE);
                }

            }
        });
    }

    //init tasks
    private void initTasks(final View root){
        initType();
//        displayList.add(new Tasks(2,"2","2"));
//        displayList.add(new Tasks(3,"3","3"));
//        displayList.add(new Tasks(4,"4","4"));
//        remainedList.add(new Tasks(5,"5","5"));
//        remainedList.add(new Tasks(6,"6","6"));
//        remainedList.add(new Tasks(7,"7","7"));
//        remainedList.add(new Tasks(8,"8","8"));
//        remainedList.add(new Tasks(9,"9","9"));
//        remainedList.add(new Tasks(10,"10","10"));
//        checkedList.add(new Tasks(1,"1","1"));
        checkedList = new ArrayList<>();
        newCheckedList = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
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
                        int i = 0;
                        for(DocumentSnapshot doc : task.getResult()){
                            Tasks atask = new Tasks(i, doc.getString("des"), type);
                            atask.setStage();
                            tasks.add(atask);
                            i++;
                        }
                        if(tasks.size() > num){
                            displayList = new ArrayList<>(tasks.subList(0,num));
                            remainedList = new ArrayList<>(tasks.subList(num, tasks.size()));
                        }
                        mCheckAdapter = new CheckAdapter(getActivity(), displayList, DashboardFragment.this);
                        new RecyclerView_Config().setConfig(recyclerView, getActivity(),mCheckAdapter);
                        initStage();
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

    //init type
    private void initType() {
        type = ((MyApplication)(getActivity().getApplication())).getType();

    }


    //set tasks
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

    //init info icon
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