package cn.burgeon.core.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import cn.burgeon.core.R;
import cn.burgeon.core.bean.AllotOutApply;

/**
 * Created by Simon on 2014/4/16.
 */
public class AllotOutApplyLVAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<AllotOutApply> list;
	private int mLayoutRes;

	public AllotOutApplyLVAdapter(Context c, ArrayList<AllotOutApply> l, int layoutRes) {
		this.mContext = c;
		this.list = l;
		this.mLayoutRes = layoutRes;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(mLayoutRes, null);
			holder = new ViewHolder();
			holder.barcodeTV = (TextView) convertView.findViewById(R.id.barcodeTV);
			holder.numET = (EditText) convertView.findViewById(R.id.numET);
			holder.colorTV = (TextView) convertView.findViewById(R.id.colorTV);
			holder.sizeTV = (TextView) convertView.findViewById(R.id.sizeTV);
			holder.priceTV = (TextView) convertView.findViewById(R.id.priceTV);
			holder.styleTV = (TextView) convertView.findViewById(R.id.styleTV);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// 赋值
		holder.barcodeTV.setText(list.get(position).getBarcode());
		// holder.numTV.setText(list.get(position).getNum());
		holder.colorTV.setText(list.get(position).getColor());
		holder.sizeTV.setText(list.get(position).getSize());
		holder.priceTV.setText(list.get(position).getPrice());
		holder.styleTV.setText(list.get(position).getStyle());
		return convertView;
	}

	static class ViewHolder {
		TextView barcodeTV;
		EditText numET;
		TextView colorTV;
		TextView sizeTV;
		TextView priceTV;
		TextView styleTV;
	}
}
