package com.demo_firebase.pulkit.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo_firebase.pulkit.R;
import com.demo_firebase.pulkit.activities.ChatActivity;
import com.demo_firebase.pulkit.adapters.SearchListAdapter;
import com.demo_firebase.pulkit.models.SearchFriend;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pulkit on 30/8/17.
 */

public class SearchFriendFragment extends Fragment {

    private TextView tv_fullname, tv_email;

    private View view;
    private String user_name;
    private String user_email;

    private RecyclerView rv_search_friend_list;
    private SearchListAdapter searchListAdapter;
    private List<SearchFriend> searchFriendList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_search_friend, container, false);

        user_name = getArguments().getString("user_name");
        user_email = getArguments().getString("user_email");

        init();

        return view;
    }

    private void init() {

        tv_fullname = (TextView) view.findViewById(R.id.tv_fullname);
        tv_email = (TextView) view.findViewById(R.id.tv_email);
        rv_search_friend_list = (RecyclerView) view.findViewById(R.id.rv_search_friend_list);

        searchFriendList.add(new SearchFriend(user_name, user_email));

        rv_search_friend_list.setLayoutManager(new LinearLayoutManager(getContext()));
        searchListAdapter = new SearchListAdapter(getContext(), searchFriendList, new SearchListAdapter.onClickButtonListener() {
            @Override
            public void onClickButton(int position, int view, SearchFriend friendList) {

                Intent intent = new Intent(getActivity(), ChatActivity.class);
                startActivity(intent);

            }
        });
        rv_search_friend_list.setAdapter(searchListAdapter);
    }


}
