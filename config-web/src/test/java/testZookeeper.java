import com.ccut.xyd.zookeeper.ZkNodeOp;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
//加载配置文件位置
@ContextConfiguration("classpath:spring-config.xml")
public class testZookeeper {
    @Autowired
    ZkNodeOp zkNodeOp;

    @Test
    public void TestDelete(){
        String str = zkNodeOp.deletePath("/p");
        Assert.assertEquals("删除成功",str);
    }
}
