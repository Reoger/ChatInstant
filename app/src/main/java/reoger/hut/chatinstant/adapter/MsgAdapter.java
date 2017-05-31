package reoger.hut.chatinstant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import reoger.hut.chatinstant.R;
import reoger.hut.chatinstant.bean.ChatMsg;


/**
 * Created by 24540 on 2017/5/23.
 *
 */

public class MsgAdapter extends ArrayAdapter<ChatMsg>{

    private int resourceId;

    public MsgAdapter(Context context, int textViewResourceId, List<ChatMsg> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMsg msg = getItem(position);
        View view = null;
        ViewHolder viewHolder;
        if(convertView == null) {
            try {
                view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            } catch (Exception e) {

                e.printStackTrace();
            }
            viewHolder = new ViewHolder();
            viewHolder.leftLayout = (LinearLayout)view.findViewById(R.id.left_layout);
            viewHolder.rightLayout = (LinearLayout)view.findViewById(R.id.right_layout);
            viewHolder.leftMsg = (TextView)view.findViewById(R.id.left_msg);
            viewHolder.rightMsg = (TextView)view.findViewById(R.id.right_msg);
            viewHolder.head1 = (ImageView)view.findViewById(R.id.head_left);
            viewHolder.head2 = (ImageView)view.findViewById(R.id.head_right);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        if(msg.getType() == ChatMsg.TYPE_RECEIVED) {
            viewHolder.leftLayout.setVisibility(View.VISIBLE);
            viewHolder.rightLayout.setVisibility(View.GONE);
            viewHolder.leftMsg.setText(msg.getContent());
            viewHolder.head2.setVisibility(View.GONE);
            viewHolder.head1.setVisibility(View.VISIBLE);
        } else if(msg.getType() == ChatMsg.TYPE_SEND) {
            viewHolder.rightLayout.setVisibility(View.VISIBLE);
            viewHolder.leftLayout.setVisibility(View.GONE);
            viewHolder.rightMsg.setText(msg.getContent());
            viewHolder.head2.setVisibility(View.VISIBLE);
            viewHolder.head1.setVisibility(View.GONE);
        }
        return view;
    }

    class ViewHolder {
        LinearLayout leftLayout;
        LinearLayout rightLayout;
        TextView leftMsg;
        TextView rightMsg;
        ImageView head1;
        ImageView head2;
    }

}
