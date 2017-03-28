package com.example.aa.mytestdemo;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class FruitDetail extends AppCompatActivity {

    private static final String FRUITNMAE ="fruit_name" ;
    private static final String FRUITIMAGE ="fruit_imageid" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit_detail);

        Intent intent = getIntent();
        String name = intent.getStringExtra(FRUITNMAE);
        int  imageid = intent.getIntExtra(FRUITIMAGE,0);

        ImageView  image = (ImageView) findViewById(R.id.imageDetail);
        Toolbar toolbar  = (Toolbar) findViewById(R.id.toolbar);
        NestedScrollView scl  = (NestedScrollView) findViewById(R.id.scrollview);
        TextView   tv = (TextView) findViewById(R.id.textaaa);
        CollapsingToolbarLayout  ctl = (CollapsingToolbarLayout) findViewById(R.id.collapsinglayout);

        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if(supportActionBar != null ){
            supportActionBar.setDisplayShowHomeEnabled(true);
        }
        ctl.setTitle(name);
        Glide.with(this).load(imageid).into(image);
        String   content = generateFriutContent(name);
        tv.setText(content);
    }

    private String  generateFriutContent(String name) {
        StringBuilder  friutName = new StringBuilder();
        for (int i = 0; i <500 ; i++) {
            friutName.append(name);
        }
        return friutName+"";
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case  android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
