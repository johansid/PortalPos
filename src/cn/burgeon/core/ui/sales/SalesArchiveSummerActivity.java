package cn.burgeon.core.ui.sales;

import java.util.ArrayList;
import java.util.List;

import cn.burgeon.core.R;
import cn.burgeon.core.adapter.SalesArchiveSummerAdapter;
import cn.burgeon.core.adapter.SalesDailyAdapter;
import cn.burgeon.core.adapter.SalesOrderSearchAdapter;
import cn.burgeon.core.adapter.SalesWareSummerAdapter;
import cn.burgeon.core.bean.Order;
import cn.burgeon.core.ui.BaseActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

public class SalesArchiveSummerActivity extends BaseActivity {
	
	ListView mList;
	SalesArchiveSummerAdapter mAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFullscreen();
        setContentView(R.layout.activity_sales_archivement_summer);

        init();
        bindList();
    }
    
    private void bindList() {
    	List<Order> data = fetchData();
    	mAdapter = new SalesArchiveSummerAdapter(data, this);
	}

	private void init(){
    	mList = (ListView) findViewById(R.id.salesArchiveSummerLV);
    	mList.setAdapter(mAdapter);
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
