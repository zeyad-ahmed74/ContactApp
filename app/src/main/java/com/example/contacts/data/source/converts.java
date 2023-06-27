package com.example.contacts.data.source;

import androidx.room.TypeConverter;

import com.example.contacts.data.model.Emp;
import com.google.gson.Gson;

public class converts {

    @TypeConverter
    public String toGson(Emp emp){
        Gson gson = new Gson();
        return gson.toJson(emp);
    }

    @TypeConverter
    public Emp fromGson(String str){
        Gson gson = new Gson();
        return gson.fromJson(str,Emp.class);
    }
}
