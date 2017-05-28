package com.bku.cse.karaoke.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bku.cse.karaoke.R;
import com.bku.cse.karaoke.adapter.PagerAdapter;
import com.bku.cse.karaoke.helper.SQLiteHandler;
import com.bku.cse.karaoke.helper.SessionManager;

import static com.bku.cse.karaoke.util.Utils.checkTheme;

public class HomeActivity extends AppCompatActivity {
    private ViewPager pager;
    private TabLayout tabLayout;
    private SessionManager session;
    private SQLiteHandler db;

    private int[] tabIcons = {
            R.drawable.ic_tab_storage,
            R.drawable.ic_tab_chart,
            R.drawable.ic_tab_near,
            R.drawable.ic_tab_storage_dark,
            R.drawable.ic_tab_chart_dark,
            R.drawable.ic_tab_near_dark
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkTheme(this);
        Test();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initUI();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        // Turn off scroll attr if not support
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            // Do something for lollipop and above versions
            AppBarLayout.LayoutParams params =
                    (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
            params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
        }


        setSupportActionBar(toolbar);

        // Disable app bar shadow
        getSupportActionBar().setElevation(0);

        // Load Page
        pager = (ViewPager) findViewById(R.id.view_pager);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        FragmentManager manager = getSupportFragmentManager();
        PagerAdapter adapter = new PagerAdapter(manager);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
        setupTabIcons();
        getSupportActionBar().setTitle("New");
        // Select first tab
        tabLayout.getTabAt(0).setIcon(tabIcons[3]);

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(pager) {

                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);
                        // Change Icon
                        changeIcon(tab.getPosition());

                        //Set action bar title
                        switch (tab.getPosition()) {
                            case 0:
                                getSupportActionBar().setTitle("New");
                                break;
                            case 1:
                                getSupportActionBar().setTitle("Hot");
                                break;
                            case 2:
                                getSupportActionBar().setTitle("Recent");
                                break;
                        }
                    }
                }
        );

    }

    public void initUI() {
        session = new SessionManager(getApplicationContext());
        db = new SQLiteHandler(getApplicationContext());
    }
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void changeIcon(int position) {
        setupTabIcons();
        tabLayout.getTabAt(position).setIcon(tabIcons[position + 3]);
    }

    /**
     * Create App bar
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("themeSetting", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_search:
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_help:
                Toast.makeText(this, "Help", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_theme1:
                edit.putString("Theme", "Theme 1");
                edit.commit();
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                return true;
            case R.id.action_theme2:
                edit.putString("Theme", "Theme 2");
                edit.commit();
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                return true;
            case R.id.action_login:
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
                return true;
            case R.id.action_logout:
                session.setLogin(false);

                Toast.makeText(this, "You are loged out! ^^", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Back button handler
     */

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    //Test Function

    public void Test() {
        Log.d("Test01", "" + System.currentTimeMillis());
    }
}
