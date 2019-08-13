package com.example.binhluc.notepad3a;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PhotoshootAdapter extends RecyclerView.Adapter<PhotoshootAdapter.ViewHolder> {
    private ArrayList<PhotoShoot> photoshoots;
    private Context context;
    private int chosenNoteId = 0;

    //Init callback method
    private onPhotoshootclicklistener listener;
    public void setListener(onPhotoshootclicklistener listener){ this.listener = listener;}

    public PhotoshootAdapter(Context context, ArrayList<PhotoShoot> photoshoots)
    {
        this.photoshoots = photoshoots;
        this.context = context;
    }
    @NonNull
    @Override
    public PhotoshootAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.photoshoot_main, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoshootAdapter.ViewHolder holder, final int position) {
        holder.noteTitle.setText(photoshoots.get(position).getNoteTitle());
        holder.noteText.setText(photoshoots.get(position).getNoteText());
        holder.noteDate.setText(photoshoots.get(position).getNoteDate());

        //Convert byte[] to bitmap then bind to viewHolder.
        byte[] image = photoshoots.get(position).getNoteImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        holder.noteImage.setImageBitmap(bitmap);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPhotoClick(photoshoots.get(position).getId());
            }
        });
    }
    @Override
    public int getItemCount() {
        return photoshoots.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView noteText;
        TextView noteDate;
        TextView noteTitle;
        ImageView noteImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noteText = itemView.findViewById(R.id.note_text);
            noteDate = itemView.findViewById(R.id.note_date);
            noteTitle = itemView.findViewById(R.id.note_title);
            noteImage = itemView.findViewById(R.id.note_image);
        }
    }



}