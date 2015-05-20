package com.zhanghui.appface.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhanghui.appface.domain.AdType;
import com.zhanghui.appface.persistence.AdTypeMapper;

/**
 * AdTypeService
 * @author tuziilm
 */
@Service
@SuppressWarnings({"rawtypes", "unchecked"})
public class AdTypeService extends ObjectBasedGroupCacheSupportService<AdType>{
	private final String LIST_ALL_KEY = "list_all";
	private final String MAP_ID_2_OBJECT = "map_id_2_object";
	private final String[] CACHE_GROUP_KEYS= new String[]{LIST_ALL_KEY, MAP_ID_2_OBJECT};
	
	@Autowired
    public void setAdTypeMapper(AdTypeMapper adTypeMapper){
        this.mapper = adTypeMapper;
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
	public void updateCacheList(Map<String, Object> update, AdType t) {
		((ArrayList<AdType>)update.get(LIST_ALL_KEY)).add(t);
		((Map<Integer, AdType>)update.get(MAP_ID_2_OBJECT)).put(t.getId(), t);
	}
	
	public List<AdType> getAdTypesCache(){
		return (List<AdType>) getCache(LIST_ALL_KEY);
	}
	
	public AdType getById(Integer id){
		return ((Map<Integer, AdType>)getCache(MAP_ID_2_OBJECT)).get(id);
	}
	
	public Map<Integer, AdType> getAdTypeMapCache(){
		return (Map<Integer, AdType>)getCache(MAP_ID_2_OBJECT);
	}
}
