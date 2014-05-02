package cn.burgeon.core.ui.sales;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import cn.burgeon.core.R;
import cn.burgeon.core.adapter.SalesSettleAdapter;
import cn.burgeon.core.bean.IntentData;
import cn.burgeon.core.bean.Product;
import cn.burgeon.core.bean.Settle;
import cn.burgeon.core.ui.BaseActivity;

public class SalesSettleActivity extends BaseActivity {

	ListView mListView;
	Button settleBtn;
	TextView payTV, counTV;
	EditText orginET, disCounET, realityET;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setupFullscreen();
		setContentView(R.layout.activity_sales_settle);

		init();
		
		settle();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		finish();
	}
	
	float pay = 0;
	int count = 0;
	int discount = 0;

	private void settle() {
		IntentData iData = (IntentData) getIntent().getParcelableExtra(PAR_KEY);
		ArrayList<Product> products = iData.getProducts();
		for(Product pro : products){
			pay += Float.parseFloat(pro.getMoney());
			count += Integer.parseInt(pro.getCount());
		}
		payTV.setText(String.format(getResources().getString(R.string.sales_settle_pay),String.valueOf(pay)));
		counTV.setText(String.format(getResources().getString(R.string.sales_settle_count),count));
		Log.d("zhang.h", ""+pay);
		realityET.setText(String.valueOf(pay));
		disCounET.setText(String.valueOf(discount));
		List<Settle> list = new ArrayList<Settle>();
		list.add(new Settle(getResources().getString(R.string.sales_settle_cash), String.valueOf(pay)));
		mListView.setAdapter(new SalesSettleAdapter(list, this));
	}

	private void init() {
		settleBtn = (Button) findViewById(R.id.settleJiezhangBtn);
		settleBtn.setOnClickListener(onClickListener);
		payTV = (TextView) findViewById(R.id.salesSettlePay);
		counTV = (TextView) findViewById(R.id.salesSettleCount);
		orginET = (EditText) findViewById(R.id.salesSettleOrginET);
		disCounET = (EditText) findViewById(R.id.salesSettleDiscountET);
		realityET = (EditText) findViewById(R.id.salesSettleRealityET);
		mListView = (ListView) findViewById(R.id.settleLV);
	}
	
	View.OnClickListener onClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			save();
			forwardActivity(DailySalesActivity.class);
		}
	};
	
/*	db.execSQL("CREATE TABLE IF NOT EXISTS c_settle" +  
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, orderno VARCHAR,"+
			"settleTime VARCHAR, type VARCHAR,count INTEGER,money VARCHAR,"
			+ "orderEmployee VARCHAR,"+
            "status VARCHAR)");*/
	
	public void save(){
		startProgressDialog();
		db.beginTransaction();
        try {
        	Date currentTime = new Date();
        	db.execSQL("insert into c_settle('settleTime','type','count','money','orderEmployee',"
        			+ "'status','settleDate','settleMonth')"+
        				" values(?,?,?,?,?,?,?,?)",
					new Object[]{new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentTime),
								getResources().getString(R.string.sales_settle_novip),
								count,
								realityET.getText(),
								"",
								getResources().getString(R.string.sales_settle_noup),
								new SimpleDateFormat("yyyy-MM-dd").format(currentTime),
								new SimpleDateFormat("yyyy-MM-dd").format(currentTime).substring(0, 7)});
//        	new SimpleDateFormat("yyyy-MM-dd").format(currentTime).substring(0, 7)
            db.setTransactionSuccessful();
            Thread.sleep(500);
        } catch(Exception e){}
        finally {  
            db.endTransaction();
        } 
        stopProgressDialog();
	}

}
