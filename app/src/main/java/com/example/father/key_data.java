package com.example.father;

import java.util.ArrayList;

public class key_data {
    String key;
    Object data;
    key_data(String key,Object data)
    {
        this.key=key;
        this.data=data;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
