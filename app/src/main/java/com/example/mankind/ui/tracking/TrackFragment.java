package com.example.mankind.ui.tracking;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mankind.Question1Activity;
import com.example.mankind.Question2_1Activity;
import com.example.mankind.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TrackFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tracker,container,false);
        initButton(root);
        return root;
    }

    private void initButton(View root) {
        Button redo1 = root.findViewById(R.id.redo1);
        redo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Question1Activity.class);
                intent.putExtra("flag", 1);
                startActivity(intent);
            }
        });
        Button redo2 = root.findViewById(R.id.redo2);
        redo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Question2_1Activity.class);
                intent.putExtra("flag", 1);
                startActivity(intent);
            }
        });
    }
}
