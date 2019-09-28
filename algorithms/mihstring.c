#include "mihstring.h"
#include<string.h>
#include<stdlib.h>
#include<stdio.h>

#define TEST 1
#define MAX_STRING_SIZE 100

//包括第end个字符
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

/*
 next[]下标从0开始计数
 */
int* getnext(char* targetString) {
    if (targetString == NULL) {
        return NULL;
    }
    int length = strlen(targetString);
    int* next = (int* )malloc(length * sizeof(int));
    next[0] = -1;
    if (length == 1) {
        return next;
    }
    //i 表示匹配到源字符串位置
    int i = 0;
    //j 表示模式字符串匹配到第j位出错(因为原字符串下标从0开始计数， 所以上一位用-1表示)
    int j = -1;

    while (i <= length) {
        if (j == -1 || targetString[i] == targetString[j]) {
            i ++;
            j ++;
            next[i] = j;
        } else {
            j = next[j];
        }
    }
    return next;
}

int KMP(char* string, char* targetString) {
    int length = strlen(string);
    int targetStringlength = strlen(targetString);
    int i = 0;
    int j = 0;
    int* next = getnext(targetString);
    if (next != NULL)
    {
        while (i <= length)
        {
            if (j == -1) {
                j = 0;
                i = i + 1;
            }
            else
            {
                if (string[i] == targetString[j])
                {
                    i++;
                    j++;
                } else {
                    j = next[j];
                }
            }
            if (j == targetStringlength) {
                return 1;
            }
        }
    }

    return 0;
}

#if TEST
int main(int argc, char const *argv[])
{
    /* code */
    char* string = (char* )malloc(sizeof(char) * MAX_STRING_SIZE);
    char* targetString = (char* )malloc(sizeof(char) * MAX_STRING_SIZE);
    puts("input the string:");
    scanf("%s", string);
    puts("input the match string:");
    scanf("%s", targetString);
    int sucess = KMP(string, targetString);
    puts("--------------------------------------------------------------------------------\n");
    printf("%d\n", sucess);
    free(string);
    string = NULL;
    free(targetString);
    targetString = NULL;
    system("pause");
    return 0;
}
#endif