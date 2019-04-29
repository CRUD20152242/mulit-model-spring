package com.ccut.xyd.configZkClient;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@ToString
@Slf4j
public class ZkParameter {
    public static String host = "127.0.0.1:2181";
    public static List<String> path = new ArrayList<String>();

}
