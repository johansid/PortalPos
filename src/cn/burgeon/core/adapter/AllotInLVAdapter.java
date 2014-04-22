package cn.burgeon.core.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cn.burgeon.core.Constant;
import cn.burgeon.core.R;
import cn.burgeon.core.bean.AllotIn;

/**
 * Created by Simon on 2014/4/16.
 */
public class AllotInLVAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<AllotIn> list;
    private int mLayoutRes;

    public AllotInLVAdapter(Context c, ArrayList<AllotIn> l, int layoutRes) {
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
            holder.docnoTV = (TextView) convertView.findViewById(R.id.docnoTV);
            holder.uploadStateTV = (TextView) convertView.findViewById(R.id.uploadStateTV);
            holder.docStateIV = (TextView) convertView.findViewById(R.id.docStateIV);
            holder.billdateIV = (TextView) convertView.findViewById(R.id.billdateIV);
            holder.corigidIV = (TextView) convertView.findViewById(R.id.corigidIV);
            holder.totqtyoutIV = (TextView) convertView.findViewById(R.id.totqtyoutIV);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 赋值
        holder.docnoTV.setText(list.get(position).getDOCNO());
        holder.billdateIV.setText(list.get(position).getBILLDATE());
        holder.corigidIV.setText(list.get(position).getC_ORIG_ID());
        holder.totqtyoutIV.setText(list.get(position).getTOT_QTYOUT());
        return convertView;
    }

    static class ViewHolder {
        TextView docnoTV;
        TextView uploadStateTV;
        TextView docStateIV;
        TextView billdateIV;
        TextView corigidIV;
        TextView totqtyoutIV;
    }
}
