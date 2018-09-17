#include<stdio.h>

void exitfunction(void);

void main(int argc, char const *argv[])
{
    /* code */
    printf("hello ");
    atexit(exitfunction);
}

void exitfunction() {
    printf("world.\n");
}