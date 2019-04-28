package com.ccut.xyd.zookeeper;

import com.ccut.xyd.Vo.NodePo;

import java.util.List;

public interface ZkNodeOp {
    List<String> getPaths(String path);
    String  createPath(NodePo nodePo,String res);
    boolean addData(String path,String  data);
    String getData(String path);
    String deletePath(String path);
}
