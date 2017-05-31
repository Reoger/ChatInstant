package reoger.hut.chatinstant.utils;

import java.security.MessageDigest;

/**
 * Created by 24540 on 2017/5/28.
 * md5 为String 生成唯一的表示符
 *
 */

public class MD5 {
    public static String getStringMD5(String val)  {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(val.getBytes());
            byte[] m = md5.digest();//加密

        return getString(m);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int getIntMD5(String val)  {
        try {
            MessageDigest md5 =  java.security.MessageDigest.getInstance("MD5");
            md5.update(val.getBytes());
            byte[] m = md5.digest();//加密

            return getInt(m,0);
        } catch (Exception e) {
            e.printStackTrace();
            log.e(e);
            return 0;
        }
    }

    private static int getInt(byte[] ary, int offset) {
        int value;
        value = (int) ((ary[offset]&0xFF)
                | ((ary[offset+1]<<8) & 0xFF00)
                | ((ary[offset+2]<<16)& 0xFF0000)
                | ((ary[offset+3]<<24) & 0xFF000000));
        log.e("这里的实质是："+value);
        return value;
    }

    private static String getString(byte[] b){
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < b.length; i ++){
            sb.append(b[i]);
        }
        return sb.toString();
    }

    /**
     * 将String转换成int
     * @param jid 要转化的String
     * @return  转化后的int
     */
    public static int StringToInt(String jid){
        byte[] m = jid.getBytes();
        return getInt(m,0);
    }
}
