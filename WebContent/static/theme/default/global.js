//for login
function checkLogin() {
	var username = $('[name="username"]').val();
	var passwd = $('[name="passwd"]').val();
	if (username == "") {
		$("#errors").html("您还没有输入用户名!");
		return false;
	}
	if (passwd == "") {
		$("#errors").html("您还没有输入密码!");
		return false;
	}
	var request = $.ajax({
		type : "POST",
		url : "login",
		data : {
			"username" : username,
			"passwd" : passwd
		}
	});
	request.done(function(msg) {
		var result=eval(msg);
		if(result.success){
			location.href=location.href.substr(0,location.href.length-5);
		}else{
			$("#errors").html(result.msg);
		}
	});
	request.fail(function(msg) {
		$("#errors").html("服务器繁忙，请稍后再试!");
	});
	return false;
}
function showDetail(count,elem){
	$("#detail_"+count).toggle();
	$("i",elem).toggleClass(function (){
		if($(this).hasClass("icon-plus-sign")){
			return "icon-minus-sign";
		} else {
			return "icon-plus-sign";
		}
	});
}
//for some operations
function doOps(url,data){
	var request = $.ajax({
		type : "POST",
		url : $.trim(url),
		data : data
	});
	request.done(function(msg) {
		var result=eval(msg);
		if(result.success){
			gotoPage($("#paginator .active a").html());
		}else{
			alert("操作失败，请稍后再试！");
		}
	});
	request.fail(function(msg) {
		alert("服务器繁忙，请稍后再试!");
	});
}
//-------form check-----------
function clearMsg(){
	$("#errors").html("");
	$("#msg").html("");
}
function empty(input){
	return $.trim(input.val())=="";
}
function required(input,msg){
	if($.trim(input.val())==""){
		$("#errors").append("<li>"+msg+"</li>");
		return false;
	}
	return true;
}
function notVal(input,val,msg){
	if($.trim(input.val())==val){
		$("#errors").append("<li>"+msg+"</li>");
		return false;
	}
	return true;
}
function greatThanlength(input,len,msg){
	if(input.val().length>len){
		$("#errors").append("<li>"+msg+"</li>");
		return false;
	}
	return true;
}
function notRange(input,min,max,msg){
	if(input.val().length>max || input.val().length<min){
		$("#errors").append("<li>"+msg+"</li>");
		return false;
	}
	return true;
}
function notIntValueRange(input,min,max,msg){
	var val=parseInt(input.val());
	if(val>max || val<min){
		$("#errors").append("<li>"+msg+"</li>");
		return false;
	}
	return true;
}
function checkSize(input, msg){
	if(!/^\d+\.?\d*[GgMmKk][Bb]$/.test(input.val())){
		$("#errors").append("<li>"+msg+"</li>");
		return false;
	}
	return true;
}
function checkFreqency(input, msg){
	if(!/^\d+\.?\d*[GgMmKk]?[Hh][Zz]$/.test(input.val())){
		$("#errors").append("<li>"+msg+"</li>");
		return false;
	}
	return true;
}
function checkVersion(input, msg){
	if(!/^\d+(\.\d+){0,2}$/.test(input.val())){
		$("#errors").append("<li>"+msg+"</li>");
		return false;
	}
	return true;
}
function checkAllDigits(input, msg){
	if(!/^\d+$/.test(input.val())){
		$("#errors").append("<li>"+msg+"</li>");
		return false;
	}
	return true;
}
function checkUrl(input, msg){
	if(!/^https?:\/\/(\w+\.)+\w+(:\d{2,5})?(\/.*)?$/.test(input.val())){
		$("#errors").append("<li>"+msg+"</li>");
		return false;
	}
	return true;
}
//------channel form ---------
function onSubmitChannelForm(form){
	var ctx=$(form);
	clearMsg();
	var pass=required($("input[name='name']",ctx), "渠道名称不能为空");
	//var a=input.attr("name");
	pass=pass&&notRange($("input[name='name']",ctx), 1, 20, "渠道名称长度应在1~20个字符");
	
	pass=pass&&checkAllDigits($("input[name='shareProportion']",ctx), "分成比例不能为空，且只能输入数字");
	pass=pass&&notIntValueRange($("input[name='shareProportion']",ctx), 0, 100, "分成比例范围0~100");
	
	pass=pass&&checkAllDigits($("input[name='hiddenProportion']",ctx), "结算比例不能为空，且只能输入数字");
	pass=pass&&notIntValueRange($("input[name='hiddenProportion']",ctx), 0, 100, "结算比例范围0~100");
	return pass;
}
//------client form ---------
function onSubmitClientForm(form){
	var ctx=$(form);
	clearMsg();
	var pass=checkAllDigits($("input[name='channelHiddenProportion']",ctx), "扣量比例不能为空，且只能输入数字");
	pass=pass&&notIntValueRange($("input[name='channelHiddenProportion']",ctx), 0, 100, "扣量比例范围0~100");
	
	pass=pass&&checkAllDigits($("input[name='monthLimit']",ctx), "月限额不能为空，且只能输入数字");
	pass=pass&&notIntValueRange($("input[name='monthLimit']",ctx), 0, 10000, "月限额范围0~10000");
	return pass;
}
//------sp_channel form ---------
function onSubmitSpChannelForm(form){
	var ctx=$(form);
	clearMsg();
	var pass=required($("input[name='name']",ctx), "通道名称不能为空");
	pass=pass&&notRange($("input[name='name']",ctx), 1, 100, "通道名称长度应在1~100个字符");
	
	pass=pass&&required($("select[name='company']",ctx), "接入公司名不能为空");
	pass=pass&&notRange($("select[name='company']",ctx), 1, 20, "接入公司名长度应在1~20个字符");
	
	pass=pass&&checkAllDigits($("input[name='totalDayLimit']",ctx), "总日限额不能为空，且只能输入数字");
	pass=pass&&notIntValueRange($("input[name='totalDayLimit']",ctx), 0, 10000, "总日限额范围0~10000");
	
	pass=pass&&checkAllDigits($("input[name='userDayLimit']",ctx), "单用户日限额不能为空，且只能输入数字");
	pass=pass&&notIntValueRange($("input[name='userDayLimit']",ctx), 0, 10000, "单用户日限额范围0~10000");
	
	pass=pass&&checkAllDigits($("input[name='userMonthLimit']",ctx), "单用户月限额不能为空，且只能输入数字");
	pass=pass&&notIntValueRange($("input[name='userMonthLimit']",ctx), 0, 10000, "单用户月限额范围0~10000");
	
	pass=pass&&checkAllDigits($("input[name='fee']",ctx), "业务资费不能为空，且只能输入数字");
	pass=pass&&notIntValueRange($("input[name='fee']",ctx), 0, 1000000, "业务资费范围0~1000000");
	
	pass=pass&&checkAllDigits($("input[name='settlementPrice']",ctx), "结算价格不能为空，且只能输入数字");
	pass=pass&&notIntValueRange($("input[name='settlementPrice']",ctx), 0, 1000000, "结算价格范围0~1000000");
	
	pass=pass&&required($("input[name='instruction']",ctx), "指令不能为空");
	pass=pass&&notRange($("input[name='instruction']",ctx), 1, 100, "指令长度应在1~100个字符");
	
	pass=pass&&required($("input[name='port']",ctx), "端口不能为空");
	pass=pass&&notRange($("input[name='port']",ctx), 1, 20, "端口长度应在1~20个字符");
	
	pass=pass&&required($("input[name='serverIp']",ctx), "SP服务器IP不能为空");
	pass=pass&&checkUrl($("input[name='serverIp']",ctx),"SP服务器IP格式不正确！例：http://zhanghui.com");
	return pass;
}
//------product form ---------
function onSubmitProductForm(form){
	var ctx=$(form);
	clearMsg();
	var pass=required($("input[name='name']",ctx),"产品名称不能为空！");
	pass=pass&&notRange($("input[name='name']",ctx), 1, 20, "产品名称长度应在1~20个字符");
	
	pass=pass&&required($("input[name='pricePointId']",ctx), "对接计费产品ID不能为空");
	pass=pass&&notRange($("input[name='pricePointId']",ctx), 1, 20, "对接计费产品ID长度应在1~20个字符");
	return pass;
}
//------product_mt form ---------
function onSubmitProductMtForm(form){
	var ctx=$(form);
	clearMsg();
	
	var pass=required($("select[name='pricePointId']",ctx), "对接计费产品ID不能为空");
	pass=pass&&notRange($("select[name='pricePointId']",ctx), 1, 20, "对接计费产品ID长度应在1~20个字符");
	
	pass=pass&&required($("input[name='mtInfo']",ctx), "MT信息不能为空");
	pass=pass&&notRange($("input[name='mtInfo']",ctx), 1, 1000, "MT信息长度应在1~1000个字符");
	return pass;
}




