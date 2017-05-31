package reoger.hut.chatinstant.interfaces;

import java.util.List;

import reoger.hut.chatinstant.bean.ChatMsg;

/**
 * Created by 24540 on 2017/5/26.
 */

public interface IControlChat {
    public interface IPrsenter{
        void initData();
        void sendChatMessage(String body);
        void deleteDataInDb();
    }

    public interface  iView{
        void initDataFromDb(List<ChatMsg> msgs);

    }
}
