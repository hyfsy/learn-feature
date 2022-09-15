package com.hyf.feature.actuator.mbean;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

/**
 * @author baB_hyf
 * @date 2020/10/21
 */
@Component
@ManagedResource
public class MMM implements MyMBean
{
    @ManagedAttribute(defaultValue = "sss", description = "测试获取名称")
    public String getName() {
        return "test";
    }

    @ManagedOperation(description = "后台输出")
    public void sout() {
        System.out.println("mbean 测试后台输出");
    }
}
