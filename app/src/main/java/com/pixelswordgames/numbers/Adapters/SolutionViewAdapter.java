package com.pixelswordgames.numbers.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pixelswordgames.numbers.R;
import com.pixelswordgames.numbers.Views.SolutionView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SolutionViewAdapter extends RecyclerView.Adapter<SolutionViewAdapter.SolutionViewHolder> {
    static class SolutionViewHolder extends RecyclerView.ViewHolder {
        SolutionView solutionView;

        public SolutionViewHolder(@NonNull View itemView) {
            super(itemView);
            solutionView = (SolutionView) itemView;
        }

        void setSolution(String value){
            solutionView.setSolution(value);
        }
    }

    private List<String> solutions;

    public SolutionViewAdapter() {
        solutions = new ArrayList<>();
    }

    @NonNull
    @Override
    public SolutionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SolutionView solutionView = new SolutionView(parent.getContext());
        return new SolutionViewHolder(solutionView);
    }

    @Override
    public void onBindViewHolder(@NonNull SolutionViewAdapter.SolutionViewHolder holder, int position) {
        holder.setSolution(solutions.get(position));
    }

    @Override
    public int getItemCount() {
        return solutions.size();
    }

    public void setSolutions(List<String> solutions) {
        this.solutions = solutions;
        Collections.shuffle(solutions);
        notifyDataSetChanged();
    }


    public String getSolution(int position){
        return solutions.get(position);
    }
}
