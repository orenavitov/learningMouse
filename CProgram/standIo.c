#include<stdio.h>
#include"mihInclude/test.h"

int main(int args, char* argv) {

    int ch;

    /*
    只有一行的开始处^z才视为EOF的结束标志
    */
    while((ch = getchar()) != EOF) {
        putchar(ch);
    }
    getchar();
    return 0;
}