#!/usr/bin/bash

for item in /media/${USER}/MICROBIT*; do
    uflash -m "$1" $item
done