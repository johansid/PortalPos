package cn.burgeon.core.ui.allot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.TextView;
import cn.burgeon.core.App;
import cn.burgeon.core.R;
import cn.burgeon.core.adapter.AllotOutLVAdapter;
import cn.burgeon.core.bean.AllotOut;
import cn.burgeon.core.ui.BaseActivity;
import cn.burgeon.core.utils.PreferenceUtils;
import cn.burgeon.core.utils.ScreenUtils;

import com.android.volley.Response;

public class AllotOutActivity extends BaseActivity {
    private ListView allotOutLV;
    private TextView recodeNumTV;
    private Button addBtn, queryBtn, delBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allot_out);

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

        allotOutLV = (ListView) findViewById(R.id.allotoutLV);
        recodeNumTV = (TextView) findViewById(R.id.recodeNumTV);

        addBtn = (Button) findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new ClickEvent());
        queryBtn = (Button) findViewById(R.id.queryBtn);
        delBtn = (Button) findViewById(R.id.delBtn);
    }

    private void initLVData() {
        Map<String, String> params = new HashMap<String, String>();
        try {
            JSONArray array = new JSONArray();
            JSONObject transactions = new JSONObject();
            transactions.put("id", 112);
            transactions.put("command", "Query");

            JSONObject paramsInTransactions = new JSONObject();
            paramsInTransactions.put("table", "M_TRANSFER");
            paramsInTransactions.put("columns", new JSONArray().put("ID").put("DOCNO").put("BILLDATE").put("C_DEST_ID").put("TOT_QTYOUT"));
            transactions.put("params", paramsInTransactions);

            array.put(transactions);
            params.put("transactions", array.toString());
            sendRequest(params, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // 取消进度条
                    stopProgressDialog();

                    try {
                        ArrayList<AllotOut> lists = resJAToList(response);
                        AllotOutLVAdapter mAdapter = new AllotOutLVAdapter(AllotOutActivity.this, lists, R.layout.allot_out_item);
                        allotOutLV.setAdapter(mAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<AllotOut> resJAToList(String response) throws JSONException {
        ArrayList<AllotOut> lists = new ArrayList<AllotOut>();

        JSONArray resJA = new JSONArray(response);
        JSONObject resJO = resJA.getJSONObject(0);
        JSONArray rowsJA = resJO.getJSONArray("rows");
        int len = rowsJA.length();
        recodeNumTV.setText(len + "条记录");
        for (int i = 0; i < len; i++) {
            // ["TF0912140000005",20091214,3909,11]
            String currRow = rowsJA.get(i).toString();
            String[] currRows = currRow.split(",");

            AllotOut allotOut = new AllotOut();
            allotOut.setID(currRows[0].substring(1, currRows[0].length()));
            allotOut.setDOCNO(currRows[1].substring(1, currRows[1].length() - 1));
            allotOut.setBILLDATE(currRows[2]);
            allotOut.setC_DEST_ID(currRows[3]);
            allotOut.setTOT_QTYOUT(currRows[4].substring(0, currRows[4].length() - 1));
            lists.add(allotOut);
        }
        return lists;
    }

    class ClickEvent implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.addBtn:
                    forwardActivity(AllotOutApplyActivity.class);
                    break;
            }
        }
    }
}
