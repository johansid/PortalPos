package cn.burgeon.core.ui.inventory;

import java.util.ArrayList;
import java.util.List;

import cn.burgeon.core.R;
import cn.burgeon.core.adapter.InventoryNearAdapter;
import cn.burgeon.core.bean.InventoryNear;
import cn.burgeon.core.ui.BaseActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

public class InventoryNearActivity extends BaseActivity {
	
	private ListView mListView;
	private InventoryNearAdapter mNearAdapter;
	private Button buttonAdd;
	private Button buttonUpdate;
	private Button buttonSearch;
	private Button buttonDelete;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFullscreen();
        setContentView(R.layout.activity_inventory_near);

        init();
        bindList();
    }
    
    private void bindList() {
    	List<InventoryNear> data = fetchData();
    	mNearAdapter = new InventoryNearAdapter(data, this);
    	mListView.setAdapter(mNearAdapter);
	}

	private void init(){
		mListView = (ListView) findViewById(R.id.inventoryListView);
    	buttonAdd = (Button) findViewById(R.id.inventoryButtonAdd);
    	buttonUpdate = (Button) findViewById(R.id.inventoryButtonUpdate);
    	buttonUpdate = (Button) findViewById(R.id.inventoryButtonSearch);
    	buttonDelete = (Button) findViewById(R.id.inventoryButtonDelete);
    }

	private List<InventoryNear> fetchData() {
		List<InventoryNear> data = new ArrayList<InventoryNear>();
		
		//测试数据
		for(int i = 0;i < 10000; i++){
			data.add(new InventoryNear("尼玛","尼玛",i,"尼玛啊"));
		}
	
		return data;
	}
}
