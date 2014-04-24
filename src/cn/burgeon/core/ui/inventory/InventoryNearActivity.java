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
import cn.burgeon.core.adapter.InventoryNearAdapter;
import cn.burgeon.core.bean.InventoryNear;
import cn.burgeon.core.ui.BaseActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class InventoryNearActivity extends BaseActivity {
	
	private ListView mListView;
	private InventoryNearAdapter mNearAdapter;
	private Button buttonAdd;
	private Button buttonUpdate;
	private Button buttonSearch;
	private Button buttonDelete;
	private EditText styleNumberEditText;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFullscreen();
        setContentView(R.layout.activity_inventory_near);

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

    private void bindList(List<InventoryNear> data) {
    	mNearAdapter = new InventoryNearAdapter(data, this);
    	mListView.setAdapter(mNearAdapter);
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
			paramsInTransactions.put("columns", new JSONArray().put("C_STORE_ID").put("M_PRODUCT_ID").put("QTY").put("M_PRODUCTALIAS_ID"));
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
    private List<InventoryNear> resJAToList(String response) throws JSONException {
        ArrayList<InventoryNear> lists = new ArrayList<InventoryNear>();

        JSONArray resJA = new JSONArray(response);
        JSONObject resJO = resJA.getJSONObject(0);
        JSONArray rowsJA = resJO.getJSONArray("rows");
        for (int i = 0; i < rowsJA.length(); i++) {
            // ["TF0912140000005", 20091214, 3909, 11]
            String currRow = rowsJA.get(i).toString();
            String[] currRows = currRow.split(",");

            InventoryNear inventoryNear = new InventoryNear();
            inventoryNear.setStoreName(currRows[0]);
            inventoryNear.setStyleNumber(currRows[1]);
            inventoryNear.setStyleCount(currRows[2]);
            inventoryNear.setBarCode(currRows[3]);
            lists.add(inventoryNear);
        }
        return lists;
    }
}
