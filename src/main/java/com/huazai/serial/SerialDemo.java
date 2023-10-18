package com.huazai.serial;

import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONUtil;
import lombok.Data;

import java.io.*;

/**
 * @author huazai
 * @date 2023/10/10
 */
public class SerialDemo {
    final static String PATH = "./userObj.txt";

    public static void main(String[] args) throws IOException {
        final User user = new User();
        user.setName("test");
        user.setAge(20);
        IoUtil.writeObj(new ObjectOutputStream(new FileOutputStream(new File(PATH))), true, user);

        final User user1 = IoUtil.readObj(new FileInputStream(new File(PATH)), User.class);
        System.out.println("user1 = " + JSONUtil.toJsonStr(user1));
    }
}

@Data
class User implements Serializable {
    private static final long serialVersionUID = -5430348103376135943L;
    private String name;
    private int age;
}
