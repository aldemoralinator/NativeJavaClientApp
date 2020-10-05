package com.aldemoralinator.myapplication.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.aldemoralinator.myapplication.R;
import com.aldemoralinator.myapplication.todo.TodoActivity;

public class MainActivity extends AppCompatActivity {

    Button startTodoActivityBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startTodoActivityBtn = (Button) findViewById(R.id.btn_start_todo_activity);

        startListeners();

    }

    private void startListeners() {


        startTodoActivityBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity( new Intent(MainActivity.this, TodoActivity.class) );
            }
        });

    }
}