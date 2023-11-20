package com.example.testcrudmysql.Class;
import java.util.List;

import retrofit2.Callback;

public class Value {

    String value;
    String message;
    List<Result> result;

    public String getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public List<Result> getResult() {
        return result;
    }
}
