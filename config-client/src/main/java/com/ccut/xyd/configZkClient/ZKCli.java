package com.ccut.xyd.configZkClient;

import com.ccut.xyd.GetZKClient;
import org.apache.curator.framework.CuratorFramework;


public class ZKCli {
   public static CuratorFramework client = GetZKClient.getZkClient();
    public ZKCli(){
        GetZKClient.setHostPort(ZkParameter.host);
    }
}
