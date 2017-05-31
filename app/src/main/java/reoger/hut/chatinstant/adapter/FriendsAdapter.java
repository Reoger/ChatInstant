package reoger.hut.chatinstant.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jivesoftware.smack.roster.RosterEntry;

import java.util.ArrayList;
import java.util.List;

import reoger.hut.chatinstant.R;
import reoger.hut.chatinstant.bean.ItemHolder;

/**
 * Created by 24540 on 2017/5/26.
 * 适配qi
 */

public class FriendsAdapter extends RecyclerView.Adapter<ItemHolder> {
    private List<RosterEntry> firends = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;

    public FriendsAdapter(List<RosterEntry> firends, Context mContext) {
        this.firends = firends;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_friend,parent,false);
        ItemHolder itemHolder = new ItemHolder(view);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {

        holder.imageView.setImageResource(R.drawable.ic_people_green_24dp);
        holder.textView.setText(firends.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return firends.size();
    }

}
