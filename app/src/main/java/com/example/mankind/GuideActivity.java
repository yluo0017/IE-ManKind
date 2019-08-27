package com.example.mankind;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.mankind.MyComponent.ImageViewPlus;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class GuideActivity extends Activity implements
        ViewPager.OnPageChangeListener{
    private ViewPager vP;
    private int []imageArray;

    private ViewGroup viewGroup;
    private List<View> viewsList;

    private ImageViewPlus iv_point;
    private ImageViewPlus [] iv_PointArray;

    private Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slideguide);

        button = (Button)findViewById(R.id.startButton);
        button.getBackground().setAlpha(100);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuideActivity.this,Question1Activity.class));
                finish();
            }
        });
        initViewPager();
        initPoint();
    }

    private void initViewPager(){
        vP = (ViewPager)findViewById(R.id.viewpager_launcher);
        imageArray = new int[]{R.drawable.question,R.drawable.result ,R.drawable.customized};
        viewsList = new ArrayList<>();
        LinearLayout.LayoutParams params= new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        int len = imageArray.length;
        for (int i = 0;i<len;i++){
            ImageView IV = new ImageView(this);
            IV.setLayoutParams(params);
            IV.setBackgroundResource(imageArray[i]);
            viewsList.add(IV);
        }
        vP.setAdapter(new ViewPagerAdapter(viewsList));
        vP.addOnPageChangeListener(this);
    }

    private  void  initPoint() {
        viewGroup = (ViewGroup)findViewById(R.id.dot);
        iv_PointArray = new ImageViewPlus[viewsList.size()];
        int size = viewsList.size();
        for(int i = 0; i <size;i++){
            iv_point = new ImageViewPlus(this);
            iv_point.setScaleX(0.5f);
            iv_point.setScaleY(0.5f);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(60,60);
            lp.leftMargin = 20;
            lp.rightMargin = 20;
            iv_point.setLayoutParams(lp);


            iv_PointArray[i] = iv_point;
            if(i == 0){
                iv_point.setBackgroundResource(R.drawable.dot);
            }else{
                iv_point.setBackgroundResource(R.drawable.black_dot);
            }
            viewGroup.addView(iv_PointArray[i]);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int lenth = imageArray.length;
        for (int i = 0; i < lenth; i++) {
            iv_PointArray[i].setBackgroundResource(R.drawable.dot);
            if (position != i) {
                iv_PointArray[i].setBackgroundResource(R.drawable.black_dot);
            }
            if (position == lenth - 1) {
                button.setVisibility(View.VISIBLE);
            } else {
                button.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }}