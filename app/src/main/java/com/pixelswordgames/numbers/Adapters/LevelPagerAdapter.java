package com.pixelswordgames.numbers.Adapters;

import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.pixelswordgames.numbers.DB.DBLab;
import com.pixelswordgames.numbers.Game.Level;
import com.pixelswordgames.numbers.R;
import com.pixelswordgames.numbers.Utils.ExpressionsUtils;

import java.util.List;

public class LevelPagerAdapter extends PagerAdapter {
    private final List<Level> levels;

    public LevelPagerAdapter(List<Level> levels) {
        this.levels = levels;
    }

    @Override
    public int getCount() {
        return levels.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater
                .from(container.getContext())
                .inflate(R.layout.level_info_view, container, false);

        ((TextView) view.findViewById(R.id.textView)).setText(
                container.getContext().getString(R.string.lvl, levels.get(position).getLvl())
        );
        double time = DBLab.get(container.getContext()).getHighScore(levels.get(position).getLvl());
        if(time != 0){
            ((TextView) view.findViewById(R.id.textView1)).setText(
                    container.getContext().getString(R.string.best_time, ExpressionsUtils.formatDouble(time))
            );
            ((TextView) view.findViewById(R.id.textView2)).setText(
                    container.getContext().getString(
                            R.string.games_played,
                            DBLab.get(container.getContext()).getGamesCount(levels.get(position).getLvl())
                    )
            );
            view.findViewById(R.id.textView1).setVisibility(View.VISIBLE);
        } else
            view.findViewById(R.id.textView1).setVisibility(View.GONE);

        container.addView(view);

        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
