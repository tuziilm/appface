<%@page import="org.springframework.validation.BindingResult"%>
<%@include file="../include/common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<c:set var="_pageTitle" value="创建广告位置" scope="request"/>
<c:set var="_underAreaManage" value="active" scope="request"/>
<c:set var="_activeAd" value="active" scope="request"/>
<c:set var="_module" value="ad" scope="request"/>
<c:import url="../theme/${_theme}/header.jsp"></c:import>
<!-- main content -->
		<div class="page-header"><h1>新增广告位置</h1></div>
		<div id="pageContent">
			<c:import url="../theme/${_theme}/errors.jsp"></c:import>
			<form action="${basePath}area_manage/save" method="post" class="form-horizontal">
				<input name="id" type="hidden" value="${form.id}">
				<input name="_queryString" type="hidden" value="${param.queryString}">
				<div class="control-group required-field">
				  <label class="control-label">位置名称:</label>
				  <div class="controls">
				    <input name="area" value="${fn:escapeXml(form.area)}" type="text" class="input-large">
				  </div>
				</div>
				<div class="control-group required-field">
				  <label class="control-label">广告类型:</label>
				  <div class="controls">
				    <select id="adType_sel" name="adType" class="input-large" onclick="javascript:show()">
					  		<option value="1" ${fn:escapeXml(form.adType) == 1 ?'selected="selected"':"" }>文字</option>
					  		<option value="2" ${fn:escapeXml(form.adType) == 2 ?'selected="selected"':"" }>图片</option>
					 </select>
				   </div>
				</div>
				<script type="text/javascript">
					function show(){
						var sel=document.getElementById("adType_sel").value;
						if(sel=='1'){
							document.getElementById("areaType_sel").disabled=false;
						}
						if(sel=='2'){
							document.getElementById("areaType_sel").value='0';
							document.getElementById("areaType_sel").disabled=true;
						}
					}
				</script>
				<div class="control-group required-field">
				  <label class="control-label">位置顺序:</label>
				  <div class="controls">
				    <input name="areaNum" value="${fn:escapeXml(form.areaNum)}" type="text" class="input-large">
				  </div>
				</div>
				<div class="control-group required-field">
				  <label class="control-label">位置类型:</label>
				  <div class="controls">
				    <select id="areaType_sel" name="areaType" class="input-large">
				    		<option value="0" ${form.areaType == 0 ?'selected="selected"':"" }>请选择位置类型</option>
					  		<option value="1" ${form.areaType == 1 ?'selected="selected"':"" }>一行一列</option>
					  		<option value="2" ${form.areaType == 2 ?'selected="selected"':"" }>一行两列</option>
					  		<option value="3" ${form.areaType == 3 ?'selected="selected"':"" }>一行四列</option>
							<option value="4" ${form.areaType == 4 ?'selected="selected"':"" }>带图标一行四列</option>
							<option value="5" ${form.areaType == 5 ?'selected="selected"':"" }>带图标一行三列</option>
					 </select>
				   </div>
				</div>
				<div class="control-group required-field">
                    <label class="control-label">国家:</label>
                    <div class="controls">
                        <a id="selectCountryLink" href="#selectCountryModal" role="button" class="btn" data-toggle="modal" onclick="javascript:onSearchCountry()">选择</a>
                        <input name="supportCountries" value="${fn:escapeXml(form.supportCountries)}" type="hidden">
                        <div id="selectedCountriesDiv">
                        </div>
                        <div id="selectCountryModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                <div class="input-append">
                                    <input class="input-large" name="countryKeyword" id="countryKeyword" type="text">
                                    <button class="btn" type="button" onclick="javascript:onSearchCountry()">搜索</button>
                                </div>
                                <span class="remark">空的关键字可查询所有国家</span>
                            </div>
                            <div class="modal-body">
                                <div id="countryUl"> 
                                </div>
                            </div>
                            <div class="modal-footer">
                                <span class="pull-left">
                                    <a class="btn" onclick="onSelectAllCountry()">全选</a>
                                    <a class="btn" onclick="onUnSelectAllCountry()">重置</a>
                                </span>
                                <button type="button" class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
                                <button class="btn btn-primary" type="button" onclick="javascript:onAddCountry()">确定</button>
                            </div>
                        </div>
                    </div>
                </div>
				<div class="control-group required-field">
				  <label class="control-label">备注:</label>
				  <div class="controls">
				    <input name="remark" value="${fn:escapeXml(form.remark)}" type="text" class="input-large">
				  </div>
				</div>
				<div class="form-actions">
				  <input class="btn btn-primary" type="submit" value="确认">
				  <button type="button" class="btn" onclick="javascript:history.go(-1)">取消</button>
				</div>
			</form>
        </div>
