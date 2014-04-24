package cn.burgeon.core.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

public class PreferenceUtils {

    public static final String Burgeon_PREF = "BurgeonPreference";
    public static final String store_key = "storeKey";

    private static Context mContext;
    private static final int PRIVATE_MODE = ContextWrapper.MODE_PRIVATE;

    public PreferenceUtils(Context context) {
        mContext = context;
    }

    public boolean savePreferenceStr(String PrefKey, String PrefValue) {
        try {
            SharedPreferences cbhPref = new ContextWrapper(mContext).getSharedPreferences(Burgeon_PREF, PRIVATE_MODE);
            SharedPreferences.Editor editor = cbhPref.edit();
            editor.putString(PrefKey, PrefValue);
            editor.commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean savePreferenceInt(String PrefKey, Integer PrefValue) {
        try {
            SharedPreferences cbhPref = new ContextWrapper(mContext).getSharedPreferences(Burgeon_PREF, PRIVATE_MODE);
            SharedPreferences.Editor editor = cbhPref.edit();
            editor.putInt(PrefKey, PrefValue);
            editor.commit();
            return true;
        } catch (Exception ee) {
            return false;
        }
    }

    public boolean savePreferenceLong(String PrefKey, long PrefValue) {
        try {
            SharedPreferences cbhPref = new ContextWrapper(mContext).getSharedPreferences(Burgeon_PREF, PRIVATE_MODE);
            SharedPreferences.Editor editor = cbhPref.edit();
            editor.putLong(PrefKey, PrefValue);
            editor.commit();
            return true;
        } catch (Exception ee) {
            return false;
        }
    }

    public String getPreferenceStr(String RefKey) {
        try {
            SharedPreferences cbhPref = new ContextWrapper(mContext).getSharedPreferences(Burgeon_PREF, PRIVATE_MODE);
            return cbhPref.getString(RefKey, "");
        } catch (Exception ee) {
            return "";
        }
    }

    public Integer getPreferenceInt(String RefKey) {
        try {
            SharedPreferences cbhPref = new ContextWrapper(mContext).getSharedPreferences(Burgeon_PREF, PRIVATE_MODE);
            return cbhPref.getInt(RefKey, 0);
        } catch (Exception ee) {
            return 0;
        }
    }

    public long getPreferenceLong(String RefKey) {
        try {
            SharedPreferences cbhPref = new ContextWrapper(mContext).getSharedPreferences(Burgeon_PREF, PRIVATE_MODE);
            return cbhPref.getLong(RefKey, 0);
        } catch (Exception ee) {
            return 0;
        }
    }


}