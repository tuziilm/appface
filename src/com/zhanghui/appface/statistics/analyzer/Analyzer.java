package com.zhanghui.appface.statistics.analyzer;

import com.zhanghui.appface.statistics.common.ChartPvUvData;

import java.util.List;

public interface Analyzer {
    List<ChartPvUvData> analyze() throws Exception;
}
