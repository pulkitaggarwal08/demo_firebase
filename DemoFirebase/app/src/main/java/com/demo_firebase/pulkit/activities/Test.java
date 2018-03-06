package com.demo_firebase.pulkit.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.demo_firebase.pulkit.R;
import com.demo_firebase.pulkit.fragments.SearchFriendFragment;
import com.demo_firebase.pulkit.models.SearchFriend;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.api.model.StringList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * Created by pulkit on 31/8/17.
 */

public class Test extends Fragment {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    View view;
    EditText et_test;
    Button done, search;
    TextView getData;
    String str_test, str_uid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_test, container, false);

        et_test = (EditText) view.findViewById(R.id.et_test);
        done = (Button) view.findViewById(R.id.done);
        search = (Button) view.findViewById(R.id.search);
        getData = (TextView) view.findViewById(R.id.getData);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        str_uid = firebaseAuth.getCurrentUser().getUid();

        init();

        return view;
    }

    private void init() {

        final DatabaseReference databaseReference1 = databaseReference.child(str_uid);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                str_test = et_test.getText().toString();

                DatabaseReference databaseReference2 = databaseReference1.child("test_data").push();

                Map<String, Object> stringStringMap = new HashMap<String, Object>();
                stringStringMap.put("my_data", str_test);
                databaseReference2.updateChildren(stringStringMap);

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str_test = et_test.getText().toString();

                final SearchFriend searchFriend = new SearchFriend();
                databaseReference.child("test_data").setValue(searchFriend);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                            Iterator iterator = ((HashMap) postSnapshot.getValue(SearchFriend.class).getTest_data()).keySet().iterator();
                            while (iterator.hasNext()) {
                                String name = (String) ((HashMap) ((HashMap) postSnapshot.getValue(SearchFriend.class).getTest_data()).get(iterator.next())).get("my_data");
                                if (name.contains(str_test)) {
                                    Log.v("oye ye le", name);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

    }

}
