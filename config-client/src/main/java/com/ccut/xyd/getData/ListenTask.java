package com.ccut.xyd.getData;

import com.ccut.xyd.configZkClient.ZKCli;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.recipes.cache.NodeCache;

@Slf4j
public class ListenTask implements Runnable {
    private  String path;


    public void run() {
        NodeCache nodeCache = new NodeCache(ZKCli.client,path);
        nodeCache.getListenable().addListener(new NodeWatcher(path,nodeCache));
        try {
            nodeCache.start();
        } catch (Exception e) {
            log.info("path监听器启动异常");
            e.printStackTrace();
        }
    }

    public ListenTask(String path){
        this.path = path;
    }
}
