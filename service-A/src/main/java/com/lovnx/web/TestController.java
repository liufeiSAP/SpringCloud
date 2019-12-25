package com.lovnx.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
class TestController {

    @Value("${from}")
    private String from;
    
    
    @Value("${from1}")
    private String from1;

    @RequestMapping("/from")
    public String from() {

        return this.from;
    }
    
    @RequestMapping("/from1")
    public String from1() {

        return this.from1;
    }

    public String getFrom1() {
		return from1;
	}

	public void setFrom1(String from1) {
		this.from1 = from1;
	}

	public void setFrom(String from) {
        this.from = from;
    }

    public String getFrom() {
        return from;
    }

}