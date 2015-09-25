package com.tc.food2.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tc.food2.R;
import com.tc.food2.adapters.MutilChoiceSpinnerAdapter;
import com.tc.food2.utils.DimenUtils;

/**
 * User: tianchao
 * Date: 15/9/21
 * Time: 下午10:56
 * PS: 学如逆水行舟，不进则退
 * <p/>
 * 自定义多选项下拉列表
 */
public class MutilChoiceSpinner extends LinearLayout {
    private Context mContext;
    private int mHeadViewHeight;
    private int mListViewHeight;
    private int mLeftMargin;
    private int mRightMargin;
    private int mHeadViewPadding;
    private int mListViewTopMargin;

    private TextView mHeadView;
    private ListView mListView;

    private boolean mListViewShown;
    private SparseArray mSelecteArray;//保存选中得数据

    private String[] mData;

    public MutilChoiceSpinner(Context context) {
        super(context);
        init(context);
    }

    public MutilChoiceSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MutilChoiceSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mSelecteArray = new SparseArray();
        mContext = context;
        mHeadViewHeight = DimenUtils.dp2px(30, mContext);
        mLeftMargin = DimenUtils.dp2px(10, mContext);
        mRightMargin = DimenUtils.dp2px(10, mContext);
        mHeadViewPadding = DimenUtils.dp2px(5, mContext);
        mListViewHeight = DimenUtils.dp2px(80, mContext);
        mListViewTopMargin = DimenUtils.dp2px(1, mContext);
        setOrientation(VERTICAL);
        initView();
    }

    private void initView() {
        mHeadView = genHeadView();
        mListView = genListView();
        addView(mHeadView);
        addView(mListView);
        mListViewShown = true;

        mHeadView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListViewShown) {
                    dissmissListView();
                    mListViewShown = false;
                    mListView = null;
                } else {
                    mListView = genListView();
                    addView(mListView);
                    mListViewShown = true;
                }
            }
        });
    }

    /**
     * 当ListView消失时要保存选中数据
     */
    private void dissmissListView() {
        MutilChoiceSpinnerAdapter adapter = (MutilChoiceSpinnerAdapter) mListView.getAdapter();
        boolean[] selected = adapter.getSelected();
        for (int i = 0; i < adapter.getCount(); i++) {
            if (selected[i])
                mSelecteArray.append(i, mData[i]);
        }
        removeView(mListView);
    }

    private ListView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            MutilChoiceSpinnerAdapter adapter = (MutilChoiceSpinnerAdapter) mListView.getAdapter();
            if (mData.length <= 1 || position == mData.length) {
                if (mData.length <= 1)
                    adapter.onItemClick(position);
                dissmissListView();
                mListViewShown = false;
            } else {
                adapter.onItemClick(position);
            }
        }
    };

    //TODO 这里根据实际需要修改方法

    /**
     * 绑定spinner数据,默认显示第一条数据
     */
    public void setSpinnerData(String[] data) {
        mData = data;
        mHeadView.setText(data[0]);
        MutilChoiceSpinnerAdapter adapter = (MutilChoiceSpinnerAdapter) mListView.getAdapter();
        adapter.setData(data);
    }

    public SparseArray getSelectedData() {
        return mSelecteArray;
    }

    /**
     * 生成下拉列表头部
     */
    private TextView genHeadView() {
        TextView headView = new TextView(mContext);
        headView.setPadding(mHeadViewPadding, 0, mHeadViewPadding, 0);
        headView.setGravity(Gravity.CENTER_VERTICAL);
        headView.setTextColor(getResources().getColor(R.color.hex_393a47));
        Drawable drawable = mContext.getResources().getDrawable(R.mipmap.ico_arrowdown);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        headView.setCompoundDrawablePadding(DimenUtils.dp2px(10, mContext));
        headView.setCompoundDrawables(null, null, drawable, null);
        headView.setBackgroundResource(R.drawable.shape_rectangle_white);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, mHeadViewHeight);
        params.leftMargin = mLeftMargin;
        params.rightMargin = mRightMargin;
        params.gravity = Gravity.CENTER_VERTICAL;
        headView.setLayoutParams(params);
        return headView;
    }

    /**
     * 生成下拉列表
     */
    private ListView genListView() {
        ListView listView = new ListView(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.leftMargin = mLeftMargin;
        params.rightMargin = mRightMargin;
        params.topMargin = mListViewTopMargin;
        listView.setLayoutParams(params);
        listView.setBackgroundResource(R.drawable.shape_rectangle_white);
        listView.setDividerHeight(0);
        MutilChoiceSpinnerAdapter adapter = new MutilChoiceSpinnerAdapter(mContext, listView);
        listView.setAdapter(adapter);
        adapter.setData(mData);
        listView.setOnItemClickListener(mItemClickListener);
        return listView;
    }

}
