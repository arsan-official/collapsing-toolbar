package com.arsan.collapsingtoolbar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout appBarLayout;
    private RecyclerView recList;
    private Menu collapseMenu;

    private boolean appBarExpanded = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        recList = (RecyclerView) findViewById(R.id.scrollableview);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbar.setTitle("Android Desserts");

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        DessertAdapter adapter = new DessertAdapter();
        recList.setAdapter(adapter);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.landscape);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {

            @SuppressWarnings("ResourceType")
            @Override
            public void onGenerated(Palette palette) {
                int vibrantColor = palette.getVibrantColor(R.color.primary_500);
                collapsingToolbar.setContentScrimColor(vibrantColor);
                collapsingToolbar.setStatusBarScrimColor(R.color.black_trans80);
            }
        });

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(Math.abs(verticalOffset) > 200){
                    appBarExpanded = false;
                }else{
                    appBarExpanded = true;
                }
                invalidateOptionsMenu();
            }
        });

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(collapseMenu != null && (!appBarExpanded || collapseMenu.size() != 1)){
            //collapsed
            collapseMenu.add("Add")
                    .setIcon(R.drawable.ic_add_32dp)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }else{

        }
        return super.onPrepareOptionsMenu(collapseMenu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        this.collapseMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.action_settings:
                Toast.makeText(this, "Setting menu clicked!", Toast.LENGTH_SHORT).show();
                break;
        }

        if(item.getTitle() == "Add"){
            Toast.makeText(this, "Add menu clicked!", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
