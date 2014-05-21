package cn.burgeon.core.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cn.burgeon.core.R;
import cn.burgeon.core.bean.AllotReplenishment;

/**
 * Created by Simon on 2014/4/16.
 */
public class AllotReplenishmentLVAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<AllotReplenishment> list;
    private int mLayoutRes;

    public AllotReplenishmentLVAdapter(Context c, ArrayList<AllotReplenishment> l, int layoutRes) {
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
            holder.billdateIV = (TextView) convertView.findViewById(R.id.billdateIV);
            holder.cdestidIV = (TextView) convertView.findViewById(R.id.cdestidIV);
            holder.statuserTV = (TextView) convertView.findViewById(R.id.statuserTV);
            holder.descTV = (TextView) convertView.findViewById(R.id.descTV);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 赋值
        holder.docnoTV.setText(list.get(position).getDOCNO());
        holder.billdateIV.setText(list.get(position).getDOCDATE());
        holder.cdestidIV.setText(list.get(position).getOUT_STORE());
        holder.statuserTV.setText(list.get(position).getAPPLY_PEOPLE());
        holder.descTV.setText(list.get(position).getREMARK());
        return convertView;
    }

    static class ViewHolder {
        TextView docnoTV;
        TextView uploadStateTV;
        TextView billdateIV;
        TextView cdestidIV;
        TextView statuserTV;
        TextView descTV;
    }
}
