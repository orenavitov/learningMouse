#include<stdio.h>
#include<stdlib.h>
#include "mihdatastruct.h"

//字符串逆序
char* reserveString(char* str) {
    int stringSize = strlen(str);
    char temp;
    for (int i = stringSize - 1, j = 0; i > j; i--, j ++) {
        temp = str[i];
        str[i] = str[j];
        str[j] = temp;
    }
    return str;
}

/*
 *将file1.txt中的内容复制到file2.txt中，并在file2.txt中每一行的开始写入行号，最后返回file1.txt中的字符数（不包括空格）
 */
#define MAX_NUMBER 20
char** readLines(char* srcfile) {
    FILE* file = fopen(srcfile, "r");
    //一次最多读20行
    char** lines = (char** )calloc(MAX_NUMBER, sizeof(char* ));
    char* line = (char* )calloc(MAX_NUMBER, sizeof(char));
    char inputc;
    //当前允许的每一行最多的字节数
    int maxNumber = MAX_NUMBER;
    //用于扩大每一行允许的最多的字节数
    int k = 1;
    //i表示在填充每一行中每个字符的计数
    int i = 0;
    //j记录行号
    int j = 0;
    //用于扩展最多读取的行书
    int j_extend = 1;
    //当前允许读取的最多行数
    int maxlines = MAX_NUMBER;
    while((inputc = getc(file)) != EOF){
        if (j >= maxlines) {
            maxlines = MAX_NUMBER * (j_extend + 1);
            char** lines_ = (char** )calloc(maxlines, sizeof(char* ));
            memcpy(lines_, lines, j_extend * MAX_NUMBER * sizeof(char* ));
            free(lines);
            lines = lines_;
            j_extend ++;
        }
        if (inputc != '\n' && i < maxNumber) {
            line[i] = inputc;
            i ++;
        } else {
            if (inputc == '\n') {
                //恢复k, 恢复maxNumber
                k = 1;
                maxNumber = MAX_NUMBER;
                lines[j] = line;
                j ++;
                line = (char* ) calloc(maxNumber, sizeof(char));
                i = 0;
            }
            if (i >= maxNumber) {
                //每次增加MAX_NUMBER个字节
                k ++;
                char* linecopy = (char* )calloc(k * maxNumber, sizeof(char));
                memcpy(linecopy, line, maxNumber);
                maxNumber = k * maxNumber;
                free(line);
                line = linecopy;
                line[i] = inputc;
                i ++;
            }
        }
    }
    return lines;
}

/*
 *快速排序
 */
int* quickSort(int* numbers, int low_, int high_) {
    //第一个元素作为关键字
    int key = numbers[0];
    int low = low_;
    int high = high_;
    while(low < high) {
        while(numbers[high] > key && low < high) {
            high --;
        }
        if (numbers[high] < key) {
            numbers[low] = numbers[high];
            low ++;
        }
        while(numbers[low] < key && low < high) {
            low ++;
        }
        if (numbers[low] > key) {
            numbers[high] = numbers[low];
            high --;
        }
    }
    numbers[low] = key;
    quickSort(numbers, low_, low - 1);
    quickSort(numbers + low + 1,  low + 1, high_);
    return numbers;
}