package com.aldemoralinator.myapplication.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.aldemoralinator.myapplication.R;
import com.aldemoralinator.myapplication.data.Todo;
import com.aldemoralinator.myapplication.data.TodoApi;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TodoActivity extends AppCompatActivity {

    private final int currentUserId = 1;

    private ListView listView;

    private TextInputEditText textInputEditText;

    private Button button;

    private TodoAdapter todoAdapter;

    private List<Todo> todos = new ArrayList<>();

    private TodoApi todoApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        todoApi = retrofit.create(TodoApi.class);

        getTodos();

        textInputEditText = (TextInputEditText) findViewById(R.id.textinput_new_todo);
        button = (Button) findViewById(R.id.btn_add_todo);

        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                String newTitle = textInputEditText.getText().toString();

                Todo newTodo = new Todo(currentUserId, newTitle, false);

                createTodo(newTodo);
            }
        });
    }

    private void getTodos() {

        final TodoActivity todoActivity = this;

        Call<List<Todo>> call = todoApi.getTodos(currentUserId, 10);

        call.enqueue(new Callback<List<Todo>>() {
            @Override
            public void onResponse(Call<List<Todo>> call, Response<List<Todo>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(TodoActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                todos = response.body();
                todoAdapter = new TodoAdapter(TodoActivity.this, todos, todoActivity);

                listView = (ListView) findViewById(R.id.listview_todos);
                listView.setAdapter(todoAdapter);
            }

            @Override
            public void onFailure(Call<List<Todo>> call, Throwable t) {
                Toast.makeText(TodoActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createTodo(Todo newTodo) {
        Call<Todo> call = todoApi.addTodo(newTodo);

        call.enqueue(new Callback<Todo>() {
            @Override
            public void onResponse(Call<Todo> call, Response<Todo> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(TodoActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                todoAdapter.add(response.body());
                Toast.makeText(TodoActivity.this, "new todo created!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Todo> call, Throwable t) {
                Toast.makeText(TodoActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void updateTodo(Todo todo, int position, final Button updateBtn) {

        final Todo currentTodo = todo;
        final int currentPosition = position;

        Call<Todo> call = todoApi.updateTodo(todo.getId(), todo);

        call.enqueue(new Callback<Todo>() {
            @Override
            public void onResponse(Call<Todo> call, Response<Todo> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(TodoActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                todoAdapter.remove(currentTodo);
                todoAdapter.insert(response.body(), currentPosition);
                updateBtn.setText("done");

                Toast.makeText(TodoActivity.this, "todo completed!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Todo> call, Throwable t) {
                Toast.makeText(TodoActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void deleteTodo(Todo todo) {

        final Todo currentTodo = todo;

        Call<Void> call = todoApi.deleteTodo(todo.getId());

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(TodoActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                todoAdapter.remove(currentTodo);

                Toast.makeText(TodoActivity.this, "Todo deleted!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(TodoActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



}