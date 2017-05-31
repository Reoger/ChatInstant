package reoger.hut.chatinstant.bean;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by 24540 on 2017/5/30.
 *
 */

public class ChatMessage implements Parcelable {


    private String uuid;

    protected ChatMessage(Parcel in) {
        uuid = in.readString();
    }

    public static final Creator<ChatMessage> CREATOR = new Creator<ChatMessage>() {
        @Override
        public ChatMessage createFromParcel(Parcel in) {
            return new ChatMessage(in);
        }

        @Override
        public ChatMessage[] newArray(int size) {
            return new ChatMessage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uuid);
    }
}
