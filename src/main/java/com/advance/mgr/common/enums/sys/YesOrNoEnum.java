package com.advance.mgr.common.enums.sys;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author huangj
 * @Description: YES OR NO
 * @date 2018/7/3
 */
public enum YesOrNoEnum {
    NO(0,"否"), YES(1,"是");

    YesOrNoEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static YesOrNoEnum parse(int value){
        for(YesOrNoEnum type:values()){
            if(type.getValue() == value){
                return type;
            }
        }
        return null;
    }

    public static boolean isContain(int value){
        return getEnumAllValues().contains(value);
    }

    public static List<Integer> getEnumAllValues(){
        return Arrays.stream(values())
                .map(e -> e.getValue())
                .collect(Collectors.toList());
    }

    private int value;

    private String name;

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

}
