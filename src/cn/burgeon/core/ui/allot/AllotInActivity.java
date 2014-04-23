package cn.burgeon.core.ui.allot;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.burgeon.core.R;
import cn.burgeon.core.adapter.AllotInLVAdapter;
import cn.burgeon.core.bean.AllotIn;
import cn.burgeon.core.ui.BaseActivity;
import cn.burgeon.core.utils.ScreenUtils;

public class AllotInActivity extends BaseActivity {

    private Button detailBtn;
    private ListView allotinLV;
    private TextView recodeNumTV;

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
        TextView currTimeTV = (TextView) findViewById(R.id.currTimeTV);
        currTimeTV.setText(getCurrDate());

        HorizontalScrollView hsv = (HorizontalScrollView) findViewById(R.id.hsv);
        ViewGroup.LayoutParams params = hsv.getLayoutParams();
        params.height = (int) ScreenUtils.getAllotInLVHeight(this);

        allotinLV = (ListView) findViewById(R.id.allotinLV);
        recodeNumTV = (TextView) findViewById(R.id.recodeNumTV);

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
                        ArrayList<AllotIn> lists = resJAToList(response);
                        AllotInLVAdapter mAdapter = new AllotInLVAdapter(AllotInActivity.this, lists, R.layout.allot_in_item);
                        allotinLV.setAdapter(mAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<AllotIn> resJAToList(String response) throws JSONException {
        ArrayList<AllotIn> lists = new ArrayList<AllotIn>();

        JSONArray resJA = new JSONArray(response);
        JSONObject resJO = resJA.getJSONObject(0);
        JSONArray rowsJA = resJO.getJSONArray("rows");
        int len = rowsJA.length();
        recodeNumTV.setText(len + "条记录");
        for (int i = 0; i < len; i++) {
            // ["TF0912140000005", 20091214, 3909, 11]
            String currRow = rowsJA.get(i).toString();
            String[] currRows = currRow.split(",");

            AllotIn allotIn = new AllotIn();
            allotIn.setDOCNO(currRows[0].substring(2, currRows[0].length() - 1));
            allotIn.setBILLDATE(currRows[1]);
            allotIn.setC_ORIG_ID(currRows[2]);
            allotIn.setTOT_QTYOUT(currRows[3].substring(0, currRows[3].length() - 1));
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
