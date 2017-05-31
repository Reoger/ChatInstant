package reoger.hut.chatinstant.listener;

import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;

import reoger.hut.chatinstant.utils.log;

/**
 * Created by 24540 on 2017/5/27.
 */

public class SmackChatManagerListener implements ChatManagerListener {

    @Override
    public void chatCreated(Chat chat, boolean createdLocally) {
        chat.addMessageListener(new ChatMessageListener() {
            @Override
            public void processMessage(Chat chat, Message message) {
                log.d(message.getBody()+"--*-*-*-*-*-*****************");

            }
        });
    }
}
