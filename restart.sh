#!/bin/bash

APP_HOME=/home/appface/apps/appface
BASE_DIR=/data/appface/bootstrap
TOMCAT_BASE=/home/appface/servers/apache-tomcat-7.0.54

# check the pre command have run ok?
function check_fail(){
        if [ $? -ne 0 ]; then
                echo "ERROR: $1"
                exit 1
        fi
}

# update svn and build sources
function build(){
        cd $APP_HOME
        echo "check out and build app files."
        git pull
        check_fail "svn up: failed to update hacker svn sources"
        ant clean
        check_fail "ant clean: failed to clean ant build"
        ant
        check_fail "ant build: failed to ant build"
}

function backup_app_files(){
        echo "back app files."
        stamp=`date +%Y-%m-%d-%H-%M-%S`
        cp $TOMCAT_BASE/webapps/ROOT.war $BASE_DIR/backup/ROOT-${stamp}.war
        check_fail "cp: failed to backup webapps files"
        ln -f $BASE_DIR/backup/ROOT-${stamp}.war $BASE_DIR/backup/ROOT-latest.war
        check_fail "ln: failed to link latest webapps files"
}

function restore_app_files(){
        echo "restore app files."
        rm -rf $TOMCAT_BASE/webapps/ROOT*
        check_fail "rm: failed to remove webapps files"
        cp $BASE_DIR/backup/ROOT-latest.war $TOMCAT_BASE/webapps/ROOT.war
        check_fail "cp: failed to restore webapps files"
}

function replace_app_files(){
        echo "replace app files."
        rm -rf $TOMCAT_BASE/webapps/ROOT*
        check_fail "rm: failed to remove webapps files"
        cp $APP_HOME/dist/ROOT.war $TOMCAT_BASE/webapps/
        check_fail "cp : failed to copy app file to webapps dir"
}

# stop tomcat
function stop_tomcat(){
        echo "stop tomcat..."
        ps aux | grep "appName=appface" | grep -v grep | awk '{print $2}' | xargs kill -9
#       check_fail "stop tomcat: failed to stop tomcat"
        # ignore stop tomcat error when tomcat has not started.
        echo "always assume tomcat has stoped."
}

function start_tomcat(){
        echo "start tomcat..."
        export CATALINA_OPTS='-DappName=appface -server -XX:+DisableExplicitGC -XX:+PrintFlagsFinal -XX:+UseConcMarkSweepGC -Xmx1200m -Xms1200m -XX:NewSize=300m -XX:PermSize=96m -XX:MaxPermSize=96m -Xloggc:/data/appface/work/logs/tomcat/gc.log -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xdebug -Xrunjdwp:transport=dt_socket,address=18787,server=y,suspend=n'
        export CATALINA_BASE=$TOMCAT_BASE
        export CATALINA_HOME=$TOMCAT_BASE
        sh $TOMCAT_BASE/bin/startup.sh
        check_fail "start tomcat: failed to start tomcat"
}

function success(){
        echo "restart successfully"
}

function main(){
        # svn workflow
        [ "$1" == "git" ] && build && stop_tomcat && backup_app_files && replace_app_files && start_tomcat && success && exit 0
        # rollbakc workflow
        [ "$1" == "rb" ] && stop_tomcat && restore_app_files && start_tomcat && success && exit 0
        # normal workflow
        stop_tomcat && start_tomcat && success && exit 0
}

main $1

