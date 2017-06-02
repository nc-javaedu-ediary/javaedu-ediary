package com.ncjavaedu.ediary.model;

import com.google.gson.annotations.SerializedName;

public enum Role {
    @SerializedName("0")
    Admin(0),

    @SerializedName("1")
    Student(1),

    @SerializedName("2")
    Lecturer(2);

    private final int value;
    public int getValue() {
        return value;
    }

    private Role(int value) {
        this.value = value;
    }
}
