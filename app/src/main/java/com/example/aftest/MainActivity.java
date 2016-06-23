package com.example.aftest;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    protected final static String PROMTION = "promotion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mLayoutManager = new LinearLayoutManager(MainActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        if(isNetworkConnected())
            new DownloadAsyncTask(this, new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if(msg.what == 0) {
                        boolean responseObj = msg.obj == null;
                        if(responseObj){
                            Toast.makeText(MainActivity.this, "Oops... Something went wrong.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        displayData(((List<Promotion>) msg.obj));
                    }
                }
            }).execute("https://www.abercrombie.com/anf/nativeapp/Feeds/promotions.json");
        else{
            File file = new File(Environment.getExternalStorageDirectory()+File.separator+"AFPromotions.json");
            if(!file.exists())
                Toast.makeText(MainActivity.this, "No internet connection.", Toast.LENGTH_SHORT).show();
            else{
                try {
                    Toast.makeText(MainActivity.this, "Loading the cached data...", Toast.LENGTH_SHORT).show();
                    FileReader reader = new FileReader(file);
                    BufferedReader bReader = new BufferedReader(reader);
                    String line = "";
                    StringBuilder builder = new StringBuilder();
                    while((line = bReader.readLine())!= null){
                        builder.append(line);
                    }
                    displayData(AFUtil.getPromotionDetails(builder.toString()));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void displayData(List<Promotion> object){
        mAdapter = new MyAdapter(object);
        mRecyclerView.setAdapter(mAdapter);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private List<Promotion> promotions;

        public MyAdapter(List<Promotion> obj) {
            this.promotions = obj;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView logo;
            public TextView name;
            public TextView address;
            public View view;
            public ViewHolder(View v) {
                super(v);
                this.view = v;
                logo = (ImageView)v.findViewById(R.id.image);
                name = (TextView)v.findViewById(R.id.name);
                address = (TextView)v.findViewById(R.id.address);
            }
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);

            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            ImageLoader.getInstance().displayImage(promotions.get(position).getImage(), holder.logo);
            holder.name.setText(promotions.get(position).getTitle());
            holder.address.setText(promotions.get(position).getDescription());
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, PromotionDetailsActivity.class);
                    intent.putExtra(PROMTION, promotions.get(position));
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return promotions.size();
        }
    }
}