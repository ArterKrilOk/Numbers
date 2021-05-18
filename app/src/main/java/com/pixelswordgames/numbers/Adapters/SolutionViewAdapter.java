package com.pixelswordgames.numbers.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pixelswordgames.numbers.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SolutionViewAdapter extends RecyclerView.Adapter<SolutionViewAdapter.SolutionViewHolder> {
    static class SolutionViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public SolutionViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }

        void setSolution(Integer value){
            textView.setText(value.toString());
        }
    }

    private List<Integer> solutions;

    public SolutionViewAdapter() {
        solutions = new ArrayList<>();
    }

    @NonNull
    @Override
    public SolutionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.solution_frame, parent, false);
        return new SolutionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SolutionViewAdapter.SolutionViewHolder holder, int position) {
        holder.setSolution(solutions.get(position));
    }

    @Override
    public int getItemCount() {
        return solutions.size();
    }

    public void setSolutions(List<Integer> solutions) {
        this.solutions = solutions;
        notifyDataSetChanged();
    }

    public void generateSolutions(Integer value){
        solutions.clear();
        solutions.add(value);
        for(int  i = 0; i < 5; ++i)
            solutions.add(value + new Random().nextInt(20) - 10);
        Collections.shuffle(solutions);
        notifyDataSetChanged();
    }

    public int getSolution(int position){
        return solutions.get(position);
    }
}
