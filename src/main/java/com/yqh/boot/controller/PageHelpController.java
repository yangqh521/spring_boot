package com.yqh.boot.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yqh.boot.common.AppResponse;
import com.yqh.boot.entity.AdverInfo;
import com.yqh.boot.log.filter.LogWebContext;
import com.yqh.boot.service.AdverService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试pagehelp使用
 *  1，springboot必须使用pagehelp版本1.2.1以上
 *  2，底层sql执行分两部执行，1：获取总条数，2：limit处理
 */
@RequestMapping("/pagehelp")
@RestController
public class PageHelpController {

    @Autowired
    AdverService adverService;


    @RequestMapping("/adverList")
    public AppResponse adverList(String adverName, Integer pageNo, Integer pageSize){

        AppResponse resp = new AppResponse();

        LogWebContext log = LogWebContext.getInstance();

        PageHelper.startPage(pageNo,pageSize);

        Map<String,Object> queryMap = new HashMap<>();
        if(StringUtils.isNotBlank(adverName)){
            queryMap.put("adverNameLike", adverName);
        }
        List<AdverInfo> adverInfoList = adverService.getAdverInfoList(queryMap);

        PageInfo<AdverInfo> pageInfo = new PageInfo<>(adverInfoList);

        log.putLog("pageInfo", pageInfo);
        resp.setData(pageInfo);
        return resp;

    }




}
