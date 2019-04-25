#include<stdio.h>
#include<stdlib.h>
#include<string.h>

#define MAX_SIZE 500000

int strMatch(char* S, char* T) {
    int result = 0;
    int index = 0;
    int t = 0;
    int lengthS = strlen(S);
    int lengthT = strlen(T);
    int failedIndex = 0;
    for (;index < lengthS;) {
        if (failedIndex == 1 && t != 0) {
            index = index - 1;
        }
        char schar = S[index];
        char tchar = T[t]; 
        if (schar == '?' || schar == tchar) {
            t ++;
            if (t == lengthT) {
                t = 0;
                result ++;
            }
            failedIndex = 0;
        } else {
            failedIndex = 1;
            t = 0;
        }
        index ++;
    }

    return result;
}


int getCount(int nLength, int skip);

int main(int argc, char const *argv[])
{
    /* code */
    // char* S = (char* )malloc(MAX_SIZE * sizeof(char));

    // char* T = (char* )malloc(MAX_SIZE * sizeof(char));

    // puts("input S:");
    // scanf("%s", S);
    // puts("input T:");
    // scanf("%s", T);

    // int result = strMatch(S, T);

    int count;
    puts("input the count.");
    scanf("%d", &count);
    int* tests = (int* )calloc(count, sizeof(int));
    int* results = (int* )calloc(count, sizeof(int));
    puts("input the numbers:");
    for (int i = 0; i < count; i++) {
        scanf("%d", &tests[i]);
    }
    for (int i = 0; i < count; i ++) {
        results[i] = getCount(tests[i], tests[i] + 1);
    }
    for (int i = 0; i < count; i ++) {
        printf("the result is %d\n", results[i]);
    }
    
    getchar();

    return 0;
}


int getCount(int nLength, int skip) {
    int result = 0;
    int length = 4 * nLength;
    int* road = (int* )calloc(length, sizeof(int));
    int run = 1;
    int index = 0;
    while (run)
    {
        
        if (index + skip < length) {
            index = index + skip;

        } else {
            index = index + skip - length;

        }
        if (road[index] == 1) {            
            run = 0;
        } else {
            road[index] = 1;
        }
        result ++;
    }
    
    free(road);
    return result;
}