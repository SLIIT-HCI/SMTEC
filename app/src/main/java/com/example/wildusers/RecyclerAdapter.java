package com.example.wildusers;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.myViewHolder> {

    private ArrayList<Experiment> arrayList = new ArrayList<>();

    public RecyclerAdapter(ArrayList<Experiment> arrayList){
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return  arrayList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}