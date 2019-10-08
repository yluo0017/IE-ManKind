package com.example.mankind.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mankind.CheckAdapter;
import com.example.mankind.Entity.Tasks;
import com.example.mankind.MyApplication;
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

    //goal
    private final int num = 3;
    //container for display ongoing tasks
    private RecyclerView recyclerView;
    //violence type
    private String type;
    // adapter for ongoing tasks
    private CheckAdapter mCheckAdapter;
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

    private LinearLayout current;

    private RecyclerView stage1;
    private RecyclerView stage2;
    private RecyclerView stage3;
    private RecyclerView stage4;
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
        recyclerView = root.findViewById(R.id.recycler_view);
        tv = root.findViewById(R.id.congratulation);
        current = root.findViewById(R.id.current);
        initList(root);
        initButton(root);
        return root;
    }

    private void initView(View root) {
        stage1 = root.findViewById(R.id.stage1);
        stage2 = root.findViewById(R.id.stage2);
        stage3 = root.findViewById(R.id.stage3);
        stage4 = root.findViewById(R.id.stage4);
        List<Tasks> taskList = new ArrayList<>();
        List<Tasks> stage1List = new ArrayList<>();
        List<Tasks> stage2List = new ArrayList<>();
        List<Tasks> stage3List = new ArrayList<>();
        List<Tasks> stage4List = new ArrayList<>();
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
        CheckAdapter adapter1 = new CheckAdapter(getActivity(), stage1List, DashboardFragment.this);
        CheckAdapter adapter2 = new CheckAdapter(getActivity(), stage2List, DashboardFragment.this);
        CheckAdapter adapter3 = new CheckAdapter(getActivity(), stage3List, DashboardFragment.this);
        CheckAdapter adapter4 = new CheckAdapter(getActivity(), stage4List, DashboardFragment.this);
        new RecyclerView_Config().setConfig(stage1, getActivity(),adapter1);
        new RecyclerView_Config().setConfig(stage2, getActivity(),adapter2);
        new RecyclerView_Config().setConfig(stage3, getActivity(),adapter3);
        new RecyclerView_Config().setConfig(stage4, getActivity(),adapter4);

    }

    private void initStage() {
       stage = (4*checkedList.size())/(displayList.size()) + 1;
    }

    private void setCurrentView(View root){
        TextView stage5 = root.findViewById(R.id.stage5);
        current.setVisibility(View.VISIBLE);
        stage1.setVisibility(View.GONE);
        stage2.setVisibility(View.GONE);
        stage3.setVisibility(View.GONE);
        stage4.setVisibility(View.GONE);
        stage5.setVisibility(View.GONE);

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

            }

            @Override
            public void getProgressOnActionUp(SignSeekBar signSeekBar, int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(SignSeekBar signSeekBar, int progress, float progressFloat, boolean fromUser) {
                TextView stage5 = root.findViewById(R.id.stage5);
                if(progress == stage){
                    current.setVisibility(View.VISIBLE);
                    stage1.setVisibility(View.GONE);
                    stage2.setVisibility(View.GONE);
                    stage3.setVisibility(View.GONE);
                    stage4.setVisibility(View.GONE);
                    stage5.setVisibility(View.GONE);
                }
                else{
                    if(progress == 1){
                        current.setVisibility(View.GONE);
                        stage1.setVisibility(View.VISIBLE);
                        stage2.setVisibility(View.GONE);
                        stage3.setVisibility(View.GONE);
                        stage4.setVisibility(View.GONE);
                        stage5.setVisibility(View.GONE);
                    }
                    else if(progress == 2){
                        current.setVisibility(View.GONE);
                        stage2.setVisibility(View.VISIBLE);
                        stage1.setVisibility(View.GONE);
                        stage3.setVisibility(View.GONE);
                        stage4.setVisibility(View.GONE);
                        stage5.setVisibility(View.GONE);
                    }
                    else if(progress == 3){
                        current.setVisibility(View.GONE);
                        stage3.setVisibility(View.VISIBLE);
                        stage2.setVisibility(View.GONE);
                        stage1.setVisibility(View.GONE);
                        stage4.setVisibility(View.GONE);
                        stage5.setVisibility(View.GONE);
                    }
                    else if(progress == 4){
                        current.setVisibility(View.GONE);
                        stage4.setVisibility(View.VISIBLE);
                        stage2.setVisibility(View.GONE);
                        stage3.setVisibility(View.GONE);
                        stage1.setVisibility(View.GONE);
                        stage5.setVisibility(View.GONE);
                    }
                    else{
                        current.setVisibility(View.GONE);
                        stage4.setVisibility(View.GONE);
                        stage2.setVisibility(View.GONE);
                        stage3.setVisibility(View.GONE);
                        stage1.setVisibility(View.GONE);
                        stage5.setVisibility(View.VISIBLE);
                    }

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
            initText();
            stage = currentStage;
        }
        else{
            tv.setVisibility(View.GONE);
            stage = currentStage;
        }
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
//        if (num == 0)
//            tv_progress.setText("0");
//        else
//            tv_progress.setText(checkedList.size() + "/" + num);
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
                mCheckAdapter = new CheckAdapter(getActivity(), displayList, DashboardFragment.this);
                new RecyclerView_Config().setConfig(recyclerView, getActivity(),mCheckAdapter);
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
        Button button = root.findViewById(R.id.submit);
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
                        mCheckAdapter.notifyDataSetChanged();
                        mCheckAdapter.initCheck();
                        return;
                    }
                }
                set = new HashSet<>(newCheckedList);
                checkedList.addAll(new ArrayList<Tasks>(set));
                sortList();
                newCheckedList.clear();
                mCheckAdapter.initCheck();
                mCheckAdapter.notifyDataSetChanged();
                setStage();
                simulateProgress();
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
//        for(Tasks t: displayList){
//            t.setStage();
//        }
//        for(Tasks t: checkedList){
//            t.setStage();
//        }
//        for(Tasks t: remainedList){
//            t.setStage();
//        }
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
                        mCheckAdapter = new CheckAdapter(getActivity(), displayList, DashboardFragment.this);
                        new RecyclerView_Config().setConfig(recyclerView, getActivity(),mCheckAdapter);
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
        mCheckAdapter.notifyDataSetChanged();
    }
}