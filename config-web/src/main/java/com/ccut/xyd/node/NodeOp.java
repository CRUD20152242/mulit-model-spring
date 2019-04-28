package com.ccut.xyd.node;


import com.ccut.xyd.RootController;
import com.ccut.xyd.Vo.MessageVo;
import com.ccut.xyd.Vo.NodePo;
import com.ccut.xyd.Vo.NodeVo;
import com.ccut.xyd.zookeeper.ZkNodeOp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class NodeOp extends RootController {

    @Autowired
    ZkNodeOp zkNodeOp;

    @RequestMapping("/deletePath")
    @ResponseBody
    public MessageVo deletePath(NodeVo nodeVo){
        log.info("请求成功 删除的path = {}",nodeVo.getPath());
        MessageVo messageVo = new MessageVo();
        String message = "请求成功";
//        删除node的方法
        message = zkNodeOp.deletePath(nodeVo.getPath());
        messageVo.setMesaage(message);
        return messageVo;
    }

    @RequestMapping("/createNode")
    @ResponseBody
    public MessageVo createPath(NodeVo nodeVo){
        log.info("请求成功 请求创建的的path = {}",nodeVo.getPath());
        MessageVo messageVo = new MessageVo();
        String message = "请求成功";
        NodePo nodePo = new NodePo();
        nodePo.setPath(nodeVo.getPath());
        nodePo.setData("null");
        message = zkNodeOp.createPath(nodePo,message);
        messageVo.setMesaage(message);
        return messageVo;
    }
}
