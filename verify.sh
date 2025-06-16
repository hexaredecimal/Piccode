#!/usr/bin/env bash

JAR=target/*-jar-with-dependencies.jar
JAVA=java

log() {
  printf "[INFO] %s\n" "$1"
}

checkJar() {
  if [ -d "picoc/lib/app" ]; then
    log "Found jar in picoc/lib/app"
    JAR=picoc/lib/app/*-jar-with-dependencies.jar
    log "Found java in picoc/bin"
    JAVA=jpicoc/bin/java
  else
    log "Using default tar and java"
  fi
}

compileExample() {
  $JAVA -jar $JAR run $1 >/dev/null
}

compileExamples() {
  checkJar
  log "Compiling sources"

  ls ./examples/*.pics | while read line; do
    log "Compiling: $line"
    compileExample $line
  done

  log "Done"
}

main() {
  compileExamples
}

main
