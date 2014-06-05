package cn.burgeon.core.ui.check;

import android.database.Cursor;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.burgeon.core.App;
import cn.burgeon.core.R;
import cn.burgeon.core.adapter.CheckQueryLVAdapter;
import cn.burgeon.core.bean.Order;
import cn.burgeon.core.ui.BaseActivity;
import cn.burgeon.core.utils.PreferenceUtils;
import cn.burgeon.core.utils.ScreenUtils;

public class CheckDocManagerActivity extends BaseActivity {

	private TextView totalOutCountTV,recordCountTV;
    CheckQueryLVAdapter mAdapter;
    ListView mList;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_doc_manager);

        init();

        initLVData();
    }

	private void initLVData() {
		List<Order> data = fetchData();
		mAdapter = new CheckQueryLVAdapter(this, data,R.layout.check_query_item);
		mList.setAdapter(mAdapter);
		if(data.size() > 0)
			upateBottomBarInfo(data);
	}
	
	private void upateBottomBarInfo(List<Order> data) {
		int count = 0;
		for(Order pro : data){
			count += Integer.parseInt(pro.getOrderCount());
		}
		recordCountTV.setText(String.format(getResources().getString(R.string.sales_new_common_record), data.size()));
	}

	private List<Order> fetchData() {
		Order order = null;
		List<Order> data = new ArrayList<Order>();
		Cursor c = db.rawQuery("select * from c_check", null);
		while (c.moveToNext()) {
			order = new Order();
            order.setOrderDate(c.getString(c.getColumnIndex("checkTime")));
            order.setOrderNo(c.getString(c.getColumnIndex("checkno")));
            order.setOrderCount(c.getString(c.getColumnIndex("count")));
            order.setOrderType(c.getString(c.getColumnIndex("type")));
            order.setSaleAsistant(c.getString(c.getColumnIndex("orderEmployee")));
            order.setOrderState(c.getString(c.getColumnIndex("status")));
            order.setIsChecked(c.getString(c.getColumnIndex("isChecked")));
			data.add(order);
		}
		if (c != null && !c.isClosed())
			c.close();
		return data;
	}

	private void init() {
		// 初始化门店信息
		TextView storeTV = (TextView) findViewById(R.id.storeTV);
		storeTV.setText(App.getPreferenceUtils().getPreferenceStr(
				PreferenceUtils.store_key));

		TextView currTimeTV = (TextView) findViewById(R.id.currTimeTV);
		currTimeTV.setText(getCurrDate());

		HorizontalScrollView hsv = (HorizontalScrollView) findViewById(R.id.hsv);
		ViewGroup.LayoutParams params = hsv.getLayoutParams();
		params.height = (int) ScreenUtils.getAllotInLVHeight(this);

		mList = (ListView) findViewById(R.id.docManagerLV);
		recordCountTV = (TextView) findViewById(R.id.recordCountTV);
		totalOutCountTV = (TextView) findViewById(R.id.totalOutCountTV);
	}
}
