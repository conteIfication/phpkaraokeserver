package com.bku.cse.karaoke.controller;

import android.content.Intent;
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
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bku.cse.karaoke.R;
import com.bku.cse.karaoke.fragment.AllFragment;
import com.bku.cse.karaoke.fragment.HotFragment;
import com.bku.cse.karaoke.fragment.NewFragment;
import com.bku.cse.karaoke.fragment.RecentFragment;
import com.bku.cse.karaoke.helper.DatabaseHelper;
import com.bku.cse.karaoke.helper.SessionManager;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import static com.bku.cse.karaoke.util.Utils.checkTheme;

public class HomeActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_SETTING = 10;
    private static final int REQUEST_CODE_LOGIN = 11;
    private final String TAG = HomeActivity.class.getSimpleName();
    public final int REQUEST_INTERNET = 123;
    public final int REQUEST_W_EXTERNAL = 124;
    public final int REQUEST_RECORD_AUDIO = 125;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private SessionManager session;
    private DatabaseHelper db;
    private BottomBar bottomBar;
    private BottomBarTab bottomBarTab_me;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkTheme(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Init UI
        session = new SessionManager(getApplicationContext());
        db = new DatabaseHelper(getApplicationContext());
        bottomBar = (BottomBar) findViewById(R.id.bottombar);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        bottomBarTab_me = (BottomBarTab) bottomBar.findViewById(R.id.tab_me);

        //check Login Status
        if ( !session.isLoggedIn() ) {
            bottomBarTab_me.setTitle("Guest");
        }else {
            bottomBarTab_me.setTitle("Me");
        }
        //Toolbar scroll enable
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            // Do something for lollipop and above versions
            AppBarLayout.LayoutParams params =
                    (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
            params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
        }
        setSupportActionBar(toolbar);

        // Load Pager
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        setUpViewPager(viewPager);
        viewPager.setCurrentItem(1);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager(viewPager);

        //Add TabLayout Listener
        final TabLayout.ViewPagerOnTabSelectedListener listenerTabLayout =
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
                switch (tabId) {
                    case R.id.tab_song_book:  //SONGBOOK tab
                        Toast.makeText(getApplicationContext(), "SongBook", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.tab_feed:    //FEED tab
                        finish();
                        Intent feedIntent = new Intent(getApplicationContext(), FeedActivity.class);
                        startActivity(feedIntent);
                        break;
                    case R.id.tab_me:        //ME tab
                        if ( session.isLoggedIn() ) {
                            finish();
                            Intent meIntent = new Intent(getApplicationContext(), MeActivity.class);
                            startActivity(meIntent);
                        }
                        else {
                            //not Login
                            Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivityForResult(loginIntent, REQUEST_CODE_LOGIN);
                        }
                        break;
                    default: break;
                }
            }
        });
        //bottom bar click again
        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_me:
                        if ( session.isLoggedIn() ) {
                            finish();
                            Intent meIntent = new Intent(getApplicationContext(), MeActivity.class);
                            startActivity(meIntent);
                        }
                        else {
                            //not Login
                            Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivityForResult(loginIntent, REQUEST_CODE_LOGIN);
                        }
                        break;
                    default:break;
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
        switch (item.getItemId()) {
            //Search
            case R.id.action_search:
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
                Intent iSearch = new Intent(this, SearchActivity.class);
                startActivity(iSearch);
                return true;

            //Open Setting Activity
            case R.id.action_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                Intent iSetting = new Intent(getApplicationContext(), SettingActivity.class);
                startActivityForResult(iSetting, REQUEST_CODE_SETTING);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_SETTING:
                //if theme of application is changed
                Boolean isChangedTheme = data.getBooleanExtra("isChangedTheme", false);
                if (isChangedTheme) {
                    finish();
                    startActivity(getIntent());
                }
                break;
            case REQUEST_CODE_LOGIN:
                if ( session.isLoggedIn() ) {
                    finish();
                    Intent meIntent = new Intent(getApplicationContext(), MeActivity.class);
                    startActivity(meIntent);
                }
                bottomBar.selectTabAtPosition(0);
                break;
            default:break;
        }
    }

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
