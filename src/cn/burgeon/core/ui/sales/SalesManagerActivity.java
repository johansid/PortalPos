package cn.burgeon.core.ui.sales;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import cn.burgeon.core.Constant;
import cn.burgeon.core.R;
import cn.burgeon.core.adapter.SalesManagerAdapter;
import cn.burgeon.core.ui.BaseActivity;

public class SalesManagerActivity extends BaseActivity {
	
    private GridView salesGV;
    private SalesManagerAdapter mAdapter;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sales_manager);
		
		init();
	}
	
    private void init() {
    	salesGV = (GridView) findViewById(R.id.salesGV);
        mAdapter = new SalesManagerAdapter(this);
        salesGV.setAdapter(mAdapter);
        salesGV.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemValue = (String) parent.getItemAtPosition(position);
                if (itemValue != null && Constant.salesTopMenuTextValues[0].equals(itemValue)) {
                	Log.d("SalesManager", "==================="+itemValue);
                    forwardActivity(DailySalesActivity.class);
                } /*else if (itemValue != null && Constant.salesTopMenuTextValues[1].equals(itemValue)) {
                    forwardActivity(ReplenishmentActivity.class);
                } else if (itemValue != null && Constant.salesTopMenuTextValues[2].equals(itemValue)) {
                    forwardActivity(ReplenishmentOrderActivity.class);
                }*/
            }
        });
    }

}
