#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include"mihdatastruct.h"
#define TEST 0


sortStack* initSortStack(int maxSize) {
    sortStack* stack = (sortStack* )malloc(sizeof(sortStack));
    stack -> top = -1;
    void** elements = (void** )malloc(maxSize * sizeof(void* ));
    stack -> elements = elements;
    return stack;
}

sortStack* sortStackpush(void* element, sortStack* stack) {
    int top = stack -> top;
    if (top == -1) {
        return stack;
    }
    void** oldElements = stack -> elements;
    size_t oldSize = _msize(stack -> elements);
    if (top >= oldSize) {
        void** newElements = (void** )realloc(oldElements , oldSize * 2);
        stack -> elements = newElements;
    }

    stack -> top = top + 1;
    stack -> elements[stack -> top] = element;
    return stack;
}



linkStack* initLinkStack() {
    linkStack* stack = (linkStack* )malloc(sizeof(linkStack));
    stack -> top = -1;
    stack -> link = NULL;
    return stack;
}

linkStack* linkStackpush(linkStack* stack, void* element) {
    int top = stack -> top;
    //在链首部进行插入
    stack -> link = llinkTableInsert(stack -> link, element, 0, WITH_NO_HEAD);
    stack -> top = top + 1;
    return stack;
}

//链栈出栈
LNode* linkStackpop(linkStack* stack) {
    if (linkStackIsEmpty(stack) == NOT_EMPTY)
    {
        LNode* originNodeCpy = (LNode* )malloc(sizeof(LNode));
        int top = stack->top;
        LNode *link = stack->link;
        memcpy(originNodeCpy, link, sizeof(LNode));
        LNode* newLink = deleteLLinkTableNode(link, 0, WITH_NO_HEAD);
        stack -> top = top - 1;
        stack -> link = newLink;
        return originNodeCpy;
    } else {
        return NULL;
    }
}

LNode* getTopOfLinkStack(linkStack* stack) {
    if (linkStackIsEmpty(stack) == NOT_EMPTY) {
        LNode* link = stack -> link;
        return indexOfLLinkTable(link, 0 , WITH_NO_HEAD);
    }
    return NULL;
}

ISEMPTY linkStackIsEmpty(linkStack* stack) {
    if (stack -> top == -1) {
        return EMPTY;
    }
    return NOT_EMPTY;
} 

void printLinkStack(linkStack* stack) {
    if (linkStackIsEmpty(stack) == NOT_EMPTY) {
        LNode* link = stack -> link;
        LNode* currentNode = link;
        int index = 1;
        while (currentNode != NULL) {
            printf("%-3d ", *(int* )(currentNode -> element));
            currentNode = currentNode -> next;
            if (index % 10 == 0) {
                putchar('\n');
            }
            index ++;
        }
    }
}



