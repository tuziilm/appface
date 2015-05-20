package com.zhanghui.appface.mvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.google.common.base.Strings;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhanghui.appface.common.Country;
import com.zhanghui.appface.common.Paginator;
import com.zhanghui.appface.domain.HighChartData;
import com.zhanghui.appface.domain.LinkNodeAccessLogStatistics;
import com.zhanghui.appface.service.LinkNodeAccessLogStatisticsService;
import com.zhanghui.appface.service.LinkNodeService;

/**
 * LinkNodeController
 */
@Controller
@RequestMapping("/link_node_access_log_statistics")
public class LinkNodeAccessLogStatisticsController extends ListController<LinkNodeAccessLogStatistics, LinkNodeAccessLogStatisticsService, LinkNodeAccessLogStatisticsController.Query> {
	private final static Logger log = LoggerFactory.getLogger(LinkNodeAccessLogStatisticsController.class);
	@Resource
	private LinkNodeService linkNodeService;
	protected final String CHART_VIEW;

	public LinkNodeAccessLogStatisticsController() {
		super("link_node_access_log_statistics");
        CHART_VIEW = String.format("/%s/chart", "link_node_access_log_statistics");
	}

	@Resource
	public void setLinkNodeAccessLogStatisticsService(	LinkNodeAccessLogStatisticsService service) {
		this.service = service;
	}

	@RequestMapping("/chart")
	public String chart(Model model, ChartQuery query) throws Exception {
		model.addAttribute("linkNodeMap", linkNodeService.getCache());
		model.addAttribute("linkNodes", linkNodeService.getCache().values());
		model.addAttribute("countries", Country.countries);
        model.addAttribute("countryMap", Country.shortcut2CountryMap);
		Paginator paginator = new Paginator(1, 10000);
		paginator.setQuery(query);
		List<LinkNodeAccessLogStatistics> list = service.getChartDatas(paginator);
		HighChartData hcd = service.handleList(list, query.startTime, query.endTime);
		model.addAttribute("jsonStr", hcd.toString());
		return CHART_VIEW;
	}

	@Override
	protected boolean preList(int page, Paginator paginator, Query query,	Model model) {
		model.addAttribute("linkNodeMap", linkNodeService.getCache());
		model.addAttribute("linkNodes", linkNodeService.getCache().values());
		model.addAttribute("countries", Country.countries);
        model.addAttribute("countryMap", Country.shortcut2CountryMap);
		return super.preList(page, paginator, query, model);
	}

	public static class Query extends com.zhanghui.appface.common.Query {
		private String code;
		private String country;
		protected String module;
		protected String startTime;
        private String from;

		public Query() {
			this.startTime = DateFormatUtils.format(DateUtils.addDays(new Date(), -1), "yyyy-MM-dd");
			this.module = "LINK_NODE_DAY_1";
            setCountry("all");
            setFrom("all");
            setCode("all");
		}

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
            this.addItem("from", from);
        }

        public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
            this.addItem("country", country);
		}

		public String getStartTime() {
			return startTime;
		}

		public void setStartTime(String startTime) {
			this.startTime = startTime.replaceAll("/", "-");
			this.addItem("startTime", startTime);
		}

		public String getModule() {
			return module;
		}

		public void setModule(String module) {
			this.module = module;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
			this.addItem("code", code);
		}
	}

	public static class ChartQuery extends Query {
        protected String endTime;

        public ChartQuery() {
            this.startTime = DateFormatUtils.format(DateUtils.addDays(new Date(), -30), "yyyy-MM-dd");
            this.endTime = DateFormatUtils.format(DateUtils.addDays(new Date(), -1), "yyyy-MM-dd");
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime.replaceAll("/", "-");
            this.addItem("endTime", endTime);
        }

        @Override
        public void setFrom(String from) {
            if(Strings.isNullOrEmpty(from)){
                from="all";
            }
            super.setFrom(from);
        }

        @Override
        public void setCountry(String country) {
            if(Strings.isNullOrEmpty(country)){
                country="all";
            }
            super.setCountry(country);
        }

        @Override
        public void setCode(String code) {
            if(Strings.isNullOrEmpty(code)){
                code="all";
            }
            super.setCode(code);
        }
    }
}