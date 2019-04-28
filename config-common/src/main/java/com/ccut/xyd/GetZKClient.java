package com.ccut.xyd;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.stereotype.Component;


@ToString
@Component
@Slf4j
public class GetZKClient {
    private static String hostPort = "127.0.0.1:2181";
    private static CuratorFramework zkClient = CuratorFrameworkFactory.newClient(hostPort,new RetryNTimes(10,1000));

    public static CuratorFramework getZkClient(){
        log.info("begin to connect zookeeper server host and port is {}",hostPort);
        try {
            zkClient.start();
        }catch (Exception e){
            log.info("zookeeper已启动");
        }

        return zkClient;
    }
    public static void setHostPort(String hostPort){
        log.debug("注入host:port 是{}",hostPort);
        GetZKClient.hostPort = hostPort;
    }
}
