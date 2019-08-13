package com.example.binhluc.notepad3a;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;


public class MainActivity extends AppCompatActivity  {
    //property of actionbar
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    //bottom bar adapter
    private BottomBarAdapter pagerAdapter;
    private NoSwipePager viewPager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_notes:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_todolist:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_photo:
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pagerAdapter = new BottomBarAdapter(getSupportFragmentManager());

        NoteFragment noteFragment = new NoteFragment();
        pagerAdapter.addFragments(noteFragment);
        ToDoListFragment toDoListFragment = new ToDoListFragment();
        pagerAdapter.addFragments(toDoListFragment);
        PhotoShootFragment photoShootFragment =new PhotoShootFragment();
        pagerAdapter.addFragments(photoShootFragment);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.noSwipePager);

        viewPager.setPagingEnabled(false);
        viewPager.setAdapter(pagerAdapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//clickable
        getSupportActionBar().setHomeButtonEnabled(true);//add the < at the left of the icon.
        getSupportActionBar().setTitle("");

        drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
}
