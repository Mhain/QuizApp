package com.example.mhainulhoque.quizapp;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.mhainulhoque.quizapp.Model.Category;

public class Home extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

    bottomNavigationView=(BottomNavigationView)findViewById(R.id.navigation);

    bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
        @Override
        public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
            Fragment selectFragment=null;
            switch (menuItem.getItemId())
            {
                case R.id.action_category:
                selectFragment=CategoryFragment.newInstance();
                break;
                case R.id.action_ranking:
                    selectFragment=RankingFragment.newInstance();
                    break;
            }
            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout,selectFragment);
            transaction.commit();
            return ;
        }

    });
setDefaultFragment();
  }


public void setDefaultFragment(){
 FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
transaction.replace(R.id.frame_layout,CategoryFragment.newInstance());
transaction.commit();
    }

}
