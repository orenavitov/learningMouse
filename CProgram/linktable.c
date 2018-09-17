#include<stdio.h>
#include<stdlib.h>
#include".\mihInclude\mihdatastruct.h"
#define TEST 0


/*
 初始化单向链表
 */
LNode* getLLinkTable(void** elements, int flag_1, int flag_2) {
    int index = 0;
    int size = _msize(elements) / sizeof(void* );
    LNode* head = (LNode* )malloc(2 * sizeof(void* ));
    if (size <= 0) {
        if (flag_1 == WITH_HEAD) {
            head -> next = NULL;
            return head;
        }
        return NULL;
    }
    LNode* firstNode = (LNode* )malloc(2 * sizeof(void* ));
    firstNode -> element = elements[index];
    LNode* priorNode = firstNode;
    LNode* currentNode = firstNode;
    while (index < size - 1) {
        index ++;
        currentNode = (LNode* )malloc(2 * sizeof(void* ));
        currentNode -> element = elements[index];
        priorNode -> next = currentNode;
        priorNode = currentNode;
    }
    if (flag_2 == LOOP) {
        currentNode -> next = firstNode;
    } else {
        currentNode -> next = NULL;
    }
    if (flag_1 == WITH_HEAD) {
        head -> next = firstNode;
        return head;
    }
    
    return firstNode;
}

/*
 初始化双向链表；
 */
DNode* initDlinkList(int size, void* elements[size], int flag_1, int flag_2) {
    DNode* head = (DNode* )malloc(3 * sizeof(*elements));
    head -> prior = NULL;
    if (size <= 0) {
        if (flag_1 == WITH_HEAD) {
            head -> next = NULL;
            return head;
        }
        return NULL;
    }
    int index = 0;
    DNode* firstNode = (DNode* )malloc(3 * sizeof(*elements));
    firstNode -> element = elements[index];
    DNode* priorNode = firstNode;
    DNode* currentNode = firstNode;
    while(index < size - 1) {
        index ++;
        currentNode = (DNode* )malloc(3 * sizeof(*elements));
        currentNode -> element = elements[index];
        currentNode -> prior = priorNode;
        priorNode -> next = currentNode;
        priorNode = currentNode;
    }
    if (flag_2 == LOOP) {
        currentNode -> next = firstNode;
    } else {
        currentNode -> next = NULL;
    }
    if (flag_1 == WITH_HEAD) {
        firstNode -> prior = head;
        head -> next = firstNode;
        return head;
    } 
    return firstNode;
}

int lengthOfLLinkTable(LNode* lNode, int flag_1) {
    int length = 1;
    LNode* currentNode = flag_1 == WITH_HEAD ? lNode -> next : lNode;
    if (currentNode == NULL) {
        return 0;
    }
    while (currentNode -> next != NULL) {
        length ++;
        currentNode = currentNode -> next;
    }
    return length;
}

int lengthOfDLinkList(DNode* dNode) {
    int length = 1;
    DNode* currentNode = dNode;
    while (currentNode -> next != NULL) {
        length ++;
        currentNode = currentNode -> next;
    }
    return length;
}

LNode* indexOfLLinkTable(LNode* lNode, int index, int flag_1) {
    int length = lengthOfLLinkTable(lNode, flag_1);
    if (index < 0 || index >= length) {
        return NULL;
    } 
    int currentIndex = 0;
    LNode* targetNode = flag_1 == WITH_HEAD ? lNode -> next : lNode;
    while (currentIndex != index) {
        targetNode = targetNode -> next;
        currentIndex ++;
    }
    return targetNode;
}

DNode* indexOfDLinkList(DNode* dNode, int index) {
    int length = lengthOfDLinkList(dNode);
    if (index < 0 || index >= length) {
        return NULL;
    }
    int currentIndex = 0;
    DNode* targetNode = dNode;
    while (currentIndex != index) {
        targetNode = targetNode -> next;
        currentIndex ++;
    }
    return targetNode;
}

/*
 传入的链表为null可以进行插入， 返回的链首节点是插入元素形成的节点
 */
LNode* llinkTableInsert(LNode* lNode, void* element, int index, int flag_1) {
    int length = lengthOfLLinkTable(lNode, flag_1);
    if (index < 0 || index > length) {
        return NULL;
    }
    LNode* insertNode = (LNode* )malloc(sizeof(LNode));
    insertNode -> element = element;
    if (index == 0) {
        if (flag_1 == WITH_HEAD) {
            LNode* firstNode = lNode -> next;
            lNode -> next = insertNode;
            insertNode -> next = firstNode;
        }
        insertNode -> next = lNode;
        return insertNode;
    }
    LNode* priorNode = indexOfLLinkTable(lNode, index - 1, flag_1);
    if (index == length) {
        priorNode -> next = insertNode;
        insertNode -> next = NULL;
    } else {
        LNode* nextNode = indexOfLLinkTable(lNode, index, flag_1); 
        priorNode -> next = insertNode;
        insertNode -> next = nextNode;
    }
    return lNode;

}

LNode* findLLinkTableTearNode(LNode* lNode, int flag_1) {
    if (lNode == NULL) {
        return NULL;
    }
    LNode* firstNode = flag_1 == WITH_HEAD ? lNode -> next : lNode;
    if (firstNode != NULL)
    {
        LNode *lastNode = firstNode->next;
        while (lastNode != NULL)
        {
            firstNode = lastNode;
            lastNode = firstNode->next;
        }
    }
    return firstNode;
}

