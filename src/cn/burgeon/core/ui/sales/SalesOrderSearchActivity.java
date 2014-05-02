package cn.burgeon.core.ui.sales;

import java.util.ArrayList;
import java.util.List;

import cn.burgeon.core.App;
import cn.burgeon.core.R;
import cn.burgeon.core.adapter.SalesDailyAdapter;
import cn.burgeon.core.adapter.SalesOrderSearchAdapter;
import cn.burgeon.core.bean.Order;
import cn.burgeon.core.ui.BaseActivity;
import cn.burgeon.core.utils.PreferenceUtils;
import cn.burgeon.core.utils.ScreenUtils;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.TextView;

public class SalesOrderSearchActivity extends BaseActivity {
	
	ListView mList;
	SalesOrderSearchAdapter mAdapter;
	Button btnViewDetail, btnSearch;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFullscreen();
        setContentView(R.layout.activity_sales_order_search);

        init();
        bindList();
    }
    
    private void bindList() {
    	List<Order> data = fetchData();
    	mAdapter = new SalesOrderSearchAdapter(data, this);
    	mList.setAdapter(mAdapter);
	}

	private void init(){
        // 初始化门店信息
        TextView storeTV = (TextView) findViewById(R.id.storeTV);
        storeTV.setText(App.getPreferenceUtils().getPreferenceStr(PreferenceUtils.store_key));

        TextView currTimeTV = (TextView) findViewById(R.id.currTimeTV);
        currTimeTV.setText(getCurrDate());

        HorizontalScrollView hsv = (HorizontalScrollView) findViewById(R.id.hsv);
        ViewGroup.LayoutParams params = hsv.getLayoutParams();
        params.height = (int) ScreenUtils.getAllotInLVHeight(this);
    	mList = (ListView) findViewById(R.id.salesOrderLV);
    	btnViewDetail = (Button) findViewById(R.id.sales_daily_btn_add);
    	btnSearch = (Button) findViewById(R.id.sales_daily_btn_search);
    }

	private List<Order> fetchData() {
		Order order = null;
		List<Order> data = new ArrayList<Order>();
		Cursor c = db.rawQuery("select * from c_settle", null);
		Log.d("zhang.h", "cursor size===========" + c.getCount());
		while(c.moveToNext()){
			order = new Order();
			order.setOrderNo(c.getString(c.getColumnIndex("orderno")));
			order.setOrderDate(c.getString(c.getColumnIndex("settleTime")));
			order.setOrderType(c.getString(c.getColumnIndex("type")));
			order.setOrderCount(c.getString(c.getColumnIndex("count")));
			order.setOrderMoney(c.getString(c.getColumnIndex("money")));
			order.setSaleAsistant(c.getString(c.getColumnIndex("orderEmployee")));
			data.add(order);
		}
		return data;
	}
}
