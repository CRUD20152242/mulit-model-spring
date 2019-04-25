package com.ccut.xyd;

public class TestImpl implements testServer
{
    public void printMessage() {
        System.out.println("你好！！！");
    }

    public static void main(String[] args) {
        System.out.println("ok");
        new Test().test();
    }
}
