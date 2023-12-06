#!/bin/bash

javac ./common/*.java
javac ./server/*.java
javac ./client/*.java

echo "Server starting now..."
java server.BattleShipDriver 6767
