package com.aldemoralinator.myapplication.todo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aldemoralinator.myapplication.R;
import com.aldemoralinator.myapplication.data.Todo;
import com.aldemoralinator.myapplication.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class TodoAdapter extends ArrayAdapter<Todo> {

    private Context mContext;

    private List<Todo> todos;

    private TodoActivity todoActivity;

    public TodoAdapter(@NonNull Context context, List<Todo> todos, TodoActivity todoActivity) {
        super(context, 0, todos);
        this.mContext = context;
        this.todos = todos;
        this.todoActivity = todoActivity;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.todos_item, parent, false);

        final Todo currentTodo = todos.get(position);

        TextView id = (TextView) listItem.findViewById(R.id.todos_item_id);
        id.setText(Integer.toString(currentTodo.getId()));

        TextView title = (TextView) listItem.findViewById(R.id.todos_item_title);
        title.setText(currentTodo.getTitle());

        final Button updateBtn = (Button) listItem.findViewById(R.id.btn_todos_item_update);
        updateBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                currentTodo.setCompleted(true);
                todoActivity.updateTodo(currentTodo, position, updateBtn);
            }
        });

        Button deleteBtn = (Button) listItem.findViewById(R.id.btn_todos_item_delete);
        deleteBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                todoActivity.deleteTodo(currentTodo);
            }
        });

        if (currentTodo.isCompleted()) {
            updateBtn.setText("done");
        } else {
            updateBtn.setText("not done");
        }

        return listItem;
    }

}
