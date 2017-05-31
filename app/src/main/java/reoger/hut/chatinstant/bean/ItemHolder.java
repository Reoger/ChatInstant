package reoger.hut.chatinstant.bean;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import reoger.hut.chatinstant.R;

/**
 * Created by 24540 on 2017/5/26.
 */

public class ItemHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;
    public TextView textView;

    public ItemHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.imageView2);
        textView = (TextView) itemView.findViewById(R.id.fiends_name);
    }
}
