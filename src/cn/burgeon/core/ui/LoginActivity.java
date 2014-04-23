package cn.burgeon.core.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.burgeon.core.App;
import cn.burgeon.core.R;
import cn.burgeon.core.utils.PreferenceUtils;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private Spinner storeSpinner;
    private ImageView configBtn, loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFullscreen();
        setContentView(R.layout.activity_login);

        // 初始化门店
        initStoreData();

        // 初始化布局控件
        init();
    }

    private void init() {
        storeSpinner = (Spinner) findViewById(R.id.storeSpin);
        storeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 存入本地
                String storeItem = parent.getSelectedItem().toString();
                App.getPreferenceUtils().savePreferenceStr(PreferenceUtils.store_key, storeItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        configBtn = (ImageView) findViewById(R.id.configBtn);
        configBtn.setOnClickListener(this);
        loginBtn = (ImageView) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);
    }

    private void initStoreData() {
        Map<String, String> params = new HashMap<String, String>();
        JSONArray array;
        JSONObject transactions;
        try {
            array = new JSONArray();
            transactions = new JSONObject();
            transactions.put("id", 112);
            transactions.put("command", "Query");

            JSONObject paramsInTransactions = new JSONObject();
            paramsInTransactions.put("table", "C_V_RESTORE");
            paramsInTransactions.put("columns", new JSONArray().put("NAME"));
            transactions.put("params", paramsInTransactions);

            array.put(transactions);
            params.put("transactions", array.toString());

            sendRequest(params, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        String[] stores = resJAToList(response);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(LoginActivity.this, android.R.layout.simple_spinner_item, stores);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        storeSpinner.setAdapter(adapter);

                        // 初始化门店名
                        String storeInDBVal = App.getPreferenceUtils().getPreferenceStr(PreferenceUtils.store_key);
                        if (storeInDBVal != null && storeInDBVal.length() > 0) {
                            int position = adapter.getPosition(storeInDBVal);
                            storeSpinner.setSelection(position, true);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String[] resJAToList(String response) throws JSONException {
        String[] stores = null;

        JSONArray resJA = new JSONArray(response);
        JSONObject resJO = resJA.getJSONObject(0);
        JSONArray rowsJA = resJO.getJSONArray("rows");
        int len = rowsJA.length();
        stores = new String[len];
        for (int i = 0; i < len; i++) {
            // ["D江苏扬州陈勇"]
            String currRow = rowsJA.get(i).toString();
            stores[i] = currRow.substring(2, currRow.length() - 2);
        }
        return stores;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.configBtn:
                break;
            case R.id.loginBtn:
                forwardActivity(SystemActivity.class);
                break;
        }
    }

}
