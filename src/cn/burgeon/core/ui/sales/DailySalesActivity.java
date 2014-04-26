package cn.burgeon.core.ui.sales;

import java.util.ArrayList;
import java.util.List;

import cn.burgeon.core.R;
import cn.burgeon.core.adapter.SalesDailyAdapter;
import cn.burgeon.core.bean.Order;
import cn.burgeon.core.ui.BaseActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class DailySalesActivity extends BaseActivity {
	
	ListView mList;
	SalesDailyAdapter mAdapter;
	Button btnAdd, btnUpdate, btnSearch, btnDelete;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFullscreen();
        setContentView(R.layout.activity_sales_manager_dailysales);

        init();
        bindList();
    }
    
    private void bindList() {
    	List<Order> data = fetchData();
    	mAdapter = new SalesDailyAdapter(data, this);
    	mList.setAdapter(mAdapter);
	}

	private void init(){
    	mList = (ListView) findViewById(R.id.dailySalesLV);
    	btnAdd = (Button) findViewById(R.id.sales_daily_btn_add);
    	btnUpdate = (Button) findViewById(R.id.sales_daily_btn_update);
    	btnSearch = (Button) findViewById(R.id.sales_daily_btn_search);
    	btnDelete = (Button) findViewById(R.id.sales_daily_btn_delete);
    	btnAdd.setOnClickListener(onClickListener);
    	btnUpdate.setOnClickListener(onClickListener);
    	btnSearch.setOnClickListener(onClickListener);
    	btnDelete.setOnClickListener(onClickListener);
    }
	
	View.OnClickListener onClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.sales_daily_btn_add:
				forwardActivity(SalesNewOrderActivity.class);
				break;
			case R.id.sales_daily_btn_update:
				
				break;
			case R.id.sales_daily_btn_search:
				
				break;
			case R.id.sales_daily_btn_delete:
				
				break;
			default:
				break;
			}
		}
	};

	private List<Order> fetchData() {
		List<Order> data = new ArrayList<Order>();
		data.add(new Order("2014/4/12","vip","1","2","3","4","5"));
		data.add(new Order("2014/4/12","vip","1","2","3","4","5"));
		data.add(new Order("2014/4/12","vip","1","2","3","4","5"));
		data.add(new Order("2014/4/12","vip","1","2","3","4","5"));
		data.add(new Order("2014/4/12","vip","1","2","3","4","5"));
		data.add(new Order("2014/4/12","vip","1","2","3","4","5"));
		data.add(new Order("2014/4/12","vip","1","2","3","4","5"));
		return data;
	}
}
