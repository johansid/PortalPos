package cn.burgeon.core.ui.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import cn.burgeon.core.R;
import cn.burgeon.core.adapter.MemberListAdapter;
import cn.burgeon.core.bean.Order;
import cn.burgeon.core.ui.BaseActivity;

public class MemberListActivity extends BaseActivity {
	
	ListView mListView;
	MemberListAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setupFullscreen();
		setContentView(R.layout.activity_member_list);
		
		init();
	}

	private void init() {
		mListView = (ListView) findViewById(R.id.memberLV);
		mAdapter = new MemberListAdapter(getData(), this);
		mListView.setAdapter(mAdapter);
	}

	private List<Order> getData() {
		postRequest();
		return null;
	}

	private void postRequest() {
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
			paramsInTransactions.put("table", 12899);
			paramsInTransactions.put("columns", new JSONArray().put("cardno").put("vipname"));
			transactions.put("params", paramsInTransactions);
			
			//在params中的params
			//JSONObject paramsInTransactions = new JSONObject();
			//paramsInTransactions.put("table", 12899);
			//paramsInTransactions.put("columns", new JSONArray().put("cardno").put("vipname"));
	
			array.put(transactions);
			params.put("transactions", array.toString());
			sendRequest(params,new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					//Log.d("zhang.h", response);
					
				}
			});
		} catch (JSONException e) {}
	}
}
