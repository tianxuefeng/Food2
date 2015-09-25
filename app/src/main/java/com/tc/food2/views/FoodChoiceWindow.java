package com.tc.food2.views;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tc.food2.R;

/**
 * User: tianchao
 * Date: 15/9/21
 * Time: 下午8:39
 * PS: 学如逆水行舟，不进则退
 */
public class FoodChoiceWindow extends Dialog {
    private Context mContext;
    private int width;
    private int height;

    private ImageView ivFavor;
    private ImageView ivClose;
    private EditText etRequest;
    private TextView btAddToOrder;
    private MutilChoiceSpinner choiceSpinner;
    private AddToOrderListener listener;

    public FoodChoiceWindow(Context context) {
        super(context);
        init(context);
    }

    public FoodChoiceWindow(Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        width = metrics.widthPixels;
        height = metrics.heightPixels;
        setContentView(genContentView());
    }

    //TODO 项目中务必要重写此方法设置数据源
    public void setData(String[] data) {
        if (choiceSpinner != null)
            choiceSpinner.setSpinnerData(data);
    }

    public void setListener(AddToOrderListener listener) {
        this.listener = listener;
    }

    private View genContentView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_food_choice_dialog, null);
        ivFavor = (ImageView) view.findViewById(R.id.iv_favor);
        ivClose = (ImageView) view.findViewById(R.id.iv_close);
        etRequest = (EditText) view.findViewById(R.id.et_request);
        btAddToOrder = (TextView) view.findViewById(R.id.bt_add_to_order);
        choiceSpinner = (MutilChoiceSpinner) view.findViewById(R.id.mcs_list);

        ivFavor.setOnClickListener(mListener);
        ivClose.setOnClickListener(mListener);
        btAddToOrder.setOnClickListener(mListener);
        return view;
    }

    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_favor:
                    //TODO 点击收藏按钮的操作
                    ivFavor.setSelected(!ivFavor.isSelected());
                    break;
                case R.id.iv_close:
                    dismiss();
                    break;
                case R.id.bt_add_to_order:
                    String request = etRequest.getText().toString();
                    if (listener != null)
                        listener.addToOrder(choiceSpinner.getSelectedData(), request);
                    dismiss();
                    break;
            }
        }
    };

    public interface AddToOrderListener {
        void addToOrder(SparseArray selectedArray, String request);
    }
}
