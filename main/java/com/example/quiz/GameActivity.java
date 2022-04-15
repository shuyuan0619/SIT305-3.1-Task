package com.example.quiz;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

    public int index = 1;
    public String name = "";
    public TextView text_welcome;
    public TextView text_process;
    public TextView text_question_A;
    public TextView text_question_B;
    public TextView text_question_C;
    public TextView text_question_title;
    public SeekBar bar_process;
    public Button btn_ans1;
    public Button btn_ans2;
    public Button btn_ans3;
    public Button btn_submit;
    public int[] correct_answer = new int[]{2,1,3,2,2};
    public String[] questions_title = new String[]{"For sending a broadcast, an Intent is launched in the following way",
            "The database that is often used in development is",
            "what one is incorrect about playing video in Android",
            "The following choices about who receives broadcasts first in the wrong order are",
            "How to change an Activity interface element in a Service（B）"};
    public String[][] answers = new String[][]{{"Explicit startup","Implicit start","A, and B"},
            {"SQLite3","Oracle","Sql Server"},
            {"can use the SurfaceView component to play video","can use the VideoView component to broadcast video","The VideoView component can manipulate the position and size of the"},
            {"Broadcast in an orderly manner. Those with A higher priority receive first","Ordered broadcast, static and dynamic broadcast receivers with the same priority, static has precedence over dynamic","Ordered broadcast, dynamic broadcast receivers of the same priority, the first to register more than the last to register"},
            {"By passing the current Activity object to the Service object","Send a broadcast to the Activity","Change the Activity interface element using the Context object"}};

    public int choose = 0;
    public boolean flag = false;
    public int count = 0;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏图标和文字颜色为暗色
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        setContentView(R.layout.activity_game);
        SysApplication.getInstance().addActivity(this);
        text_welcome = findViewById(R.id.text_welcome);
        text_process = findViewById(R.id.text_process);
        text_question_A = findViewById(R.id.text_question_A);
        text_question_B = findViewById(R.id.text_question_B);
        text_question_C = findViewById(R.id.text_question_C);
        text_question_title = findViewById(R.id.text_question_title);
        bar_process = findViewById(R.id.bar_process);
        btn_ans1 = findViewById(R.id.btn_ans1);
        btn_ans2 = findViewById(R.id.btn_ans2);
        btn_ans3 = findViewById(R.id.btn_ans3);
        btn_submit = findViewById(R.id.btn_submit);

        name = getIntent().getStringExtra("name");
        text_welcome.setText("Welcome "+name+"!");
        bar_process.setMax(100);
        nextQuestion();

        btn_ans1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag){
                    return;
                }
                btn_ans1.setBackgroundResource(R.drawable.btn_select_shape);
                btn_ans2.setBackgroundResource(R.drawable.answerbtn_shape);
                btn_ans3.setBackgroundResource(R.drawable.answerbtn_shape);
                choose = 1;
            }
        });

        btn_ans2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag){
                    return;
                }
                btn_ans1.setBackgroundResource(R.drawable.answerbtn_shape);
                btn_ans2.setBackgroundResource(R.drawable.btn_select_shape);
                btn_ans3.setBackgroundResource(R.drawable.answerbtn_shape);
                choose = 2;
            }
        });

        btn_ans3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag){
                    return;
                }
                btn_ans1.setBackgroundResource(R.drawable.answerbtn_shape);
                btn_ans2.setBackgroundResource(R.drawable.answerbtn_shape);
                btn_ans3.setBackgroundResource(R.drawable.btn_select_shape);
                choose = 3;
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag){
                    flag = false;
                    btn_submit.setText("Submit");
                    index++;
                    if(index<=5){
                        btn_ans1.setBackgroundResource(R.drawable.answerbtn_shape);
                        btn_ans2.setBackgroundResource(R.drawable.answerbtn_shape);
                        btn_ans3.setBackgroundResource(R.drawable.answerbtn_shape);
                    }else{
                        Intent i = new Intent(GameActivity.this,ResultActivity.class);
                        i.putExtra("name",name);
                        i.putExtra("count",count);
                        startActivity(i);
                    }

                    nextQuestion();
                }else{
                    if(choose==0){
                        Toast.makeText(GameActivity.this,"please choose an answer",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    flag = true;
                    btn_submit.setText("Next");
                    refreshAfterChoose();
                }

            }
        });
    }

    public void nextQuestion() {
        if(index>5){
            return;
        }
        text_process.setText(index + "/5");
        bar_process.setProgress(index*20);
        text_question_title.setText(index+"、"+questions_title[index-1]);
        text_question_A.setText("A、"+answers[index-1][0]);
        text_question_B.setText("B、"+answers[index-1][1]);
        text_question_C.setText("C、"+answers[index-1][2]);
        choose = 0;


    }

    public void refreshAfterChoose(){
        if(choose== correct_answer[index-1]){

            count++;
            switch (choose){
                case 1:
                    btn_ans1.setBackgroundResource(R.drawable.btn_true_shape);
                    btn_ans2.setBackgroundResource(R.drawable.answerbtn_shape);
                    btn_ans3.setBackgroundResource(R.drawable.answerbtn_shape);
                    break;
                case 2:
                    btn_ans1.setBackgroundResource(R.drawable.answerbtn_shape);
                    btn_ans2.setBackgroundResource(R.drawable.btn_true_shape);
                    btn_ans3.setBackgroundResource(R.drawable.answerbtn_shape);
                    break;
                case 3:
                    btn_ans1.setBackgroundResource(R.drawable.answerbtn_shape);
                    btn_ans2.setBackgroundResource(R.drawable.answerbtn_shape);
                    btn_ans3.setBackgroundResource(R.drawable.btn_true_shape);
                    break;
                default:
                    break;
            }
        }else{
            btn_ans1.setBackgroundResource(R.drawable.answerbtn_shape);
            btn_ans2.setBackgroundResource(R.drawable.answerbtn_shape);
            btn_ans3.setBackgroundResource(R.drawable.answerbtn_shape);
            switch (choose){
                case 1:
                    btn_ans1.setBackgroundResource(R.drawable.btn_false_shape);
                    break;
                case 2:
                    btn_ans2.setBackgroundResource(R.drawable.btn_false_shape);
                    break;
                case 3:
                    btn_ans3.setBackgroundResource(R.drawable.btn_false_shape);
                    break;
                default:
                    break;
            }
            switch (correct_answer[index-1]){
                case 1:
                    btn_ans1.setBackgroundResource(R.drawable.btn_true_shape);
                    break;
                case 2:
                    btn_ans2.setBackgroundResource(R.drawable.btn_true_shape);
                    break;
                case 3:
                    btn_ans3.setBackgroundResource(R.drawable.btn_true_shape);
                    break;
                default:
                    break;
            }
        }
    }


}