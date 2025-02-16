package com.martin.cpmbackend.utils.md5;

import java.util.Random;

/**
 * @author sunyinuo
 */
public class Salt {

    public static Integer salt(){
        return new Random().nextInt();
    }

}
