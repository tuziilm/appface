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

function sync(){
        cd $APP_HOME
        echo "check out and build app files."
        git pull
        check_fail "git pull: failed to update hacker svn sources"
	rm -rf $TOMCAT_BASE/webapps/ROOT/pages
        check_fail "rm: failed to delete pages"
	cp -rf WebContent/pages $TOMCAT_BASE/webapps/ROOT/
	
}


function success(){
        echo "restart successfully"
}

function main(){
        sync && success && exit 0
}

main $1

