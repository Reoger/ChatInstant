package reoger.hut.chatinstant.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;

/**
 * Created by 24540 on 2017/5/27.
 * 储存到数据库的聊天
 */

@Entity
public class ChatLog {
    @Id(autoincrement = true)
    private Long id;

    //聊天对象的jib
    @NotNull
    private String jid;

    //标识是自己发送的消息还是对方发送的消息，true表示对方发送的消息
    private boolean other;

    //消息的主题内容
    private String content;

    //消息保存的事件
    private Date date;

    @Generated(hash = 481426084)
    public ChatLog(Long id, @NotNull String jid, boolean other, String content,
            Date date) {
        this.id = id;
        this.jid = jid;
        this.other = other;
        this.content = content;
        this.date = date;
    }

    @Generated(hash = 1994978153)
    public ChatLog() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJid() {
        return this.jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }

    public boolean getOther() {
        return this.other;
    }

    public void setOther(boolean other) {
        this.other = other;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    

}
