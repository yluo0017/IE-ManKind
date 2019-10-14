package com.example.mankind.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mankind.CheckAdapter;
import com.example.mankind.Entity.Tasks;
import com.example.mankind.MyApplication;
import com.example.mankind.NoCheckAdapter;
import com.example.mankind.Nocheck_Config;
import com.example.mankind.R;
import com.example.mankind.RecyclerView_Config;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;
import com.zhouyou.view.seekbar.SignSeekBar;

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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


/**
 * The type Dashboard fragment.
 */
public class DashboardFragment extends Fragment implements CheckAdapter.CheckItemListener {

    //violence type
    private String type;
    //totoal tasks
    private List<Tasks> tasks = new ArrayList<>();
    //temp list
    private List<Tasks> newCheckedList = new ArrayList<>();
    //temp list
    private List<Tasks> newRestoreList = new ArrayList<>();
    //completed task list
    private List<Tasks> checkedList = new ArrayList<>();
    //ongoing task list
    private List<Tasks> displayList = new ArrayList<>();
    //remained task list
    private List<Tasks> ongoingList = new ArrayList<>();
    // progress bar to be displayed while loading
    private ProgressBar pb;
    //textview to display congratulation
    private TextView tv;
    //progress bar to display current progress
    private SignSeekBar signSeekBar;
    //current stage
    private int stage;
    //recycler view for stage 1 tasks
    private RecyclerView stage1;
    //recycler view for stage 2 tasks
    private RecyclerView stage2;
    //recycler view for stage 3 tasks
    private RecyclerView stage3;
    //recycler view for stage 4 tasks
    private RecyclerView stage4;
    //recycler view for stage 5 tasks
    private RecyclerView stage5;
    //confirm button
    private Button button;
    //task list
    private List<Tasks> taskList;
    //stage 1 list
    private List<Tasks> stage1List;
    //stage 2 list
    private List<Tasks> stage2List;
    //stage 3 list
    private List<Tasks> stage3List;
    //stage 4 list
    private List<Tasks> stage4List;
    //adapter for stage 1 list
    private RecyclerView.Adapter adapter1;
    //adapter for stage 2 list
    private RecyclerView.Adapter adapter2;
    //adapter for stage 3 list
    private RecyclerView.Adapter adapter3;
    //adapter for stage 4 list
    private RecyclerView.Adapter adapter4;
    //adapter for stage 5 list
    private RecyclerView.Adapter adapter5;
    private boolean showText = false;



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
        pb = root.findViewById(R.id.progressBar);
        tv = root.findViewById(R.id.congratulation);
        initList(root);
        initButton(root);
        return root;
    }

    private void initView(View root) {
        stage1 = root.findViewById(R.id.stage1);
        stage2 = root.findViewById(R.id.stage2);
        stage3 = root.findViewById(R.id.stage3);
        stage4 = root.findViewById(R.id.stage4);
        stage5 = root.findViewById(R.id.stage5_view);
//        displayList.add(new Tasks(2,"2","2"));
//        displayList.add(new Tasks(3,"3","3"));
//        displayList.add(new Tasks(4,"4","4"));
//        displayList.add(new Tasks(5,"5","5"));
//        displayList.add(new Tasks(6,"6","6"));
//        displayList.add(new Tasks(7,"7","7"));
//        displayList.add(new Tasks(8,"8","8"));
//        displayList.add(new Tasks(9,"9","9"));
//        displayList.add(new Tasks(10,"10","10"));
//        checkedList.add(new Tasks(1,"1","1"));
//        for(Tasks t: displayList){
//            t.setStage();
//        }
//        for(Tasks t: checkedList){
//            t.setStage();
//        }
        taskList = new ArrayList<>();
        stage1List = new ArrayList<>();
        stage2List = new ArrayList<>();
        stage3List = new ArrayList<>();
        stage4List = new ArrayList<>();
        taskList.addAll(displayList);
        for(Tasks t: taskList){
            if(t.getStage() == 1)
                stage1List.add(t);
            if(t.getStage() == 2)
                stage2List.add(t);
            if(t.getStage() == 3)
                stage3List.add(t);
            if (t.getStage() == 4)
                stage4List.add(t);
        }
        adapter1 = new CheckAdapter(getActivity(), stage1List, DashboardFragment.this);
        new RecyclerView_Config().setConfig(stage1, getActivity(),adapter1);
        if(stage >= 2){
            adapter2 = new CheckAdapter(getActivity(), stage2List, DashboardFragment.this);
            new RecyclerView_Config().setConfig(stage2, getActivity(),adapter2);
        }
        else{
            adapter2 = new NoCheckAdapter(getActivity(),stage2List);
            new Nocheck_Config().setConfig(stage2, getActivity(), adapter2);
        }
        if(stage >= 3){
            adapter3 = new CheckAdapter(getActivity(), stage3List, DashboardFragment.this);
            new RecyclerView_Config().setConfig(stage3, getActivity(),adapter3);
        }
        else{
            adapter3 = new NoCheckAdapter(getActivity(),stage3List);
            new Nocheck_Config().setConfig(stage3, getActivity(), adapter3);
        }
        if(stage >= 4){
           adapter4 = new CheckAdapter(getActivity(), stage4List, DashboardFragment.this);
            new RecyclerView_Config().setConfig(stage4, getActivity(),adapter4);
        }
        else{
            adapter4 = new NoCheckAdapter(getActivity(),stage4List);
            new Nocheck_Config().setConfig(stage4, getActivity(), adapter4);
        }
        adapter5 = new CheckAdapter(getActivity(), taskList, DashboardFragment.this);
        new RecyclerView_Config().setConfig(stage5, getActivity(),adapter5);

    }


    private void initStage() {
        if(displayList.size() == 0)
            stage = (4*checkedList.size())/10 + 1;
        else
            stage = (4*checkedList.size())/(displayList.size()) + 1;
    }

    private void setCurrentView(View root){
        TextView stage5Text = root.findViewById(R.id.stage5);
        if(stage == 1){
            stage1.setVisibility(View.VISIBLE);
            stage2.setVisibility(View.GONE);
            stage3.setVisibility(View.GONE);
            stage4.setVisibility(View.GONE);
            stage5.setVisibility(View.GONE);
            stage5Text.setVisibility(View.GONE);
        }
        if(stage == 2){
            stage1.setVisibility(View.GONE);
            stage2.setVisibility(View.VISIBLE);
            stage3.setVisibility(View.GONE);
            stage4.setVisibility(View.GONE);
            stage5.setVisibility(View.GONE);
            stage5Text.setVisibility(View.GONE);
        }
        if(stage == 3){
            stage1.setVisibility(View.GONE);
            stage2.setVisibility(View.GONE);
            stage3.setVisibility(View.VISIBLE);
            stage4.setVisibility(View.GONE);
            stage5.setVisibility(View.GONE);
            stage5Text.setVisibility(View.GONE);
        }
        if(stage == 4){
            stage1.setVisibility(View.GONE);
            stage2.setVisibility(View.GONE);
            stage3.setVisibility(View.GONE);
            stage4.setVisibility(View.VISIBLE);
            stage5.setVisibility(View.GONE);
            stage5Text.setVisibility(View.GONE);
        }
        if(stage == 5){
            stage1.setVisibility(View.GONE);
            stage2.setVisibility(View.GONE);
            stage3.setVisibility(View.GONE);
            stage4.setVisibility(View.GONE);
            stage5.setVisibility(View.VISIBLE);
            stage5Text.setVisibility(View.VISIBLE);

        }


    }

    //init progress bar
    private void initProgressBar(final View root) {
        signSeekBar = root.findViewById(R.id.pb);
        setStage();
        signSeekBar.getConfigBuilder()
                .min(1)
                .max(5)
                .sectionCount(4)
                .thumbColor(ContextCompat.getColor(getContext(), R.color.orange))
                .setUnit("stage")
                .reverse()
                .sectionTextColor(getResources().getColor(R.color.colorPrimary))
                .sectionTextPosition(SignSeekBar.TextPosition.BELOW_SECTION_MARK)
                .sectionTextSize(15)
                .build();
        simulateProgress();
        setCurrentView(root);
        signSeekBar.setOnProgressChangedListener(new SignSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(SignSeekBar signSeekBar, int progress, float progressFloat, boolean fromUser) {
                if(progressFloat >= 4.5 && stage != 5){
                    pb.setProgress(stage);
                }
                Toast.makeText(getActivity(),"After complete all the tasks this stage is visible", Toast.LENGTH_SHORT);
            }

            @Override
            public void getProgressOnActionUp(SignSeekBar signSeekBar, int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(SignSeekBar signSeekBar, int progress, float progressFloat, boolean fromUser) {
                tv.setVisibility(View.GONE);
                if(progress == 5 && stage != 5){
                    pb.setProgress(stage);
                    Toast.makeText(getActivity(),"After complete all the tasks this stage is visible", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(stage >= progress)
                    button.setVisibility(View.VISIBLE);
                else
                    button.setVisibility(View.GONE);
                TextView stage5 = root.findViewById(R.id.stage5);
                if(progress == 1){
                        stage1.setVisibility(View.VISIBLE);
                        stage2.setVisibility(View.GONE);
                        stage3.setVisibility(View.GONE);
                        stage4.setVisibility(View.GONE);
                        stage5.setVisibility(View.GONE);
                    DashboardFragment.this.stage5.setVisibility(View.GONE);
                }
                else if(progress == 2){
                        stage2.setVisibility(View.VISIBLE);
                        stage1.setVisibility(View.GONE);
                        stage3.setVisibility(View.GONE);
                        stage4.setVisibility(View.GONE);
                        stage5.setVisibility(View.GONE);
                    DashboardFragment.this.stage5.setVisibility(View.GONE);
                }
                else if(progress == 3){
                        stage3.setVisibility(View.VISIBLE);
                        stage2.setVisibility(View.GONE);
                        stage1.setVisibility(View.GONE);
                        stage4.setVisibility(View.GONE);
                        stage5.setVisibility(View.GONE);
                    DashboardFragment.this.stage5.setVisibility(View.GONE);
                }
                else if(progress == 4){
                        stage4.setVisibility(View.VISIBLE);
                        stage2.setVisibility(View.GONE);
                        stage3.setVisibility(View.GONE);
                        stage1.setVisibility(View.GONE);
                        stage5.setVisibility(View.GONE);
                    DashboardFragment.this.stage5.setVisibility(View.GONE);
                }
                else{
                    stage4.setVisibility(View.GONE);
                    stage2.setVisibility(View.GONE);
                    stage3.setVisibility(View.GONE);
                    stage1.setVisibility(View.GONE);
                    stage5.setVisibility(View.VISIBLE);
                    DashboardFragment.this.stage5.setVisibility(View.VISIBLE);
                }
                if(stage >= 2){
                    CheckAdapter adapter2 = new CheckAdapter(getActivity(), stage2List, DashboardFragment.this);
                    new RecyclerView_Config().setConfig(stage2, getActivity(),adapter2);
                }
                else{
                    NoCheckAdapter adapter2 = new NoCheckAdapter(getActivity(),stage2List);
                    new Nocheck_Config().setConfig(stage2, getActivity(), adapter2);
                }
                if(stage >= 3){
                    CheckAdapter adapter3 = new CheckAdapter(getActivity(), stage3List, DashboardFragment.this);
                    new RecyclerView_Config().setConfig(stage3, getActivity(),adapter3);
                }
                else{
                    NoCheckAdapter adapter3 = new NoCheckAdapter(getActivity(),stage3List);
                    new Nocheck_Config().setConfig(stage3, getActivity(), adapter3);
                }
                if(stage >= 4){
                    CheckAdapter adapter4 = new CheckAdapter(getActivity(), stage4List, DashboardFragment.this);
                    new RecyclerView_Config().setConfig(stage4, getActivity(),adapter4);
                }
                else{
                    NoCheckAdapter adapter4 = new NoCheckAdapter(getActivity(),stage4List);
                    new Nocheck_Config().setConfig(stage4, getActivity(), adapter4);
                }



            }
        });

    }

    private void setStage() {
        int currentStage;
        if(ongoingList.isEmpty())
            currentStage = 5;
        else
            currentStage = ongoingList.get(0).getStage();
        if(currentStage > stage){
            showText = true;
            initText();
            stage = currentStage;
        }
        else{
            showText = false;
            tv.setVisibility(View.GONE);
            stage = currentStage;
        }
        if(currentStage == 5)
            tv.setVisibility(View.GONE);
    }

    //update progree bar
    private void simulateProgress() {
        int num = displayList.size();
        if(num == 0 || stage < 1){
            signSeekBar.setProgress(0);
        }
        else{
            signSeekBar.setProgress(stage);
        }
        signSeekBar.invalidate();
    }

    //display text
    private void initText() {
        tv.setText("Congratulation! You have completed stage " + stage + ". Please re-attempt the questions.");
        tv.setVisibility(View.VISIBLE);
    }

    //init tasks
    private void initList(View root) {
        readFile();
        if(displayList.size() == 0)
            initTasks(root);
        else{
            initType();
            String temptype = "";
            if(displayList.size() != 0)
                temptype = displayList.get(0).getType();
            if(!temptype.equals(type))
                initTasks(root);
            else{
                pb.setVisibility(View.GONE);
                initStage();
                initView(root);
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
                ongoingList = (List<Tasks>) objectInputStream.readObject();
                if(ongoingList == null)
                    ongoingList = new ArrayList<>();
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

    //sort display list, checked list and ongoing list
    private void sortList() {
        for(int i = 0; i<ongoingList.size()-1; i++){
                for(int j = i+1; j<ongoingList.size(); j++){
                    if(ongoingList.get(i).getId() > ongoingList.get(j).getId()){
                        Tasks t = ongoingList.get(i);
                        ongoingList.set(i,ongoingList.get(j));
                        ongoingList.set(j,t);
                    }
                }
        }
        for(int i = 0; i<checkedList.size()-1; i++){
            for(int j = i+1; j<checkedList.size(); j++){
                if(checkedList.get(i).getId() > checkedList.get(j).getId()){
                    Tasks t = checkedList.get(i);
                    checkedList.set(i,checkedList.get(j));
                    checkedList.set(j,t);
                }
            }
        }
        displayList.clear();
        displayList.addAll(ongoingList);
        displayList.addAll(checkedList);
    }

    //init confirm button
    private void initButton(View root) {
        button = root.findViewById(R.id.submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedList.removeAll(newRestoreList);
                Set<Tasks> set = new HashSet<>(newRestoreList);
                for (Tasks t : set) {
                    ongoingList.add(0, t);
                }
                sortList();
                newRestoreList.clear();
                ongoingList.removeAll(newCheckedList);
                int max = findMaxStage(newCheckedList);
                for(Tasks t : ongoingList){
                    if(t.getStage()<max){
                        Toast.makeText(getActivity(), "Please complete all the tasks before this one", Toast.LENGTH_SHORT).show();
                        ongoingList.addAll(newCheckedList);
                        newCheckedList.clear();
                        sortList();
                        return;
                    }
                }
                set = new HashSet<>(newCheckedList);
                checkedList.addAll(new ArrayList<Tasks>(set));
                sortList();
                newCheckedList.clear();
                setStage();
                simulateProgress();
                if(showText)
                    tv.setVisibility(View.VISIBLE);
                writeFile();
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
            objectOutputStream.writeObject(ongoingList);
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
                            atask.setStage();
                            tasks.add(atask);
                            i++;
                        }
                        displayList = new ArrayList<>(tasks);
                        ongoingList = new ArrayList<>(displayList);
                        initStage();
                        initView(root);
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



    //init info icon
//    private void infoDisplay(View root) {
//        Button info = root.findViewById(R.id.info_icon);
//        info.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
//                dialogBuilder.setMessage("In curriculum a series of tasks are designed for you." + "\n" +
//                            "\n" + "You can track and manage your tasks by clicking buttons and checkboxes.");
//                AlertDialog alertDialog = dialogBuilder.create();
//                alertDialog.show();
//            }
//        });
//    }

    @Override
    public void itemChecked(Tasks task, boolean isChecked) {
        if(isChecked){
            task.setChecked(true);
            newCheckedList.add(task);
        }
        else{
            task.setChecked(false);
            if(newCheckedList.contains(task))
                newCheckedList.remove(task);
            if(checkedList.contains(task)){
                newRestoreList.add(task);
            }

        }
    }
}