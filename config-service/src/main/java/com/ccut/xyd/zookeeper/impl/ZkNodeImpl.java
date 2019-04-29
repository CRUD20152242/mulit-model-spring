package com.ccut.xyd.zookeeper.impl;

import com.ccut.xyd.GetZKClient;
import com.ccut.xyd.Vo.NodePo;
import com.ccut.xyd.zookeeper.ZkNodeOp;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ZkNodeImpl implements ZkNodeOp {

    /**
     * 获取所有的节点
     * @return
     */
    public List<String> getPaths(String path) {
        if (StringUtils.isEmpty(path)){
            path="/";
        }
        List<String> paths = new ArrayList<String>();
        if (!path.contains("/")){
            paths.add("error");
            paths.add("miss /");
            return paths;
        }

        this.getpaths(GetZKClient.getZkClient(),path,paths);
        log.info("获取所有的path成功 paths.size = {}",paths.size());
        return paths;
    }

    public String createPath(NodePo nodePo, String res) {
        String path = nodePo.getPath();
        log.info("准备创建节点 path = {}",path);
        CuratorFramework zkCli = GetZKClient.getZkClient();
        String gerRes = "init";
        try {
            if (StringUtils.isNotEmpty(nodePo.getDatas())){
                gerRes = zkCli.create().forPath(path,nodePo.getDatas().getBytes("GBK"));
            }else {
                gerRes = zkCli.create().forPath(path);
            }

            if (path.equals(gerRes)){
                log.info("节点创建成功！");
                res="创建成功";
                return res;
            }
        } catch (Exception e) {
            log.error("创建节点失败，节点可能已经存在了 返回结果为：{}",gerRes);
            res="创建失败 创建父节点不存在或节点已存在";
            e.printStackTrace();
        }
        return res;
    }

    public String addData(String path, String data) {
        log.info("准备添加数据 path = {}，data = {}",path,data);
        CuratorFramework zkCli = GetZKClient.getZkClient();

        try {
            zkCli.setData().forPath(path,data.getBytes("GBK"));
            return "添加成功";
        } catch (Exception e) {
            log.error("向节点添加数据失败 path = {},data = {}",path,data);
            e.printStackTrace();
        }
        return "添加失败";
    }

    public String getData(String path) {
        log.info("准备获取 path = {} 的数据",path);
        CuratorFramework zkCli = GetZKClient.getZkClient();
        try {
            String result = new String(zkCli.getData().forPath(path));
            log.info("{} 的数据时 ：{}",path,result);
            return result;
        } catch (Exception e) {
            log.error("获取数据错误 ");
            e.printStackTrace();
            return "获取数据错误";
        }

    }

    public String deletePath(String path) {
//        首先检测节点是否存在
        CuratorFramework zkClient = GetZKClient.getZkClient();
        try {
            if(null==zkClient.checkExists().forPath(path)){
                    log.info("节点不存在 path={}",path);
                    return "删除失败 节点"+path+"不存在";
            }
            //节点存在  开始删除
            zkClient.delete().forPath(path);
            return "删除成功";
        } catch (Exception e) {
            log.error("检测path是否存在/删除path 出现异常！！！path = {}",path);
            e.printStackTrace();
            return "删除失败 可能存在父节点";
        }

    }

    /**
     * 获取所有节点的递归算法
     * @param zkClient
     * @param root
     * @param myPaths
     */
    public void getpaths(CuratorFramework zkClient, String root, List<String> myPaths){
        String temp = root;
        try {
            if(null==zkClient.checkExists().forPath(root)){
                myPaths.add("不存在");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (root.contains("zookeeper")){
            return;
        }
        myPaths.add(root);
        List<String> paths= getChildNode(zkClient,root);
        if(null==paths){
            return;
        }else {
            for (String path1:paths) {
                root = "/".equals(root)?root+path1:root+"/"+path1;
                getpaths(zkClient,root,myPaths);
                root=temp;

            }
        }
    }

    /**
     *  判断是否有子节点
     * @param zkClient
     * @param path
     * @return
     */
    public List<String> getChildNode(CuratorFramework zkClient,String path){
        try {
            List<String> result = zkClient.getChildren().forPath(path);
            //这有个bug，第一次节点为zookeeper的会过滤掉 但是下一轮不会 因为不影响功能
            if ("zookeeper".equals(result.get(0))){
                result.remove(0);
            }
            return result.size()==0?null:result;
        } catch (Exception e) {

        }
        return null;
    }
}
