package com.example.binhluc.notepad3a;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ToDoTaskAdapter extends RecyclerView.Adapter<ToDoTaskAdapter.ToDoTaskHolder>{
    private ArrayList<ToDoTask> todotasks;
    private Context context;
    private TextView text;
    private ImageView deleteImage;
    private CheckBox check;

    //Init callback method
    private onToDoTaskClickListener listener;
    public void setListener(onToDoTaskClickListener listener){ this.listener = listener;}

    ToDoTaskAdapter(Context context, ArrayList<ToDoTask> todotasks) {
        this.context = context;
        this.todotasks = todotasks;
    }
    @NonNull
    @Override
    public ToDoTaskHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.todotask_main, viewGroup, false);
        return new ToDoTaskHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ToDoTaskHolder holder, final int position) {
        holder.todotaskText.setText(todotasks.get(position).getToDoTask_text());
        if(todotasks.get(position).isCheck()== true)
            holder.checkBox.setChecked(true);
        else if(todotasks.get(position).isCheck()== false)
            holder.checkBox.setChecked(false);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onToDoTaskClick(todotasks.get(position).getId());
            }
        });
        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onToDoTaskClickDeleteTask(todotasks.get(position).getId());
            }
        });
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true)
                    todotasks.get(position).setCheck(true);
                else if(isChecked == false)
                    todotasks.get(position).setCheck(false);
                listener.onToDoTaskChecked(todotasks.get(position).isCheck()
                        ,todotasks.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return todotasks.size();
    }

    public class ToDoTaskHolder extends RecyclerView.ViewHolder {
        TextView todotaskText;
        ImageView imageView;
        CheckBox checkBox;
        public ToDoTaskHolder(@NonNull View itemView) {
            super(itemView);
            todotaskText = itemView.findViewById(R.id.txt_todotask);
            imageView = itemView.findViewById(R.id.image_delete);
            checkBox = itemView.findViewById(R.id.checkbox);

            check = checkBox;
            deleteImage = imageView;
            text = todotaskText;
        }
    }

}
