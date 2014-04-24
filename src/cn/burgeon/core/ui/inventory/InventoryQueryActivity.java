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
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class InventoryQueryActivity extends BaseActivity {
	
	private ListView mListView;
	private InventoryQueryAdapter mQueryAdapter;
	private Button buttonAdd;
	private Button buttonUpdate;
	private Button buttonSearch;
	private Button buttonDelete;
	private EditText styleNumberEditText;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFullscreen();
        setContentView(R.layout.activity_inventory_query);

        init();
    }
    


	private void init(){
		mListView = (ListView) findViewById(R.id.inventoryListView);
		
    	buttonAdd = (Button) findViewById(R.id.inventoryButtonAdd);
    	buttonUpdate = (Button) findViewById(R.id.inventoryButtonUpdate);
    	buttonSearch = (Button) findViewById(R.id.inventoryButtonSearch);
    	buttonDelete = (Button) findViewById(R.id.inventoryButtonDelete);
    	styleNumberEditText = (EditText) findViewById(R.id.inventoryStyleNumberEditText);
    	
    	
    	buttonAdd.setOnClickListener(new ClickEvent());
    	buttonUpdate.setOnClickListener(new ClickEvent());
    	buttonSearch.setOnClickListener(new ClickEvent());
    	buttonAdd.setOnClickListener(new ClickEvent());
    }

    private void bindList(List<InventorySelf> data) {
    	mQueryAdapter = new InventoryQueryAdapter(data, this);
    	mListView.setAdapter(mQueryAdapter);
	}
    
	public class ClickEvent implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.inventoryButtonAdd:
				startSearch();
				break;
			case R.id.inventoryButtonUpdate:
				startSearch();
				break;					
			case R.id.inventoryButtonSearch:
				startSearch();
				break;
			case R.id.inventoryButtonDelete:
				break;
			}
		}
	}
	
	//响应 查询 按钮
	private void startSearch(){
		
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
                    // 取消进度条
                    stopProgressDialog();
                    
                    try {
                    	bindList(resJAToList(response));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
					
				}
			});
		} catch (JSONException e) {}		
	}

	//解析服务器返回数据
    private List<InventorySelf> resJAToList(String response) throws JSONException {
        ArrayList<InventorySelf> lists = new ArrayList<InventorySelf>();

        JSONArray resJA = new JSONArray(response);
        JSONObject resJO = resJA.getJSONObject(0);
        JSONArray rowsJA = resJO.getJSONArray("rows");
        for (int i = 0; i < rowsJA.length(); i++) {
            // ["TF0912140000005", 20091214, 3909, 11]
            String currRow = rowsJA.get(i).toString();
            String[] currRows = currRow.split(",");

            InventorySelf inventorySelf = new InventorySelf();
            inventorySelf.setStyleNumber(currRows[0]);
            inventorySelf.setStyleCount(currRows[1]);
            inventorySelf.setStyleName(currRows[2]);
            lists.add(inventorySelf);
        }
        return lists;
    }
}
