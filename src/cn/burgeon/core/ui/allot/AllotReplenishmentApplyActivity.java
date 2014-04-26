package cn.burgeon.core.ui.allot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.TextView;
import cn.burgeon.core.App;
import cn.burgeon.core.R;
import cn.burgeon.core.adapter.AllotReplenishmentApplyLVAdapter;
import cn.burgeon.core.bean.AllotReplenishmentApply;
import cn.burgeon.core.ui.BaseActivity;
import cn.burgeon.core.utils.PreferenceUtils;
import cn.burgeon.core.utils.ScreenUtils;

import com.android.volley.Response;

public class AllotReplenishmentApplyActivity extends BaseActivity implements OnClickListener {

	ArrayList<AllotReplenishmentApply> lists = new ArrayList<AllotReplenishmentApply>();

	private ListView allotreplenishmentapplyLV;
	private TextView recodeNumTV, totalCountTV;
	private EditText barcodeET;
	private Button okBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_allot_replenishment_apply);

		init();

		initLVData();
	}

	private void init() {
		// 初始化门店信息
		TextView storeTV = (TextView) findViewById(R.id.storeTV);
		storeTV.setText(App.getPreferenceUtils().getPreferenceStr(PreferenceUtils.store_key));

		TextView currTimeTV = (TextView) findViewById(R.id.currTimeTV);
		currTimeTV.setText(getCurrDate());

		barcodeET = (EditText) findViewById(R.id.barcodeET);
		okBtn = (Button) findViewById(R.id.okBtn);
		okBtn.setOnClickListener(this);

		HorizontalScrollView hsv = (HorizontalScrollView) findViewById(R.id.hsv);
		ViewGroup.LayoutParams params = hsv.getLayoutParams();
		params.height = (int) ScreenUtils.getAllotInLVHeight(this);

		allotreplenishmentapplyLV = (ListView) findViewById(R.id.allotreplenishmentapplyLV);
		recodeNumTV = (TextView) findViewById(R.id.recodeNumTV);
		totalCountTV = (TextView) findViewById(R.id.totalCountTV);
	}

	private void initLVData() {
		// nothing
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.okBtn:
			/*
			 * { "table":"12668", "columns":[ "NO", "M_PRODUCT_ID;COLORS", "M_PRODUCT_ID;SIZES", "M_PRODUCT_ID;PRICELIST",
			 * "M_PRODUCT_ID;NAME" ], "params":{"column":"NO","condition":"109454D334620"} }
			 */
			Map<String, String> params = new HashMap<String, String>();
			JSONArray array = new JSONArray();
			try {
				JSONObject transactions = new JSONObject();
				transactions.put("id", 112);
				transactions.put("command", "Query");

				JSONObject paramsTable = new JSONObject();
				paramsTable.put("table", "12668");
				paramsTable.put("columns", new JSONArray().put("NO")
						.put("M_PRODUCT_ID;COLORS")
						.put("M_PRODUCT_ID;SIZES")
						.put("M_PRODUCT_ID;NAME"));
				JSONObject paramsCondition = new JSONObject();
				paramsCondition.put("column", "NO");
				paramsCondition.put("condition", barcodeET.getText().toString());
				paramsTable.put("params", paramsCondition);

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

							for (int i = 0; i < len; i++) {
								// ["109454D334620","1596","1634,1635,1636,1639,1640,1638","109454D334"]
								String currRow = rowsJA.get(i).toString();
								String[] currRows = currRow.split("\",");

								AllotReplenishmentApply allotReplenishmentApply = new AllotReplenishmentApply();
								allotReplenishmentApply.setBarcode(currRows[0].substring(2, currRows[0].length()));
								allotReplenishmentApply.setColor(currRows[1].substring(1, currRows[1].length()));
								allotReplenishmentApply.setSize(currRows[2].substring(1, currRows[2].length()));
								allotReplenishmentApply.setStyle(currRows[3].substring(1, currRows[3].length() - 2));
								allotReplenishmentApply.setNum("1");
								lists.add(allotReplenishmentApply);
							}

							// 记录数
							recodeNumTV.setText("记录数：" + lists.size());
							int num = 0;
							for (AllotReplenishmentApply allotReplenishmentApply : lists) {
								num += Integer.valueOf(allotReplenishmentApply.getNum());
							}
							totalCountTV.setText("数量：" + num);

							AllotReplenishmentApplyLVAdapter mAdapter = new AllotReplenishmentApplyLVAdapter(
									AllotReplenishmentApplyActivity.this, lists, R.layout.allot_replenishment_apply_item);
							allotreplenishmentapplyLV.setAdapter(mAdapter);
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

}
