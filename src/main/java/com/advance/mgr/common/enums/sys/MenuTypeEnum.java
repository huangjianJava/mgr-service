package com.advance.mgr.common.enums.sys;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author huangj
 * @Description: 菜单类型
 * @date 2018/7/3
 */
public enum MenuTypeEnum {
    DIRECTORY(1,"目录"), MENU(2,"菜单"), BUTTON(3,"按钮");

    MenuTypeEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static MenuTypeEnum parse(int value){
        for(MenuTypeEnum type:values()){
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
