package cn.qiuc.org.idialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";

    private Button btn_dialog1;
    private Button btn_custom;
    private Button btn_custom2;
    private TextView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        setListener();
    }

    private void setListener() {
        btn_dialog1.setOnClickListener(new Btn_dialo1onClick());
        btn_custom.setOnClickListener(new Inner_btn_customClick());
        btn_custom2.setOnClickListener(new Btn_custom2Click());
    }

    private void initView() {
        btn_dialog1 = (Button) findViewById(R.id.btn_dialog1);
        btn_custom = (Button) findViewById(R.id.btn_custom);
        btn_custom2 = (Button) findViewById(R.id.btn_custom2);
        show = (TextView) findViewById(R.id.show);
    }

    class Btn_custom2Click implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Context context = MainActivity.this;
            CustomDialog2 cd = new CustomDialog2(context);
            cd.show();
        }
    }


    private class CustomDialog2 extends Dialog {
        public CustomDialog2(Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.custom_dialog);
            setTitle("选择播放列表");

            TextView text = (TextView) findViewById(R.id.text);
            text.setText("hello, this is a custome dialog");
            ImageView image = (ImageView) findViewById(R.id.image);
            image.setImageResource(R.mipmap.music_ico);

            Button btn_yes = (Button) findViewById(R.id.button_yes);
            btn_yes.setHeight(5);
            btn_yes.setOnClickListener(new Button.OnClickListener() {

                @Override
                public void onClick(View v) {
                    show.setText("you clicked the button yes");
                    dismiss();
                }
            });

            Button btn_no = (Button) findViewById(R.id.button_no);
            btn_no.setSingleLine(true);
            btn_no.setOnClickListener(new Button.OnClickListener() {

                @Override
                public void onClick(View v) {
                    show.setText("you clicked the button no");
                    dismiss();
                }
            });
        }

        @Override
        protected void onStop() {
            super.onStop();
            Log.d(TAG, "+++++++++++++++++++++++++++++++++++");
        }
    }

    class Btn_dialo1onClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            show.setText("click me");

            AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
            ad.setTitle("自己瞎试试");
            ad.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            ad.setNegativeButton("门户网站", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Uri uri = Uri.parse("http://www.baidu.com");
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }
            });
            ad.setItems(new String[]{"播放", "从列表中移除"}, new Inner_itemListonClick());
            ad.show();
        }
    }

    class Inner_itemListonClick implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case 0:
                    show.setText("播放");
                    break;
                case 1:
                    show.setText("从列表中删除");
                    break;
            }
        }
    }

    class Inner_btn_customClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            show.setText("come on, click me, make custom dialog");
            int mCount = 0;
            final ProgressDialog mPDialog;
            mPDialog = new ProgressDialog(MainActivity.this);
            mPDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mPDialog.setTitle("have a msg");
            mPDialog.setMessage("doing processing ...");
            mPDialog.setIcon(R.mipmap.music_ico);
            mPDialog.setIndeterminate(false);
            mPDialog.setCancelable(true);

            mPDialog.show();

            Timer timer = new Timer();
            class MyTask extends TimerTask {
                int num=0;

                @Override
                public void run() {
                    num++;
                    Log.d(TAG, "已花费了" + num + "s");
                    if (num > 4) {
                        mPDialog.cancel();
                    }
                }
            }

            timer.schedule(new MyTask(), 1, 1000);
        }
    }
}
