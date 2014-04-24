package cn.burgeon.core.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.burgeon.core.App;
import cn.burgeon.core.R;
import cn.burgeon.core.bean.IntentData;
import cn.burgeon.core.utils.PreferenceUtils;
import cn.burgeon.core.widget.UndoBarController;
import cn.burgeon.core.widget.UndoBarStyle;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private Spinner storeSpinner;
    private EditText userET, pswET;
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

        userET = (EditText) findViewById(R.id.userET);
        pswET = (EditText) findViewById(R.id.pswET);

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
                    // 取消进度条
                    stopProgressDialog();

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.configBtn:
                break;
            case R.id.loginBtn:
                try {
                    Map<String, String> params = new HashMap<String, String>();
                    JSONArray array = new JSONArray();

                    JSONObject transactions = new JSONObject();
                    transactions.put("id", 112);
                    transactions.put("command", "Query");

                    JSONObject paramsTable = new JSONObject();
                    paramsTable.put("table", "14630");
                    paramsTable.put("columns", new JSONArray().put("name").put("C_STORE_ID"));
                    JSONObject paramsCombine = new JSONObject();
                    paramsCombine.put("combine", "and");
                    JSONObject expr1JO = new JSONObject();
                    expr1JO.put("column", "name");
                    expr1JO.put("condition", userET.getText());
                    paramsCombine.put("expr1", expr1JO);
                    JSONObject expr2JO = new JSONObject();
                    expr2JO.put("column", "C_STORE_ID");
                    expr2JO.put("condition", "3890");
                    paramsCombine.put("expr2", expr2JO);
                    paramsTable.put("params", paramsCombine);

                    transactions.put("params", paramsTable);
                    array.put(transactions);
                    params.put("transactions", array.toString());
                    sendRequest(params, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // 取消进度条
                            stopProgressDialog();

                            try {
                                JSONArray resJA = new JSONArray(response);
                                JSONObject resJO = resJA.getJSONObject(0);
                                JSONArray rowsJA = resJO.getJSONArray("rows");
                                int len = rowsJA.length();
                                // 有效用户
                                if (len > 0) {
                                    // 跳转并传递数据
                                    IntentData intentData = new IntentData();
                                    intentData.setStore(storeSpinner.getSelectedItem().toString());
                                    intentData.setUser(userET.getText().toString());
                                    forwardActivity(SystemActivity.class, intentData);
                                } else {
                                    // 提示用户不存在
                                    // Toast.makeText(LoginActivity.this, "用户不存在", Toast.LENGTH_LONG).show();
                                    UndoBarStyle MESSAGESTYLE = new UndoBarStyle(-1, -1, 3000);
                                    UndoBarController.show(LoginActivity.this, "用户不存在", null, MESSAGESTYLE);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
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
}
