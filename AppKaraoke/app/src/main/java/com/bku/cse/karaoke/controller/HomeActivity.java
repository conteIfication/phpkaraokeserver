package com.bku.cse.karaoke.controller;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bku.cse.karaoke.R;
import com.bku.cse.karaoke.fragment.AllFragment;
import com.bku.cse.karaoke.fragment.HotFragment;
import com.bku.cse.karaoke.fragment.NewFragment;
import com.bku.cse.karaoke.fragment.RecentFragment;
import com.bku.cse.karaoke.helper.SQLiteHandler;
import com.bku.cse.karaoke.helper.SessionManager;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import static com.bku.cse.karaoke.util.Utils.checkTheme;

public class HomeActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private SessionManager session;
    private SQLiteHandler db;
    private BottomBar bottomBar;

    Toolbar toolbar;

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

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initUI();

        // Load Pager
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        setUpViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        //Add TabLayout Listener
        TabLayout.ViewPagerOnTabSelectedListener listenerTabLayout =
                new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);
                        //Set action bar title
                        switch (tab.getPosition()) {
                            case 0:
                                getSupportActionBar().setTitle("Recent");
                                break;
                            case 1:
                                getSupportActionBar().setTitle("Hot");
                                break;
                            case 2:
                                getSupportActionBar().setTitle("New");
                                break;
                            case 3:
                                getSupportActionBar().setTitle("All");
                        }
                    }
                };
        tabLayout.addOnTabSelectedListener(listenerTabLayout);

        //bottom bar listener
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                //SONGBOOK tab
                if (tabId == R.id.tab_song_book) {
                    Toast.makeText(getApplicationContext(), "SongBOOk", Toast.LENGTH_SHORT).show();
                }
                //FEED tab
                else if (tabId == R.id.tab_feed) {
                    Intent feed_intent = new Intent(getApplicationContext(), FeedActivity.class);
                    startActivity(feed_intent);
                    finish();
                }
                //ME tab
                else if (tabId == R.id.tab_me) {
                    Intent me_intent = new Intent(getApplicationContext(), MeActivity.class);
                    startActivity(me_intent);
                    finish();
                }
            }
        });
    }
    public void setUpViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());;
        adapter.addFrag(new RecentFragment(), "RECENT");
        adapter.addFrag(new HotFragment(), "HOT");
        adapter.addFrag(new NewFragment(), "NEW");
        adapter.addFrag(new AllFragment(), "ALL");
        viewPager.setAdapter(adapter);
    }

    public void initUI() {
        session = new SessionManager(getApplicationContext());
        db = new SQLiteHandler(getApplicationContext());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            // Do something for lollipop and above versions
            AppBarLayout.LayoutParams params =
                    (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
            params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
        }
        setSupportActionBar(toolbar);
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    /**
     * Create App bar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
                Intent iSearch = new Intent(this, SearchActivity.class);
                startActivity(iSearch);
                return true;
            case R.id.action_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_help:
                Toast.makeText(this, "Help", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_theme1:
                edit.putString("Theme", "Theme 1");
                edit.apply();
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
    protected Boolean isActivityRunning(Class activityClass)
    {
        ActivityManager activityManager = (ActivityManager) getBaseContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(Integer.MAX_VALUE);

        for (ActivityManager.RunningTaskInfo task : tasks) {
            if (activityClass.getCanonicalName().equalsIgnoreCase(task.baseActivity.getClassName()))
                return true;
        }

        return false;
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

}
