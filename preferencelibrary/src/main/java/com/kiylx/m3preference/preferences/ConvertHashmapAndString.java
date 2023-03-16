package com.kiylx.m3preference.preferences;

import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.HashMap;

/**
 * 创建者 kiylx
 * 创建时间 2020/5/5 14:29
 */
public class ConvertHashmapAndString {
    /**
     *把hashmap通过ObjectOutPutStream(byteArrayOutPutStream)输出到字节数组，再把字节数组解析成base64编码的字符串
     *
     */
    public static String TransformHashMapToStr(HashMap<String, String> map) throws IOException {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        ObjectOutputStream outputStream = new ObjectOutputStream(byteArray);
        outputStream.writeObject(map);
        String result = Base64.encodeToString(byteArray.toByteArray(), Base64.DEFAULT);
        outputStream.close();
        return result;
    }
    /**
     *
     * @param orign
     * @return
     * @throws StreamCorruptedException 从对象流中读取的控制信息违反内部一致性检查时抛出。
     * @throws ClassNotFoundException
     * @throws IOException
     * <p>
     *     把字符串解码成字节数组，然后放进ObjectInputStream(new ByteArrayInputStream)中，使用readObject读取对象，反序列化
     */
    public static HashMap<String, String> TransformStrToHashMap(String orign) throws StreamCorruptedException, ClassNotFoundException, IOException {
        byte[] origan = Base64.decode(orign, Base64.DEFAULT);
        ByteArrayInputStream inArray = new ByteArrayInputStream(origan);
        ObjectInputStream inputStream = new ObjectInputStream(inArray);
        HashMap<String, String> result = (HashMap<String, String>) inputStream.readObject();
        inputStream.close();
        return result;
    }
}
