package com.ccut.xyd.searchPath;

import com.ccut.xyd.RootController;
import com.ccut.xyd.Vo.NodePo;
import com.ccut.xyd.Vo.PathPo;
import com.ccut.xyd.zookeeper.ZkNodeOp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class SearchPathController extends RootController {

    @Autowired
    private ZkNodeOp zk;

    @RequestMapping(method = RequestMethod.POST,value = "/allPaths")
    @ResponseBody
    public List<String>  getAllPaths(PathPo pathPo){
        log.info("请求路径 /allPaths ，请求查看所有path前台传过来的数据是："+pathPo.toString());
        if ("all".equals(pathPo.getScope())){
            return zk.getPaths("/");
        }else {
            return zk.getPaths(pathPo.getPath());
        }
    }

}
