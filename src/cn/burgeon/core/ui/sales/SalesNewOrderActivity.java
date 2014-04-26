package cn.burgeon.core.ui.sales;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import cn.burgeon.core.R;
import cn.burgeon.core.adapter.SalesNewOrderAdapter;
import cn.burgeon.core.bean.IntentData;
import cn.burgeon.core.bean.Member;
import cn.burgeon.core.bean.Product;
import cn.burgeon.core.ui.BaseActivity;
import cn.burgeon.core.ui.SystemActivity;
import cn.burgeon.core.ui.member.MemberRegistActivity;

public class SalesNewOrderActivity extends BaseActivity {
	
	Button vipBtn, accountBtn, verifyBarCodeBtn;
	EditText cardNoET, styleBarcodeET;
	ListView mListView;
	SalesNewOrderAdapter mAdapter;
	ArrayList<Product> data = new ArrayList<Product>();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFullscreen();
        setContentView(R.layout.activity_sales_new_order);

        init();
    }

	private void init() {
		vipBtn = (Button) findViewById(R.id.salesNewVIPbtn);
		accountBtn = (Button) findViewById(R.id.salesNewJiezhangBtn);
		verifyBarCodeBtn = (Button) findViewById(R.id.verifyBarCodeBtn);
		vipBtn.setOnClickListener(onClickListener);
		accountBtn.setOnClickListener(onClickListener);
		verifyBarCodeBtn.setOnClickListener(onClickListener);
		cardNoET = (EditText) findViewById(R.id.cardnoDiscountET);
		styleBarcodeET = (EditText) findViewById(R.id.styleBarcodeET);
		mListView = (ListView) findViewById(R.id.newOrderLV);
		mAdapter = new SalesNewOrderAdapter(data, this);
		mListView.setAdapter(mAdapter);
	}
	
	View.OnClickListener onClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.salesNewVIPbtn:
				//跳转到会员注册页面
				//forwardActivity(MemberRegistActivity.class);
				break;
			case R.id.salesNewJiezhangBtn:
				// 跳转并传递数据
                IntentData intentData = new IntentData();
                intentData.setProducts(data);
                forwardActivity(SalesSettleActivity.class, intentData);
				break;
			case R.id.verifyBarCodeBtn:
				verifyBarCode();
				break;
			default:
				break;
			}
		}
	}; 
	
	private void verifyBarCode() {
		Map<String,String> params = new HashMap<String, String>();
		JSONArray array;
		JSONObject transactions;
		try {
			array = new JSONArray();
			transactions = new JSONObject();
			transactions.put("id", 112);
			transactions.put("command", "Query");
			
			//第一个params
			JSONObject paramsInTransactions = new JSONObject();
			paramsInTransactions.put("table", 12668);
			paramsInTransactions.put("columns", new JSONArray().put("NO")
					.put("M_PRODUCT_ID;PRICELIST")
					.put("M_PRODUCT_ID;value")
					.put("M_PRODUCT_ID;name"));
			//在params中的params
			paramsInTransactions.put("params", new JSONObject().put("column", "NO")
					.put("condition", styleBarcodeET.getText().toString()));
			transactions.put("params", paramsInTransactions);
			array.put(transactions);
			
			Log.d("zhang.h", array.toString());
			params.put("transactions", array.toString());
			sendRequest(params,new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					// 取消进度条
                    stopProgressDialog();
					Log.d("zhang.h", response);
					//response = "";109454D334620  650012110 53785267
					List<Product> list = parseResult(response);
					data.addAll(list);
					//mListView.setAdapter(mAdapter);
					mAdapter.notifyDataSetChanged();
				}
			});
		} catch (JSONException e) {}
	}
	
	private List<Product> parseResult(String response) {
		Product product = null;
		List<Product> list = null;
	    try {
	    	list = new ArrayList<Product>();
			JSONArray resJA = new JSONArray(response);
			JSONObject resJO = resJA.getJSONObject(0);
			JSONArray rowsJA = resJO.getJSONArray("rows");
			for(int i = 0; i < rowsJA.length(); i++){
				String row = rowsJA.get(i).toString();
				String[] rowArr = row.split(",");
				
				product = new Product();
				product.setBarCode(rowArr[0].substring(2,rowArr[0].length()-1));
				product.setDiscount("0");
				product.setPrice(rowArr[1].replace("\"", ""));
				product.setCount("1");
				product.setMoney(String.valueOf((Integer.parseInt(product.getCount()) * Float.parseFloat(product.getPrice()))));
				product.setName(rowArr[3].substring(1,rowArr[3].length()-2));
				list.add(product);
			}
		} catch (JSONException e) {}
		return list;
	}

}
