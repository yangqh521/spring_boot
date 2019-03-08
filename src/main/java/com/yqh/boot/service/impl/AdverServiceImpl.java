package com.yqh.boot.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yqh.boot.dao.dsp.AdverInfoMapper;
import com.yqh.boot.entity.AdverInfo;
import com.yqh.boot.service.AdverService;

@Service
public class AdverServiceImpl implements AdverService {
	
	@Autowired
	private AdverInfoMapper adverInfoMapper;

	@Override
	public List<AdverInfo> getAdverInfoList(Map<String, Object> map) {
		return adverInfoMapper.getAdverInfoList(map);
	}

}
