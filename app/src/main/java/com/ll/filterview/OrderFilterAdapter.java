package com.ll.filterview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by zlb on 2016/5/19.
 */
public class OrderFilterAdapter extends BaseAdapter {
    private List<String> mData;
    private Context mContext;
    private LayoutInflater mInflater;
    private int mSelectIndex;
    public OrderFilterAdapter(Context context, List<String> data, int selectIndex){
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mSelectIndex = selectIndex;
    }
    @Override
    public int getCount() {
        return mData == null? 0:mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.order_filter_item,null);
            holder.tvContent = (TextView) convertView.findViewById(R.id.tv_order_filter_item);
            holder.ivSelect = (ImageView) convertView.findViewById(R.id.iv_order_filter_arrow);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvContent.setText(mData.get(position));
        if(position == mSelectIndex){
            holder.ivSelect.setVisibility(View.VISIBLE);
        }else {
            holder.ivSelect.setVisibility(View.GONE);
        }
        return convertView;
    }

    public void setData(List<String> orderFilterDate, int index) {
        this.mSelectIndex = index;
        this.mData = orderFilterDate;
        notifyDataSetChanged();
    }

    class ViewHolder{
        TextView tvContent;
        ImageView ivSelect;
    }
}
