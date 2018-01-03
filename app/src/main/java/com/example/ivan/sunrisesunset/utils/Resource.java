package com.example.ivan.sunrisesunset.utils;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Resource<T> {

    @NonNull
    public final StatusType statusType;
    @Nullable
    public final T data;
    @Nullable public final String message;
    private Resource(@NonNull StatusType statusType, @Nullable T data, @Nullable String message) {
        this.statusType = statusType;
        this.data = data;
        this.message = message;
    }

    public static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(StatusType.SUCCESS, data, null);
    }

    public static <T> Resource<T> error(String msg, @Nullable T data) {
        return new Resource<>(StatusType.ERROR, data, msg);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(StatusType.LOADING, data, null);
    }
}
