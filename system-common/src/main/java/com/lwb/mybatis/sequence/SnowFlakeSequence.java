package com.lwb.mybatis.sequence;

import com.lwb.utils.SystemPropertiesUtil;

/**
 * 生成Id
 * @author zhangxueshu
 *
 */
public class SnowFlakeSequence {

    private static SnowFlakeGenerator generator;

    static {
        Long datacenterId = Long.parseLong((String) SystemPropertiesUtil.getValue("snow.flake.datacenterId", "0"));
        Long serverId = Long.parseLong((String) SystemPropertiesUtil.getValue("snow.flake.serverId", "0"));
        generator = new SnowFlakeGenerator.Factory().create(datacenterId, serverId);
    }

    public static Long next(){
        return generator.nextId();
    }

    public static void main(String[] strs){
        generator = new SnowFlakeGenerator.Factory().create(0, 0);
        for(int i=0;i<1;i++){
            System.out.println(generator.nextId());
        }
    }
}
