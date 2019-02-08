package com.bootdo.util;

import redis.clients.jedis.Jedis;

public class RedisUtil {

    public static Jedis getRedis() {
        return new Jedis();
    }


}
