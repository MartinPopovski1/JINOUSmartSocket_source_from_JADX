package com.jinoux.pwersupplysocket.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.android.jinoux.powersupplysocket.C0048R;
import com.android.jinoux.util.Log;

public class UseexplainActivity extends Activity {
    public static int height;
    public static int width;
    private AddLockMenrigthOnclick addLockMenrigthOnclick;
    private AddLockOnclick addLockOnclick;
    private LinearLayout addlocklins1;
    private HomepageOnclick homepageOnclick;
    private LayoutInflater inflater;
    private LinearLayout linearbottom1;
    private LinearLayout lineartop1;
    private LockSoftwareEditionOnclick lockSoftwareEditionOnclick;
    private PopupWindow popupwindow;
    private ReturnMenLeftOnclick returnMenLeftOnclick;
    private Button top_left_button;
    private Button top_right_bt;
    private TextView top_text;
    private TextView useexplaintext;

    private class AddLockMenrigthOnclick implements OnClickListener {
        private AddLockMenrigthOnclick() {
        }

        public void onClick(View v) {
            if (UseexplainActivity.this.popupwindow != null) {
                Log.m2i("菜单按钮点击事件 == 隐藏");
                UseexplainActivity.this.dismiss();
                return;
            }
            Log.m2i("菜单按钮点击事件 == 打开");
            View view = UseexplainActivity.this.inflater.inflate(C0048R.layout.popuplinear, null);
            UseexplainActivity.this.getpnpmenu(view);
            UseexplainActivity.this.popupwindow = new PopupWindow(view, (UseexplainActivity.width / 10) * 2, UseexplainActivity.height / 5);
            UseexplainActivity.this.popupwindow.showAsDropDown(v);
            UseexplainActivity.this.popupwindow.showAtLocation(UseexplainActivity.this.top_right_bt, 17, 20, 20);
        }
    }

    private class AddLockOnclick implements OnClickListener {
        private AddLockOnclick() {
        }

        public void onClick(View v) {
            Log.m2i("配对新锁");
            UseexplainActivity.this.dismiss();
            Intent intent = new Intent();
            intent.setClass(UseexplainActivity.this.getApplication(), AddSocketActivity.class);
            intent.putExtras(intent);
            UseexplainActivity.this.finish();
            UseexplainActivity.this.startActivity(intent);
        }
    }

    private class HomepageOnclick implements OnClickListener {
        private HomepageOnclick() {
        }

        public void onClick(View v) {
            UseexplainActivity.this.dismiss();
            Intent intent = new Intent();
            intent.setClass(UseexplainActivity.this.getApplication(), MainActivity.class);
            intent.putExtras(intent);
            UseexplainActivity.this.finish();
            UseexplainActivity.this.startActivity(intent);
        }
    }

    private class LockSoftwareEditionOnclick implements OnClickListener {
        private LockSoftwareEditionOnclick() {
        }

        public void onClick(View v) {
            UseexplainActivity.this.dismiss();
            Intent intent = new Intent();
            intent.setClass(UseexplainActivity.this.getApplication(), EditionformationActivity.class);
            intent.putExtras(intent);
            UseexplainActivity.this.finish();
            UseexplainActivity.this.startActivity(intent);
        }
    }

    private class ReturnMenLeftOnclick implements OnClickListener {
        private ReturnMenLeftOnclick() {
        }

        public void onClick(View v) {
            UseexplainActivity.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(C0048R.layout.useexplain_activity);
        this.inflater = LayoutInflater.from(this);
        Display display = getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
        Log.m2i("宽：" + width + "  高：" + height);
        this.addlocklins1 = (LinearLayout) findViewById(C0048R.id.ruseexplainlins1);
        this.lineartop1 = (LinearLayout) findViewById(C0048R.id.ruseexplainlineartop1);
        this.linearbottom1 = (LinearLayout) findViewById(C0048R.id.ruseexplainlineartext1);
        this.top_left_button = (Button) findViewById(C0048R.id.ruseexplain_left_button);
        this.top_right_bt = (Button) findViewById(C0048R.id.ruseexplain_right_bt);
        this.top_text = (TextView) findViewById(C0048R.id.ruseexplain_text);
        this.useexplaintext = (TextView) findViewById(C0048R.id.useexplaintext);
        this.returnMenLeftOnclick = new ReturnMenLeftOnclick();
        this.addLockOnclick = new AddLockOnclick();
        this.homepageOnclick = new HomepageOnclick();
        this.lockSoftwareEditionOnclick = new LockSoftwareEditionOnclick();
        this.addLockMenrigthOnclick = new AddLockMenrigthOnclick();
        szlayoutParams();
    }

    public void szlayoutParams() {
        LayoutParams relatlins1ip = this.addlocklins1.getLayoutParams();
        relatlins1ip.width = width;
        relatlins1ip.height = height;
        this.addlocklins1.setLayoutParams(relatlins1ip);
        LayoutParams lineartop1ip = this.lineartop1.getLayoutParams();
        lineartop1ip.width = width;
        lineartop1ip.height = height / 10;
        this.lineartop1.setLayoutParams(lineartop1ip);
        LayoutParams linearbottom1ip = this.linearbottom1.getLayoutParams();
        linearbottom1ip.width = width;
        linearbottom1ip.height = (height / 100) * 90;
        this.linearbottom1.setLayoutParams(linearbottom1ip);
        LayoutParams top_left_buttonip = this.top_left_button.getLayoutParams();
        top_left_buttonip.width = (width / 10) * 2;
        top_left_buttonip.height = height / 10;
        this.top_left_button.setLayoutParams(top_left_buttonip);
        this.top_right_bt.setLayoutParams(top_left_buttonip);
        this.top_left_button.setOnClickListener(this.returnMenLeftOnclick);
        this.top_right_bt.setOnClickListener(this.addLockMenrigthOnclick);
        LayoutParams top_textip = this.top_text.getLayoutParams();
        top_textip.width = (width / 10) * 6;
        top_textip.height = height / 15;
        this.top_text.setLayoutParams(top_textip);
        LayoutParams useexplaintextip = this.useexplaintext.getLayoutParams();
        useexplaintextip.width = width;
        useexplaintextip.height = (height / 100) * 90;
        this.useexplaintext.setLayoutParams(useexplaintextip);
    }

    public void dismiss() {
        if (this.popupwindow != null) {
            this.popupwindow.dismiss();
            this.popupwindow = null;
        }
    }

    public void getpnpmenu(View view) {
        Button bt1 = (Button) view.findViewById(C0048R.id.menupontbt1);
        Button bt2 = (Button) view.findViewById(C0048R.id.menupontbt2);
        Button bt3 = (Button) view.findViewById(C0048R.id.menupontbt3);
        LayoutParams bt1ip = bt1.getLayoutParams();
        bt1ip.width = (width / 10) * 2;
        bt1ip.height = height / 15;
        bt1.setLayoutParams(bt1ip);
        bt2.setLayoutParams(bt1ip);
        bt3.setLayoutParams(bt1ip);
        bt2.setText(getResources().getString(C0048R.string.returns));
        bt1.setOnClickListener(this.addLockOnclick);
        bt2.setOnClickListener(this.homepageOnclick);
        bt3.setOnClickListener(this.lockSoftwareEditionOnclick);
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    protected void onResume() {
        super.onResume();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C0048R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
