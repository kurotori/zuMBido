#!/usr/bin/bash

for item in /media/${USER}/MICROBIT*; do
    uflash "$1" $item
done