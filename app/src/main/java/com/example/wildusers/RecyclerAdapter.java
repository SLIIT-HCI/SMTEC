package com.example.wildusers;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wildusers.Experiment;
import com.example.wildusers.R;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.myViewHolder> {

    private ArrayList<Experiment> arrayList = new ArrayList<>();

    public RecyclerAdapter(ArrayList<Experiment> arrayList){
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // RecyclerView.ViewHolder view = LayoutInflater.from(parent.getContext()).inflate(R.layout.,parent,false);
        // return new myViewHolder(view);
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

        TextView actualPhrase;
        EditText inputPhrase;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            actualPhrase = (TextView)itemView.findViewById(R.id.viewPhrase);
            inputPhrase = (EditText)itemView.findViewById(R.id.typeText);
        }
    }
}
