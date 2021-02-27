package com.doanburak.instagramapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.PostHolder> {

    private ArrayList<String> emailList;
    private ArrayList<String> commentList;
    //private ArrayList<Date> dateList;
    private ArrayList<String> urlList;

    public MainRecyclerViewAdapter(ArrayList<String> emailList, ArrayList<String> commentList, ArrayList<String> urlList) {
        this.emailList = emailList;
        this.commentList = commentList;
        //this.dateList = dateList;
        this.urlList = urlList;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recyler_row,parent,false);

        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {

        holder.rrtv_email.setText(emailList.get(position));
        holder.rrtv_comment.setText(commentList.get(position));
        //holder.rrtv_date.setText(dateList.get(position).toString());
        Picasso.get().load(urlList.get(position)).into(holder.rriv_post);

    }

    @Override
    public int getItemCount() {
        return emailList.size();
    }

    class PostHolder extends RecyclerView.ViewHolder{

        ImageView rriv_post;
        TextView rrtv_email, rrtv_comment, rrtv_date;

        public PostHolder(@NonNull View itemView) {

            super(itemView);

            rriv_post = itemView.findViewById(R.id.rriv_post);
            rrtv_email = itemView.findViewById(R.id.rrtv_email);
            rrtv_comment = itemView.findViewById(R.id.rrtv_comment);
            //rrtv_date = itemView.findViewById(R.id.rrtv_date);

        }
    }

}
