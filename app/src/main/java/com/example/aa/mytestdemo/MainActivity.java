package com.example.aa.mytestdemo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout dl;
    private boolean show = false;
    private RecyclerView rcv;
    private Fruit[] fruits ={new Fruit("苹果",R.drawable.apple),new Fruit("香蕉",R.drawable.banana),new Fruit("句子",R.drawable.orange),
            new Fruit("柠檬",R.drawable.watermelon),new Fruit("pear",R.drawable.pear), new Fruit("grap",R.drawable.grape),
            new Fruit("pine苹果",R.drawable.pineapple),new Fruit("S他人11 ",R.drawable.strawberry),new Fruit("切入",R.drawable.cherry),
            new Fruit("芒果",R.drawable.mango),};
    private  List<Fruit>  mlist  = new ArrayList<>();
    private SwipeRefreshLayout swl;
    private FirutAdapter firutAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dl = (DrawerLayout) findViewById(R.id.activity_main);
        NavigationView nv = (NavigationView) findViewById(R.id.navigationView);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setHomeAsUpIndicator(R.mipmap.ic_launcher);
        }

        nv.setCheckedItem(R.id.i1);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                dl.closeDrawers();
                return true;
            }
        });
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatbutton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("", "onClick: " + "sssssssssss");
                Snackbar.make(v, "我是弹唱内容", Snackbar.LENGTH_INDEFINITE).setAction("undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "是是是", Toast.LENGTH_LONG).show();
                    }
                }).show();
            }
        });

        //构造数据
        initFriut();
        rcv = (RecyclerView) findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);  //显示2列
        rcv.setLayoutManager(gridLayoutManager);
        firutAdapter = new FirutAdapter(mlist);
        rcv.setAdapter(firutAdapter);

//            下拉刷新功能
        swl = (SwipeRefreshLayout) findViewById(R.id.swipelayout);
        swl.setColorSchemeResources(R.color.colorPrimary); //设置拉的颜色和主题的一样
        swl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                runOnUiThread(new Thread(new Runnable() {
                    @Override
                    public void run() {
                        initFriut();
                        swl.setRefreshing(false);//刷新完了，隐藏进度条
                        firutAdapter.notifyDataSetChanged();//通知数据改变
                    }
                }));
            }
        });

    }

    private void initFriut() {
        mlist.clear();
        for (int i = 0; i <50 ; i++) {
            Random rd = new Random();
            int i1 = rd.nextInt(fruits.length);
            mlist.add(fruits[i1]);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (!show) {
                    dl.openDrawer(GravityCompat.START);
                    show = true;
                } else {
                    dl.closeDrawer(GravityCompat.START);
                    show = false;
                }

        }
        return true;
    }
}

class FirutAdapter extends RecyclerView.Adapter<FirutAdapter.ViewHolder> {
    private List<Fruit> mfirutlsit;
    private Context mcontext;

    @Override
    public FirutAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        //第三步，从写这个方法
        if (mcontext == null) {
            mcontext = parent.getContext();
        }
        View view = LayoutInflater.from(mcontext).inflate(R.layout.rec_item, parent, false);

        //点击事件
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.cardVie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                Fruit fruit = mfirutlsit.get(position);
                Intent intent = new Intent(parent.getContext(), FruitDetail.class);
                intent.putExtra("fruit_name",fruit.getFruitName());
                intent.putExtra("fruit_imageid",fruit.getImageId());
                mcontext.startActivity(intent);

            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FirutAdapter.ViewHolder holder, int position) {
//  第四步，在这个方法中处理对应的每一个内容
        Fruit fruit = mfirutlsit.get(position);
//        holder.imageView.setImageResource(fruit.getImageId());
        holder.textView.setText(fruit.getFruitName());
        Glide.with(mcontext).load(fruit.getImageId()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mfirutlsit.size();
    }

    //第一步,把Viewholder这个内部类 改为静态的，然后写他的成员变量。 这个几个都是子项目中的， 然后在这个的构造中，找ID
    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardVie;
        ImageView imageView;
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardVie = (CardView) itemView;
            imageView = (ImageView) itemView.findViewById(R.id.image);
            textView = (TextView) itemView.findViewById(R.id.fruitname);
        }
    }

    //第二步，重写整个FriutAdapter的构造，定义一个成员变量，用来接收外部传来的整个数据集合
    public FirutAdapter(List<Fruit> fruitList) {
        this.mfirutlsit = fruitList;
    }
}




