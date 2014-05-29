package cn.burgeon.core.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import cn.burgeon.core.R;
import cn.burgeon.core.bean.Settle;
import cn.burgeon.core.ui.sales.SalesSettleActivity;

public class SalesSettleAdapter extends BaseAdapter {

	private List<Settle> list;
	private Context context;

	public SalesSettleAdapter(List<Settle> list, Context context) {
		super();
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if(convertView == null){
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.sales_settle_list_item, null);
			holder.textView1 = (TextView) convertView.findViewById(R.id.settle_item_payway);
			holder.textView2 = (EditText) convertView.findViewById(R.id.settle_item_paymeony);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		Settle settle = list.get(position);
		holder.textView1.setText(settle.getPayWay());
		holder.textView2.setText(settle.getPayMoney());
		holder.textView2.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View view, boolean hasFocus) {
				if(hasFocus){
					Log.d("SalesSettle", ((SalesSettleActivity)context).getRealityET().getText().toString());
					((EditText)view).setText(((SalesSettleActivity)context).getRealityET().getText().toString());
				}
			}
		});
		return convertView;
	}

	class ViewHolder{
		TextView textView1;
		EditText textView2;
	}

}