LNode* llinkTableAppend(LNode* lNode, void* element, int flag_1) {
    LNode* lastNode = (LNode* )malloc(sizeof(LNode));
    lastNode -> element = element;
    lastNode -> next = NULL;
    LNode* tearNode = findLLinkTableTearNode(lNode, flag_1);
    if (tearNode == NULL) {
        if (flag_1 == WITH_HEAD) {
            lNode -> next = lastNode;
        } else {
            lNode = lastNode;
        }
    } else {
        tearNode -> next = lastNode;
    }
    return lNode;
}

DNode* dlinkListInsert(DNode* dNode, void* element, int index) {
    int length = lengthOfDLinkList(dNode);
    if (index < 0 || index > length) {
        return NULL;
    }
    DNode* insertNode = (DNode* )malloc(3 * sizeof(element));
    insertNode -> element = element;
    if (index == 0) {
        insertNode -> prior = NULL;
        insertNode -> next = dNode;
        dNode -> prior = insertNode;
        return insertNode;
    }
    if (index == length) {
        DNode* lastNode = indexOfDLinkList(dNode, index - 1);
        lastNode -> next = insertNode;
        insertNode -> prior = lastNode;
        insertNode -> next = NULL;
        return dNode;
    }
    DNode* priorNode = indexOfDLinkList(dNode, index - 1);
    DNode* nextNode = indexOfDLinkList(dNode, index);
    priorNode -> next = insertNode;
    insertNode -> prior = priorNode;
    insertNode -> next = nextNode;
    nextNode -> prior = insertNode;
    return dNode;
}


LNode* deleteLLinkTableNode(LNode* lNode, int index, int flag_1) {
    int length = lengthOfLLinkTable(lNode, flag_1);
    if (index < 0 || index >= length) {
        return lNode;
    }
    if (index == 0) {
        LNode* originFirstNode = flag_1 == WITH_HEAD ? lNode -> next : lNode;
        LNode* currentFirstNode = originFirstNode -> next;
        free(originFirstNode);
        originFirstNode = NULL;
        if (flag_1 == WITH_HEAD) {
            lNode -> next = currentFirstNode;
            return lNode;
        } else {
            return currentFirstNode;
        }
        
    } 
    LNode* priorNode = indexOfLLinkTable(lNode, index - 1, flag_1);
    LNode* currentNode = indexOfLLinkTable(lNode, index, flag_1);
    if (index == length - 1) {
        priorNode -> next = NULL;
        free(currentNode);
        currentNode = NULL;
        return lNode;
    } 
    LNode* nextNode = indexOfLLinkTable(lNode, index + 1, flag_1);
    priorNode -> next = nextNode;
    free(currentNode);
    currentNode = NULL;
    return lNode;
}

DNode* deleteDNode(DNode* dNode, int index) {
    int length = lengthOfDLinkList(dNode);
    if (index < 0 || index >= length) {
        return NULL;
    }
    if (index == 0) {
        DNode* firstNode = dNode -> next;
        firstNode -> prior = NULL;
        free(dNode);
        dNode = NULL;
        return firstNode;
    }
    DNode* priorNode = indexOfDLinkList(dNode, index - 1);
    DNode* currentNode = indexOfDLinkList(dNode, index);
    if (index == length - 1) {
        priorNode -> next = NULL;
        free(currentNode);
        currentNode = NULL;
        return dNode;
    }
    priorNode -> next = currentNode -> next;
    currentNode -> next -> prior = priorNode;
    free(currentNode);
    currentNode = NULL;
    return dNode;
}

void printLinktable(LNode* node, int flag_1) {
    LNode* firstNode = flag_1 == WITH_HEAD ? node -> next : node;
    int index = 1;
    while (firstNode != NULL) {
        printf("%-3d ", *(int* )(firstNode -> element));
        
        if (index % 10 == 0) {
            putchar('\n');
        }
        index ++;
        firstNode = firstNode -> next;
    }
    putchar('\n');
}

#if TEST
int main(int argc, char const *argv[])
{
    int* numbers = (int* )malloc(100 * sizeof(int));
    int** numbersAddress = (int** )malloc(100 * sizeof(int* ));
    for (int i = 0; i < 100; i++) {
        numbers[i] = i;
        numbersAddress[i] = &numbers[i];
    }
    // LNode* withHeadLinkTable = (LNode* )malloc(sizeof(LNode));
    // withHeadLinkTable -> element = NULL;
    // withHeadLinkTable -> next = NULL;
    LNode* noheadLinkTable = NULL;

    // puts("start print:");
    
    // llinkTableAppend(withHeadLinkTable, numbersAddress[3], WITH_HEAD);
    // llinkTableAppend(withHeadLinkTable, numbersAddress[4], WITH_HEAD);
    // llinkTableAppend(withHeadLinkTable, numbersAddress[23], WITH_HEAD);
    // llinkTableAppend(withHeadLinkTable, numbersAddress[34], WITH_HEAD);

    // printLinktable(withHeadLinkTable, WITH_HEAD);

    // deleteLLinkTableNode(withHeadLinkTable, 1, WITH_HEAD);
    // printLinktable(withHeadLinkTable, WITH_HEAD);


    puts("start print:\n");

    noheadLinkTable = llinkTableAppend(noheadLinkTable, numbersAddress[3], WITH_NO_HEAD);
    noheadLinkTable = llinkTableAppend(noheadLinkTable, numbersAddress[4], WITH_NO_HEAD);
    noheadLinkTable = llinkTableAppend(noheadLinkTable, numbersAddress[23], WITH_NO_HEAD);
    noheadLinkTable = llinkTableAppend(noheadLinkTable, numbersAddress[34], WITH_NO_HEAD);
    printLinktable(noheadLinkTable, WITH_NO_HEAD);

    noheadLinkTable = deleteLLinkTableNode(noheadLinkTable, 0, WITH_NO_HEAD);
    printLinktable(noheadLinkTable, WITH_NO_HEAD);

    getchar();
    return 0;
}
#endif 

