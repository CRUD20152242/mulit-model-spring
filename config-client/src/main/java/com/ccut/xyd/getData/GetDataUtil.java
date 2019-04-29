package com.ccut.xyd.getData;

import com.ccut.xyd.Start;
import com.ccut.xyd.configZkClient.ZkResults;
import com.google.gson.Gson;

import javax.xml.ws.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetDataUtil {
    private HashMap<String,List<String>> hashMap;
    private String path;
    public GetDataUtil(String path){
        this.path = path;
        hashMap = ZkResults.results.get(path);
    }

    public List<String>  getKey(){
        List<String> keys = new ArrayList<String>();
        for (String key1: hashMap.keySet()){
            keys.add(key1);
        }
        return  keys;
    }

    public String   getValue(String key){
        List<String> values = hashMap.get(key);
        return values.get(0);
    }

    public  HashMap<String,String> getK$V(String path){
        HashMap<String,String> k$v = new HashMap<String, String>();
        for (String key1: hashMap.keySet()){
            k$v.put(key1,hashMap.get(key1).get(0));
        }
        return k$v;
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>(2);
        list.add("v1");
        list.add("测试");

        Gson gson = new Gson();
        System.out.println(gson.toJson(list));
    }

}
