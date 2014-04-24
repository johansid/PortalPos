package cn.burgeon.core.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Simon on 2014/4/22.
 * 页面传递数据
 */
public class IntentData implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.store);
        dest.writeString(this.user);
    }

    public IntentData() {
    }

    private IntentData(Parcel in) {
        this.store = in.readString();
        this.user = in.readString();
    }

    public static Parcelable.Creator<IntentData> CREATOR = new Parcelable.Creator<IntentData>() {
        public IntentData createFromParcel(Parcel source) {
            return new IntentData(source);
        }

        public IntentData[] newArray(int size) {
            return new IntentData[size];
        }
    };
}
