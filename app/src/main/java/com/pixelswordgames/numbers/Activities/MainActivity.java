package com.pixelswordgames.numbers.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AnimationUtils;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.pixelswordgames.numbers.Adapters.LevelPagerAdapter;
import com.pixelswordgames.numbers.DB.DBLab;
import com.pixelswordgames.numbers.Game.Tasks;
import com.pixelswordgames.numbers.R;
import com.pixelswordgames.numbers.Views.AnimatedCharsView;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AnimatedCharsView animatedCharsView;
    private ViewPager levelPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> testDeviceIds = Collections.singletonList("72D76300F72A9B7A1E187A708019B8E6");
        RequestConfiguration configuration =
                new RequestConfiguration
                    .Builder()
                    .setTestDeviceIds(testDeviceIds)
                    .build();
        MobileAds.setRequestConfiguration(configuration);

        animatedCharsView = findViewById(R.id.charsView);
        levelPager = findViewById(R.id.levelPager);

        levelPager.setAdapter(
                new LevelPagerAdapter(new Tasks(this).getLevels())
        );

        levelPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position + 1 > DBLab.get(MainActivity.this).getLastLvl())
                    levelPager.setCurrentItem(
                            DBLab.get(MainActivity.this).getLastLvl(),
                            true
                    );
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        findViewById(R.id.titleView).setAnimation(
                AnimationUtils.loadAnimation(this, R.anim.idle_anim)
        );
        findViewById(R.id.playBtn).setAnimation(
                AnimationUtils.loadAnimation(this, R.anim.press_me)
        );
        findViewById(R.id.playBtn).setOnClickListener(v -> startGame(levelPager.getCurrentItem() + 1));

        levelPager.setCurrentItem(DBLab.get(MainActivity.this).getLastLvl(), true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        animatedCharsView.start();

        if(levelPager.getAdapter() != null)
            levelPager.getAdapter().notifyDataSetChanged();
        findViewById(R.id.playBtn).setEnabled(true);

        if(DBLab.get(MainActivity.this).getLastLvl() - 1 == levelPager.getCurrentItem())
            levelPager.setCurrentItem(DBLab.get(MainActivity.this).getLastLvl(), true);
    }

    @Override
    protected void onPause() {
        super.onPause();

        animatedCharsView.stop();
    }

    private void startGame(int lvl){
        findViewById(R.id.playBtn).setEnabled(false);
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        intent.putExtra("lvl", lvl);
        startActivity(intent);
    }
}