//------app_adv form ---------
function onSubmitAppAdvForm(form){
	var ctx=$(form);
	clearMsg();
	var pass=required($("input[name='appName']",ctx),"应用名称不能为空！");
	pass=pass&&notRange($("input[name='appName']",ctx),1,100,"应用名称长度应该在1-100个字符！");
	if(isNaN(parseInt($("input[name='id']",ctx).val()))){
		pass=pass&&required($("input[name='file']",ctx),"应用文件不能为空！");
	}
	pass=pass&&notVal($("select[name='appTypeId']",ctx),-1,"应用类型不能为空！");
	pass=pass&&notVal($("select[name='advTypeId']",ctx),-1,"广告类型不能为空！");
	pass=pass&&notVal($("select[name='settlementTypeId']",ctx),-1,"结算类型不能为空！");
	
	pass=pass&&greatThanlength($("textarea[name='desc']",ctx),1000,"应用介绍长度不能超过1000个字符！");
	
	pass=pass&&(empty($("input[name='cpuSpeed']",ctx))||checkFreqency($("input[name='cpuSpeed']",ctx),"CPU速度格式不正确，应为xxGHZ/MHZ/KHZ/HZ！"));
	pass=pass&&(empty($("input[name='ramSize']",ctx))||checkSize($("input[name='ramSize']",ctx),"RAM容量格式不正确，应为xxGB/MB/KB！"));
	pass=pass&&(empty($("input[name='romSize']",ctx))||checkSize($("input[name='romSize']",ctx),"ROM容量格式不正确，应为xxGB/MB/KB！"));
	pass=pass&&(empty($("input[name='sdSize']",ctx))||checkSize($("input[name='sdSize']",ctx),"SD卡容量格式不正确，应为xxGB/MB/KB！"));
	pass=pass&&(empty($("input[name='gpuVer']",ctx))||checkVersion($("input[name='gpuVer']",ctx),"GPU版本版本号格式不正确，应为x.x.x，如：4.0.3！"));
	pass=pass&&(empty($("input[name='androidVer']",ctx))||checkVersion($("input[name='androidVer']",ctx),"安卓系统版本号格式不正确，应为x.x.x，如：4.0.3！"));
	pass=pass&&(empty($("input[name='androidCoreVer']",ctx))||checkVersion($("input[name='androidCoreVer']",ctx),"安卓内核版本号格式不正确，应为x.x.x，如：4.0.3！"));
	return pass;
}
//-------brand or other adv form ----------
function onSubmitBrandOrOtherAdvForm(form){
	var ctx=$(form);
	clearMsg();
	var pass=required($("input[name='advName']",ctx),"广告名称不能为空！");
	pass=pass&&notRange($("input[name='advName']",ctx),1,100,"广告名称长度应该在1-100个字符！");
	
	if(document.getElementsByName("advCatalog")[0].checked){//品牌广告
		if($("input[name='file']",ctx).val()=="" && $("input[name='advFileName']",ctx).val()==""){
			$("#errors").append("<li>广告文件不能为空！</li>");
			pass=pass&&false;
		}
	}else{//其它广告
		if($("input[name='url']",ctx).val()==""){
			$("#errors").append("<li>广告网络链接不能为空！</li>");
			pass=pass&&false;
		}else{
			pass=pass&&checkUrl($("input[name='url']",ctx),"广告网络链接格式不正确！");
		}
	}
	
	pass=pass&&notVal($("select[name='promoteTypeId']",ctx),-1,"推广类型不能为空！");
	pass=pass&&notVal($("select[name='advTypeId']",ctx),-1,"广告类型不能为空！");
	pass=pass&&notVal($("select[name='settlementTypeId']",ctx),-1,"结算类型不能为空！");
	
	pass=pass&&greatThanlength($("textarea[name='desc']",ctx),1000,"广告描述长度不能超过1000个字符！");
	return pass;
}
//-------config form ----------
function onSubmitConfigForm(form){
	var ctx=$(form);
	clearMsg();
	var pass=checkAllDigits($("input[name='advFrequency']",ctx),"广告展示频率不能为空，且应由数字组成！");
	pass=pass&&notIntValueRange($("input[name='advFrequency']",ctx),1,1000,"广告展示频率应该在[1,1000]范围之内!");
	pass=pass&&checkAllDigits($("input[name='advSmsNumber']",ctx),"默认短信发送号码不能为空，且应由数字组成！");
	return pass;
}
//-------uLifeUrl form ----------
function onSubmitULifeUrlForm(form){
	var ctx=$(form);
	clearMsg();
	var pass=required($("input[name='module']",ctx),"模块名称不能为空！");
	pass=pass&&notRange($("input[name='module']",ctx),1,40,"模块名称长度应该在40个字符以内！");
	var pass=required($("input[name='url']",ctx),"链接不能为空！");
	pass=pass&&notRange($("input[name='url']",ctx),1,500,"链接长度应该在500个字符以内！");
	pass=pass&&checkUrl($("input[name='url']",ctx),"链接格式不正确！");
	return pass;
}
//-------uLifeAdv form ----------
function onSubmitULifeAdvForm(form){
	var ctx=$(form);
	clearMsg();
	var pass=required($("input[name='module']",ctx),"模块名称不能为空！");
	pass=pass&&notRange($("input[name='module']",ctx),1,40,"模块名称长度应该在40个字符以内！");
	$("input[name=mediaPath]").each(
		function(idx,elem){
			elem=$(elem);
			if(elem.val()=="") 
				elem.parent().remove();
		}
	);
	return pass;
}