package cn.burgeon.core.ui;

import java.util.Date;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import cn.burgeon.core.App;
import cn.burgeon.core.db.DbHelper;
import cn.burgeon.core.net.RequestManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by Simon on 2014/4/16.
 */
public class BaseActivity extends Activity {
	
	App mApp;
	private DbHelper helper;  
    protected SQLiteDatabase db;  

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApp = (App) getApplication();
        helper = new DbHelper(this);  
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);  
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里  
        db = helper.getWritableDatabase();  
    }

    // 设置程序全屏显示
    public void setupFullscreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    }

    // 跳转
    public void forwardActivity(Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        startActivity(intent);
    }
    
    public void sendRequest(final Map<String, String> params){
		StringRequest request = new StringRequest(Request.Method.POST,App.getHosturl(),createMyReqSuccessListener(),createMyReqErrorListener()) {

			protected Map<String, String> getParams()
					throws AuthFailureError {
				String tt = mApp.getSDF().format(new Date());
				
				//appKey,时间戳,MD5签名
				//HashMap<String, String> params = new HashMap<String, String>();
				params.put("sip_appkey", App.getSipkey());
				params.put("sip_timestamp", tt);
				params.put("sip_sign", mApp.MD5(App.getSipkey() + tt + mApp.getSIPPSWDMD5()));
				return params;
			}
		};
		RequestManager.getRequestQueue().add(request);
    }

    private Response.Listener<String> createMyReqSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.d("zhang.h", response);
			}
		};
	}

	private Response.ErrorListener createMyReqErrorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.d("zhang.h", error.getMessage());
			}
		};
	}

}
