package com.ccut.xyd.node;

import com.ccut.xyd.Vo.NodePo;
import com.ccut.xyd.Vo.NodeVo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class NodeOpTest {

    @Autowired
    NodeOp nodeOp;

    NodeVo nodePo = new NodeVo();

    @Before
    public void init(){
        nodePo.setPath("/p3");
    }
    @Test
    public void deletePath() {

    }

    @Test
    public void createPath() {
        nodeOp.createPath(nodePo);
    }
}