package com.ccut.xyd;

import com.ccut.xyd.configZkClient.ZkParameter;
import com.ccut.xyd.configZkClient.ZkResults;
import com.ccut.xyd.getData.GetData;
import com.ccut.xyd.getData.GetDataUtil;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Start {
    public String info ="1. 引入本包后设置ZkParameter的path的值" +
            "            2. 设置host 可选" +
            "            3. 调用Start.begin" +
            "            4. 从ZkResults根据path获取值" +
            "            5. GetDataUtil 可获取对应的k-v";

    public static  void begin(){
        Executor executor = Executors.newFixedThreadPool(10);
        GetData getData = new GetData(executor);
        getData.initZk();
        getData.getDatasOnChange();
    }

    public static void main(String[] args) {
        ZkParameter.path.add("/p");
        Start.begin();
        GetDataUtil getDataUtil = new GetDataUtil("/p");
        System.out.println("p节点的数据是-----------------：");
        System.out.println(   getDataUtil.getValue("p12"));

    }

}
