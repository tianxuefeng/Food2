package com.tc.food2;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.tc.food2.views.FoodChoiceWindow;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        FoodChoiceWindow window = new FoodChoiceWindow(this, R.style.FullHeightDialog);
//        FoodChoiceWindow window = new FoodChoiceWindow(this);
        window.setListener(new FoodChoiceWindow.AddToOrderListener() {
            @Override
            public void addToOrder(SparseArray selectedArray, String request) {
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < selectedArray.size(); i++) {
                    int key = selectedArray.keyAt(i);
                    builder.append(selectedArray.get(key)).append(",");
                }
                builder.append("   ").append(request);
                Toast.makeText(MainActivity.this, builder.toString(), Toast.LENGTH_LONG).show();
            }
        });
        switch (view.getId()){
            case R.id.one_choice:
                window.setData(new String[]{"Small"});
                break;
            case R.id.mutil_choice:
                window.setData(new String[]{"Small", "Medium", "Big","Small", "Medium", "Big"});
                break;
        }
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = window.getWindow().getAttributes(); // 获取对话框当前的参数值
        Point point = new Point();
        d.getSize(point);
        p.height = (int) (point.y * 0.9);
        p.width = (int) (point.x * 0.96);
        window.getWindow().setAttributes(p);
        window.show();
    }
}
