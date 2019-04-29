package com.ccut.xyd.data;

import com.ccut.xyd.Vo.MessageVo;
import com.ccut.xyd.Vo.NodePo;
import com.ccut.xyd.zookeeper.ZkNodeOp;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

/**
 * 对结点数据的操作
 */
@Controller
@Slf4j
public class NodeData {

    @Autowired
    ZkNodeOp zkNodeOp;

//todo 后台传过来的参数不确定，处理方法时 在前端拼接成j'son串  后台进行json解析即可

    @RequestMapping("/addData")
    @ResponseBody
    public MessageVo addData(NodePo nodePo){
        String datas = nodePo.getDatas();
        String path  =nodePo.getPath();
        MessageVo messageVo = new MessageVo();

        if (StringUtils.isEmpty(path)||StringUtils.isEmpty(datas)){
            messageVo.setMesaage("path或datas不可以为空");
            return messageVo;
        }
        HashMap<String, List<String>> hashMap = new HashMap<String, List<String>>(30);
        log.info("传过来的数据时 data={}",datas);

        //进行json转换 目的是防止重复key
        Gson gson = new Gson();
        try {
            gson.fromJson(datas,HashMap.class);
            zkNodeOp.addData(path,datas);
        }catch (JsonSyntaxException exception){
            messageVo.setMesaage("key值重复或格式错误请检查");
            log.error("添加数据key值重复");
            return messageVo;
        }catch (Exception e){
            messageVo.setMesaage("未知异常");
            log.error("未知异常");
            e.printStackTrace();
            return messageVo;
        }
        messageVo.setMesaage(zkNodeOp.addData(nodePo.getPath(),datas));

        return messageVo;
    }

    @RequestMapping("/getData")
    @ResponseBody
    public String getData(NodePo nodePo){
        log.info("获取参数请求成功 nodePo = {}",nodePo);
        MessageVo messageVo = new MessageVo();
        if (StringUtils.isEmpty(nodePo.getPath())){
            log.info("path is null");
            messageVo.setMesaage("path is bull");
            return messageVo.getMesaage();
        }
        String   str = zkNodeOp.getData(nodePo.getPath());
        Gson gson = new Gson();
        try {
            gson.fromJson(str,HashMap.class);
            messageVo.setMesaage(str);
        }catch (JsonSyntaxException jsonException){
            log.error("json 转换错误 服务端存储格式不对");
            messageVo.setMesaage("json 转换错误 请查看日志");
        }catch (Exception e){
            log.error("未知错误");
            messageVo.setMesaage("未知错误");
            e.printStackTrace();
        }
        return messageVo.getMesaage();

    }

}
