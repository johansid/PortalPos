package cn.burgeon.core.ui.check;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import cn.burgeon.core.App;
import cn.burgeon.core.R;
import cn.burgeon.core.adapter.CheckScanLVAdapter;
import cn.burgeon.core.adapter.SalesNewOrderAdapter;
import cn.burgeon.core.bean.CheckScan;
import cn.burgeon.core.bean.Product;
import cn.burgeon.core.ui.BaseActivity;
import cn.burgeon.core.utils.PreferenceUtils;
import cn.burgeon.core.utils.ScreenUtils;

import com.android.volley.Response;

public class CheckScanActivity extends BaseActivity{
	
	private final String TAG = "CheckScanActivity";
	List<Product> data = new ArrayList<Product>();

	private ListView checkscanLV;
	private TextView recodeNumTV, totalCountTV;
	private EditText barcodeET,shelfET;
	private Button gatherBtn,reviewBtn;
	CheckScanLVAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_scan);

		init();

	}

	private void init() {
		// 初始化门店信息
		TextView storeTV = (TextView) findViewById(R.id.storeTV);
		storeTV.setText(App.getPreferenceUtils().getPreferenceStr(PreferenceUtils.store_key));

		TextView currTimeTV = (TextView) findViewById(R.id.currTimeTV);
		currTimeTV.setText(getCurrDate());
		shelfET = (EditText) findViewById(R.id.shelfET);
		barcodeET = (EditText) findViewById(R.id.barcodeET);
		barcodeET.setOnEditorActionListener(editorActionListener);

		HorizontalScrollView hsv = (HorizontalScrollView) findViewById(R.id.hsv);
		ViewGroup.LayoutParams params = hsv.getLayoutParams();
		params.height = (int) ScreenUtils.getAllotInLVHeight(this);

		checkscanLV = (ListView) findViewById(R.id.checkscanLV);
		mAdapter = new CheckScanLVAdapter(data, this);
		checkscanLV.setAdapter(mAdapter);
		recodeNumTV = (TextView) findViewById(R.id.recodeNumTV);
		totalCountTV = (TextView) findViewById(R.id.totalCountTV);
		
		gatherBtn = (Button) findViewById(R.id.gatherBtn);
		reviewBtn = (Button) findViewById(R.id.reviewBtn);
		gatherBtn.setOnClickListener(clickListener);
		reviewBtn.setOnClickListener(clickListener);
		
		
	}

	private void verifyBarCode() {
		//从本地获取
		varLocal();
		//从网络获取
		//varNet();
	}
	
	private void varLocal() {
		Log.d(TAG, "=========varLocal========");
		String sql = "select b.style_name,c.clrname,d.sizename,e.fprice"
					+" from tc_sku as a"
					+" left join tc_style as b"
					+" on a.style = b.style"
					+" left join tdefclr as c"
					+" on a.clr = c.clr"
					+" left join tdefsize as d"
					+" on a.sizeid = d.sizeid"
					+" left join tc_styleprice as e"
					+" on a.style = e.style"
					+" where a.sku = ?";
		Cursor c = db.rawQuery(sql, new String[]{barcodeET.getText().toString()});
		Log.d(TAG, "result size = " + c.getCount());
		if(c.moveToFirst()){
			List<Product> list = parseSQLResult(c);
			data.addAll(list);
			mAdapter.notifyDataSetChanged();
			upateBottomBarInfo();
		}
	}
	
	private void upateBottomBarInfo() {
		int count = 0;
		for(Product pro : data){
			count += Integer.parseInt(pro.getCount());
		}
		totalCountTV.setText(String.format(getResources().getString(R.string.sales_new_common_count), count));
		recodeNumTV.setText(String.format(getResources().getString(R.string.sales_new_common_record), data.size()));
	}
	
	private List<Product> parseSQLResult(Cursor c) {
		List<Product> items = new ArrayList<Product>(1);
		Product pro = new Product();
		pro.setBarCode(barcodeET.getText().toString());
		pro.setName(c.getString(c.getColumnIndex("style_name")));
		pro.setPrice(c.getString(c.getColumnIndex("fprice")));
		pro.setColor(c.getString(c.getColumnIndex("clrname")));
		pro.setSize(c.getString(c.getColumnIndex("sizename")));
		pro.setDiscount("0");
		pro.setCount("1");
		items.add(pro);
		return items;
	}
	
	OnEditorActionListener editorActionListener = new OnEditorActionListener(){

		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			switch (actionId) {
			case EditorInfo.IME_ACTION_SEARCH:
				verifyBarCode();
				break;

			default:
				break;
			}
			return true;
		}
		
	};
	
	OnClickListener clickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.gatherBtn:
				
				break;
			case R.id.reviewBtn:
				showTips();
				break;
			default:
				break;
			}
		}
	};
	
    //显示对话框
    private void showTips(){
    	AlertDialog dialog = null;
    	AlertDialog.Builder builder = new AlertDialog.Builder(this)
    		.setTitle(getString(R.string.systemtips))
    		.setMessage(R.string.checktips)
    		.setPositiveButton(getString(R.string.confirm),new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					reviewShelf();
				}})
			.setNegativeButton(getString(R.string.cancel),new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}});
    	dialog = builder.create();
    	dialog.show();
    }
    
	private void reviewShelf() {
		db.beginTransaction();
        try {
        	String uuid = UUID.randomUUID().toString();
        	Date currentTime = new Date();
        	db.execSQL("insert into c_check('shelf','checkTime','type','count','employeeID','orderEmployee',"
        			+ "'status','checkUUID')"+
        				" values(?,?,?,?,?,?,?,?)",
					new Object[]{shelfET.getText().toString(),
        						new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentTime),
								getResources().getString(R.string.sales_settle_novip),
								data.size(),//数量count
								"0001",
								"test",
								getResources().getString(R.string.sales_settle_noup),
								uuid});
        	for(Product pro : data){
        		db.execSQL("insert into c_check_detail('checkUUID','barcode','count','color','size','pdtname') values (?,?,?,?,?,?)",
    					new Object[]{uuid,pro.getBarCode(),pro.getCount(),pro.getColor(),pro.getSize(),pro.getName()});
        	}
            db.setTransactionSuccessful();
        } catch(Exception e){}
        finally {  
            db.endTransaction();
        } 
	}
	
	/*@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.okBtn:
			
			 * { "table":"12668", "columns":[ "NO", "M_PRODUCT_ID;COLORS", "M_PRODUCT_ID;SIZES", "M_PRODUCT_ID;PRICELIST",
			 * "M_PRODUCT_ID;NAME" ], "params":{"column":"NO","condition":"109454D334620"} }
			 
			Map<String, String> params = new HashMap<String, String>();
			JSONArray array = new JSONArray();
			try {
				JSONObject transactions = new JSONObject();
				transactions.put("id", 112);
				transactions.put("command", "Query");

				JSONObject paramsTable = new JSONObject();
				paramsTable.put("table", "12668");
				paramsTable.put("columns",
						new JSONArray().put("NO").put("M_PRODUCT_ID;COLORS").put("M_PRODUCT_ID;SIZES").put("M_PRODUCT_ID;NAME"));
				JSONObject paramsCondition = new JSONObject();
				paramsCondition.put("column", "NO");
				paramsCondition.put("condition", barcodeET.getText().toString());
				paramsTable.put("params", paramsCondition);

				transactions.put("params", paramsTable);
				array.put(transactions);
				params.put("transactions", array.toString());

				sendRequest(params, new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						// 取消进度条
						stopProgressDialog();

						try {
							JSONArray resJA = new JSONArray(response);
							JSONObject resJO = resJA.getJSONObject(0);
							JSONArray rowsJA = resJO.getJSONArray("rows");
							int len = rowsJA.length();

							for (int i = 0; i < len; i++) {
								// ["109454D334620","1596","1634,1635,1636,1639,1640,1638","109454D334"]
								String currRow = rowsJA.get(i).toString();
								String[] currRows = currRow.split("\",");

								CheckScan checkScan = new CheckScan();
								checkScan.setBarcode(currRows[0].substring(2, currRows[0].length()));
								checkScan.setColor(currRows[1].substring(1, currRows[1].length()));
								checkScan.setSize(currRows[2].substring(1, currRows[2].length()));
								checkScan.setStyle(currRows[3].substring(1, currRows[3].length() - 2));
								checkScan.setNum("1");
								lists.add(checkScan);
							}

							// 记录数
							recodeNumTV.setText("记录数：" + lists.size());
							int num = 0;
							for (CheckScan checkScan : lists) {
								num += Integer.valueOf(checkScan.getNum());
							}
							totalCountTV.setText("数量：" + num);

							CheckScanLVAdapter mAdapter = new CheckScanLVAdapter(
									CheckScanActivity.this, lists, R.layout.check_scan_item);
							checkscanLV.setAdapter(mAdapter);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		}
	}*/
}
