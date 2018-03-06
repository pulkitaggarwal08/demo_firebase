package com.demo_firebase.pulkit.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo_firebase.pulkit.activities.ChatActivity;
import com.demo_firebase.pulkit.adapters.FriendListAdapter;
import com.demo_firebase.pulkit.R;
import com.demo_firebase.pulkit.models.FriendList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pulkit on 29/8/17.
 */

public class FriendListFragment extends Fragment {

    private RecyclerView rv_friend_list;
    private FriendListAdapter friendListAdapter;
    private List<FriendList> friendLists = new ArrayList<>();

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_friend_list, container, false);

        init();

        return view;
    }

    private void init() {

        rv_friend_list = (RecyclerView) view.findViewById(R.id.rv_friend_list);


        rv_friend_list.setLayoutManager(new LinearLayoutManager(getContext()));
        friendListAdapter = new FriendListAdapter(getContext(), friendLists, new FriendListAdapter.onClickButtonListener() {
            @Override
            public void onClickButton(int position, int view, FriendList friendList) {

                Intent intent = new Intent(getActivity(), ChatActivity.class);
                startActivity(intent);

            }
        });
        rv_friend_list.setAdapter(friendListAdapter);

    }


}
