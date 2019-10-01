package com.example.mankind.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
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

import com.example.mankind.CheckAdapter;
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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
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

    //spinner for setting goals
    private Spinner spinner;
    //goal
    private int num;
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
    private ZzHorizontalProgressBar progressBar;
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

    //init progress bar
    private void initProgressBar(View root) {
        progressBar = root.findViewById(R.id.pb);
        tv_progress = root.findViewById(R.id.tv_progress);
        simulateProgress();
    }

    //update progree bar
    private void simulateProgress() {
        int num = checkedList.size()+displayList.size()+remainedList.size();
        if(num == 0)
            progressBar.setProgress(0);
        else
            progressBar.setProgress((100*checkedList.size())/(checkedList.size()+displayList.size()+remainedList.size()));

        if (num == 0)
            tv_progress.setText("0");
        else
            tv_progress.setText(checkedList.size() + "/" + num);
    }

    //display text
    private void initText() {
        if(checkedList.size() == 10)
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
            }
        });
    }

    private void sortList() {
    }

    //init confirm button
    private void initButton(View root) {
        initUndoButton(root);
        Button button = root.findViewById(R.id.submit);
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
                sortList();
            }
        });
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
                            tasks.add(atask);
                            i++;
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

    //init type
    private void initType() {
        type = ((MyApplication)(getActivity().getApplication())).getType();

    }

    //init spinner
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