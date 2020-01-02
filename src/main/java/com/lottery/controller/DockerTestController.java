package com.lottery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/docker")
public class DockerTestController {

    @ResponseBody
    @RequestMapping(value = "/test1")
    public String dockerTest1(){
        return "HELLO ,WellCome docker test01 !";
    }

    @ResponseBody
    @RequestMapping(value = "/test2")
    public String dockerTest2(){
        return "HELLO ,WellCome to docker test02 !!";
    }
}
