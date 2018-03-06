package com.demo_firebase.pulkit;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo_firebase.pulkit.authrization.SignInActivity;
import com.demo_firebase.pulkit.fragments.FriendListFragment;
import com.demo_firebase.pulkit.fragments.SearchFriendFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;

    private RelativeLayout rel_friend_list, rel_manage_account, rel_change_password, rel_logout;
    private TextView toolbar_name;
    private TextView fa_text, fa_user, fa_lock, fa_sign_out;
    private Typeface fontAwesomeFont;

    private EditText et_search;
    private Button btn_search;
    private String str_search;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        setFontAwesomeFont();
        init();

    }

    private void init() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        rel_friend_list = (RelativeLayout) findViewById(R.id.rel_friend_list);
        rel_manage_account = (RelativeLayout) findViewById(R.id.rel_manage_account);
        rel_change_password = (RelativeLayout) findViewById(R.id.rel_change_password);
        rel_logout = (RelativeLayout) findViewById(R.id.rel_logout);
        et_search = (EditText) findViewById(R.id.et_search);
        btn_search = (Button) findViewById(R.id.btn_search);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new FriendListFragment()).commit();

        rel_friend_list.setOnClickListener(this);
        rel_manage_account.setOnClickListener(this);
        rel_change_password.setOnClickListener(this);
        rel_logout.setOnClickListener(this);
        btn_search.setOnClickListener(this);
    }

    public void setFontAwesomeFont() {

        fa_text = (TextView) findViewById(R.id.fa_text);
        fa_user = (TextView) findViewById(R.id.fa_user);
        fa_lock = (TextView) findViewById(R.id.fa_sign_out);
        fa_sign_out = (TextView) findViewById(R.id.fa_lock);
        fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fonts/fontawesome-webfont.ttf");
        fa_user.setTypeface(fontAwesomeFont);
        fa_text.setTypeface(fontAwesomeFont);
        fa_lock.setTypeface(fontAwesomeFont);
        fa_sign_out.setTypeface(fontAwesomeFont);

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.rel_friend_list) {

            getSupportFragmentManager().beginTransaction().replace(R.id.container, new FriendListFragment()).commit();
            drawerLayout.closeDrawers();

        } else if (view.getId() == R.id.rel_manage_account) {

//            toolbar_name.setText("Manage Accounts");
//            drawerLayout.closeDrawers();
//            getSupportFragmentManager().beginTransaction().replace(R.id.container, new NewEmployeeFragment()).commit();
            toolbar_name.setText("New Employee");
            drawerLayout.closeDrawers();

        } else if (view.getId() == R.id.rel_change_password) {

//            getSupportFragmentManager().beginTransaction().replace(R.id.container, new FirstFormFragment()).commit();
            toolbar_name.setText("Manage Accounts");
            drawerLayout.closeDrawers();

        } else if (view.getId() == R.id.rel_logout) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Are you sure, you want to logout?");
            builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    firebaseAuth.signOut();

                    Intent intent = new Intent(DashboardActivity.this, SignInActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.show();
        } else if (view.getId() == R.id.btn_search) {

            str_search = et_search.getText().toString();
            searchUser(str_search);
        }
    }

    private void searchUser(final String str_search) {

        databaseReference.child("user_info");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    String user_name = ((HashMap)((HashMap)postSnapshot.getValue()).get("user_info")).get("name").toString();
                    String user_email = ((HashMap)((HashMap)postSnapshot.getValue()).get("user_info")).get("user_email").toString();

                    if (user_email.contains(str_search)) {

                        SearchFriendFragment searchFriendFragment = new SearchFriendFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("user_name", user_name);
                        bundle.putString("user_email", user_email);
                        searchFriendFragment.setArguments(bundle);

                        getSupportFragmentManager().beginTransaction().replace(R.id.container, searchFriendFragment).commit();
                    }
                }
                if(!et_search.equals("")){
                    et_search.setText("");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}


