package com.ccut.xyd.zookeeper.impl;

import com.ccut.xyd.GetZKClient;
import com.ccut.xyd.zookeeper.ZkNodeOp;
import org.apache.curator.framework.CuratorFramework;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
//加载配置文件位置
//@ContextConfiguration("classpath:spring-config.xml")
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
}