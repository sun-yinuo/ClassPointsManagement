package com.martin.cpmbackend;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.HashSet;
import java.util.Set;

/**
 * 学生枚举
 * @author sunyinuo
 */
public class StudentEnum {
    private static final BiMap<Integer, String> studentMap = HashBiMap.create();
    private static final Set<String> nameSet = new HashSet<>();

    static {
        studentMap.put(0, "班主任");

        studentMap.put(1, "曹燕琳");
        studentMap.put(2, "陈雨涵");
        studentMap.put(3, "董漪澜");
        studentMap.put(4, "何思萱");
        studentMap.put(5, "何易璇");
        studentMap.put(6, "黄诗禹");
        studentMap.put(7, "姜乐幽");
        studentMap.put(8, "姜若瑶");
        studentMap.put(9, "李采扬");
        studentMap.put(10, "李思锐");
        studentMap.put(11, "李翊萱");
        studentMap.put(12, "鲁晨熙");
        studentMap.put(13, "庞宇轩");
        studentMap.put(14, "彭怡然");
        studentMap.put(15, "孙炜琦");
        studentMap.put(16, "孙一铎");
        studentMap.put(17, "王涵钥");
        studentMap.put(18, "王若曦");
        studentMap.put(19, "吴旻");
        studentMap.put(20, "吴一凡");
        studentMap.put(21, "肖佳妮");
        studentMap.put(22, "谢艾希");
        studentMap.put(23, "张天语");
        studentMap.put(24, "张怡恬");
        studentMap.put(25, "曹溢");
        studentMap.put(26, "高晓斯");
        studentMap.put(27, "郭子禾");
        studentMap.put(28, "何简至");
        studentMap.put(30, "黄琛贺");
        studentMap.put(31, "金骏浩");
        studentMap.put(33, "刘博渊");
        studentMap.put(34, "陆俊毅");
        studentMap.put(35, "任镕均");
        studentMap.put(36, "施皓璋");
        studentMap.put(37, "孙一诺");
        studentMap.put(38, "孙羿博");
        studentMap.put(40, "魏子博");
        studentMap.put(41, "吴明灏");
        studentMap.put(42, "谢金熠");
        studentMap.put(43, "杨子轩");
        studentMap.put(44, "袁振林");
        studentMap.put(45, "周子轩");
    }

    /**
     * 根据学号查找姓名
     */
    public static String getNameByStudentId(int studentId) {
        return studentMap.get(studentId);
    }

    /**
     * 根据姓名查找学号
     */
    public static Integer getStudentIdByName(String name) {
        return studentMap.inverse().get(name);
    }

    /**
     * 判断姓名是否在名单中
     */
    public static boolean containsName(String name) {
        return studentMap.inverse().containsKey(name);
    }

}
