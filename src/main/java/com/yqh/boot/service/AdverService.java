package com.yqh.boot.service;

import java.util.List;
import java.util.Map;

import com.yqh.boot.entity.AdverInfo;

public interface AdverService {

	List<AdverInfo> getAdverInfoList(Map<String,Object> map);
}