<!-- end main content -->
<c:import url="../theme/${_theme}/footer.jsp"></c:import>
<script>
    var countries=[];
    countries.push({code:"ad", chineseName: "安道尔"});
    countries.push({code:"ae", chineseName: "阿联酋"});
    countries.push({code:"af", chineseName: "阿富汗"});
    countries.push({code:"ag", chineseName: "安提瓜和巴布达"});
    countries.push({code:"ai", chineseName: "安圭拉"});
    countries.push({code:"al", chineseName: "阿尔巴尼亚"});
    countries.push({code:"am", chineseName: "亚美尼亚"});
    countries.push({code:"ao", chineseName: "安哥拉"});
    countries.push({code:"aq", chineseName: "南极洲"});
    countries.push({code:"ar", chineseName: "阿根廷"});
    countries.push({code:"as", chineseName: "美属萨摩亚"});
    countries.push({code:"at", chineseName: "奥地利"});
    countries.push({code:"au", chineseName: "澳大利亚"});
    countries.push({code:"aw", chineseName: "阿鲁巴"});
    countries.push({code:"ax", chineseName: "奥兰群岛"});
    countries.push({code:"az", chineseName: "阿塞拜疆"});
    countries.push({code:"ba", chineseName: "波黑"});
    countries.push({code:"bb", chineseName: "巴巴多斯"});
    countries.push({code:"bd", chineseName: "孟加拉"});
    countries.push({code:"be", chineseName: "比利时"});
    countries.push({code:"bf", chineseName: "布基纳法索"});
    countries.push({code:"bg", chineseName: "保加利亚"});
    countries.push({code:"bh", chineseName: "巴林"});
    countries.push({code:"bi", chineseName: "布隆迪"});
    countries.push({code:"bj", chineseName: "贝宁"});
    countries.push({code:"bl", chineseName: "圣巴泰勒米岛"});
    countries.push({code:"bm", chineseName: "百慕大"});
    countries.push({code:"bn", chineseName: "文莱"});
    countries.push({code:"bo", chineseName: "玻利维亚"});
    countries.push({code:"bq", chineseName: "荷兰加勒比区"});
    countries.push({code:"br", chineseName: "巴西"});
    countries.push({code:"bs", chineseName: "巴哈马"});
    countries.push({code:"bt", chineseName: "不丹"});
    countries.push({code:"bv", chineseName: "布韦岛"});
    countries.push({code:"bw", chineseName: "博茨瓦纳"});
    countries.push({code:"by", chineseName: "白俄罗斯"});
    countries.push({code:"bz", chineseName: "伯利兹"});
    countries.push({code:"ca", chineseName: "加拿大"});
    countries.push({code:"cc", chineseName: "科科斯群岛"});
    countries.push({code:"cd", chineseName: "刚果（金）"});
    countries.push({code:"cf", chineseName: "中非"});
    countries.push({code:"cg", chineseName: "刚果（布）"});
    countries.push({code:"ch", chineseName: "瑞士"});
    countries.push({code:"ci", chineseName: "科特迪瓦"});
    countries.push({code:"ck", chineseName: "库克群岛"});
    countries.push({code:"cl", chineseName: "智利"});
    countries.push({code:"cm", chineseName: "喀麦隆"});
    countries.push({code:"cn", chineseName: "中国"});
    countries.push({code:"co", chineseName: "哥伦比亚"});
    countries.push({code:"cr", chineseName: "哥斯达黎加"});
    countries.push({code:"cu", chineseName: "古巴"});
    countries.push({code:"cv", chineseName: "佛得角"});
    countries.push({code:"cw", chineseName: "库拉索"});
    countries.push({code:"cx", chineseName: "圣诞岛"});
    countries.push({code:"cy", chineseName: "塞浦路斯"});
    countries.push({code:"cz", chineseName: "捷克"});
    countries.push({code:"de", chineseName: "德国"});
    countries.push({code:"dj", chineseName: "吉布提"});
    countries.push({code:"dk", chineseName: "丹麦"});
    countries.push({code:"dm", chineseName: "多米尼克"});
    countries.push({code:"do", chineseName: "多米尼加"});
    countries.push({code:"dz", chineseName: "阿尔及利亚"});
    countries.push({code:"ec", chineseName: "厄瓜多尔"});
    countries.push({code:"ee", chineseName: "爱沙尼亚"});
    countries.push({code:"eg", chineseName: "埃及"});
    countries.push({code:"eh", chineseName: "西撒哈拉"});
    countries.push({code:"er", chineseName: "厄立特里亚"});
    countries.push({code:"es", chineseName: "西班牙"});
    countries.push({code:"et", chineseName: "埃塞俄比亚"});
    countries.push({code:"fi", chineseName: "芬兰"});
    countries.push({code:"fj", chineseName: "斐济群岛"});
    countries.push({code:"fk", chineseName: "马尔维纳斯群岛（福克兰）"});
    countries.push({code:"fm", chineseName: "密克罗尼西亚联邦"});
    countries.push({code:"fo", chineseName: "法罗群岛"});
    countries.push({code:"fr", chineseName: "法国"});
    countries.push({code:"ga", chineseName: "加蓬"});
    countries.push({code:"gb", chineseName: "英国"});
    countries.push({code:"gd", chineseName: "格林纳达"});
    countries.push({code:"ge", chineseName: "格鲁吉亚"});
    countries.push({code:"gf", chineseName: "法属圭亚那"});
    countries.push({code:"gg", chineseName: "根西岛"});
    countries.push({code:"gh", chineseName: "加纳"});
    countries.push({code:"gi", chineseName: "直布罗陀"});
    countries.push({code:"gl", chineseName: "格陵兰"});
    countries.push({code:"gm", chineseName: "冈比亚"});
    countries.push({code:"gn", chineseName: "几内亚"});
    countries.push({code:"gp", chineseName: "瓜德罗普"});
    countries.push({code:"gq", chineseName: "赤道几内亚"});
    countries.push({code:"gr", chineseName: "希腊"});
    countries.push({code:"gs", chineseName: "南乔治亚岛和南桑威奇群岛"});
    countries.push({code:"gt", chineseName: "危地马拉"});
    countries.push({code:"gu", chineseName: "关岛"});
    countries.push({code:"gw", chineseName: "几内亚比绍"});
    countries.push({code:"gy", chineseName: "圭亚那"});
    countries.push({code:"hk", chineseName: "香港"});
    countries.push({code:"hm", chineseName: "赫德岛和麦克唐纳群岛"});
    countries.push({code:"hn", chineseName: "洪都拉斯"});
    countries.push({code:"hr", chineseName: "克罗地亚"});
    countries.push({code:"ht", chineseName: "海地"});
    countries.push({code:"hu", chineseName: "匈牙利"});
    countries.push({code:"id", chineseName: "印尼"});
    countries.push({code:"ie", chineseName: "爱尔兰"});
    countries.push({code:"il", chineseName: "以色列"});
    countries.push({code:"im", chineseName: "马恩岛"});
    countries.push({code:"in", chineseName: "印度"});
    countries.push({code:"io", chineseName: "英属印度洋领地"});
    countries.push({code:"iq", chineseName: "伊拉克"});
    countries.push({code:"ir", chineseName: "伊朗"});
    countries.push({code:"is", chineseName: "冰岛"});
    countries.push({code:"it", chineseName: "意大利"});
    countries.push({code:"je", chineseName: "泽西岛"});
    countries.push({code:"jm", chineseName: "牙买加"});
    countries.push({code:"jo", chineseName: "约旦"});
    countries.push({code:"jp", chineseName: "日本"});
    countries.push({code:"ke", chineseName: "肯尼亚"});
    countries.push({code:"kg", chineseName: "吉尔吉斯斯坦"});
    countries.push({code:"kh", chineseName: "柬埔寨"});
    countries.push({code:"ki", chineseName: "基里巴斯"});
    countries.push({code:"km", chineseName: "科摩罗"});
    countries.push({code:"kn", chineseName: "圣基茨和尼维斯"});
    countries.push({code:"kp", chineseName: "朝鲜"});
    countries.push({code:"kr", chineseName: "韩国"});
    countries.push({code:"kw", chineseName: "科威特"});
    countries.push({code:"ky", chineseName: "开曼群岛"});
    countries.push({code:"kz", chineseName: "哈萨克斯坦"});
    countries.push({code:"la", chineseName: "老挝"});
    countries.push({code:"lb", chineseName: "黎巴嫩"});
    countries.push({code:"lc", chineseName: "圣卢西亚"});
    countries.push({code:"li", chineseName: "列支敦士登"});
    countries.push({code:"lk", chineseName: "斯里兰卡"});
    countries.push({code:"lr", chineseName: "利比里亚"});
    countries.push({code:"ls", chineseName: "莱索托"});
    countries.push({code:"lt", chineseName: "立陶宛"});
    countries.push({code:"lu", chineseName: "卢森堡"});
    countries.push({code:"lv", chineseName: "拉脱维亚"});
    countries.push({code:"ly", chineseName: "利比亚"});
    countries.push({code:"ma", chineseName: "摩洛哥"});
    countries.push({code:"mc", chineseName: "摩纳哥"});
    countries.push({code:"md", chineseName: "摩尔多瓦"});
    countries.push({code:"me", chineseName: "黑山"});
    countries.push({code:"mf", chineseName: "法属圣马丁"});
    countries.push({code:"mg", chineseName: "马达加斯加"});
    countries.push({code:"mh", chineseName: "马绍尔群岛"});
    countries.push({code:"mk", chineseName: "马其顿"});
    countries.push({code:"ml", chineseName: "马里"});
    countries.push({code:"mm", chineseName: "缅甸"});
    countries.push({code:"mn", chineseName: "蒙古国"});
    countries.push({code:"mo", chineseName: "澳门"});
    countries.push({code:"mp", chineseName: "北马里亚纳群岛"});
    countries.push({code:"mq", chineseName: "马提尼克"});
    countries.push({code:"mr", chineseName: "毛里塔尼亚"});
    countries.push({code:"ms", chineseName: "蒙塞拉特岛"});
    countries.push({code:"mt", chineseName: "马耳他"});
    countries.push({code:"mu", chineseName: "毛里求斯"});
    countries.push({code:"mv", chineseName: "马尔代夫"});
    countries.push({code:"mw", chineseName: "马拉维"});
    countries.push({code:"mx", chineseName: "墨西哥"});
    countries.push({code:"my", chineseName: "马来西亚"});
    countries.push({code:"mz", chineseName: "莫桑比克"});
    countries.push({code:"na", chineseName: "纳米比亚"});
    countries.push({code:"nc", chineseName: "新喀里多尼亚"});
    countries.push({code:"ne", chineseName: "尼日尔"});
    countries.push({code:"nf", chineseName: "诺福克岛"});
    countries.push({code:"ng", chineseName: "尼日利亚"});
    countries.push({code:"ni", chineseName: "尼加拉瓜"});
    countries.push({code:"nl", chineseName: "荷兰"});
    countries.push({code:"no", chineseName: "挪威"});
    countries.push({code:"np", chineseName: "尼泊尔"});
    countries.push({code:"nr", chineseName: "瑙鲁"});
    countries.push({code:"nu", chineseName: "纽埃"});
    countries.push({code:"nz", chineseName: "新西兰"});
    countries.push({code:"om", chineseName: "阿曼"});
    countries.push({code:"pa", chineseName: "巴拿马"});
    countries.push({code:"pe", chineseName: "秘鲁"});
    countries.push({code:"pf", chineseName: "法属波利尼西亚"});
    countries.push({code:"pg", chineseName: "巴布亚新几内亚"});
    countries.push({code:"ph", chineseName: "菲律宾"});
    countries.push({code:"pk", chineseName: "巴基斯坦"});
    countries.push({code:"pl", chineseName: "波兰"});
    countries.push({code:"pm", chineseName: "圣皮埃尔和密克隆"});
    countries.push({code:"pn", chineseName: "皮特凯恩群岛"});
    countries.push({code:"pr", chineseName: "波多黎各"});
    countries.push({code:"ps", chineseName: "巴勒斯坦"});
    countries.push({code:"pt", chineseName: "葡萄牙"});
    countries.push({code:"pw", chineseName: "帕劳"});
    countries.push({code:"py", chineseName: "巴拉圭"});
    countries.push({code:"qa", chineseName: "卡塔尔"});
    countries.push({code:"re", chineseName: "留尼汪"});
    countries.push({code:"ro", chineseName: "罗马尼亚"});
    countries.push({code:"rs", chineseName: "塞尔维亚"});
    countries.push({code:"ru", chineseName: "俄罗斯"});
    countries.push({code:"rw", chineseName: "卢旺达"});
    countries.push({code:"sa", chineseName: "沙特阿拉伯"});
    countries.push({code:"sb", chineseName: "所罗门群岛"});
    countries.push({code:"sc", chineseName: "塞舌尔"});
    countries.push({code:"sd", chineseName: "苏丹"});
    countries.push({code:"se", chineseName: "瑞典"});
    countries.push({code:"sg", chineseName: "新加坡"});
    countries.push({code:"sh", chineseName: "圣赫勒拿"});
    countries.push({code:"si", chineseName: "斯洛文尼亚"});
    countries.push({code:"sj", chineseName: "斯瓦尔巴群岛和扬马延岛"});
    countries.push({code:"sk", chineseName: "斯洛伐克"});
    countries.push({code:"sl", chineseName: "塞拉利昂"});
    countries.push({code:"sm", chineseName: "圣马力诺"});
    countries.push({code:"sn", chineseName: "塞内加尔"});
    countries.push({code:"so", chineseName: "索马里"});
    countries.push({code:"sr", chineseName: "苏里南"});
    countries.push({code:"ss", chineseName: "南苏丹"});
    countries.push({code:"st", chineseName: "圣多美和普林西比"});
    countries.push({code:"sv", chineseName: "萨尔瓦多"});
    countries.push({code:"sx", chineseName: "荷属圣马丁"});
    countries.push({code:"sy", chineseName: "叙利亚"});
    countries.push({code:"sz", chineseName: "斯威士兰"});
    countries.push({code:"tc", chineseName: "特克斯和凯科斯群岛"});
    countries.push({code:"td", chineseName: "乍得"});
    countries.push({code:"tf", chineseName: "法属南部领地"});
    countries.push({code:"tg", chineseName: "多哥"});
    countries.push({code:"th", chineseName: "泰国"});
    countries.push({code:"tj", chineseName: "塔吉克斯坦"});
    countries.push({code:"tk", chineseName: "托克劳"});
    countries.push({code:"tl", chineseName: "东帝汶"});
    countries.push({code:"tm", chineseName: "土库曼斯坦"});
    countries.push({code:"tn", chineseName: "突尼斯"});
    countries.push({code:"to", chineseName: "汤加"});
    countries.push({code:"tr", chineseName: "土耳其"});
    countries.push({code:"tt", chineseName: "特立尼达和多巴哥"});
    countries.push({code:"tv", chineseName: "图瓦卢"});
    countries.push({code:"tw", chineseName: "台湾"});
    countries.push({code:"tz", chineseName: "坦桑尼亚"});
    countries.push({code:"ua", chineseName: "乌克兰"});
    countries.push({code:"ug", chineseName: "乌干达"});
    countries.push({code:"um", chineseName: "美国本土外小岛屿"});
    countries.push({code:"us", chineseName: "美国"});
    countries.push({code:"uy", chineseName: "乌拉圭"});
    countries.push({code:"uz", chineseName: "乌兹别克斯坦"});
    countries.push({code:"va", chineseName: "梵蒂冈"});
    countries.push({code:"vc", chineseName: "圣文森特和格林纳丁斯"});
    countries.push({code:"ve", chineseName: "委内瑞拉"});
    countries.push({code:"vg", chineseName: "英属维尔京群岛"});
    countries.push({code:"vi", chineseName: "美属维尔京群岛"});
    countries.push({code:"vn", chineseName: "越南"});
    countries.push({code:"vu", chineseName: "瓦努阿图"});
    countries.push({code:"wf", chineseName: "瓦利斯和富图纳"});
    countries.push({code:"ws", chineseName: "萨摩亚"});
    countries.push({code:"ye", chineseName: "也门"});
    countries.push({code:"yt", chineseName: "马约特"});
    countries.push({code:"za", chineseName: "南非"});
    countries.push({code:"zm", chineseName: "赞比亚"});
    countries.push({code:"zw", chineseName: "津巴布韦"});

    var countryCode2ChName={};
    var idx =0;
    for(;idx<countries.length;idx++){
        var c = countries[idx];
        countryCode2ChName[c.code]= c.chineseName;
    }

    function onSearchCountry(){
        var kw = $("#countryKeyword").val();
            var ul = $("#countryUl");
            ul.html("");
            var idx=0;
            var countryInput=$("input[name=supportCountries]");
            var supportedCountries = countryInput.val()==""?[]:countryInput.val().split(",");
            for(;idx<countries.length;idx++){
                var c= countries[idx];
                var flag=true;
                if(kw == ""){
                    if(supportedCountries.length>0){
	                    for(var i=0;i<supportedCountries.length;i++){
		                    if(supportedCountries[i]==c.code){
			                    ul.append($("<label class=\"checkbox inline\"> <input type=checkbox value=\""+ c.code+"\" checked=true>"+ c.chineseName+"</label>"));
		                    	flag=false;
		                    	break;
		                    }
	                    }
	                    if(flag){
	                    	ul.append($("<label class=\"checkbox inline\"> <input type=checkbox value=\""+ c.code+"\" >"+ c.chineseName+"</label>"));
	                    	flag = true;
	                    }
                    }else{
                    	 ul.append($("<label class=\"checkbox inline\"> <input type=checkbox value=\""+ c.code+"\" >"+ c.chineseName+"</label>"));
                    }
                }else{
                	if(supportedCountries.length>0){
	                    for(var i=0;i<supportedCountries.length;i++){
		                    if(supportedCountries[i]==c.code&&c.chineseName.indexOf(kw)>=0){
			                    ul.append($("<label class=\"checkbox inline\"> <input type=checkbox value=\""+ c.code+"\" checked=true>"+ c.chineseName+"</label>"));
		                    	flag=false;
		                    	break;
		                    }else if(flag&&c.chineseName.indexOf(kw)>=0){
		                    	ul.append($("<label class=\"checkbox inline\"> <input type=checkbox value=\""+ c.code+"\" >"+ c.chineseName+"</label>"));
		                    	flag = false;
		                    	break;
		                    }
	                    }
                    }else{
                    	if(flag&&c.chineseName.indexOf(kw)>=0){
	                    	ul.append($("<label class=\"checkbox inline\"> <input type=checkbox value=\""+ c.code+"\" >"+ c.chineseName+"</label>"));
	                    	flag = true;
	                    }
                    }
                }
                }
    }
    function onAddCountry(){
        var countryInput=$("input[name=supportCountries]");
        countryInput.val('');
        var countries = countryInput.val()==""?[]:countryInput.val().split(",");
        $("#countryUl input").each(function(idx, elem){
            if(elem.checked){
                var val = elem.value
                if ($.inArray(val, countries) == -1){
                    countries.push(val);
                }
            }
        });
        countryInput.val(countries.join(","));
        renderSelectedCountries(countries);
        $('#selectCountryModal').modal('hide');
    }

    function renderSelectedCountries(countries){
        var scd = $("#selectedCountriesDiv");
        scd.html("");
        var idx=0;
        for(;idx<countries.length;idx++){
            var c = countries[idx];
            scd.append("<span class=\"delLable\">"+countryCode2ChName[c]+"<a href=\"#\" onclick=\"javascript:delCountry(this, '"+c+"')\" class=\"delIcon\">×</a></span>");
        }
    }

    function initSelectedCountries(){
        var countryInput=$("input[name=supportCountries]");
        var countries = countryInput.val()==""?[]:countryInput.val().split(",");
        renderSelectedCountries(countries);
    }

    function delCountry(elem, country){
        var countryInput=$("input[name=supportCountries]");
        var countries = countryInput.val()==""?[]:countryInput.val().split(",");
        countries.splice($.inArray(country, countries),1);
        countryInput.val(countries.join(","));
        $(elem).parent("span").remove();
    }

    function onSelectAllCountry(){
        $("#countryUl input").each(function(idx, elem){
            elem.checked=true;
        });
    }

    function onUnSelectAllCountry(){
        $("#countryUl input").each(function(idx, elem){
            elem.checked=false;
        });
    }
    $("#countryKeyword").keypress(function(event) {
        if ( event.which == 13 ) {
            event.preventDefault();
            onSearchCountry();
        }
    });
    $.ready(initSelectedCountries());
</script>