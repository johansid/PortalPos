package cn.burgeon.core.ui.sales;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import cn.burgeon.core.App;
import cn.burgeon.core.R;
import cn.burgeon.core.adapter.SalesSettleAdapter;
import cn.burgeon.core.bean.IntentData;
import cn.burgeon.core.bean.Product;
import cn.burgeon.core.bean.Settle;
import cn.burgeon.core.ui.BaseActivity;
import cn.burgeon.core.utils.PreferenceUtils;

public class SalesSettleActivity extends BaseActivity {

	ListView mListView;
	Button settleBtn;
	TextView payTV, counTV;
	EditText orginET, disCounET, realityET;
	ArrayList<Product> products;
	String command;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupFullscreen();
		setContentView(R.layout.activity_sales_settle);

		init();
		
		settle();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		finish();
	}
	
	float pay = 0;
	int count = 0;
	int discount = 0;

	private void settle() {
		IntentData iData = (IntentData) getIntent().getParcelableExtra(PAR_KEY);
		products = iData.getProducts();
		command = iData.getCommand();
		Log.d("zhang.h", "command=" + command);
		for(Product pro : products){
			pay += Float.parseFloat(pro.getMoney());
			count += Integer.parseInt(pro.getCount());
		}
		payTV.setText(String.format(getResources().getString(R.string.sales_settle_pay),String.valueOf(pay)));
		counTV.setText(String.format(getResources().getString(R.string.sales_settle_count),count));
		
		realityET.setText(String.valueOf(pay));
		disCounET.setText(String.valueOf(discount));
		List<Settle> list = new ArrayList<Settle>();
		list.add(new Settle(getResources().getString(R.string.sales_settle_cash), String.valueOf(pay)));
		mListView.setAdapter(new SalesSettleAdapter(list, this));
	}

	private void init() {
        // 初始化门店信息
        TextView storeTV = (TextView) findViewById(R.id.storeTV);
        storeTV.setText(App.getPreferenceUtils().getPreferenceStr(PreferenceUtils.store_key));

        TextView currTimeTV = (TextView) findViewById(R.id.currTimeTV);
        currTimeTV.setText(getCurrDate());
        
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
			if("unknow".equals(command))
				save();
			else
				update();
			forwardActivity(DailySalesActivity.class);
		}
	};
	
	public void save(){
		db.beginTransaction();
        try {
        	String uuid = UUID.randomUUID().toString();
        	Date currentTime = new Date();
        	db.execSQL("insert into c_settle('settleTime','type','count','money','employeeID','orderEmployee',"
        			+ "'status','settleDate','settleMonth','settleUUID')"+
        				" values(?,?,?,?,?,?,?,?,?,?)",
					new Object[]{new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentTime),
								getResources().getString(R.string.sales_settle_novip),
								count,
								realityET.getText(),
								"0001",
								"test",
								getResources().getString(R.string.sales_settle_noup),
								new SimpleDateFormat("yyyy-MM-dd").format(currentTime),
								new SimpleDateFormat("yyyy-MM-dd").format(currentTime).substring(0, 7),
								uuid});
        	for(Product pro : products){
        		db.execSQL("insert into c_settle_detail('barcode','price','discount'"
        				+ ",'count','money','settleUUID','pdtname','color','size','settleDate')"
        				+ " values(?,?,?,?,?,?,?,?,?,?)",
    					new Object[]{pro.getBarCode(),pro.getPrice(), pro.getDiscount(),
        						pro.getCount(), pro.getMoney(), uuid, pro.getName(),
        						pro.getColor(),pro.getSize(),new SimpleDateFormat("yyyy-MM-dd").format(currentTime)});
        	}
            db.setTransactionSuccessful();
        } catch(Exception e){}
        finally {  
            db.endTransaction();
        } 
	}
	
	public void update(){
		startProgressDialog();
		db.beginTransaction();
        try {
        	Date currentTime = new Date();
        	db.execSQL("update c_settle set 'settleTime' = ?,'type' = ?,'count' = ?,"
        			+ "'money' = ?,"
        			+ "'status' = ?,'settleDate' = ?,'settleMonth' = ? "
        			+ " where settleUUID = ?",
					new Object[]{new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentTime),
								getResources().getString(R.string.sales_settle_novip),
								count,
								realityET.getText(),
								getResources().getString(R.string.sales_settle_noup),
								new SimpleDateFormat("yyyy-MM-dd").format(currentTime),
								new SimpleDateFormat("yyyy-MM-dd").format(currentTime).substring(0, 7),
								command});
        	
        	for(Product pro : products){
        		if(pro.getUuid() != null){
	        		db.execSQL("update c_settle_detail "
	        				+ "set 'price' = ?,'discount' = ?,'settleDate' = ?,"
	        				+ "'count' = ?,'money' = ? where settleUUID = ?",
	    					new Object[]{pro.getPrice(), pro.getDiscount(), 
	        						new SimpleDateFormat("yyyy-MM-dd").format(currentTime),
	        						pro.getCount(), pro.getMoney(), command});
        		}else{
            		db.execSQL("insert into c_settle_detail('barcode','price','discount'"
            				+ ",'count','money','settleUUID','pdtname','color','size','settleDate')"
            				+ " values(?,?,?,?,?,?,?,?,?,?)",
        					new Object[]{pro.getBarCode(),pro.getPrice(), pro.getDiscount(),
            						pro.getCount(), pro.getMoney(), command, pro.getName(),
            						pro.getColor(),pro.getSize(),new SimpleDateFormat("yyyy-MM-dd").format(currentTime)});
        		}
        	}
            db.setTransactionSuccessful();
            Thread.sleep(500);
        } catch(Exception e){}
        finally {  
            db.endTransaction();
        } 
        stopProgressDialog();
	}

}
