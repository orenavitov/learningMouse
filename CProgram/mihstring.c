#include "./mihInclude/mihstring.h"
#include<string.h>
#include<stdlib.h>

char* stringslip(char* origin, int start, int end) {
    int stringlength = strlen(origin);
    if (start > end) {
        return NULL;
    }
    if (end >= stringlength) {
        end = stringlength - 1;
    }
    if (start < 0) {
        start = 0;
    }
    int targetStringLength = end - start + 2;
    char* target = (char* )malloc(targetStringLength * sizeof(char));
    memcpy(target, &origin[start], (targetStringLength - 1)* sizeof(char));
    target[targetStringLength - 1] = '\0';
    return target;
}

int indexofchar(char* origin, char target) {
    int index = 0;
    int length = strlen(origin);
    int found = 0;
    while(index < length) {
        if (origin[index] == target) {
            found = 1;
            break;
        }
        index ++;
    }
    if (found == 1) {
        return index;
    }
    return -1;
}