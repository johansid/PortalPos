package cn.burgeon.core.ui.inventory;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;
import cn.burgeon.core.Constant;
import cn.burgeon.core.R;
import cn.burgeon.core.adapter.InventoryManagerAdapter;
import cn.burgeon.core.ui.BaseActivity;
import cn.burgeon.core.ui.inventory.InventoryQueryActivity;
import cn.burgeon.core.ui.inventory.InventoryRefreshActivity;
import cn.burgeon.core.ui.inventory.InventoryNearActivity;

public class InventoryManagerActivity extends BaseActivity{
	private GridView inventoryGridView;
	private InventoryManagerAdapter inventoryAdapter; 
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setupFullscreen();
		setContentView(R.layout.activity_inventory_manager);
		
		init();
	} 
	
    private void init() {
    	inventoryGridView = (GridView) findViewById(R.id.inventoryGridView);
    	inventoryAdapter = new InventoryManagerAdapter(this);
    	inventoryGridView.setAdapter(inventoryAdapter);
    	inventoryGridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemValue = (String) parent.getItemAtPosition(position);
                if (itemValue != null && Constant.inventoryManagerTextValues[0].equals(itemValue)) {
                    forwardActivity(InventoryQueryActivity.class);
                } else if (itemValue != null && Constant.inventoryManagerTextValues[1].equals(itemValue)) {
                    forwardActivity(InventoryRefreshActivity.class);
                } else if (itemValue != null && Constant.inventoryManagerTextValues[2].equals(itemValue)) {
                    forwardActivity(InventoryNearActivity.class);
                }
            }
        });
    }
}
