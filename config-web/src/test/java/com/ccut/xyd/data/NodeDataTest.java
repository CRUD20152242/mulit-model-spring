package com.ccut.xyd.data;

import com.ccut.xyd.Vo.NodePo;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class NodeDataTest {

    @Autowired
    NodeData nodeData;

    HashMap<String,String> hashMap;
    String data;
    NodePo nodePo;
    @Before
    public  void init(){
        nodePo = new NodePo();
        hashMap= new HashMap<String, String>(30);
        hashMap.put("key1","value1");
        hashMap.put("key2","value2");
        hashMap.put("key3","value3");



        Gson gson = new Gson();
        data = gson.toJson(hashMap);

        nodePo.setPath("/p");
        String addData = "{\"默认key\":[\"默认的value\",\"默认测试数据 添加数据时会覆盖\"],\"默认的key2\":[\"默认的value\",\"默认测试数据 添加数据时会覆盖\"]}";
        try {
            String data = new String(addData.getBytes("utf-8"),"utf-8");
            nodePo.setDatas(addData);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void addData() {
        String str = nodeData.addData(nodePo).getMesaage();
        System.out.println(str);
    }

    @Test
    public void getData() {
        String res = nodeData.getData(nodePo).toString();
        System.out.println(res);
        Gson gson = new Gson();
        hashMap = gson.fromJson(res,HashMap.class);
    }
}