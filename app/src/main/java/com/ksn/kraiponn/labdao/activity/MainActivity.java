package com.ksn.kraiponn.labdao.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.ksn.kraiponn.labdao.R;
import com.ksn.kraiponn.labdao.fragment.MainFragment;
import com.ksn.kraiponn.labdao.fragment.SecondFragment;
import com.ksn.kraiponn.labdao.fragment.TestProductFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements MainFragment.onOkHttpClickListener,
        MainFragment.onIncomeExpenseClickListener{

    /*****************************
     *  Variable Zone
     ****************************/
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navView;

    private final int SCAN_CODE = 999;
    //private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 21) {
            requestRunTimePermission();
        } else {
            //
        }

        initInstance();
        if (savedInstanceState == null) {
            MainFragment fm = MainFragment.newInstance();
            openFragment(fm);
        }
    }



    /*****************************
     *  Override Method Zone
     ****************************/

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onOkHttpClicked() {
        startActivity(new Intent(
                MainActivity.this,
                MainIncomeExpenseActivity.class
        ));
        overridePendingTransition(
                R.anim.slide_in_right, R.anim.slide_out_left
        );
    }

    @Override
    public void onIncomeExpenseClick() {
        startActivity(new Intent(
                MainActivity.this,
                MainIncomeExpenseActivity.class
        ));
        overridePendingTransition(
                R.anim.slide_in_right, R.anim.slide_out_left
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item;
        SpannableStringBuilder builder;
        Drawable drawable;
        ImageSpan imageSpan;

        for (int i=0; i<menu.size(); i++) {
            item = menu.getItem(i);
            builder = new SpannableStringBuilder()
                    .append("      ")
                    .append(item.getTitle());

            if (item.getIcon() != null &&
                    item.getIcon().getConstantState() != null) {
                drawable = item.getIcon().getConstantState().newDrawable();
                drawable.setBounds(
                        0, 0,
                        drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight()
                );

                imageSpan = new ImageSpan(drawable);
                builder.setSpan(imageSpan, 0, 1,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                item.setTitle(builder);
            }
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.action_settings:{
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                R.anim.from_bottom, R.anim.to_top,
                                R.anim.from_top, R.anim.to_bottom
                        )
                        .replace(R.id.contentContainer,
                                MainFragment.newInstance())
                        .commit();
                return true;
            }
            case R.id.action_second_fragment: {
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                R.anim.from_bottom, R.anim.to_top,
                                R.anim.from_top, R.anim.to_bottom
                        )
                        .replace(R.id.contentContainer,
                                SecondFragment.newInstance())
                        .commit();
                return true;
            }
            case R.id.action_scanner:{
                Intent itn = new Intent(
                        MainActivity.this,
                        ScanCodeActivity.class
                );
                startActivityForResult(itn, 999);
                overridePendingTransition(
                        R.anim.slide_in_right,
                        R.anim.slide_out_left
                );
                return true;
            }

            case R.id.action_custom_spinner:{
                startActivity(new Intent(
                        MainActivity.this,
                        CustomSpinnerActivity.class
                ));
                overridePendingTransition(
                        R.anim.slide_in_right,
                        R.anim.slide_out_left
                );
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCAN_CODE && resultCode == RESULT_OK) {
            String res = data.getStringExtra("result_scan");
            Toast.makeText(this,
                    "Result " + res,
                    Toast.LENGTH_SHORT).show();
        }
    }

    /*****************************
     *  Custom Method Zone
     ****************************/
    private void initInstance() {
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        navView = findViewById(R.id.navView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.open_menu, R.string.close_menu
        );

        navView.setNavigationItemSelectedListener(navViewItemClick);
        //btn.setOnClickListener(btnOkHttpClickListener);
    }

    private void requestRunTimePermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        //
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(
                            List<PermissionRequest> permissions,
                            PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void showToast(String text) {
        Toast.makeText(this,
                text,
                Toast.LENGTH_SHORT).show();
    }

    private void openFragment(Fragment fm) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in_right, R.anim.slide_out_left,
                        R.anim.slide_in_left, R.anim.slide_out_right
                )
                .replace(R.id.contentContainer,
                        fm)
                .commit();
    }


    /*****************************
     *  Listener Zone
     ****************************/
    NavigationView.OnNavigationItemSelectedListener navViewItemClick = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            drawerLayout.closeDrawer(GravityCompat.START);

            switch (id) {
                case R.id.menu_profile:{
                    MainFragment fm = MainFragment.newInstance();
                    openFragment(fm);
                    return true;
                }
                case R.id.menu_notification:{
                    SecondFragment fm = SecondFragment.newInstance();
                    openFragment(fm);
                    return true;
                }
                case R.id.menu_products:{
                    TestProductFragment fm = TestProductFragment.newInstance();
                    openFragment(fm);
                    return true;
                }
            }

            return true;
        }
    };



}
