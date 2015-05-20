package com.zhanghui.appface.statistics.analyzer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.zhanghui.appface.statistics.common.*;
import com.zhanghui.appface.common.LogModule;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class LinkNodePvUvAnalyzer implements Analyzer {
    private Date date;
    private Map<String, ChartPvUvData> pvUvDataMap = new HashMap<>();
    private NDay nDay;


    public LinkNodePvUvAnalyzer(Date date) {
        this.date = date;
        this.nDay = new NDay("LINK_NODE_DAY_1", date);
    }

    private static class NDay {
        public String[] logFiles;
        public String[] dayStrings;
        public String module;

        NDay(String module, Date date) {
            this.module = module;
            this.logFiles = Config.getPreNDaysLogFiles(1, date);
            this.dayStrings = Config.getPreNDaysStrings(1, date);
        }
    }

    public static class LineEntry extends com.zhanghui.appface.statistics.common.LineEntry {
        public String country;
        public String linkNode;
        public String from;
        public String ipHash;
        @Override
        public LineEntry load(String line) {
            super.load(line);
            if (!LogModule.LinkNode.name().equals(name) || Strings.isNullOrEmpty(ip) || entries.length < 7) {
                valid = false;
                return this;
            }
            ipHash=String.valueOf(Math.abs(Objects.hashCode(ip))%256);
            country=entries[4];
            if(Strings.isNullOrEmpty(country)){
                country = "00";
            }
            linkNode=ChartPvUvData.mappedLinkNode(entries[6]);
            from=entries[7];
            from=from.length()>10?from.substring(0,10).trim():from.trim();//兼容线上from参数的错误
            if(Strings.isNullOrEmpty(from)){
                from="0000";
            }
            return this;
        }

        public String[] toKeys(){
            return new String[]{linkNode + Config.SEP + country + Config.SEP + from,
                    "all" + Config.SEP + country + Config.SEP + from,
                    linkNode + Config.SEP + "all" + Config.SEP + from,
                    linkNode + Config.SEP + country + Config.SEP + "all",
                    "all" + Config.SEP + "all" + Config.SEP + from,
                    "all" + Config.SEP + country + Config.SEP + "all",
                    linkNode + Config.SEP + "all" + Config.SEP + "all",
                    "all" + Config.SEP + "all" + Config.SEP + "all"
            };
        }
    }
    public static class PreHandledLineEntry extends ValidLineEntry{
        public String linkNode;
        public String country;
        public String from;
        public String ip;

        public PreHandledLineEntry load(String line){
            Iterator<String> fields = Splitter.on(Config.SEP).split(line).iterator();
            linkNode = fields.next();
            country = fields.next();
            from = fields.next();
            ip = fields.next();
            valid = true;
            return this;
        }

        public String[] toKeys(){
            return new String[]{linkNode + Config.SEP + country + Config.SEP + from,
                    "all" + Config.SEP + country + Config.SEP + from,
                    linkNode + Config.SEP + "all" + Config.SEP + from,
                    linkNode + Config.SEP + country + Config.SEP + "all",
                    "all" + Config.SEP + "all" + Config.SEP + from,
                    "all" + Config.SEP + country + Config.SEP + "all",
                    linkNode + Config.SEP + "all" + Config.SEP + "all",
                    "all" + Config.SEP + "all" + Config.SEP + "all"
            };
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
        }
    }

    public List<ChartPvUvData> analyze() throws Exception {
        System.out.print("pre processing...");
        preprocess();
        System.out.println("...done");
        System.out.print("processing...");
        process();
        System.out.println("...done");
        List<ChartPvUvData> pvUvDatas = new ArrayList<>(pvUvDataMap.size());
        for(Map.Entry<String, ChartPvUvData> entry : pvUvDataMap.entrySet()){
            pvUvDatas.add(entry.getValue());
        }
        return pvUvDatas;
    }

    private void process() throws IOException {
        File dir = new File(Config.DIR_LOG+"/pvuv/"+nDay.dayStrings[0]);
        if(dir.exists()) {
            File[] files = dir.listFiles();
            AbstractLogFileHandler handler = new AbstractLogFileHandler<PreHandledLineEntry>() {
                private Map<String, Set<String>> uvMap;

                @Override
                public PreHandledLineEntry loadLineEntry(String line) {
                    return new PreHandledLineEntry().load(line);
                }

                @Override
                public void preHandleSingleFile() {
                    uvMap = new HashMap<>();
                }

                @Override
                public void postHandleSingleFile() {
                    for(Map.Entry<String, Set<String>> entry : uvMap.entrySet()){
                        ChartPvUvData chartPvUvData = pvUvDataMap.get(entry.getKey());
                        chartPvUvData.setUv(chartPvUvData.getUv() + entry.getValue().size());
                    }
                }

                @Override
                public void handleLine(int fIdx, PreHandledLineEntry entry) {
                    if (entry.valid) {
                        String[] keys = entry.toKeys();
                        for(String key : keys) {
                            Set<String> ipSet = uvMap.get(key);
                            if (ipSet == null) {
                                ipSet = new HashSet<>();
                                uvMap.put(key, ipSet);
                            }
                            ipSet.add(entry.ip);
                        }
                    }
                }
            };
            handler.handleFile(files);
            FileUtils.deleteDirectory(dir);
        }
    }

    /**
     * 预处理
     * 1，按IP Hash切分文件
     * 2，统计PV
     */
    private void preprocess() throws IOException {
        //创建切分文件夹
        Path path=Paths.get(Config.DIR_LOG+"/pvuv/"+nDay.dayStrings[0]);
        FileUtils.deleteDirectory(path.toFile());
        Files.createDirectories(path);
        final Map<String, BufferedWriter> outFileMap=new HashMap<>();
        AbstractLogFileHandler handler = new AbstractLogFileHandler<LineEntry>(){
            public int count=0;

            @Override
            public void postHandleMultiFiles() {
                System.out.println("count:"+count);
                for(Map.Entry<String, BufferedWriter> entry : outFileMap.entrySet()){
                    try {
                        entry.getValue().close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.exit(1);
                    }
                }
            }

            @Override
            public LineEntry loadLineEntry(String line) {
                return new LineEntry().load(line);
            }

            @Override
            public void handleLine(int fIdx, LineEntry entry) {
                if(entry.valid){
                    BufferedWriter bw = outFileMap.get(entry.ipHash);
                    if(bw ==null){
                        try {
                            bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Config.DIR_LOG+"/pvuv/"+nDay.dayStrings[fIdx]+"/"+entry.ipHash+".log"),"UTF-8"));
                            outFileMap.put(entry.ipHash, bw);
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.exit(1);
                        }
                    }
                    try {
                        String[] keys=entry.toKeys();
                        for(String key : keys) {
                            ChartPvUvData chartPvUvData = pvUvDataMap.get(key);
                            if (chartPvUvData == null) {
                                chartPvUvData = new ChartPvUvData("LINK_NODE_DAY_1", nDay.dayStrings[fIdx], key, 1);
                                pvUvDataMap.put(key, chartPvUvData);
                            } else {
                                chartPvUvData.setPv(chartPvUvData.getPv() + 1);
                            }
                        }
                        bw.write(entry.linkNode);
                        bw.write(Config.SEP);
                        bw.write(entry.country);
                        bw.write(Config.SEP);
                        bw.write(entry.from);
                        bw.write(Config.SEP);
                        bw.write(entry.ip);
                        bw.write("\n");

                        count++;
                        if(count%100000==0){
                            System.out.println("count:"+count);
                            bw.flush();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.exit(1);
                    }
                }
            }
        };
        handler.handleFile(nDay.logFiles);
    }
}
