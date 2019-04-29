package com.ccut.xyd.zookeeper.impl;

import com.ccut.xyd.GetZKClient;
import com.ccut.xyd.zookeeper.ZkNodeOp;
import com.google.gson.Gson;
import org.apache.curator.framework.CuratorFramework;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;

import static org.junit.Assert.*;

//@RunWith(SpringJUnit4ClassRunner.class)
public class ZkNodeImplTest {

    CuratorFramework zkClient;

    @Autowired
    ZkNodeOp zkNodeOp;
    @Before
    public void init(){
        zkClient = GetZKClient.getZkClient();
    }

    public void deletePath(){
        zkNodeOp.deletePath("");
    }

    @Test
    public void createPath() throws Exception {
        try {
//            String have = zkClient.create().creatingParentsIfNeeded().forPath("/p","p".getBytes());

//            String haveNo = zkClient.create().creatingParentsIfNeeded().forPath("/pp/ppp/ppppp","ppp".getBytes());
//
////            System.out.println("对于不存在的节点 返回的是+"+have);
//            System.out.println("对于存在的节点 返回的是+"+haveNo);
            System.out.println("删除不存在的节点："+zkClient.delete().forPath("/1234"));


        } catch (Exception e) {
            e.printStackTrace();
            try {
                System.out.println("删除存在的节点："+zkClient.delete().forPath("/temp"));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            System.out.println("删除参数错误的节点："+zkClient.delete().forPath("temp"));
        }
    }

    @Test
    public void testHashMap(){
        HashMap<String,String> hashMap = new HashMap();
        hashMap.put("key1","value1");
        hashMap.put("key2","value2");
        hashMap.put("key3","value3");

        Gson gson = new Gson();
        String str = gson.toJson(hashMap);
        System.out.println("转换后的json为");
        System.out.println(str);

        System.out.println("然后含有相同的key 转化后的结果为");

        String nstr = str.replace("key2","key1");

        System.out.println("替换后的返回值时"+nstr);
        System.out.println("str = "+str);
        hashMap = gson.fromJson(nstr,HashMap.class);

        for (String key:hashMap.keySet()) {
            System.out.println(hashMap.get(key));
        }


    }
}