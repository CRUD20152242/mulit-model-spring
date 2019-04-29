package com.ccut.xyd.getData;

import com.ccut.xyd.configZkClient.ZkParameter;
import com.ccut.xyd.configZkClient.ZkResults;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;

import java.util.HashMap;
import java.util.List;

@Slf4j
public class NodeWatcher implements NodeCacheListener {

    private String path;
    private NodeCache nodeCache;
    public NodeWatcher(String path,NodeCache nodeCache){
        this.path = path;
        this.nodeCache =nodeCache;
    }
    public void nodeChanged() throws Exception {
        String datas = new String(nodeCache.getCurrentData().getData(),"GBK");
        Gson gson = new Gson();
        HashMap<String, List<String>> keys = gson.fromJson(datas,HashMap.class);
        ZkResults.results.put(path,keys);
        log.info("服务端数据发生变化 path={},datas = {}",path,datas);
    }
}
