package com.bku.cse.karaoke.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bku.cse.karaoke.R;
import com.bku.cse.karaoke.fragment.FavoriteFragment;
import com.bku.cse.karaoke.fragment.MyRecordFragment;
import com.bku.cse.karaoke.fragment.SharedRecordFragment;
import com.bku.cse.karaoke.helper.SessionManager;
import com.bku.cse.karaoke.model.User;
import com.bku.cse.karaoke.rest.ApiClient;
import com.bku.cse.karaoke.util.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabSelectListener;

import org.w3c.dom.Text;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MeActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    SessionManager session;
    BottomBar bottomBar;
    TextView tv_name, tv_email;
    CircleImageView avatar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Utils.checkTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);

        tabLayout = (TabLayout) findViewById(R.id.me_tab_layout);
        viewPager = (ViewPager) findViewById(R.id.me_view_pager);
        setUpViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setCurrentItem(1);
        tabLayout.setupWithViewPager(viewPager);
        session = new SessionManager(getApplicationContext());
        bottomBar = (BottomBar) findViewById(R.id.bottombar);
        tv_name = (TextView) findViewById(R.id.me_tv_name);
        tv_email = (TextView) findViewById(R.id.me_tv_email);
        avatar = (CircleImageView) findViewById(R.id.me_avatar);

        //set User Info
        setUserInfo();

        //Set Me Selected
        bottomBar.selectTabAtPosition(2);

        //bottom Bar Listener
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_song_book:  //SONGBOOK tab
                        finish();
                        Intent sbIntent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(sbIntent);
                        break;
                    case R.id.tab_feed:    //FEED tab
                        finish();
                        Intent feedIntent = new Intent(getApplicationContext(), FeedActivity.class);
                        startActivity(feedIntent);
                        break;
                    case R.id.tab_me:       //ME tab
                        Toast.makeText(getApplicationContext(), "Me", Toast.LENGTH_SHORT).show();
                        break;
                    default: break;
                }
            }
        });
    }
    public void setUserInfo() {
        User user = session.getUserInfo();
        String avatar_path = user.getAvatar().equals("") ? User.AVATAR_DEFAULT : user.getAvatar();

        tv_name.setText(user.getName());
        tv_email.setText(user.getEmail());
        Glide.with(getApplicationContext()).load(ApiClient.BASE_URL + avatar_path)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(avatar);
    }

    //click 3 DOt TOP RIGHT on Me activity
    public void popupSetting(View view) {
        PopupMenu popup = new PopupMenu(getApplicationContext(), view);
        try {
            Field[] fields = popup.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popup);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        popup.getMenuInflater().inflate(R.menu.me_popup_setting, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.logout:
                        finish();
                        Toast.makeText(getApplicationContext(), "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        session.setLogin(false, null);

                        //start Home Activity
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        break;
                    default:break;
                }
                return true;
            }
        });
        popup.show();
    }

    public void setUpViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());;
        adapter.addFrag(new FavoriteFragment(), "✰ FAVORITE");
        adapter.addFrag(new MyRecordFragment(), "MY RECORD");
        adapter.addFrag(new SharedRecordFragment(), "SHARED RECORD");
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

    public void logout() {
        session.setLogin(false, null);
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
