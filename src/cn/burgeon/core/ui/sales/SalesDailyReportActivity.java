package cn.burgeon.core.ui.sales;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import cn.burgeon.core.App;
import cn.burgeon.core.R;
import cn.burgeon.core.adapter.SalesDailyReportAdapter;
import cn.burgeon.core.bean.Order;
import cn.burgeon.core.ui.BaseActivity;
import cn.burgeon.core.utils.PreferenceUtils;

public class SalesDailyReportActivity extends BaseActivity {
	
	ListView mList;
	SalesDailyReportAdapter mAdapter;
	Button btnSearch;
	TextView commonRecordnum,commonCount,commonMoney;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFullscreen();
        setContentView(R.layout.activity_sales_daily_report);

        init();
        bindList();
        //queryForUpdate();
    }
    
    private void bindList() {
    	List<Order> data = fetchData();
    	mAdapter = new SalesDailyReportAdapter(data, this);
    	mList.setAdapter(mAdapter);
    	upateBottomBarInfo(data);
	}
    
	private void upateBottomBarInfo(List<Order> data) {
		float pay = 0.0f;
		int count = 0;
		for(Order pro : data){
			pay += Float.parseFloat(pro.getOrderMoney());
			count += Integer.parseInt(pro.getOrderCount());
		}
		Log.d("zhang.h", "pay=" + pay+",count=" + count);
		
		commonMoney.setText(String.format(getResources().getString(R.string.sales_new_common_money),String.valueOf(pay)));
		commonCount.setText(String.format(getResources().getString(R.string.sales_new_common_count), count));
		commonRecordnum.setText(String.format(getResources().getString(R.string.sales_new_common_record), data.size()));
	}

	private void init(){
        TextView storeTV = (TextView) findViewById(R.id.storeTV);
        storeTV.setText(App.getPreferenceUtils().getPreferenceStr(PreferenceUtils.store_key));

        TextView currTimeTV = (TextView) findViewById(R.id.currTimeTV);
        currTimeTV.setText(getCurrDate());

/*        HorizontalScrollView hsv = (HorizontalScrollView) findViewById(R.id.hsv);
        ViewGroup.LayoutParams params = hsv.getLayoutParams();
        params.height = (int) ScreenUtils.getAllotInLVHeight(this);*/
        commonRecordnum = (TextView) findViewById(R.id.sales_common_recordnum);
        commonCount = (TextView) findViewById(R.id.sales_common_count);
        commonMoney = (TextView) findViewById(R.id.sales_common_money);
    	mList = (ListView) findViewById(R.id.salesDailyReportLV);
    	btnSearch = (Button) findViewById(R.id.salesDailyReportQueryBtn);
    }
	
    private void queryForUpdate() {
		Cursor c = db.rawQuery("select * from c_settle", null);
		Log.d("zhang.h", "result size:" + c.getCount());
		while(c.moveToNext()){
			Log.d("zhang.h", "settleUUID = " + c.getString(c.getColumnIndex("settleUUID")) + 
					" | settleDate = " + c.getString(c.getColumnIndex("settleDate")) +
					" | count = " + c.getString(c.getColumnIndex("count")) +
					" | money = " + c.getString(c.getColumnIndex("money")));
		}
    }

	private List<Order> fetchData() {
		Order order = null;
		List<Order> data = new ArrayList<Order>();
		Cursor c = db.rawQuery("select settleDate, sum(count) as totalCount,sum(money) as totalMoney from c_settle group by settleDate",null);
		Log.d("zhang.h", "cursor size===========" + c.getCount());
		while(c.moveToNext()){
			order = new Order();
			order.setOrderDate(c.getString(c.getColumnIndex("settleDate")));
			order.setOrderCount(c.getString(c.getColumnIndex("totalCount")));
			order.setOrderMoney(c.getString(c.getColumnIndex("totalMoney")));
			data.add(order);
		}
		c.close();
		return data;
	}
}
