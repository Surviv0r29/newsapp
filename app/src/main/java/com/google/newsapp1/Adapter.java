package com.google.newsapp1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.newsapp1.Model.Articles;
import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    Context context;
    List<Articles> articles;

    public Adapter(Context context, List<Articles> articles) {
        this.context = context;
        this.articles = articles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Articles a = articles.get(position);
           holder.tvtitle.setText(a.getTitle());
           holder.tvSource.setText(a.getSource().getName());
           holder.tvDate.setText("\u2022"+dateTime(a.getPublishedAt()));

           String imageurl = a.getUrlToImage();
           Picasso.get().load(imageurl).into(holder.img);

           final String url;
           url= a.getUrl();

           holder.cardView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent intent = new Intent(context,Detailed.class);
                   intent.putExtra("title",a.getTitle());
                   intent.putExtra("source",a.getSource().getName());
                   intent.putExtra("time",dateTime(a.getPublishedAt()));
                   intent.putExtra("desc",a.getDescription());
                   intent.putExtra("imageurl",a.getUrlToImage());
                   intent.putExtra("url",url);
                   context.startActivity(intent);
               }
           });
    }

    @Override
    public int getItemCount() {
        try {
            return articles.size();
        } catch (Exception ex){return 0;}
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvtitle,tvSource,tvDate;
        ImageView img;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvtitle =  itemView.findViewById(R.id.tvTitle);
            tvSource =  itemView.findViewById(R.id.tvSource);
            tvDate =  itemView.findViewById(R.id.tvDate);
            img =  itemView.findViewById(R.id.image);
            cardView =  itemView.findViewById(R.id.cardView);
        }
    }

    public String dateTime(String s){
        PrettyTime prettyTime = new PrettyTime(new Locale(getCountry()));
        String time = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:",Locale.ENGLISH);
            Date date = simpleDateFormat.parse(s);
            time = prettyTime.format(date);

        } catch (Exception e) {

            e.printStackTrace();
        }

        return time;
    }

    public String getCountry(){
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }
}
