package com.aldemoralinator.myapplication.data;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface TodoApi {

    @GET("todos")
    Call<List<Todo>> getTodos(
            @Query("userId") Integer userId,
            @Query("_limit") Integer limit);

    @POST("todos")
    Call<Todo> addTodo(@Body Todo todo);

    @PATCH("todos/{id}")
    Call<Todo> updateTodo(@Path("id") int id, @Body Todo todo);

    @DELETE("todos/{id}")
    Call<Void> deleteTodo(@Path("id") int id);

}
