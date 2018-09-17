
#include<stdlib.h>
#include<stdio.h>
#include "./mihInclude/mihdatastruct.h"
#include "./mihInclude/mihstring.h"
#include<string.h>
#define TEST_NUM 2

#define L_NODE_TEST
#define D_NODE_TEST

mihlist* InitList(int size, void* elements) {
    printf("the address of elements is %p\n", elements);
    mihlist *listpt = malloc(size * sizeof(elements) + sizeof(int));
    listpt -> element = elements;
    listpt -> size = size;
    return listpt;
}

void* index(mihlist* list, int index) {
    return list -> element;
}

int length(mihlist* list) {
    return list -> size;
}

mihlist* delete(mihlist* list, int index) {
    return NULL;
}

mihlist* insert(mihlist* list, int index) {
    return NULL;
}

