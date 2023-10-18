package com.huazai.lamda;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.json.JSONUtil;

/**
 * @author huazai
 * @date 2023/10/11
 */
public class LamdaTest {
    public static void main(String[] args) {
        System.out.println("args = " + JSONUtil.toJsonStr(ListUtil.of("aa", "bb")));
    }
}
