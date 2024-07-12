package com.example.qrtest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity3 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
      DrawerLayout drawer;
      private CardView cd;
       String dataToSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home3);

//        Intent intent = getIntent();
//        Bundle bundle = new Bundle();
//        String temp = intent.getStringExtra("userId");
//        bundle.putString("userId",temp);
//
//        Editor editor = new Editor();
//        editor.setArguments(bundle);

//        Drafts drafts = new Drafts();
//        drafts.setArguments(bundle);

        Intent intent = getIntent();
        dataToSend = intent.getStringExtra("userId");


        Toolbar toolbar = findViewById(R.id.idToolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawerLayout);
        NavigationView navigationview = findViewById(R.id.navigationView);
        navigationview.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState==null)
        {
           // Profile fragment = Profile.newInstance(dataToSend);
            getSupportFragmentManager().beginTransaction().replace(R.id.idFragContainer,new Profile(dataToSend)).commit();
            navigationview.setCheckedItem(R.id.nav_dashboard);
        }



    }

    @Nullable
    @Override
   public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {

            if(item.getItemId()==R.id.nav_dashboard)
            {
                getSupportFragmentManager().beginTransaction().replace(R.id.idFragContainer,new Profile(dataToSend)).commit();
            }
            else if(item.getItemId()==R.id.nav_profile)
            {

                Profile1 fragment = Profile1.newInstance(dataToSend);
                getSupportFragmentManager().beginTransaction().replace(R.id.idFragContainer,fragment).commit();
            }
            else if(item.getItemId()==R.id.nav_gen)
            {

                Editor fragment = Editor.newInstance(dataToSend,"");
                getSupportFragmentManager().beginTransaction().replace(R.id.idFragContainer,fragment).commit();
            }


//           else if(item.getItemId()==R.id.nav_settings)
//            {
//                getSupportFragmentManager().beginTransaction().replace(R.id.idFragContainer,new Settings()).commit();
//            }
//           else if(item.getItemId()==R.id.nav_trash)
//            {
//                getSupportFragmentManager().beginTransaction().replace(R.id.idFragContainer,new Trash()).commit();
//            }
            else if(item.getItemId()==R.id.nav_editor)
            {
                Editor fragment = Editor.newInstance(dataToSend,"");
                getSupportFragmentManager().beginTransaction().replace(R.id.idFragContainer,fragment).commit();
            }
            else if(item.getItemId()==R.id.nav_drafts)
            {

                Drafts fragment = Drafts.newInstance(dataToSend);
                getSupportFragmentManager().beginTransaction().replace(R.id.idFragContainer,fragment).commit();
            }
            else if(item.getItemId()==R.id.nav_scan)
            {

                Scanner fragment = Scanner.newInstance(dataToSend);
                getSupportFragmentManager().beginTransaction().replace(R.id.idFragContainer,fragment).commit();
            }
            else if(item.getItemId()==R.id.nav_about)
            {
                Intent intent = new Intent(this,AboutApp.class);
                startActivity(intent);
               // getSupportFragmentManager().beginTransaction().replace(R.id.idFragContainer,new AppInfo()).commit();
            }

            else if(item.getItemId()==R.id.nav_logout)
            {
                Intent intent = new Intent(HomeActivity3.this, LogInn.class);
                startActivity(intent);
            }



           drawer.closeDrawer(GravityCompat.START);
           return true;
    }

    public void onBackPressed()
    {
        if(drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }
//@Override
//public void onBackPressed() {
//    moveTaskToBack(true);
//}


}