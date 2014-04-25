package cn.burgeon.core.bean;

import java.io.Serializable;

/**
 * Created by Simon on 2014/4/22.
 * 页面传递数据
 */
public class IntentData implements Serializable {
    private String store;
    private String user;

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

}
