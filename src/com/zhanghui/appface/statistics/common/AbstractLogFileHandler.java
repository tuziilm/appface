package com.zhanghui.appface.statistics.common;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;

public abstract class AbstractLogFileHandler<T extends ValidLineEntry> implements LogFileHandler<T>{
    public void handleFile(String[] logFiles) throws IOException {
        File[] files = new File[logFiles.length];
        for(int i=0;i< logFiles.length;i++) {
            String filename = logFiles[i];
            files[i]= new File(filename);
        }
        handleFile(files);
    }

    public void handleFileBak(File[] logFiles) throws IOException {
        preHandleMultiFiles();
        for(int i=0;i< logFiles.length;i++){
            File file = logFiles[i];
            if(!file.exists()){
                continue;
            }
            LineIterator li = null;
            try{
                preHandleSingleFile();
                li= FileUtils.lineIterator(file, "UTF-8");
                while(li.hasNext()){
                    T entry = loadLineEntry(li.next());
                    if(entry.valid ){
                        handleLine(i, entry);
                    }
                }
            }finally{
                try {
                    postHandleSingleFile();
                }finally {
                    LineIterator.closeQuietly(li);
                }
            }
        }
        postHandleMultiFiles();
    }
    public void handleFile(File[] logFiles) throws IOException {
        preHandleMultiFiles();
        for(int i=0;i< logFiles.length;i++){
            File file = logFiles[i];
            if(!file.exists()){
                continue;
            }
            try{
                preHandleSingleFile();
                Files.readLines(file, Charsets.UTF_8, new CbLineProcessor<Object>(i));
            }finally{
                postHandleSingleFile();
            }
        }
        postHandleMultiFiles();
    }

    public void preHandleMultiFiles(){
    }

    public void preHandleSingleFile(){
    }

    public void postHandleMultiFiles(){
    }

    public void postHandleSingleFile(){
    }

    public abstract T loadLineEntry(String line);

    private class CbLineProcessor<K extends Object> implements LineProcessor<K> {
        private int fIdx;
        public CbLineProcessor(int fIdx) {
            this.fIdx = fIdx;
        }

        @Override
        public boolean processLine(String line) throws IOException {
            T entry = AbstractLogFileHandler.this.loadLineEntry(line);
            if(entry.valid ){
                handleLine(fIdx, entry);
            }
            entry=null;
            return true;
        }

        @Override
        public K getResult() {
            return null;
        }
    }
}
