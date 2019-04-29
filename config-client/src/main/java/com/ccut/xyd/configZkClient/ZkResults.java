package com.ccut.xyd.configZkClient;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ZkResults {
    public static ConcurrentHashMap<String,HashMap<String, List<String >>> results = new ConcurrentHashMap<String, HashMap<String, List<String>>>();
}
