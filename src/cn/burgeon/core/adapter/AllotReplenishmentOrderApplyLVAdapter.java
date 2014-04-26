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
import cn.burgeon.core.bean.AllotReplenishmentOrderApply;

/**
 * Created by Simon on 2014/4/16.
 */
public class AllotReplenishmentOrderApplyLVAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<AllotReplenishmentOrderApply> list;
	private int mLayoutRes;

	public AllotReplenishmentOrderApplyLVAdapter(Context c, ArrayList<AllotReplenishmentOrderApply> l, int layoutRes) {
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
			holder.colorTV = (TextView) convertView.findViewById(R.id.colorTV);
			holder.sizeTV = (TextView) convertView.findViewById(R.id.sizeTV);
			holder.numET = (EditText) convertView.findViewById(R.id.numET);
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
		holder.styleTV.setText(list.get(position).getStyle());
		return convertView;
	}

	static class ViewHolder {
		TextView barcodeTV;
		TextView colorTV;
		TextView sizeTV;
		EditText numET;
		TextView styleTV;
	}
}
