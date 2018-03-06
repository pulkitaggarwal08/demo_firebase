package com.demo_firebase.pulkit.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo_firebase.pulkit.R;
import com.demo_firebase.pulkit.models.FriendList;

import java.util.List;

/**
 * Created by pulkit on 29/8/17.
 */

public class FriendListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<FriendList> friendLists;
    private onClickButtonListener onClickButtonListener;
    private Context context;

    public interface onClickButtonListener {
        void onClickButton(int layoutPosition, int position, FriendList friendList);
    }

    public FriendListAdapter(Context context, List<FriendList> friendLists, FriendListAdapter.onClickButtonListener onClickButtonListener) {
        this.context = context;
        this.friendLists = friendLists;
        this.onClickButtonListener = onClickButtonListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FriendListHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_friend_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FriendListHolder friendListHolder = (FriendListHolder) holder;

        friendListHolder.tvName.setText(friendLists.get(position).getFullName());
        friendListHolder.tvEmail.setText(friendLists.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return friendLists.size();
    }

    private class FriendListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvName, tvEmail;

        private FriendListHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tv_fullname);
            tvEmail = (TextView) itemView.findViewById(R.id.tv_email);

        }

        @Override
        public void onClick(View v) {
            onClickButtonListener.onClickButton(getLayoutPosition(), v.getId(), friendLists.get(getLayoutPosition()));
        }
    }

}
