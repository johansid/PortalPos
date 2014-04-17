package cn.burgeon.core.ui.sales;

import java.util.ArrayList;
import java.util.List;

import cn.burgeon.core.R;
import cn.burgeon.core.adapter.SalesDailyAdapter;
import cn.burgeon.core.bean.Order;
import cn.burgeon.core.ui.BaseActivity;
import android.os.Bundle;
import android.util.Log;
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
        Log.d("SalesManager", "=======onCreate========");
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
    }

	private List<Order> fetchData() {
		List<Order> data = new ArrayList<Order>();
		data.add(new Order("2014/4/12","vip","1","2","3","4","5"));
		data.add(new Order("2014/4/12","vip","1","2","3","4","5"));
		data.add(new Order("2014/4/12","vip","1","2","3","4","5"));
		data.add(new Order("2014/4/12","vip","1","2","3","4","5"));
		data.add(new Order("2014/4/12","vip","1","2","3","4","5"));
		data.add(new Order("2014/4/12","vip","1","2","3","4","5"));
		data.add(new Order("2014/4/12","vip","1","2","3","4","5"));
		data.add(new Order("2014/4/12","vip","1","2","3","4","5"));
		data.add(new Order("2014/4/12","vip","1","2","3","4","5"));
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
