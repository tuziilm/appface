package com.zhanghui.appface.persistence;

import java.util.List;

import com.zhanghui.appface.common.Paginator;
import com.zhanghui.appface.domain.LinkNodeAccessLogStatistics;

/**
 * LinkNodeAccessLogStatisticsMapper
 */
public interface LinkNodeAccessLogStatisticsMapper extends BaseMapper<LinkNodeAccessLogStatistics> {
	List<LinkNodeAccessLogStatistics> getChartDatas(Paginator paginator);
}
