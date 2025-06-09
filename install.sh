#!/usr/bin/bash

mkdir -p ~/.local/bin
cp -r ./picoc ./target/*-with-dependencies.jar ./std ~/.local/bin/
