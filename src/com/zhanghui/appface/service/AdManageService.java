package com.zhanghui.appface.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhanghui.appface.domain.AdManage;
import com.zhanghui.appface.persistence.AdManageMapper;
@Service
@SuppressWarnings({"rawtypes","unchecked"})
public class AdManageService extends ObjectBasedGroupCacheSupportService<AdManage>{
	private final String LIST_ALL_KEY = "list_all";//查询出list集合
	private final String MAP_ID_2_OBJECT = "map_id_2_object";//查询出map
	private final String[] CACHE_GROUP_KEYS= new String[]{LIST_ALL_KEY, MAP_ID_2_OBJECT};
	
	@Autowired
    public void setAdManageMapper(AdManageMapper adManageMapper){
        this.mapper = adManageMapper;
    }

	@Override
	public String[] cacheGroupKeys() {
		return CACHE_GROUP_KEYS;
	}

	@Override
	public Object newObject(String cacheGroupKey) {
		if(cacheGroupKey.startsWith("list")){
			return new ArrayList();
		}else if(cacheGroupKey.startsWith("map")){
			return new HashMap();
		}
		return null;
	}

	@Override
	public void updateCacheList(Map<String, Object> update, AdManage t) {
		((ArrayList<AdManage>)update.get(LIST_ALL_KEY)).add(t);
		((Map<Integer, AdManage>)update.get(MAP_ID_2_OBJECT)).put(t.getId(), t);
	}
	
	public List<AdManage> getAdManageCache(){
		return (List<AdManage>) getCache(LIST_ALL_KEY);
	}
	
	public AdManage getById(Integer id){
		return ((Map<Integer, AdManage>)getCache(MAP_ID_2_OBJECT)).get(id);
	}
	
	public Map<Integer, AdManage> getAdManageMapCache(){
		return (Map<Integer, AdManage>)getCache(MAP_ID_2_OBJECT);
	}
	
}
