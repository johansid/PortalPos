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
import cn.burgeon.core.R;
import cn.burgeon.core.adapter.AllotInLVAdapter;
import cn.burgeon.core.bean.AllotIn;
import cn.burgeon.core.ui.BaseActivity;
import cn.burgeon.core.utils.ScreenUtils;

import com.android.volley.Response;

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
			paramsInTransactions.put("columns", new JSONArray().put("ID").put("DOCNO").put("BILLDATE").put("C_ORIG_ID").put("TOT_QTYOUT"));
			transactions.put("params", paramsInTransactions);

			array.put(transactions);
			params.put("transactions", array.toString());
			sendRequest(params, new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					// 取消进度条
					stopProgressDialog();

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
			// [2766,"TF0912140000005",20091214,3909,11]
			String currRow = rowsJA.get(i).toString();
			String[] currRows = currRow.split(",");

			AllotIn allotIn = new AllotIn();
			allotIn.setID(currRows[0].substring(1, currRows[0].length()));
			allotIn.setDOCNO(currRows[1].substring(1, currRows[1].length() - 1));
			allotIn.setBILLDATE(currRows[2]);
			allotIn.setC_ORIG_ID(currRows[3]);
			allotIn.setTOT_QTYOUT(currRows[4].substring(0, currRows[4].length() - 1));
			lists.add(allotIn);
		}
		return lists;
	}

	class ClickEvent implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			/*
			 * { "table":"M_TRANSFERITEM", "columns":["M_PRODUCTALIAS_ID", // 条码 "M_ATTRIBUTESETINSTANCE_ID;VALUE1", // 颜色
			 * "M_ATTRIBUTESETINSTANCE_ID;VALUE2_CODE", // 尺寸 "QTYOUT", // 出库数量 "QTYIN", // 入库数量 "PRICELIST", // 标准价 "M_PRODUCT_ID;VALUE"],
			 * // 品名 "params":{"condition":"ID","column":"M_TRANSFER_ID"} }
			 */
			case R.id.detailBtn:
				forwardActivity(AllotInDetailActivity.class);
				break;
			}
		}
	}
}
