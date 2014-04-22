package cn.burgeon.core.ui.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;

import cn.burgeon.core.R;
import cn.burgeon.core.adapter.InventoryQueryAdapter;
import cn.burgeon.core.bean.InventorySelf;
import cn.burgeon.core.ui.BaseActivity;
import cn.burgeon.core.ui.SystemActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class InventoryQueryActivity extends BaseActivity {
	
	private ListView mListView;
	private InventoryQueryAdapter mQueryAdapter;
	private Button buttonAdd;
	private Button buttonUpdate;
	private Button buttonSearch;
	private Button buttonDelete;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFullscreen();
        setContentView(R.layout.activity_inventory_query);

        init();
        bindList();
    }
    
    private void bindList() {
    	List<InventorySelf> data = fetchData();
    	mQueryAdapter = new InventoryQueryAdapter(data, this);
    	mListView.setAdapter(mQueryAdapter);
	}

	private void init(){
		mListView = (ListView) findViewById(R.id.inventoryListView);
    	buttonAdd = (Button) findViewById(R.id.inventoryButtonAdd);
    	buttonAdd.setOnClickListener(new ClickEvent());
    	buttonUpdate = (Button) findViewById(R.id.inventoryButtonUpdate);
    	buttonUpdate.setOnClickListener(new ClickEvent());
    	buttonSearch = (Button) findViewById(R.id.inventoryButtonSearch);
    	buttonSearch.setOnClickListener(new ClickEvent());
    	buttonDelete = (Button) findViewById(R.id.inventoryButtonDelete);
    	buttonAdd.setOnClickListener(new ClickEvent());
    }

	private List<InventorySelf> fetchData() {
		List<InventorySelf> queryData = new ArrayList<InventorySelf>();
		
		//测试数据
		for(int i = 0;i < 10000; i++){
			queryData.add(new InventorySelf("我草",i,"我草啊"));
		}
	
		return queryData;
	}
	
	public class ClickEvent implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.inventoryButtonSearch:
				
				Map<String,String> params = new HashMap<String, String>();
								
				JSONObject transactions;
				
				try {
					transactions = new JSONObject();
					//id
					transactions.put("id", 112);
					//command
					transactions.put("command", "Query");
					//params
					JSONObject paramsInTransactions = new JSONObject();
					paramsInTransactions.put("table", 15632);
					paramsInTransactions.put("columns", new JSONArray().put("M_PRODUCT_ID").put("QTY").put("AD_ORG_ID"));
					paramsInTransactions.put("count", true);
					transactions.put("params", paramsInTransactions);
					
					params.put("transactions", new JSONArray().put(transactions).toString());
					sendRequest(params,new Response.Listener<String>() {
						@Override
						public void onResponse(String response) {
							Log.d("_____________我草___________", response);
							
						}
					});
				} catch (JSONException e) {}
				break;
			case R.id.loginBtn:
				forwardActivity(SystemActivity.class);
				break;
			case R.id.logoutBtn:
				break;
			}
		}
	}	
}
