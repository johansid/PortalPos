package cn.burgeon.core.ui.sales;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

	private void settle() {
		IntentData iData = (IntentData) getIntent().getParcelableExtra(PAR_KEY);
		ArrayList<Product> products = iData.getProducts();
		float pay = 0;
		int count = 0;
		int discount = 0;
		
		Log.d("zhang.h", "products size =========" + products.size());
		for(Product pro : products){
			Log.d("zhang.h", "pro.getMoney() =========" + pro.getMoney());
			pay += Float.parseFloat(pro.getMoney());
			count += Integer.parseInt(pro.getCount());
		}
		payTV.setText(String.format(getResources().getString(R.string.sales_settle_pay),pay));
		counTV.setText(String.format(getResources().getString(R.string.sales_settle_count),count));
		realityET.setText(payTV.getText());
		disCounET.setText(String.valueOf(discount));
		List<Settle> list = new ArrayList<Settle>();
		list.add(new Settle(getResources().getString(R.string.sales_settle_cash), payTV.getText().toString()));
		mListView.setAdapter(new SalesSettleAdapter(list, this));
	}

	private void init() {
		payTV = (TextView) findViewById(R.id.salesSettlePay);
		counTV = (TextView) findViewById(R.id.salesSettleCount);
		orginET = (EditText) findViewById(R.id.salesSettleOrginET);
		disCounET = (EditText) findViewById(R.id.salesSettleDiscountET);
		realityET = (EditText) findViewById(R.id.salesSettleRealityET);
		mListView = (ListView) findViewById(R.id.settleLV);
	}

}
