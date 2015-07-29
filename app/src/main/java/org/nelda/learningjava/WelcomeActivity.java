package org.nelda.learningjava;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by Administrator on 2015/7/22 0022.
 */
public class WelcomeActivity extends Activity {
    private ImageView mWallpapreImageView;
    private Animation alpha;
    private Animation alpha2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);
        mWallpapreImageView = (ImageView)findViewById(R.id.welcome_image);
        mWallpapreImageView.setImageResource(R.drawable.app_wallpaper);
        alpha = AnimationUtils.loadAnimation(this,R.anim.alpha_anim);
        alpha2 = AnimationUtils.loadAnimation(this,R.anim.alpha2_anim);
        mWallpapreImageView.startAnimation(alpha);
        new ThreadWallpaper(2000,0).start();
    }

    public class ThreadWallpaper extends Thread{
        private long time;
        private int num;
        public ThreadWallpaper(long timetosleep,int num){
            super();
            this.time = timetosleep;
            this.num = num;
        }
        @Override
        public void run() {
            super.run();
            try {
                sleep(time);
                handler.sendEmptyMessage(num);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

     Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if ( msg.what == 0 ){
                mWallpapreImageView.setImageResource(R.drawable.content_background4);
                mWallpapreImageView.startAnimation(alpha2);
                new ThreadWallpaper(1760,1).start();
            }else if(msg.what == 1){
                Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
                finish();
            }
        }
    };
}
