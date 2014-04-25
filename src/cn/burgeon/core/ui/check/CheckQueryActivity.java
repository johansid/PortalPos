package cn.burgeon.core.ui.check;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.burgeon.core.App;
import cn.burgeon.core.R;
import cn.burgeon.core.adapter.CheckQueryLVAdapter;
import cn.burgeon.core.bean.CheckQuery;
import cn.burgeon.core.ui.BaseActivity;
import cn.burgeon.core.utils.PreferenceUtils;
import cn.burgeon.core.utils.ScreenUtils;

public class CheckQueryActivity extends BaseActivity {

    private TextView recordCountTV, totalOutCountTV;
    private ListView checkQueryLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_query);

        init();

        initLVData();
    }

    private void init() {
        // 初始化门店信息
        TextView storeTV = (TextView) findViewById(R.id.storeTV);
        storeTV.setText(App.getPreferenceUtils().getPreferenceStr(PreferenceUtils.store_key));

        TextView currTimeTV = (TextView) findViewById(R.id.currTimeTV);
        currTimeTV.setText(getCurrDate());

        HorizontalScrollView hsv = (HorizontalScrollView) findViewById(R.id.hsv);
        ViewGroup.LayoutParams params = hsv.getLayoutParams();
        params.height = (int) ScreenUtils.getAllotInLVHeight(this);

        checkQueryLV = (ListView) findViewById(R.id.checkQueryLV);
        recordCountTV = (TextView) findViewById(R.id.recordCountTV);
    }

    private void initLVData() {
        Map<String, String> params = new HashMap<String, String>();
        try {
            JSONArray array = new JSONArray();
            JSONObject transactions = new JSONObject();
            transactions.put("id", 112);
            transactions.put("command", "Query");

            JSONObject paramsInTransactions = new JSONObject();
            paramsInTransactions.put("table", "M_INVENTORY");
            paramsInTransactions.put("columns", new JSONArray().put("ID").put("BILLDATE").put("DOCNO").put("DOCTYPE"));
            transactions.put("params", paramsInTransactions);

            array.put(transactions);
            params.put("transactions", array.toString());
            sendRequest(params, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // 取消进度条
                    stopProgressDialog();

                    try {
                        ArrayList<CheckQuery> lists = resJAToList(response);
                        CheckQueryLVAdapter mAdapter = new CheckQueryLVAdapter(CheckQueryActivity.this, lists, R.layout.check_query_item);
                        checkQueryLV.setAdapter(mAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<CheckQuery> resJAToList(String response) throws JSONException {
        ArrayList<CheckQuery> lists = new ArrayList<CheckQuery>();

        JSONArray resJA = new JSONArray(response);
        JSONObject resJO = resJA.getJSONObject(0);
        JSONArray rowsJA = resJO.getJSONArray("rows");
        int len = rowsJA.length();
        recordCountTV.setText("记录数：" + len);
        for (int i = 0; i < len; i++) {
            // [65608,20091212,"IV09121200001","历史盘点"]
            String currRow = rowsJA.get(i).toString();
            String[] currRows = currRow.split(",");

            CheckQuery checkQuery = new CheckQuery();
            checkQuery.setID(currRows[0].substring(1, currRows[0].length()));
            checkQuery.setBILLDATE(currRows[1]);
            checkQuery.setDOCNO(currRows[2].substring(1, currRows[2].length() - 1));
            checkQuery.setDOCTYPE(currRows[3].substring(1, currRows[3].length() - 2));
            lists.add(checkQuery);
        }
        return lists;
    }
}
