package com.zhanghui.appface.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhanghui.appface.common.JsonSupport;
import com.zhanghui.appface.common.Paginator;
import com.zhanghui.appface.domain.HighChartData;
import com.zhanghui.appface.domain.LinkNodeAccessLogStatistics;
import com.zhanghui.appface.domain.Series;
import com.zhanghui.appface.mvc.LinkNodeAccessLogStatisticsController.ChartQuery;
import com.zhanghui.appface.persistence.LinkNodeAccessLogStatisticsMapper;

/**
 * LinkNodeAccessLogStatistics service
 */
@Service
public class LinkNodeAccessLogStatisticsService extends BaseService<LinkNodeAccessLogStatistics> {
	private LinkNodeAccessLogStatisticsMapper linkNodeAccessLogStatisticsMapper;
	@Autowired
    public void setLinkNodeAccessLogStatisticsMapper(LinkNodeAccessLogStatisticsMapper linkNodeAccessLogStatisticsMapper){
        this.mapper = linkNodeAccessLogStatisticsMapper;
        this.linkNodeAccessLogStatisticsMapper = linkNodeAccessLogStatisticsMapper;
    }
    public List<LinkNodeAccessLogStatistics> getChartDatas(Paginator paginator){
    	return linkNodeAccessLogStatisticsMapper.getChartDatas(paginator);
    }

	public HighChartData handleList(List<LinkNodeAccessLogStatistics> list, String startTime, String endTime) throws Exception{
		HighChartData hcd = new HighChartData();
		List<String> dateList = getAllDates(startTime, endTime);
        Series pv = new Series("PV", dateList.size());
        Series uv = new Series("UV", dateList.size());
        hcd.setDate(dateList);
        hcd.setSeries(Lists.newArrayList(pv, uv));
        Map<String, LinkNodeAccessLogStatistics> datas = convertLinkNodeAccessLogStatistics2Map(list);
        for(String date : dateList){
            LinkNodeAccessLogStatistics linkNodeAccessLogStatistics = datas.get(date);
            if(linkNodeAccessLogStatistics==null){
                pv.addQuantity(0);
                uv.addQuantity(0);
            }else {
                pv.addQuantity(linkNodeAccessLogStatistics.getPv());
                uv.addQuantity(linkNodeAccessLogStatistics.getUv());
            }
        }
		return hcd;
	}

    private Map<String, LinkNodeAccessLogStatistics> convertLinkNodeAccessLogStatistics2Map(List<LinkNodeAccessLogStatistics> list) {
        Map<String, LinkNodeAccessLogStatistics> map = new HashMap<>(list.size());
        for(LinkNodeAccessLogStatistics s : list){
            map.put(s.getSchedule(), s);
        }
        return map;
    }

    /**
	 * 获取查询时间段内的所有日期字符串
	 */
	private List<String> getAllDates(String startTime, String endTime) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date begin=sdf.parse(startTime);
		Date end=sdf.parse(endTime);
        if(begin.after(end)){
            return Collections.EMPTY_LIST;
        }
        List<String> list = new ArrayList<>();
        while(true){
            list.add(sdf.format(begin));
            if(begin.compareTo(end)==0){
                break;
            }
            begin = DateUtils.addDays(begin,1);
        }
		return list;
	}
}
