package cn.burgeon.core.ui.sales;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cn.burgeon.core.App;
import cn.burgeon.core.R;
import cn.burgeon.core.adapter.SalesDailyAdapter;
import cn.burgeon.core.bean.Order;
import cn.burgeon.core.bean.Product;
import cn.burgeon.core.ui.BaseActivity;
import cn.burgeon.core.ui.QueryDialog;
import cn.burgeon.core.utils.PreferenceUtils;
import cn.burgeon.core.utils.ScreenUtils;
import android.content.res.Resources.NotFoundException;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.TextView;

public class DailySalesActivity extends BaseActivity {
	
	private static final String TAG = "DailySalesActivity";
	TextView recodeNumTV;
	ListView mList;
	SalesDailyAdapter mAdapter;
	Button btnAdd, btnUpdate, btnSearch, btnDelete;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFullscreen();
        setContentView(R.layout.activity_sales_manager_dailysales);

        init();
        bindList();
    }
    
    @Override
    protected void onStop() {
    	// TODO Auto-generated method stub
    	super.onStop();
    	finish();
    }
    
    private void bindList() {
    	List<Order> data = fetchData();
    	mAdapter = new SalesDailyAdapter(data, this);
    	mList.setAdapter(mAdapter);
    	
    	recodeNumTV.setText(String.format(getResources().getString(R.string.sales_new_common_record),data.size())); 
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
        
        recodeNumTV = (TextView) findViewById(R.id.recodeNumTV);
    	mList = (ListView) findViewById(R.id.dailySalesLV);
    	mList.setOnItemClickListener(onItemClickListener);
    	btnAdd = (Button) findViewById(R.id.sales_daily_btn_add);
    	btnUpdate = (Button) findViewById(R.id.sales_daily_btn_update);
    	btnSearch = (Button) findViewById(R.id.sales_daily_btn_search);
    	btnDelete = (Button) findViewById(R.id.sales_daily_btn_delete);
    	btnAdd.setOnClickListener(onClickListener);
    	btnUpdate.setOnClickListener(onClickListener);
    	btnSearch.setOnClickListener(onClickListener);
    	btnDelete.setOnClickListener(onClickListener);
    }
	
	View.OnClickListener onClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.sales_daily_btn_add:
				forwardActivity(SalesNewOrderActivity.class);
				break;
			case R.id.sales_daily_btn_update:
				if(currentSelectedOrder != null)
					forwardActivity(SalesNewOrderActivity.class,"updateID",currentSelectedOrder.getUuid());
				break;
			case R.id.sales_daily_btn_search:
				//queryForUpdate();
				//QueryDialog dialog = new QueryDialog(DailySalesActivity.this, R.style.QueryDialog);
				//dialog.show();
				new Thread(new Runnable() {
					@Override
					public void run() {
						createSku();
					}
				}).start();
				new Thread(new Runnable() {
					@Override
					public void run() {
						createStyle();
					}
				}).start();
				new Thread(new Runnable() {
					@Override
					public void run() {
						createtdefclr();
					}
				}).start();
				new Thread(new Runnable() {
					@Override
					public void run() {
						createtdefsize();
					}
				}).start();
				
				new Thread(new Runnable() {
					@Override
					public void run() {
						createstyleprice();
					}
				}).start();
				break;
			case R.id.sales_daily_btn_delete:
				//delete();
				queryForUpdate();
				//querySku();
				break;
			default:
				break;
			}
		}
	};
	
    private void querySku() {
		Cursor c = db.rawQuery("select * from tc_styleprice", null);
		Log.d("zhang.h", "result size:" + c.getCount());
    }
    
    private void createStyle(){
    	InputStream is = null;
		BufferedReader br = null;
		try{
			String line = null;
			is = getResources().openRawResource(R.raw.tc_style);
			br = new BufferedReader(new InputStreamReader(is));
			while((line = br.readLine()) != null){
				
				String[] temp = line.split(",");
				Log.d(TAG, "sytle_name:" + temp[1].substring(2));
				db.execSQL("insert into tc_style(style,style_name,attrib1,attrib2,attrib3,attrib4,attrib5,attrib6,attrib7,attrib8,attrib9,attrib10) "
						+ "values (?,?,?,?,?,?,?,?,?,?,?,?)", 
						new Object[]{temp[0],temp[1].substring(2),temp[2].substring(2),
						temp[3].substring(2),temp[4].substring(2),temp[5].substring(2)
						,temp[6].substring(2),temp[7].substring(2),temp[8].substring(2)
						,temp[9].substring(2),temp[10].substring(2),temp[11].substring(2)});
			}
		}catch(Exception e){}
		finally{
			try {
				br.close();
				is.close();
			} catch (IOException e) {
			}
		}
		Log.d(TAG, "CREATE TABLE STYLE DONE...");
    }
    
    private void createtdefclr(){
    	InputStream is = null;
		BufferedReader br = null;
		try{
			String line = null;
			is = getResources().openRawResource(R.raw.tdefclr);
			br = new BufferedReader(new InputStreamReader(is));
			while((line = br.readLine()) != null){
				String[] temp = line.split(",");
				db.execSQL("insert into tdefclr(clr,clrname) values (?,?)", 
						new Object[]{temp[0],temp[1].substring(2)});
			}
		}catch(Exception e){}
		finally{
			try {
				br.close();
				is.close();
			} catch (IOException e) {
			}
		}
		Log.d(TAG, "CREATE TABLE tdefclr DONE...");
    }
    
    private void createstyleprice(){
    	InputStream is = null;
		BufferedReader br = null;
		try{
			String line = null;
			is = getResources().openRawResource(R.raw.tc_styleprice);
			br = new BufferedReader(new InputStreamReader(is));
			while((line = br.readLine()) != null){
				String[] temp = line.split(",");
				db.execSQL("insert into tc_styleprice(style,store,fprice) values (?,?,?)", 
						new Object[]{temp[0],temp[1].substring(2),temp[2].substring(2)});
			}
		}catch(Exception e){}
		finally{
			try {
				br.close();
				is.close();
			} catch (IOException e) {
			}
		}
		Log.d(TAG, "CREATE TABLE tc_styleprice DONE...");
    }
	
	private void createSku(){
		InputStream is = null;
		BufferedReader br = null;
		try {
			is = getResources().openRawResource(R.raw.tc_sku);
			br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while((line = br.readLine()) != null){
				String[] temp = line.split(",");
				db.execSQL("insert into tc_sku(sku,style,clr,sizeid,pname) values (?,?,?,?,?)", 
						new Object[]{temp[0],temp[1].substring(2),temp[2].substring(2),
						temp[3].substring(2),temp[4].substring(2)});
			}
		} catch (Exception e) {}
		finally{
			try {
				br.close();
				is.close();
			} catch (IOException e) {
			}
		}
		Log.d(TAG, "CREATE TABLE SKU DONE...");
	}
	
    private void createtdefsize(){
    	InputStream is = null;
		BufferedReader br = null;
		try{
			String line = null;
			is = getResources().openRawResource(R.raw.tdefsize);
			br = new BufferedReader(new InputStreamReader(is));
			while((line = br.readLine()) != null){
				String[] temp = line.split(",");
				db.execSQL("insert into tdefsize(sizeid,sizename) values (?,?)", 
						new Object[]{temp[0],temp[1].substring(2)});
			}
		}catch(Exception e){}
		finally{
			try {
				br.close();
				is.close();
			} catch (IOException e) {
			}
		}
		Log.d(TAG, "CREATE TABLE tdefsize DONE...");
    }
    
    private void queryForUpdate() {
		try {
			Cursor c = db.rawQuery("select * from c_settle_detail", null);
			Log.d("zhang.h", "result size:" + c.getCount());
			while(c.moveToNext()){
				Log.d("zhang.h", "settleUUID = " + c.getString(c.getColumnIndex("settleUUID")) + 
						" | price = " + c.getString(c.getColumnIndex("price")) +
						" | discount = " + c.getString(c.getColumnIndex("discount")) + 
						" | count = " + c.getString(c.getColumnIndex("count")) +
						" | name = " + c.getString(c.getColumnIndex("pdtname")) +
						" | settleDate = " + c.getString(c.getColumnIndex("settleDate")) +
						" | money = " + c.getString(c.getColumnIndex("money")));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	private void delete() {
		if(currentSelectedOrder != null){new String();
			db.execSQL("delete from c_settle where settleUUID = ?", new Object[]{currentSelectedOrder.getUuid()});
			db.execSQL("delete from c_settle_detail where settleUUID = ?", new Object[]{currentSelectedOrder.getUuid()});
		}
		bindList();
	}
	
	private List<Order> fetchData() {
		Order order = null;
		List<Order> data = new ArrayList<Order>();
		Cursor c = db.rawQuery("select * from c_settle", null);
		Log.d("zhang.h", "cursor size===========" + c.getCount());
		while(c.moveToNext()){
			order = new Order();
			order.setId(c.getInt(c.getColumnIndex("_id")));
			order.setUuid(c.getString(c.getColumnIndex("settleUUID")));
			order.setOrderNo(c.getString(c.getColumnIndex("orderno")));
			order.setOrderDate(c.getString(c.getColumnIndex("settleTime")));
			order.setOrderType(c.getString(c.getColumnIndex("type")));
			order.setOrderCount(c.getString(c.getColumnIndex("count")));
			order.setOrderMoney(c.getString(c.getColumnIndex("money")));
			order.setOrderState(c.getString(c.getColumnIndex("status")));
			order.setSaleAsistant(c.getString(c.getColumnIndex("orderEmployee")));
			data.add(order);
		}
		return data;
	}
	
	Order currentSelectedOrder;
	View previous;
	int selectedPosition;
	
	OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long arg3) {
			
			if(previous != null) previous.setBackgroundDrawable(view.getBackground());
			view.setBackgroundResource(R.drawable.button_bg);
			previous = view;
			currentSelectedOrder = (Order) parent.getItemAtPosition(position);
		}
	};
}
