package cn.burgeon.core.ui.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import cn.burgeon.core.R;
import cn.burgeon.core.adapter.MemberListAdapter;
import cn.burgeon.core.bean.Member;
import cn.burgeon.core.ui.BaseActivity;
import cn.burgeon.core.ui.sales.SalesNewOrderActivity;

import com.android.volley.Response;

public class MemberListActivity extends BaseActivity {
	
	ListView mListView;
	Button addBtn,queryBtn,updateBtn,delBtn;
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
		addBtn = (Button) findViewById(R.id.memberListAdd);
		queryBtn = (Button) findViewById(R.id.memberListQuery);
		updateBtn = (Button) findViewById(R.id.memberListUpdate);
		mListView = (ListView) findViewById(R.id.memberLV);
		mAdapter = new MemberListAdapter(postRequest(), this);
		
	}

	private List<Member> postRequest() {
		final List<Member> data = new ArrayList<Member>();
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
			paramsInTransactions.put("columns", new JSONArray().put("cardno").put("vipname").put("sex").put("birthday").put("C_VIPTYPE_ID;name"));
			//在params中的params
			paramsInTransactions.put("params", new JSONObject().put("column", "C_STORE_ID").put("condition", 3865));
			transactions.put("params", paramsInTransactions);
			array.put(transactions);
			params.put("transactions", array.toString());
			sendRequest(params,new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					Log.d("zhang.h", response);
					parseResult(response,data);
					mListView.setAdapter(mAdapter);
				}
			});
		} catch (JSONException e) {}
		return data;
	}
	
	private void parseResult(String result,List<Member> data){
		try {
			JSONArray array = new JSONArray(result);
			JSONObject obj = array.getJSONObject(0);
			JSONArray rows = obj.getJSONArray("rows");
			Member member = null;
			for(int i = 0; i < rows.length(); i++){
				String row = rows.get(i).toString();
				String[] rowArr = row.split(",");
				
				member = new Member();
				member.setCardNum(rowArr[0]);
				member.setName(rowArr[1]);
				member.setSex(rowArr[2]);
				member.setBirthday(rowArr[3]);
				member.setType(rowArr[4]);
				data.add(member);
			}
		} catch (JSONException e) {
			Log.d("MemberListActivity", e.toString());
		}
	}
	
	View.OnClickListener onClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.addBtn:
				forwardActivity(SalesNewOrderActivity.class);
				break;

			default:
				break;
			}
		}
	}; 
}
