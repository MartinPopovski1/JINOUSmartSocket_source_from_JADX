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

public class EditionformationActivity extends Activity {
    public static int height;
    public static int width;
    private AddLockMenrigthOnclick addLockMenrigthOnclick;
    private AddLockOnclick addLockOnclick;
    private LinearLayout addlocklins1;
    private HomepageOnclick homepageOnclick;
    private LayoutInflater inflater;
    private LinearLayout linearbottom1;
    private LinearLayout lineartop1;
    private PopupWindow popupwindow;
    private ReturnMenLeftOnclick returnMenLeftOnclick;
    private Button top_left_button;
    private Button top_right_bt;
    private TextView top_text;
    private UseExplainOnclick useExplainOnclick;
    private TextView useexplaintext;

    private class AddLockMenrigthOnclick implements OnClickListener {
        private AddLockMenrigthOnclick() {
        }

        public void onClick(View v) {
            if (EditionformationActivity.this.popupwindow != null) {
                Log.m2i("菜单按钮点击事件 == 隐藏");
                EditionformationActivity.this.dismiss();
                return;
            }
            Log.m2i("菜单按钮点击事件 == 打开");
            View view = EditionformationActivity.this.inflater.inflate(C0048R.layout.popuplinear, null);
            EditionformationActivity.this.getpnpmenu(view);
            EditionformationActivity.this.popupwindow = new PopupWindow(view, (EditionformationActivity.width / 10) * 2, EditionformationActivity.height / 5);
            EditionformationActivity.this.popupwindow.showAsDropDown(v);
            EditionformationActivity.this.popupwindow.showAtLocation(EditionformationActivity.this.top_right_bt, 17, 20, 20);
        }
    }

    private class AddLockOnclick implements OnClickListener {
        private AddLockOnclick() {
        }

        public void onClick(View v) {
            Log.m2i("配对新锁");
            EditionformationActivity.this.dismiss();
            Intent intent = new Intent();
            intent.setClass(EditionformationActivity.this.getApplication(), AddSocketActivity.class);
            intent.putExtras(intent);
            EditionformationActivity.this.finish();
            EditionformationActivity.this.startActivity(intent);
        }
    }

    private class HomepageOnclick implements OnClickListener {
        private HomepageOnclick() {
        }

        public void onClick(View v) {
            EditionformationActivity.this.dismiss();
            Intent intent = new Intent();
            intent.setClass(EditionformationActivity.this.getApplication(), MainActivity.class);
            intent.putExtras(intent);
            EditionformationActivity.this.finish();
            EditionformationActivity.this.startActivity(intent);
        }
    }

    private class ReturnMenLeftOnclick implements OnClickListener {
        private ReturnMenLeftOnclick() {
        }

        public void onClick(View v) {
            EditionformationActivity.this.finish();
        }
    }

    private class UseExplainOnclick implements OnClickListener {
        private UseExplainOnclick() {
        }

        public void onClick(View v) {
            EditionformationActivity.this.dismiss();
            Intent intent = new Intent();
            intent.setClass(EditionformationActivity.this.getApplication(), UseexplainActivity.class);
            intent.putExtras(intent);
            EditionformationActivity.this.finish();
            EditionformationActivity.this.startActivity(intent);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(C0048R.layout.editionformation_activity);
        this.inflater = LayoutInflater.from(this);
        Display display = getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
        Log.m2i("宽：" + width + "  高：" + height);
        this.addlocklins1 = (LinearLayout) findViewById(C0048R.id.editioninformationlins1);
        this.lineartop1 = (LinearLayout) findViewById(C0048R.id.editioninformationlineartop1);
        this.linearbottom1 = (LinearLayout) findViewById(C0048R.id.editioninformationlineartext1);
        this.top_left_button = (Button) findViewById(C0048R.id.editioninformation_left_button);
        this.top_right_bt = (Button) findViewById(C0048R.id.editioninformation_right_bt);
        this.top_text = (TextView) findViewById(C0048R.id.editioninformation_text);
        this.useexplaintext = (TextView) findViewById(C0048R.id.editiontext);
        this.returnMenLeftOnclick = new ReturnMenLeftOnclick();
        this.addLockOnclick = new AddLockOnclick();
        this.homepageOnclick = new HomepageOnclick();
        this.useExplainOnclick = new UseExplainOnclick();
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
        bt3.setText(getResources().getString(C0048R.string.returns));
        bt1.setOnClickListener(this.addLockOnclick);
        bt2.setOnClickListener(this.useExplainOnclick);
        bt3.setOnClickListener(this.homepageOnclick);
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
