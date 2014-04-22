package cn.burgeon.core.ui.allot;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.burgeon.core.R;
import cn.burgeon.core.bean.AllotIn;
import cn.burgeon.core.ui.BaseActivity;

public class AllotInActivity extends BaseActivity {

    private ArrayList<AllotIn> lists;
    private Button detailBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFullscreen();
        setContentView(R.layout.activity_allot_in);

        // 初始化布局控件
        init();

        // 初始化列表数据
        initLVData();
    }


    private void init() {
        detailBtn = (Button) findViewById(R.id.detailBtn);
        detailBtn.setOnClickListener(new ClickEvent());
    }

    private void initLVData() {
        Map<String, String> params = new HashMap<String, String>();
        JSONArray array;
        JSONObject transactions;
        try {
            array = new JSONArray();
            transactions = new JSONObject();
            transactions.put("id", 112);
            transactions.put("command", "Query");

            JSONObject paramsInTransactions = new JSONObject();
            paramsInTransactions.put("table", "M_TRANSFER");
            paramsInTransactions.put("columns", new JSONArray().put("DOCNO").put("BILLDATE").put("C_ORIG_ID").put("TOT_QTYOUT"));
            transactions.put("params", paramsInTransactions);

            array.put(transactions);
            params.put("transactions", array.toString());
            sendRequest(params, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        resJAToList(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
        }
    }

    private ArrayList<AllotIn> resJAToList(String response) throws JSONException {
        ArrayList<AllotIn> lists = new ArrayList<AllotIn>();

        JSONArray resJA = new JSONArray(response);
        JSONObject resJO = resJA.getJSONObject(0);
        JSONArray rowsJA = resJO.getJSONArray("rows");
        for (int i = 0; i < rowsJA.length(); i++) {
            // ["TF0912140000005", 20091214, 3909, 11]
            String currRow = rowsJA.get(i).toString();
            String[] currRows = currRow.split(",");

            AllotIn allotIn = new AllotIn();
            allotIn.setDOCNO(currRows[0]);
            allotIn.setBILLDATE(currRows[1]);
            allotIn.setC_ORIG_ID(currRows[2]);
            allotIn.setTOT_QTYOUT(currRows[3]);
            lists.add(allotIn);
        }
        return lists;
    }

    class ClickEvent implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.detailBtn:
                    forwardActivity(AllotInDetailActivity.class);
                    break;
            }
        }
    }
}
