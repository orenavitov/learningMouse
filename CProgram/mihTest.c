#include "./mihInclude/mihstring.h"
#include<stdio.h>
#include<stdarg.h>
#define PRINT(X) printf("@"#X" %p\n", &X)

void test(int a, int b, int c) {
    int var1 = 1;
    int var2 = 2;
    PRINT(var1);
    PRINT(var2);
    PRINT(a);
    PRINT(b);
    PRINT(c);
}
int main(int argc, char const *argv[])
{
    test(1, 2, 3);
    return 0;
}
