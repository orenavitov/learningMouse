#include<stdio.h>
#include<stdlib.h>
#include "mihdatastruct.h"

#pragma comment(dll, "E:/mihCode/learningMouse/C_mihLib/linktable.dll")

//单链表逆序
LNode* reserveLNode(LNode* head) {
    LNode* currentNode = head -> next;
    LNode* temp;
    head -> next = NULL;
    while(currentNode != NULL) {
        //首先保存当前节点的下一个节点
        temp = currentNode -> next;
        currentNode -> next = head -> next;
        //head -> next用来保存上一个节点
        head -> next = currentNode;
        //将currentNode设置为下一个节点实现循环
        currentNode = temp;
    }
    
}

int main(int argc, char const *argv[])
{
    int numbers[5] = {1, 2, 3, 4, 5};
    int** number_addresses = (int** )malloc(5 * sizeof(int* ));
    for (int i = 0; i < 5; i++) {
        number_addresses[i] = &numbers[i];
    }
    LNode* head = getLLinkTable(number_addresses, WITH_HEAD, NO_LOOP);
    LNode* reserved_head = reserveLNode(head);
    LNode* currentNode = reserved_head -> next;
    int i = 1;
    while(currentNode != NULL){
        int element = (int* )(currentNode -> element);
        printf("the element of %d node: %d", i, element);
        i ++;
    }
    printf("hello world!");
    getchar();
    return 0;
}
