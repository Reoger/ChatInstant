package reoger.hut.chatinstant.utils;

import android.util.Log;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jxmpp.jid.DomainBareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.jid.parts.Localpart;

import java.net.InetAddress;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import reoger.hut.chatinstant.listener.ChatLogListener;

/**
 * Created by 24540 on 2017/5/23.
 * 连接服务器的基类
 */

public class XmppManager {
    public static final String server = "reoger.cc";
    private static final String HOST = "123.207.33.131";

    private static XMPPTCPConnection connection;
    private static ChatManager chatManager;

    /**
     * 获取一个单利的 XMPPTCPConnection 对象
     *
     * @return
     */
    public synchronized static XMPPTCPConnection getConnection() {
        if (connection == null) {
            connection = OenXMPPConnection();
        }
        Log.d("TAG", "是否连接" + connection.isConnected());
        return connection;
    }

    /**
     * 建立连接
     *
     * @return XMPPTCPConnection
     */
    private static XMPPTCPConnection OenXMPPConnection() {
                XMPPTCPConnection conn1 = null;
                try {
                    if (!isConnected()) {
                        InetAddress addr = InetAddress.getByName(HOST);
                        HostnameVerifier verifier = new HostnameVerifier() {
                            @Override
                            public boolean verify(String hostname, SSLSession session) {
                                return false;
                            }
                        };
                        DomainBareJid serviceName = JidCreate.domainBareFrom("localhost");
                XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                        .setHost(HOST)
//                        .setHostAddress()
                        .setPort(5222)
                        .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                        .setXmppDomain(serviceName)
                        .setHostnameVerifier(verifier)
                        .setHostAddress(addr)
                        .setDebuggerEnabled(true)
                        .build();
                conn1 = new XMPPTCPConnection(config);
                conn1.connect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn1;
    }

    /**
     * 关闭连接
     */
    public static void closeConnection() {
        if (isConnected()) {
            connection.disconnect();
            connection = null;
        }
    }

    public static boolean login(String user, String password) {
        if (isConnected()) {
            try {
                SASLAuthentication.unBlacklistSASLMechanism("PLAIN");
                SASLAuthentication.blacklistSASLMechanism("DIGEST-MD5");
                connection.login(user, password);
                connection.sendStanza(new Presence(Presence.Type.available));
                //添加监听事件
                ChatLogListener logListener = new ChatLogListener();
                getChatManager().addIncomingListener(logListener);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean register(String user, String password) {
        if (isConnected()) {
            try {
                AccountManager accountManager = AccountManager.getInstance(connection);
                accountManager.createAccount(Localpart.from(user), password);
                accountManager.sensitiveOperationOverInsecureConnection(true); //安全认证
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * 获取ChatManager对象
     *
     * @return
     */
    public static ChatManager getChatManager() {
        if (chatManager == null & isConnected()) {
            chatManager = ChatManager.getInstanceFor(connection);
        }
        return chatManager;
    }


    private static boolean isConnected() {
        return connection != null && connection.isConnected();
    }

}
