package cn.burgeon.core.ui.sales;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView.CommaTokenizer;
import android.widget.TextView;
import cn.burgeon.core.App;
import cn.burgeon.core.R;
import cn.burgeon.core.adapter.SalesNewOrderAdapter;
import cn.burgeon.core.bean.IntentData;
import cn.burgeon.core.bean.Member;
import cn.burgeon.core.bean.Product;
import cn.burgeon.core.ui.BaseActivity;
import cn.burgeon.core.ui.SystemActivity;
import cn.burgeon.core.ui.member.MemberRegistActivity;
import cn.burgeon.core.ui.member.MemberSearchActivity;
import cn.burgeon.core.utils.PreferenceUtils;
import cn.burgeon.core.utils.ScreenUtils;

public class SalesNewOrderActivity extends BaseActivity {
	
	Button vipBtn, accountBtn, verifyBarCodeBtn;
	EditText cardNoET, styleBarcodeET;
	TextView commonRecordnum,commonCount,commonMoney;
	ListView mListView;
	SalesNewOrderAdapter mAdapter;
	ArrayList<Product> data = new ArrayList<Product>();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFullscreen();
        setContentView(R.layout.activity_sales_new_order);

        init();
        
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
        	String searchedMember = getIntent().getExtras().getString("searchedMember");
        	if(searchedMember != null)
        		cardNoET.setText(searchedMember);
        }
    }
    
    @Override
    protected void onNewIntent(Intent intent) {
    	// TODO Auto-generated method stub
    	super.onNewIntent(intent);
    	String searchedMember = getIntent().getExtras().getString("searchedMember");
    	Log.d("SalesNewOrderActivity", searchedMember);
    	cardNoET.setText(searchedMember);
    }
    
    
    @Override
    protected void onStop() {
    	// TODO Auto-generated method stub
    	super.onStop();
    	finish();
    }

	private void init() {
        // 初始化门店信息
        TextView storeTV = (TextView) findViewById(R.id.storeTV);
        storeTV.setText(App.getPreferenceUtils().getPreferenceStr(PreferenceUtils.store_key));

        TextView currTimeTV = (TextView) findViewById(R.id.currTimeTV);
        currTimeTV.setText(getCurrDate());

        HorizontalScrollView hsv = (HorizontalScrollView) findViewById(R.id.hsv);
        ViewGroup.LayoutParams params = hsv.getLayoutParams();
        params.height = (int) ScreenUtils.getAllotInDetailLVHeight(this)-100;
        
        commonRecordnum = (TextView) findViewById(R.id.sales_common_recordnum);
        commonCount = (TextView) findViewById(R.id.sales_common_count);
        commonMoney = (TextView) findViewById(R.id.sales_common_money);
		vipBtn = (Button) findViewById(R.id.salesNewVIPbtn);
		accountBtn = (Button) findViewById(R.id.salesNewJiezhangBtn);
		verifyBarCodeBtn = (Button) findViewById(R.id.verifyBarCodeBtn);
		vipBtn.setOnClickListener(onClickListener);
		accountBtn.setOnClickListener(onClickListener);
		verifyBarCodeBtn.setOnClickListener(onClickListener);
		cardNoET = (EditText) findViewById(R.id.cardnoDiscountET);
		styleBarcodeET = (EditText) findViewById(R.id.styleBarcodeET);
		styleBarcodeET.setText("109454d334620");
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
				forwardActivity(MemberSearchActivity.class);
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
	
	private void upateBottomBarInfo() {
		float pay = 0.0f;
		int count = 0;
		for(Product pro : data){
			pay += Float.parseFloat(pro.getMoney());
			count += Integer.parseInt(pro.getCount());
		}
		Log.d("zhang.h", "pay=" + pay+",count=" + count);
		
		commonMoney.setText(String.format(getResources().getString(R.string.sales_new_common_money),String.valueOf(pay)));
		commonCount.setText(String.format(getResources().getString(R.string.sales_new_common_count), count));
		commonRecordnum.setText(String.format(getResources().getString(R.string.sales_new_common_record), data.size()));
	}
	
	private void verifyBarCode() {
		//从本地获取
		varLocal();
		//从网络获取
		varNet();
	}

	private void varLocal() {
		
	}

	private void varNet() {
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
					upateBottomBarInfo();
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
