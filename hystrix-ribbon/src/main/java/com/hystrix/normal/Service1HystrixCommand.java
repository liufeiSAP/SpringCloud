package com.hystrix.normal;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;

public class Service1HystrixCommand extends HystrixCommand<String> {
	private Integer count;
	private Integer type;
	
  public Service1HystrixCommand(Integer count, int type){
    super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ServiceGroup"))
          .andCommandKey(HystrixCommandKey.Factory.asKey("servcie1query"))
          .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("service1ThreadPool"))
          .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
            .withCoreSize(10))//服务线程池数量
          .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
            .withCircuitBreakerErrorThresholdPercentage(60)//熔断器关闭到打开阈值
            .withCircuitBreakerRequestVolumeThreshold(3) //在统计数据之前，必须在10秒内发出3个请求。  默认是20
            .withCircuitBreakerSleepWindowInMilliseconds(3000)//熔断器打开到关闭的时间窗长度
            .withExecutionTimeoutInMilliseconds(1000)
      ));
    this.count = count;
    this.type = type;
  }

  @Override
  protected String run(){
	  try {
		Thread.sleep(200);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	 if (type.equals(1))
	    return remoteExceptionCall();
	 else
		 return remoteTimeOutCall(); 
  }

  private String remoteTimeOutCall() {
	System.out.print("remoteTimeOutCall: " + count + "  ");
	try {
		Thread.sleep(300);
	} catch (InterruptedException e) {
	}

	return "Result";
}

private String remoteExceptionCall() {
	System.out.print("remoteExceptionCall:" + count);
	try {
		Thread.sleep(200);
	} catch (InterruptedException e) {
	}
	
	if(count <10)
		System.out.print(1/0);
	
	return "Result";
}

@Override
  protected String getFallback(){
   return " Fallback ";
  }
}