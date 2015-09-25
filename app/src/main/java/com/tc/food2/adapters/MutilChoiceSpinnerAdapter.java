package com.tc.food2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tc.food2.R;

/**
 * User: tianchao
 * Date: 15/9/21
 * Time: 下午11:02
 * PS: 学如逆水行舟，不进则退
 */
public class MutilChoiceSpinnerAdapter extends BaseAdapter {
    private Context mContext;
    private ListView mListView;
    private String[] mData;
    private boolean[] mSelected;

    public MutilChoiceSpinnerAdapter(Context context, ListView listView) {
        mContext = context;
        mListView = listView;
    }

    public void setData(String[] data) {
        if (data == null)
            return;
        if (data.length > 1)
            mData = new String[data.length + 1];
        else
            mData = new String[data.length];
        for (int i = 0; i < data.length; i++) {
            mData[i] = data[i];
        }
        mSelected = new boolean[mData.length];
        notifyDataSetChanged();
    }

    public void onItemClick(int position) {
        mSelected[position] = !mSelected[position];
        notifyDataSetChanged();
    }

    public boolean[] getSelected() {
        return mSelected;
    }

    @Override
    public int getCount() {
        if (mData == null)
            return 0;
        return mData.length;
    }

    @Override
    public String getItem(int position) {
        if (mData == null)
            return null;
        return mData[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_mutilchoice_list, parent, false);
            holder = new ViewHolder();
            holder.sizeView = (TextView) convertView.findViewById(R.id.tv_portion_size);
            holder.tickView = (ImageView) convertView.findViewById(R.id.iv_tick);
            holder.parent = convertView.findViewById(R.id.ll_parent);
            holder.btnView = (TextView) convertView.findViewById(R.id.btn_done);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (getCount() > 1 && position == mData.length - 1) {
            holder.parent.setVisibility(View.GONE);
            holder.btnView.setVisibility(View.VISIBLE);
        } else {
            holder.parent.setVisibility(View.VISIBLE);
            holder.btnView.setVisibility(View.GONE);
            String item = getItem(position);
            holder.sizeView.setText(item);
            holder.tickView.setSelected(mSelected[position]);
        }
        return convertView;
    }

    private static class ViewHolder {
        android.view.View parent;
        TextView sizeView;
        ImageView tickView;
        TextView btnView;
    }
}
