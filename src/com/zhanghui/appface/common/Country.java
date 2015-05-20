package com.zhanghui.appface.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Country {
    public final static List<Country> countries = initCountries();
    public final static Map<String,Country> shortcut2CountryMap= initShortcut2CountryMap();
    public final static Map<String,Country> mcc2CountryMap= initMcc2CountryMap();

    private String shortcut;
    private String chineseName;
    private String mcc;

    private static Map<String,Country> initMcc2CountryMap() {
        Map<String, Country> map=new HashMap<>(countries.size());
        for(Country country : countries){
            map.put(country.getMcc(), country);
        }
        return map;
    }


    private static Map<String,Country> initShortcut2CountryMap() {
        Map<String, Country> map=new HashMap<>(countries.size());
        for(Country country : countries){
            map.put(country.getShortcut(), country);
        }
        return map;
    }


    private static List<Country> initCountries() {
        List<Country> _countries=new ArrayList<Country>(217);
        _countries.add(new Country("ae","431","阿联酋"));
        _countries.add(new Country("af","412","阿富汗"));
        _countries.add(new Country("ag","344","安提瓜和巴布达"));
        _countries.add(new Country("ai","365","安圭拉"));
        _countries.add(new Country("al","276","阿尔巴尼亚"));
        _countries.add(new Country("am","283","亚美尼亚"));
        _countries.add(new Country("ao","631","安哥拉"));
        _countries.add(new Country("ar","722","阿根廷"));
        _countries.add(new Country("as","544","美属萨摩亚"));
        _countries.add(new Country("at","232","奥地利"));
        _countries.add(new Country("au","505","澳大利亚"));
        _countries.add(new Country("aw","363","阿鲁巴"));
        _countries.add(new Country("az","400","阿塞拜疆"));
        _countries.add(new Country("ba","218","波斯尼亚和黑塞哥维那"));
        _countries.add(new Country("bb","342","巴巴多斯"));
        _countries.add(new Country("bd","470","孟加拉"));
        _countries.add(new Country("be","206","比利时"));
        _countries.add(new Country("bf","613","布基纳法索"));
        _countries.add(new Country("bg","284","保加利亚"));
        _countries.add(new Country("bh","426","巴林"));
        _countries.add(new Country("bi","642","布隆迪"));
        _countries.add(new Country("bj","616","贝宁"));
        _countries.add(new Country("bm","350","百慕大"));
        _countries.add(new Country("bn","528","文莱"));
        _countries.add(new Country("bo","736","玻利维亚"));
        _countries.add(new Country("br","724","巴西"));
        _countries.add(new Country("bs","364","巴哈马"));
        _countries.add(new Country("bt","402","不丹"));
        _countries.add(new Country("bw","652","博茨瓦纳"));
        _countries.add(new Country("by","257","白俄罗斯"));
        _countries.add(new Country("bz","702","伯利兹"));
        _countries.add(new Country("ca","302","加拿大"));
        _countries.add(new Country("cd","630","刚果"));
        _countries.add(new Country("cf","623","中非"));
        _countries.add(new Country("ch","228","瑞士"));
        _countries.add(new Country("ci","612","科特迪瓦"));
        _countries.add(new Country("ck","548","库克群岛"));
        _countries.add(new Country("cl","730","智利"));
        _countries.add(new Country("cm","624","喀麦隆"));
        _countries.add(new Country("cn","461","中国"));
        _countries.add(new Country("co","732","哥伦比亚"));
        _countries.add(new Country("cr","712","哥斯达黎加"));
        _countries.add(new Country("cu","368","古巴"));
        _countries.add(new Country("cv","625","佛得角"));
        _countries.add(new Country("cy","280","塞浦路斯"));
        _countries.add(new Country("cz","230","捷克"));
        _countries.add(new Country("de","262","德国"));
        _countries.add(new Country("dj","638","吉布提"));
        _countries.add(new Country("dk","238","丹麦"));
        _countries.add(new Country("dm","366","多米尼克"));
        _countries.add(new Country("do","370","多米尼加"));
        _countries.add(new Country("dz","603","阿尔及利亚"));
        _countries.add(new Country("ec","740","厄瓜多尔"));
        _countries.add(new Country("ee","248","爱沙尼亚"));
        _countries.add(new Country("eg","602","埃及"));
        _countries.add(new Country("er","657","厄立特里亚"));
        _countries.add(new Country("es","214","西班牙"));
        _countries.add(new Country("et","636","埃塞俄比亚"));
        _countries.add(new Country("fi","244","芬兰"));
        _countries.add(new Country("fj","542","斐济"));
        _countries.add(new Country("fm","550","密克罗尼西亚联邦"));
        _countries.add(new Country("fo","288","法罗群岛"));
        _countries.add(new Country("fr","208","法国"));
        _countries.add(new Country("ga","628","加蓬"));
        _countries.add(new Country("gb","234","英国"));
        _countries.add(new Country("gd","352","格林纳达"));
        _countries.add(new Country("ge","282","格鲁吉亚"));
        _countries.add(new Country("gf","742","法属圭亚那"));
        _countries.add(new Country("gh","620","加纳"));
        _countries.add(new Country("gi","266","直布罗陀"));
        _countries.add(new Country("gl","290","格陵兰"));
        _countries.add(new Country("gm","607","冈比亚"));
        _countries.add(new Country("gn","611","几内亚"));
        _countries.add(new Country("gp","340","瓜德罗普"));
        _countries.add(new Country("gq","627","赤道几内亚"));
        _countries.add(new Country("gr","202","希腊"));
        _countries.add(new Country("gt","704","危地马拉"));
        _countries.add(new Country("gu","535","关岛"));
        _countries.add(new Country("gy","738","圭亚那"));
        _countries.add(new Country("hk","454","香港"));
        _countries.add(new Country("hn","708","洪都拉斯"));
        _countries.add(new Country("hr","219","克罗地亚"));
        _countries.add(new Country("ht","372","海地"));
        _countries.add(new Country("hu","216","匈牙利"));
        _countries.add(new Country("id","510","印度尼西亚"));
        _countries.add(new Country("ie","272","爱尔兰"));
        _countries.add(new Country("il","425","以色列"));
        _countries.add(new Country("in","406","印度"));
        _countries.add(new Country("iq","418","伊拉克"));
        _countries.add(new Country("ir","432","伊朗"));
        _countries.add(new Country("is","274","冰岛"));
        _countries.add(new Country("it","222","意大利"));
        _countries.add(new Country("jm","338","牙买加"));
        _countries.add(new Country("jo","416","约旦"));
        _countries.add(new Country("jp","440","日本"));
        _countries.add(new Country("ke","639","肯尼亚"));
        _countries.add(new Country("kg","437","吉尔吉斯斯坦"));
        _countries.add(new Country("kh","456","柬埔寨"));
        _countries.add(new Country("ki","545","基里巴斯"));
        _countries.add(new Country("km","654","科摩罗"));
        _countries.add(new Country("kn","356","圣基茨和尼维斯"));
        _countries.add(new Country("kp","467","朝鲜"));
        _countries.add(new Country("kr","450","韩国"));
        _countries.add(new Country("kw","419","科威特"));
        _countries.add(new Country("ky","346","开曼群岛"));
        _countries.add(new Country("kz","401","哈萨克斯坦"));
        _countries.add(new Country("la","457","老挝"));
        _countries.add(new Country("lb","415","黎巴嫩"));
        _countries.add(new Country("lc","358","圣卢西亚"));
        _countries.add(new Country("li","295","列支敦士登"));
        _countries.add(new Country("lk","413","斯里兰卡"));
        _countries.add(new Country("lr","618","利比里亚"));
        _countries.add(new Country("ls","651","莱索托"));
        _countries.add(new Country("lt","246","立陶宛"));
        _countries.add(new Country("lv","247","拉脱维亚"));
        _countries.add(new Country("ly","606","利比亚"));
        _countries.add(new Country("ma","604","摩洛哥"));
        _countries.add(new Country("mc","212","摩纳哥"));
        _countries.add(new Country("md","259","摩尔多瓦"));
        _countries.add(new Country("me","297","黑山"));
        _countries.add(new Country("mg","646","马达加斯加"));
        _countries.add(new Country("mh","551","马绍尔群岛"));
        _countries.add(new Country("mk","294","卢森堡"));
        _countries.add(new Country("ml","610","马里"));
        _countries.add(new Country("mm","414","缅甸"));
        _countries.add(new Country("mn","428","蒙古"));
        _countries.add(new Country("mo","455","澳门"));
        _countries.add(new Country("mp","534","北马里亚纳群岛"));
        _countries.add(new Country("mq","340","马提尼克"));
        _countries.add(new Country("mr","609","毛里塔尼亚"));
        _countries.add(new Country("ms","354","蒙塞拉特岛"));
        _countries.add(new Country("mt","278","马耳他"));
        _countries.add(new Country("mu","617","毛里求斯"));
        _countries.add(new Country("mv","472","马尔代夫"));
        _countries.add(new Country("mw","650","马拉维"));
        _countries.add(new Country("mx","334","墨西哥"));
        _countries.add(new Country("my","502","马来西亚"));
        _countries.add(new Country("mz","643","莫桑比克"));
        _countries.add(new Country("na","649","纳米比亚"));
        _countries.add(new Country("nc","546","新喀里多尼亚"));
        _countries.add(new Country("ne","614","尼日尔"));
        _countries.add(new Country("ng","621","尼日利亚"));
        _countries.add(new Country("ni","710","尼加拉瓜"));
        _countries.add(new Country("nl","204","荷兰"));
        _countries.add(new Country("no","242","挪威"));
        _countries.add(new Country("np","429","尼泊尔"));
        _countries.add(new Country("nr","536","瑙鲁"));
        _countries.add(new Country("nu","555","纽埃"));
        _countries.add(new Country("nz","530","新西兰"));
        _countries.add(new Country("om","422","阿曼"));
        _countries.add(new Country("pa","714","巴拿马"));
        _countries.add(new Country("pe","716","秘鲁"));
        _countries.add(new Country("pf","547","法属波利尼西亚"));
        _countries.add(new Country("pg","537","巴布亚新几内亚"));
        _countries.add(new Country("ph","515","菲律宾"));
        _countries.add(new Country("pk","410","巴基斯坦"));
        _countries.add(new Country("pl","260","波兰"));
        _countries.add(new Country("pm","308","圣皮埃尔和密克隆"));
        _countries.add(new Country("pr","330","波多黎各"));
        _countries.add(new Country("ps","425","巴勒斯坦"));
        _countries.add(new Country("pt","268","葡萄牙"));
        _countries.add(new Country("pw","552","帕劳"));
        _countries.add(new Country("py","744","巴拉圭"));
        _countries.add(new Country("qa","427","卡塔尔"));
        _countries.add(new Country("re","647","留尼汪"));
        _countries.add(new Country("ro","226","罗马尼亚"));
        _countries.add(new Country("rs","220","塞尔维亚"));
        _countries.add(new Country("ru","250","俄罗斯"));
        _countries.add(new Country("rw","635","卢旺达"));
        _countries.add(new Country("sa","420","沙特阿拉伯"));
        _countries.add(new Country("sb","540","所罗门群岛"));
        _countries.add(new Country("sc","633","塞舌尔"));
        _countries.add(new Country("sd","634","苏丹"));
        _countries.add(new Country("se","240","瑞典"));
        _countries.add(new Country("sg","525","新加坡"));
        _countries.add(new Country("si","293","斯洛文尼亚"));
        _countries.add(new Country("sk","231","斯洛伐克"));
        _countries.add(new Country("sl","619","塞拉利昂"));
        _countries.add(new Country("sm","292","圣马力诺"));
        _countries.add(new Country("sn","608","塞内加尔"));
        _countries.add(new Country("so","637","索马里"));
        _countries.add(new Country("sr","746","苏里南"));
        _countries.add(new Country("st","626","圣多美和普林西比"));
        _countries.add(new Country("sv","706","萨尔瓦多"));
        _countries.add(new Country("sy","417","叙利亚"));
        _countries.add(new Country("sz","653","斯威士兰"));
        _countries.add(new Country("tc","376","特克斯和凯科斯群岛"));
        _countries.add(new Country("td","622","乍得"));
        _countries.add(new Country("tg","615","多哥"));
        _countries.add(new Country("th","520","泰国"));
        _countries.add(new Country("tj","436","塔吉克斯坦"));
        _countries.add(new Country("tl","514","东帝汶"));
        _countries.add(new Country("tm","438","土库曼斯坦"));
        _countries.add(new Country("tn","605","突尼斯"));
        _countries.add(new Country("to","539","汤加"));
        _countries.add(new Country("tr","286","土耳其"));
        _countries.add(new Country("tt","374","特立尼达和多巴哥"));
        _countries.add(new Country("tw","466","台湾"));
        _countries.add(new Country("tz","640","坦桑尼亚"));
        _countries.add(new Country("ua","255","乌克兰"));
        _countries.add(new Country("ug","641","乌干达"));
        _countries.add(new Country("us","316","美国"));
        _countries.add(new Country("uy","748","乌拉圭"));
        _countries.add(new Country("uz","434","乌兹别克斯坦"));
        _countries.add(new Country("va","225","梵蒂冈"));
        _countries.add(new Country("vc","360","圣文森特和格林纳丁斯"));
        _countries.add(new Country("ve","734","委内瑞拉"));
        _countries.add(new Country("vg","348","英属维尔京群岛"));
        _countries.add(new Country("vi","332","美属维尔京群岛"));
        _countries.add(new Country("vn","452","越南"));
        _countries.add(new Country("vu","541","瓦努阿图"));
        _countries.add(new Country("wf","543","瓦利斯和富图纳"));
        _countries.add(new Country("ws","549","萨摩亚"));
        _countries.add(new Country("ye","421","也门"));
        _countries.add(new Country("za","655","南非"));
        _countries.add(new Country("zm","645","赞比亚"));
        _countries.add(new Country("zw","648","津巴布韦"));
        return _countries;
    }


    public Country(String shortcut, String mcc, String chineseName) {
        this.shortcut = shortcut;
        this.chineseName = chineseName;
        this.mcc = mcc;
    }

    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }
}
