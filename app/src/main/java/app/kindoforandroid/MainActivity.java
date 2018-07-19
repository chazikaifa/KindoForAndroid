package app.kindoforandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private LinearLayout allFlipper;
//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 1:
//                    //切换到主页面
//                    allFlipper.setDisplayedChild(1);
//                    break;
//            }
//        }
//    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        allFlipper = (LinearLayout) findViewById(R.id.allFlipper);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                handler.sendEmptyMessage(1); //给UI主线程发送消息
//            }
//        }, 3000); //启动等待3秒钟
    }

    public void gameStart(View v){
        Intent intent=new Intent();
        intent.setClass(this,GameActivity.class);
        startActivity(intent);
    }

    public void howToPlay(View v){
        Intent intent=new Intent();
        intent.setClass(this,HelpActivity.class);
        startActivity(intent);
    }
}
