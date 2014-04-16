package cn.burgeon.core.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.burgeon.core.R;

/**
 * Created by Simon on 2014/4/16.
 */
public class SystemAdapter extends BaseAdapter {
    private Context mContext;

    public SystemAdapter(Context c) {
        mContext = c;
    }

    private int[] mTextValues = {R.string.sellManager, R.string.vipDown, R.string.allotState,
            R.string.checkManager, R.string.inventoryManager, R.string.sysManager};

    @Override
    public int getCount() {
        return mTextValues.length;
    }

    @Override
    public Object getItem(int position) {
        return mTextValues[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_item, null);
            holder = new ViewHolder();
            holder.gridTV = (TextView) convertView.findViewById(R.id.gridTV);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //赋值
        holder.gridTV.setText(mTextValues[position]);
        return convertView;
    }

    static class ViewHolder {
        TextView gridTV;
    }

}
