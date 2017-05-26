package com.example.omerfdemir.msku_aps;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

import java.util.ArrayList;

/**
 * Created by omerfdemir on 16.05.2017.
 */

public class Publications extends AppCompatActivity implements ObservableScrollViewCallbacks{
    String name;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publications);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        ObservableListView listView = (ObservableListView) findViewById(R.id.list);
        listView.setScrollViewCallbacks(this);
        ArrayList<String> items = new ArrayList<String>();
        for (int i = 1; i <= 100; i++) {
                items.add(name);
            }
            listView.setAdapter(new ArrayAdapter<String>(
                    this, android.R.layout.simple_list_item_1, items));
        }
        private void getAllPublications(String name){

        }
        @Override
        public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

        }

        @Override
        public void onDownMotionEvent() {

        }

        @Override
        public void onUpOrCancelMotionEvent(ScrollState scrollState) {
            ActionBar ab = getSupportActionBar();
            if (scrollState == ScrollState.UP) {
                if (ab.isShowing()) {
                    ab.hide();
                }
            } else if (scrollState == ScrollState.DOWN) {
                if (!ab.isShowing()) {
                    ab.show();
                }
            }

        }
    }
