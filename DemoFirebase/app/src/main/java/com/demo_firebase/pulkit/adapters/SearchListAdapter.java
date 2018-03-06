package com.demo_firebase.pulkit.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.demo_firebase.pulkit.R;
import com.demo_firebase.pulkit.models.SearchFriend;
import java.util.List;

public class SearchListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SearchFriend> searchFriendList;
    private onClickButtonListener onClickButtonListener;
    private Context context;

    public interface onClickButtonListener {
        void onClickButton(int layoutPosition, int position, SearchFriend searchFriend);
    }

    public SearchListAdapter(Context context, List<SearchFriend> searchFriendList, onClickButtonListener onClickButtonListener) {
        this.context = context;
        this.searchFriendList = searchFriendList;
        this.onClickButtonListener = onClickButtonListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchFriendListHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_friend_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SearchFriendListHolder searchFriendListHolder = (SearchFriendListHolder) holder;

        searchFriendListHolder.tvName.setText(searchFriendList.get(position).getFullName());
        searchFriendListHolder.tvEmail.setText(searchFriendList.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return searchFriendList.size();
    }

    private class SearchFriendListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvName, tvEmail;

        private SearchFriendListHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tv_fullname);
            tvEmail = (TextView) itemView.findViewById(R.id.tv_email);

        }

        @Override
        public void onClick(View v) {
            onClickButtonListener.onClickButton(getLayoutPosition(), v.getId(), searchFriendList.get(getLayoutPosition()));
        }
    }

}
