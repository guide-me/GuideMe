#!/bin/sh
BASEDIR=`dirname $0`
exec java -d64 -XstartOnFirstThread \
	-cp target/guideme-0.0.1-SNAPSHOT.jar:target/lib/log4j-api-2.0-beta8.jar:target/lib/log4j-core-2.0-beta8.jar:target/lib/org.eclipse.swt.cocoa.macosx.x86_64-4.3.jar \
    org.guideme.guideme.App
