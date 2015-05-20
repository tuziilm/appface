package com.zhanghui.appface.statistics;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.zhanghui.appface.statistics.analyzer.Analyzer;
import com.zhanghui.appface.statistics.common.ChartPvUvData;
import org.apache.commons.lang3.time.DateUtils;

import com.zhanghui.appface.statistics.analyzer.LinkNodePvUvAnalyzer;
import com.zhanghui.appface.statistics.common.DatabaseHelper;

public final class LogStatisticsRunner {
	public final static void persistToDatabase(Analyzer analyzer) throws Exception {
		try {
			System.out.println("analyzing....");
			List<ChartPvUvData> data = analyzer.analyze();
			System.out.println("ready for persist result to database");
			DatabaseHelper.persistToDatabase(data);
		} catch (SQLException e) {
			System.out.println("failed to persist result to database");
			e.printStackTrace();
		}
		System.out.println("work done.");
	}
	public void test() throws Exception {
        Date date = new Date();
		LinkNodePvUvAnalyzer analyzer = new LinkNodePvUvAnalyzer(date);
		persistToDatabase(analyzer);
	}
}
