package cn.burgeon.core.ui.inventory;

import java.util.ArrayList;
import java.util.List;

import cn.burgeon.core.R;
import cn.burgeon.core.adapter.InventoryQueryAdapter;
import cn.burgeon.core.bean.InventorySelf;
import cn.burgeon.core.ui.BaseActivity;
import android.os.Bundle;
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
    	buttonUpdate = (Button) findViewById(R.id.inventoryButtonUpdate);
    	buttonSearch = (Button) findViewById(R.id.inventoryButtonSearch);
    	buttonDelete = (Button) findViewById(R.id.inventoryButtonDelete);
    }

	private List<InventorySelf> fetchData() {
		List<InventorySelf> queryData = new ArrayList<InventorySelf>();
		
		//测试数据
		for(int i = 0;i < 10000; i++){
			queryData.add(new InventorySelf("我草",i,"我草啊"));
		}
	
		return queryData;
	}
}
