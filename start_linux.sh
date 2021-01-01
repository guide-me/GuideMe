#!/bin/sh

# GDK_BACKEND=x11 required for GUI to start on wayland
export GDK_BACKEND=x11

BASEDIR=`dirname $0`
exec java  \
  -cp target/guideme-0.0.1-SNAPSHOT.jar:$(echo target/lib/*.jar | tr ' ' ':') \
    org.guideme.guideme.App
