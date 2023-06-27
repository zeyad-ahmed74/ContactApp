package com.example.contacts.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "contacts")
public class Contact implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
   // @ColumnInfo(name = "first name") to change column name
   // @ColumnInfo(index = true) to search by name faster than normal
    private String name;
    private String phone;
    private String Image;
    private Emp emp;

    public Contact(String name, String phone, String image, Emp emp) {
        this.name = name;
        this.phone = phone;
        this.Image = image;
        this.emp = emp;
    }

    public Emp getEmp() {
        return emp;
    }

    public void setEmp(Emp emp) {
        this.emp = emp;
    }

    public Contact(String name, String phone, String Image) {
        this.name = name;
        this.phone = phone;
        this.Image=Image;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
