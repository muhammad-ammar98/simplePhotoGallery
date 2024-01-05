package com.islamicinternationalincims.simplephotogallery;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    // creating a variable for our context and array list.
    private final Context context;
    private final ArrayList<String> imagePathArrayList;

    // on below line we have created a constructor.
    public RecyclerViewAdapter(Context context, ArrayList<String> imagePathArrayList) {
        this.context = context;
        this.imagePathArrayList = imagePathArrayList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout in this method which we have created.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_layout, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {



        // on below line we are getting the file from the
        // path which we have stored in our list.
        File imgFile = new File(imagePathArrayList.get(position));



        // on below line we are checking if the file exists or not.
        if (imgFile.exists()) {


            // if the file exists then we are displaying that file in our image view using picasso library.
            Picasso.get().load(imgFile).placeholder(R.drawable.photo).into(holder.imageIV);

            // on below line we are adding click listener to our item of recycler view.
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent = new Intent(context, imageshowActivity2.class);


                    intent.putExtra("image", imagePathArrayList.get(position));


                    context.startActivity(intent);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    bottomsheetFragment bottomSheetFragment = new bottomsheetFragment();

                    Bundle bundle = new Bundle();

                    bundle.putString("message", imgFile.toString() );

                    bottomSheetFragment.setArguments(bundle);
                    bottomSheetFragment.show( ((AppCompatActivity) context).getSupportFragmentManager(), bottomSheetFragment.getTag());
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        // this method returns
        // the size of recyclerview
        return imagePathArrayList.size();
    }

    // View Holder Class to handle Recycler View.
    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our views.
        private final ImageView imageIV;


        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our views with their ids.
            imageIV = itemView.findViewById(R.id.showimage);
        }
    }
}
