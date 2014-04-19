package cn.burgeon.core.ui.sales;

import java.util.ArrayList;
import java.util.List;

import cn.burgeon.core.R;
import cn.burgeon.core.adapter.SalesDailyAdapter;
import cn.burgeon.core.adapter.SalesDailyReportAdapter;
import cn.burgeon.core.adapter.SalesOrderSearchAdapter;
import cn.burgeon.core.bean.Order;
import cn.burgeon.core.ui.BaseActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

public class SalesDailyReportActivity extends BaseActivity {
	
	ListView mList;
	SalesDailyReportAdapter mAdapter;
	Button btnSearch;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFullscreen();
        setContentView(R.layout.activity_sales_daily_report);

        init();
        bindList();
    }
    
    private void bindList() {
    	List<Order> data = fetchData();
    	mAdapter = new SalesDailyReportAdapter(data, this);
    	mList.setAdapter(mAdapter);
	}

	private void init(){
    	mList = (ListView) findViewById(R.id.salesDailyReportLV);
    	btnSearch = (Button) findViewById(R.id.salesDailyReportQueryBtn);
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
		return data;
	}
}
