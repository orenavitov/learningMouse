#include<stdio.h>
#include<stdlib.h>
#include".\mihInclude\mihdatastruct.h"
#define TEST 0
/*
 初始化队列
 未处理超过队列最大长度的情况， 未考虑循环标志
 */
queue* initQueue() {
    queue* que = (queue* )malloc(sizeof(queue));
    que -> front = NULL;
    que -> rear = NULL;
    return que;
}

queue* inQueue(queue* que, void* element) {
    LNode* rear = (LNode* )malloc(sizeof(LNode));
    rear -> element = element;
    rear -> next = NULL;
    if (queueIsEmpty(que) == EMPTY) {
        que -> front = rear;
        que -> rear = rear;
    } else {
        que -> rear -> next = rear;
        que -> rear = rear;
    }
    return que;
}

LNode* outQueue(queue* que) {
    if (queueIsEmpty(que) == NOT_EMPTY) {
        LNode* firstNode = que -> front;
        if (que -> front == que -> rear) {
            que -> front = NULL;
            que -> rear = NULL;
        } else {
            que -> front = que -> front -> next;
        }
        return firstNode;
    }
    return NULL;
}

int queueIsEmpty(const queue* const que) {
    LNode* front = que -> front;
    LNode* rear = que -> rear;
    if (rear == NULL && front == NULL ) {
        return EMPTY;
    }
    return NOT_EMPTY;
}

ISFULL queueIsFull(const queue* const que) {

    return NOT_FULL;
}

int queueExistSameElement(queue* que, void* element) {
    if (que == NULL || queueIsEmpty(que) == EMPTY) {
        return NOT_EXIST;
    }
    LNode* node = que -> front;
    LNode* currentNode = node;
    while(currentNode != NULL) {
        if (currentNode -> element == element) {
            return EXIST;
        }
        currentNode = currentNode -> next;
    }
    return NOT_EXIST;
}

void printQueue(queue* que) {
    if (queueIsEmpty(que) == NOT_EMPTY) {
        LNode* currentNode = que -> front;
        int index = 1;
        while(currentNode != NULL) {
            printf("%-3d ", *(int* )(currentNode -> element));
            currentNode = currentNode -> next;
            
            if (index % 10 == 0) {
                putchar('\n');
            }
            index ++;
        }
    }
}

#if TEST
int main(int argc, char const *argv[])
{
    int *numbers = (int *)malloc(100 * sizeof(int));
    int **numbersAddress = (int **)malloc(100 * sizeof(int *));
    for (int i = 0; i < 100; i++)
    {
        numbers[i] = i;
        numbersAddress[i] = &numbers[i];
    }
    queue* que = initQueue();
    inQueue(que, numbersAddress[10]);
    inQueue(que, numbersAddress[12]);
    inQueue(que, numbersAddress[14]);
    inQueue(que, numbersAddress[23]);
    inQueue(que, numbersAddress[45]);

    outQueue(que);
    outQueue(que);

    printQueue(que);
    return 0;
}
#endif
