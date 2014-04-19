package cn.burgeon.core.ui.sales;

import java.util.ArrayList;
import java.util.List;

import cn.burgeon.core.R;
import cn.burgeon.core.adapter.SalesDailyAdapter;
import cn.burgeon.core.adapter.SalesDailyReportAdapter;
import cn.burgeon.core.adapter.SalesMonthlyReportAdapter;
import cn.burgeon.core.adapter.SalesOrderSearchAdapter;
import cn.burgeon.core.bean.Order;
import cn.burgeon.core.ui.BaseActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

public class SalesMonthlyReportActivity extends BaseActivity {
	
	ListView mList;
	SalesMonthlyReportAdapter mAdapter;
	Button btnSearch;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFullscreen();
        setContentView(R.layout.activity_sales_monthly_report);

        init();
        bindList();
    }
    
    private void bindList() {
    	List<Order> data = fetchData();
    	mAdapter = new SalesMonthlyReportAdapter(data, this);
    	mList.setAdapter(mAdapter);
	}

	private void init(){
    	mList = (ListView) findViewById(R.id.salesMonthlyReportLV);
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
