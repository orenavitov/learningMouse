#include<stdio.h>
#include<stdlib.h>

extern void printfTestNumber();
static int testNumber  = 777;
int main(int argc, char const *argv[])
{
    printf("helloWorld_ testNumber is %d\n", testNumber);
    printfTestNumber();
    getchar();
    return 0;
}
