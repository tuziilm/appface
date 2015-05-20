package com.zhanghui.appface.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhanghui.appface.domain.DistributeArea;
import com.zhanghui.appface.persistence.DistributeAreaMapper;
@Service
@SuppressWarnings({"rawtypes","unchecked"})
public class DistributeAreaService extends ObjectBasedGroupCacheSupportService<DistributeArea>{
	private final String LIST_ALL_KEY = "list_all";//查询出list集合
	private final String MAP_ID_2_OBJECT = "map_id_2_object";//查询出map
	private final String[] CACHE_GROUP_KEYS= new String[]{LIST_ALL_KEY, MAP_ID_2_OBJECT};
	
	@Autowired
    public void setDistributeAreaMapper(DistributeAreaMapper distributeAreaMapper){
        this.mapper = distributeAreaMapper;
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
	public void updateCacheList(Map<String, Object> update, DistributeArea t) {
		((ArrayList<DistributeArea>)update.get(LIST_ALL_KEY)).add(t);
		((Map<Integer, DistributeArea>)update.get(MAP_ID_2_OBJECT)).put(t.getId(), t);
	}
	
	public List<DistributeArea> getDistributeAreaCache(){
		return (List<DistributeArea>) getCache(LIST_ALL_KEY);
	}
	
	public DistributeArea getById(Integer id){
		return ((Map<Integer, DistributeArea>)getCache(MAP_ID_2_OBJECT)).get(id);
	}
	
	public Map<Integer, DistributeArea> getDistributeAreaMapCache(){
		return (Map<Integer, DistributeArea>)getCache(MAP_ID_2_OBJECT);
	}
	@Override
	public int saveOrUpdate(DistributeArea t) {
		if(super.get(t.getId())==null){
			return super.save(t);
		}else{
			return super.update(t);
		}
	}

}
