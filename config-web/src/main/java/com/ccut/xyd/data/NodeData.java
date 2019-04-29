package com.ccut.xyd.data;

import com.ccut.xyd.Vo.MessageVo;
import com.ccut.xyd.Vo.NodePo;
import com.ccut.xyd.Vo.NodeVo;
import com.ccut.xyd.zookeeper.ZkNodeOp;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
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
    public HashMap<String,List<String >> getData(NodePo nodePo){
        log.info("获取获取数据参数请求成功 nodePo = {}",nodePo);
        MessageVo messageVo = new MessageVo();
        HashMap<String,List<String >> hashMap = new HashMap<String, List<String>>(30);
        List<String> vlist = new ArrayList<String>(2);
        if (StringUtils.isEmpty(nodePo.getPath())){
            log.info("path is null");
            messageVo.setMesaage("path is bull");
            vlist.add(messageVo.getMesaage());
            hashMap.put("result",vlist);
            return hashMap;
        }
        String   str = zkNodeOp.getData(nodePo.getPath());
        Gson gson = new Gson();
        try {
            hashMap = gson.fromJson(str,HashMap.class);
            messageVo.setMesaage(str);
        }catch (JsonSyntaxException jsonException){
            log.error("json 转换错误 服务端存储格式不对");
            messageVo.setMesaage("json 转换错误 请查看日志");
            hashMap.put("result",vlist);
            vlist.add(messageVo.getMesaage());
        }catch (Exception e){
            log.error("未知错误");
            messageVo.setMesaage("未知错误");
            vlist.add(messageVo.getMesaage());
            hashMap.put("result",vlist);
            e.printStackTrace();
        }
        return hashMap;

    }

    @RequestMapping("/updateDatas")
    @ResponseBody
    public MessageVo updateDatas(NodeVo nodeVo){
        MessageVo messageVo  = new MessageVo();
        log.info("更新数据请求成功！path={},datas={}","updateDatas",nodeVo.toString());
        HashMap<String,List<String>> hashMap = new HashMap<String, List<String>>(30);
        //首先把数据转换为hashMap
        String datas = nodeVo.getDatas();
        Gson gson =new Gson();
        try {
            hashMap = gson.fromJson(datas,HashMap.class);
            String delKeys = nodeVo.getDelDatas();
            String[] keys =delKeys.split(",");
            for (String key:keys) {
                if (!StringUtils.isEmpty(key)){
                        hashMap.remove(key);
                }
            }
            String results = gson.toJson(hashMap);

            zkNodeOp.addData(nodeVo.getPath(),results);
            messageVo.setMesaage("更新成功");
            return messageVo;
        } catch (JsonSyntaxException e) {
            log.error("数据格式转换异常 datas={}",datas);
            messageVo.setMesaage("数据格式转换异常 更新失败");
            e.printStackTrace();
            return messageVo;
        }catch (Exception e){
            log.error("未知异常");
            messageVo.setMesaage("未知异常 更新失败");
            e.printStackTrace();
            return messageVo;
        }

    }
}
