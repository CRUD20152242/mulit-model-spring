package com.ccut.xyd.Vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@Setter
@Getter
@ToString
public class PathPo {
    /**
     * path:查询指定path需要用
     */
    private String path;
    /**
     * 范围 all查询所有的 nolyMyself只查关联用户本人  onlyOne仅仅搜索目标path
     */
    private String scope;

    /**
     * 存储查询结果
     */
    private List<String> results;

//    public static void main(String[] args) {
//        PathPo getPathPo = new PathPo();
//        getPathPo.setPath("测试lombok插件成功");
//        System.out.println(getPathPo.getPath());
//    }

}
