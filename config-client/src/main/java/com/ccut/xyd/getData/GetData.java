package com.ccut.xyd.getData;

import com.ccut.xyd.configZkClient.ZKCli;
import com.ccut.xyd.configZkClient.ZkParameter;
import com.ccut.xyd.configZkClient.ZkResults;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 注册监听器监听客户端状态
 */
@Slf4j
public class GetData {


    private Executor executor;

    public GetData(Executor executor){
        this.executor = executor;
    }

    /**
     * 初次调用时轮询
     * @return
     */
    public void initZk(){

        String datas;

            for (String path:ZkParameter.path) {
                try {
                datas =  new String(ZKCli.client.getData().forPath(path),"GBK");
                log.info("初次加载zkclient 从服务端获取数据成功path = {}, datas={}",path,datas);
//                由于hashMap的put操作时覆盖操作 所以不会报错会覆盖 所以时最新的
                    Gson gson = new Gson();
                    HashMap<String, List<String>> keys = gson.fromJson(datas,HashMap.class);
                ZkResults.results.put(path,keys);
                } catch (Exception e) {
                    log.error("初次加载zkclient 从服务端获取数据异常 datas={}");
                    e.printStackTrace();
                    getDatasOnChange(path);
                }
            }


    }

    public void getDatasOnChange(String path){
            log.info("准备监听：patH = "+path);
            ListenTask listenTask = new ListenTask(path);
            executor.execute(listenTask);
    }

    public void getDatasOnChange(){
            for (String path:ZkResults.results.keySet()) {
                log.info("准备监听：patH = "+path);
                ListenTask listenTask = new ListenTask(path);
                executor.execute(listenTask);
            }
    }

}
