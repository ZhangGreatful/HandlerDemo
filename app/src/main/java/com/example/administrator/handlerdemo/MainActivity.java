package com.example.administrator.handlerdemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
/*
* 设置一个按钮,点击将撤销runnable方法
*
* 添加数据,利用arg一个一个的添加
*
* 添加数据,获得一个object对象添加
* */

public class MainActivity extends AppCompatActivity {
    private Button btn;
    private TextView  textView;
    private ImageView imageView;
    private int[] image = {R.mipmap.pic_1, R.mipmap.pic_2, R.mipmap.pic_3,
            R.mipmap.pic_4, R.mipmap.pic_5};
    private int index;
    private MyRunnable myRunnable = new MyRunnable();

    class Person {
        private int    age;
        private String name;

        @Override
        public String toString() {
            return "name=" + name + "age=" + age;
        }
    }

//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(android.os.Message msg) {
////            利用arg逐个添加
////            textView.setText(  msg.arg1 + "-" + msg.arg2);
//
////            利用object对象,直接添加
//            textView.setText("" + msg.obj);
//        }
//
//
//        ;
//    };



//    该方法用于截获消息,当return为false时,不会截获,继续执行
//    当return为true时,截获消息,不在执行下一步
    private Handler handler=new Handler(new Handler.Callback() {
    @Override
    public boolean handleMessage(Message msg) {
        Toast.makeText(getApplicationContext(),"1",Toast.LENGTH_SHORT).show();
        return true;
    }
}){
    public void handleMessage(Message msg) {
        Toast.makeText(getApplicationContext(),"2",Toast.LENGTH_SHORT).show();
    }
};

    /**
     * 使图片进行自动转化,时间间隔为3秒
     */
    class MyRunnable implements Runnable {

        @Override
        public void run() {
            index++;
            index = index % 5;
            imageView.setImageResource(image[index]);
            handler.postDelayed(myRunnable, 3000);
        }
    }


    //    在主线程中调用MyRunnable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.tv_text);
        imageView = (ImageView) findViewById(R.id.iv_image);
        btn= (Button) findViewById(R.id.btn_click);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                handler.removeCallbacks(myRunnable);
                handler.sendEmptyMessage(1);
            }
        });
        handler.postDelayed(myRunnable, 3000);
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2000);
//                    实例化Message对象
//                    Message message = new Message();
//                    message.arg1 = 88;
//                    message.arg2 = 300;


//                    利用obtainMessage()获得obj
                    Message message=handler.obtainMessage();
                    Person person = new Person();
                    person.age = 23;
                    person.name = "binbin";
                    message.obj = person;
                    message.sendToTarget();
//                    此时不需要该操作了
//                    handler.sendMessage(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
