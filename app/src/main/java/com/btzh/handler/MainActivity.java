package com.btzh.handler;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    class MyHandler extends Handler {

        public MyHandler() {

        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    Toast.makeText(MainActivity.this, "这是一个延迟2" +
                            "是发送的空消息", Toast.LENGTH_SHORT).show();
                    break;
                case 101:
                    String mes = msg.getData().getString("Test");
                    Toast.makeText(MainActivity.this, mes, Toast.LENGTH_SHORT).show();
                    break;
                case 102:
                    Toast.makeText(MainActivity.this, "这是一个空消息", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }

    private MyHandler myHandler;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.btn);
        if (myHandler == null) {
            myHandler = new MyHandler();
        }

    }


    public void onClick(View view) {
        testThread();

    }


    private void testThread() {

        new Thread(new Runnable() {
            @Override
            public void run() {
//模拟线程耗时操作
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }


                Boolean result = isPrime(1059);

                Message message = Message.obtain();
                message.what = 101;
                Bundle bundle = new Bundle();
                bundle.putString("Test", "我是message发送的消息");
                bundle.putBoolean("result", result);
                message.setData(bundle);
                //直接发送消息
                myHandler.sendMessage(message);
                //直接发送空消息，并指定标志位
                //myHandler.sendEmptyMessage(102);
                //延迟发送空消息，
                //标志位为100，延迟2s发送一个空消息
                //myHandler.sendEmptyMessageDelayed(100, 2000);
                //发送一个消息不为空的，延迟2s的消息
                //myHandler.sendMessageDelayed(message, 2000);
                //myHandler.post(myRunable);
                //发送消息在一个确定的时间（）
                //myHandler.sendMessageAtTime(message, SystemClock.uptimeMillis()+2000);
                // myHandler.sendMessageAtTime(message, System.currentTimeMillis()+2000);
//                myHandler.postDelayed()
            }
        }).start();

    }


    Runnable myRunable = new Runnable() {
        @Override
        public void run() {

            button.setText("我是myHandler.post(myRunable)");


        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    public static boolean isPrime(int num) {
        boolean isprime = true;
        int len = num / 2;
        for (int i = 2; i < len; i++) {
            if (num % i == 0) {
                isprime = false;
                System.out.println("---------->num:" + num + "i:" + i);
                break;
            }
        }
        return isprime;
    }

}
