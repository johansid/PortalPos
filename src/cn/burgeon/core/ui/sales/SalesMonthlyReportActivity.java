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
import android.database.Cursor;
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
		Order order = null;
		List<Order> data = new ArrayList<Order>();
		Cursor c = db.rawQuery("select settleMonth, sum(count) as totalCount,sum(money) as totalMoney from c_settle group by settleMonth",null);
		Log.d("zhang.h", "cursor size===========" + c.getCount());
		while(c.moveToNext()){
			order = new Order();
			order.setOrderDate(c.getString(c.getColumnIndex("settleMonth")));
			order.setOrderCount(c.getString(c.getColumnIndex("totalCount")));
			order.setOrderMoney(c.getString(c.getColumnIndex("totalMoney")));
			data.add(order);
		}
		c.close();
		return data;
	}
}
