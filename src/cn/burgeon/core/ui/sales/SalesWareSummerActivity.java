package cn.burgeon.core.ui.sales;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import cn.burgeon.core.R;
import cn.burgeon.core.adapter.SalesWareSummerAdapter;
import cn.burgeon.core.bean.Order;
import cn.burgeon.core.ui.BaseActivity;

public class SalesWareSummerActivity extends BaseActivity {
	
	ListView mList;
	SalesWareSummerAdapter mAdapter;
	Button btnSearch, btnQuery;
	TextView commonRecordnum,commonCount,commonMoney;
	EditText starDateET,endDateET;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFullscreen();
        setContentView(R.layout.activity_sales_ware_summer);

        init();
        bindList();
    }
    
    private void bindList() {
    	List<Order> data = fetchData();
    	mAdapter = new SalesWareSummerAdapter(data, this);
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
		starDateET = (EditText) findViewById(R.id.sales_waresummer_starttime);
		endDateET = (EditText) findViewById(R.id.sales_waresummer_endtime);
		starDateET.setOnClickListener(onClickListener);
		endDateET.setOnClickListener(onClickListener);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -1);
		starDateET.setText(sdf.format(c.getTime()));
		endDateET.setText(sdf.format(new Date()));
        commonRecordnum = (TextView) findViewById(R.id.sales_common_recordnum);
        commonCount = (TextView) findViewById(R.id.sales_common_count);
        commonMoney = (TextView) findViewById(R.id.sales_common_money);
    	mList = (ListView) findViewById(R.id.salesWareSummerLV);
    	btnSearch = (Button) findViewById(R.id.sales_ware_summer_minxibtn);
    	btnQuery = (Button) findViewById(R.id.sales_ware_summer_query);
    	btnQuery.setOnClickListener(onClickListener);
    	btnSearch.setOnClickListener(onClickListener);
    }

	private List<Order> fetchData() {
		Order order = null;
		List<Order> data = new ArrayList<Order>();
		Cursor c = db.rawQuery("select pdtcode, pdtname,sum(count) as totalCount,sum(money) as totalMoney "
				+ "from c_settle_detail  where settleDate between '"+starDateET.getText().toString()+"' and '"+endDateET.getText().toString()+"' group by pdtcode",
				null);
		Log.d("zhang.h", "cursor size===========" + c.getCount());
		while(c.moveToNext()){
			order = new Order();
			order.setTiaoMa(c.getString(c.getColumnIndex("pdtcode")));
			order.setName(c.getString(c.getColumnIndex("pdtname")));
			order.setOrderCount(c.getString(c.getColumnIndex("totalCount")));
			order.setOrderMoney(c.getString(c.getColumnIndex("totalMoney")));
			data.add(order);
		}
		c.close();
		return data;
	}
	
	Calendar c = Calendar.getInstance();
	
	OnClickListener onClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
	        case R.id.sales_waresummer_starttime:
	            int startmYear = c.get(Calendar.YEAR);
	            int startmMonth = c.get(Calendar.MONTH);
	            int startmDay = c.get(Calendar.DAY_OF_MONTH);
	            DatePickerDialog startdialog = new DatePickerDialog(SalesWareSummerActivity.this, new startmDateSetListener(), startmYear, startmMonth, startmDay);
	            startdialog.show();
	            break;
	        case R.id.sales_waresummer_endtime:
	            int endmYear = c.get(Calendar.YEAR);
	            int endmMonth = c.get(Calendar.MONTH);
	            int endmDay = c.get(Calendar.DAY_OF_MONTH);
	            DatePickerDialog enddialog = new DatePickerDialog(SalesWareSummerActivity.this, new endmDateSetListener(), endmYear, endmMonth, endmDay);
	            enddialog.show();
	            break;
            case R.id.sales_ware_summer_minxibtn:

	            break;
            case R.id.sales_ware_summer_query:
            	bindList();
	            break;
			}
		}
	};
	
    class startmDateSetListener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            int mYear = year;
            int mMonth = monthOfYear;
            int mDay = dayOfMonth;
            // Month is 0 based so add 1

            String month = String.valueOf(mMonth + 1).length() == 2 ? String.valueOf(mMonth + 1) : "0" + String.valueOf(mMonth + 1);
            String day = String.valueOf(mDay).length() == 2 ? String.valueOf(mDay) : "0" + String.valueOf(mDay);
            starDateET.setText(new StringBuilder().append(mYear).append('-').append(month).append('-').append(day));
        }
    }

    class endmDateSetListener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            int mYear = year;
            int mMonth = monthOfYear;
            int mDay = dayOfMonth;
            // Month is 0 based so add 1

            String month = String.valueOf(mMonth + 1).length() == 2 ? String.valueOf(mMonth + 1) : "0" + String.valueOf(mMonth + 1);
            String day = String.valueOf(mDay).length() == 2 ? String.valueOf(mDay) : "0" + String.valueOf(mDay);
            endDateET.setText(new StringBuilder().append(mYear).append('-').append(month).append('-').append(day));
        }
    }
}
