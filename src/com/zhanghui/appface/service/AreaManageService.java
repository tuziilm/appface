package com.zhanghui.appface.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhanghui.appface.domain.AreaManage;
import com.zhanghui.appface.persistence.AreaManageMapper;
@Service
@SuppressWarnings({"rawtypes","unchecked"})
public class AreaManageService extends ObjectBasedGroupCacheSupportService<AreaManage>{
	private final String LIST_ALL_KEY = "list_all";//查询出list集合
	private final String MAP_ID_2_OBJECT = "map_id_2_object";//查询出map
	private final String MAP_COUNTRY_2_MAP = "map_country_2_map";//查询出map
	private final String[] CACHE_GROUP_KEYS= new String[]{LIST_ALL_KEY, MAP_ID_2_OBJECT, MAP_COUNTRY_2_MAP};
	
	@Autowired
    public void setLinkNodeMapper(AreaManageMapper areaManageMapper){
        this.mapper = areaManageMapper;
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
	public void updateCacheList(Map<String, Object> update, AreaManage t) {
		((ArrayList<AreaManage>)update.get(LIST_ALL_KEY)).add(t);
		((Map<Integer, AreaManage>)update.get(MAP_ID_2_OBJECT)).put(t.getId(), t);
		Map<String, Map<Integer,AreaManage>> country2ListMap = ((Map<String,Map<Integer,AreaManage>>)update.get(MAP_COUNTRY_2_MAP));
		for(String country:t.getSupportcountriesObject()){
			if(country2ListMap.containsKey(country)){
				Map<Integer,AreaManage> map = country2ListMap.get(country);
				map.put(t.getId(), t);
			}else{
				Map<Integer,AreaManage> map = new HashMap<Integer, AreaManage>();
				map.put(t.getId(), t);
				country2ListMap.put(country, map);
			}
		}
	}
	public Map<Integer,AreaManage> getAreaManageByCountryCache(){
		return (Map<Integer,AreaManage>)getCache(MAP_COUNTRY_2_MAP);
	}
	public List<AreaManage> getAreaManageCache(){
		return (List<AreaManage>) getCache(LIST_ALL_KEY);
	}
	
	public AreaManage getById(Integer id){
		return ((Map<Integer, AreaManage>)getCache(MAP_ID_2_OBJECT)).get(id);
	}
	
	public Map<Integer, AreaManage> getAreaManageMapCache(){
		return (Map<Integer, AreaManage>)getCache(MAP_ID_2_OBJECT);
	}
}
