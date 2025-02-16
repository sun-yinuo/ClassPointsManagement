package com.martin.cpmbackend;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentEnumTest {



    @Test
    void getNameByStudentId() {
        System.out.println(StudentEnum.getNameByStudentId(1));
    }

    @Test
    void getStudentIdByName() {
        System.out.println(StudentEnum.getStudentIdByName("孙一诺"));
    }

    @Test
    void containsName() {
        System.out.println(StudentEnum.containsName("孙一诺"));
        System.out.println(StudentEnum.containsName("一"));
    }
}