package cn.burgeon.core.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.burgeon.core.R;
import cn.burgeon.core.net.RequestManager;

public class LoginActivity extends BaseActivity {

    private String[] stores = {"门店1", "门店2", "门店3"};
    private Spinner storeSpinner;
    private Button configBtn;
    private Button loginBtn;
    private Button logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFullscreen();
        setContentView(R.layout.activity_login);

        init();
    }

    private void init() {
        storeSpinner = (Spinner) findViewById(R.id.storeSpin);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(LoginActivity.this, android.R.layout.simple_spinner_item, stores);
        storeSpinner.setAdapter(adapter);

        configBtn = (Button) findViewById(R.id.configBtn);
        configBtn.setOnClickListener(new ClickEvent());
        loginBtn = (Button) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new ClickEvent());
        logoutBtn = (Button) findViewById(R.id.logoutBtn);
    }

    class ClickEvent implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.configBtn:
                    StringRequest request = new StringRequest(Request.Method.POST, "http://g.burgeon.cn:90/servlets/binserv/Rest", createMyReqSuccessListener(), createMyReqErrorListener()) {

                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> params = new HashMap<String, String>();
                            params.put("appkey", "nea@burgeon.com.cn");
                            params.put("password", "pbdev");
                            params.put("command", "GetObject");

                            /*
                            JSONObject obj = new JSONObject();
                            try {
                                obj.put("table", M_IN);
                                obj.put("id", 524376);
                                obj.put("reftables", [1024,2063]);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            */
                            params.put("params", "{\"table\":\"M_IN\", \"id\":524376, \"reftables\":[1024,2063]}");
                            return params;
                        }
                    };
                    RequestManager.getRequestQueue().add(request);

                    break;
                case R.id.loginBtn:
                    forwardActivity(SystemActivity.class);
                    break;
                case R.id.logoutBtn:
                    break;
            }
        }
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
