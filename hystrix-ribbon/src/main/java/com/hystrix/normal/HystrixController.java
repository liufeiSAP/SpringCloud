package com.hystrix.normal;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController 
@RequestMapping("/test")
@Api(value = "一个用来测试swagger注解的控制器")
public class HystrixController {
	
  @Autowired
  private HystrixService ribbonHystrixService;
  
  
  @ResponseBody
  @ApiOperation(value="測試斷路器出現異常的類", notes="測試斷路器出現異常的類")
  @RequestMapping(value="/exception" ,method=RequestMethod.GET)
  public String exception() {
	  
	  try {
          for (int i = 0; i < 20; i++) {
              final int j = i;
              Service1HystrixCommand command = new Service1HystrixCommand(j, 1) ;   // 被信号量拒绝的线程从这里抛出异常
              String execute = command.execute();
              boolean responseFromFallback = command.isResponseFromFallback();
              System.out.println("isCircuitBreakerOpen:" + command.isCircuitBreakerOpen());
              TimeUnit.SECONDS.sleep(1);
          }
      } catch (Exception e) {
          e.printStackTrace();
      }
     
	  
     return "";
  }
  
  @ResponseBody
  @ApiOperation(value="線程池隔離", notes="線程池隔離")
  @ApiImplicitParam(paramType="query", name = "str", value = "輸入參數", required = true, dataType = "String")
  @RequestMapping(value="/timeout" ,method=RequestMethod.GET)
  public String timeout(@RequestParam String str) {
	  
	  try {
          for (int i = 0; i < 22; i++) {
              final int j = i;
              Thread thread = new Thread(() ->
              System.out.println("===========" + new Service1HystrixCommand(j, 2).execute())    // 被信号量拒绝的线程从这里抛出异常
      );
      thread.start();
          }
      } catch (Exception e) {
          e.printStackTrace();
      }
	  try {
		TimeUnit.SECONDS.sleep(3);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
     return "";
  }
}