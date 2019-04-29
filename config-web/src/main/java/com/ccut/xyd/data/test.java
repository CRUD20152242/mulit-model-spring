package com.ccut.xyd.data;

import com.ccut.xyd.Vo.NodePo;
import com.ccut.xyd.Vo.NodeVo;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;

@Controller
@Slf4j
public class test {

    @RequestMapping("/test")
    public void test(NodePo nodeVo){
        String datas = nodeVo.getDatas();
    log.info("测试controller 过来的数据时："+datas);
    log.info("尝试转换为hashMap");
        HashMap<String, List<String>> hashMap = new HashMap<String, List<String>>(30);
        Gson gson = new Gson();
        try {
            hashMap = gson.fromJson(datas,HashMap.class);
            String str = gson.toJson(hashMap);
            System.out.println(str);
        } catch (JsonSyntaxException e) {
            log.info("格式转换错误");
            e.printStackTrace();
        }
    }
}
