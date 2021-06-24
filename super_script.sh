#!/bin/bash

mvn clean install -DskipTests=true
cd backendforfrontend
chmod 777 npm
chmod 777 ng
./npm install
./ng build