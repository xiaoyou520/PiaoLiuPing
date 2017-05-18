package you.xiaochen;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private View iv;

    private boolean isFull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.mToolbar);
        iv = findViewById(R.id.iv);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFull) {
                    full(false);
                    isFull = false;
                } else {
                    full(true);
                    isFull = true;
                }
            }
        });
        ConfigUtils.setStatusBarColor(MainActivity.this, mToolbar);
        initActionBar();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
    }

    private void full(boolean enable) {
        if (!enable){
            //非全屏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            mToolbar.clearAnimation();
            Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.top_translate_enter);
            mToolbar.setAnimation(animation);
            ConfigUtils.setStatusBarColor(MainActivity.this, mToolbar);
            mToolbar.setVisibility(View.VISIBLE);
            animation.start();
        }else{
            mToolbar.clearAnimation();
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.top_translate_exit);
            mToolbar.setAnimation(animation);
            animation.start();
            mToolbar.setVisibility(View.GONE);
            //清除非全屏时的flags,并设置全屏flag
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }


    private void initActionBar() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setSupportActionBar(mToolbar);
        ActionBar bar = getSupportActionBar();
        bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        View actionView = LayoutInflater.from(this).inflate(R.layout.actionbar_main, null);
        ActionBar.LayoutParams actionBarParams = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        bar.setCustomView(actionView, actionBarParams);
    }

}
