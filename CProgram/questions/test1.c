#include<stdio.h>
#include<stdlib.h>
#include"..\mihInclude\mihdatastruct.h"

// extern LNode* initLLinkList(int size, void* elements[size], NOT_HEAD, NOT_LOOP);
int numbers[] = {
        1, 2, 3
    };

LNode* getLList(void) {

    int size = sizeof(numbers) / sizeof(int);
    void** testData = (void** )malloc(sizeof(void* ) * size);
    for(int index = 0; index < size; index ++) {
        testData[index] = &numbers[index];
    }
    LNode* head = initLLinkList(testData, 1, 0);
    return head;
}

void forward_printf(LNode* head) {
    LNode* firstNode = head -> next;
    LNode* currentNode = firstNode;
    int index = 0;
    while(currentNode != NULL) {
        int* data_pt = currentNode -> element;
        printf("index[%d] is %d\n", index, *data_pt);
        index ++;
        firstNode = firstNode -> next;
        currentNode = firstNode;
    }

}

void test_print(LNode* currentNode) {
    if (currentNode -> next != NULL) {
        test_print(currentNode -> next);
    } 
    int* data_pt = currentNode -> element;
    if (data_pt != NULL) {
        printf("currentNode address is %p\n", data_pt);
        printf("currentNode value is :%d\n", *data_pt);
    } 
}

void test1(void) {

    LNode* head = getLList();
    test_print(head);
}

LNode* deleteMinNode(LNode* head) {
    LNode* firstNode = head -> next;
    if (firstNode == NULL) {
        return head;
    }
    int minNumber = *((int* )firstNode -> element);
    int index = 1;
    int minIndex = 1;
    while (firstNode != NULL)
    {
        int *currentNumber = (int *)firstNode->element;
        if (*currentNumber < minNumber)
        {
            minIndex = index;
        }
        firstNode = firstNode -> next;
        index ++;
    }
    if (head -> next != NULL) {
        deleteLNode(head, minIndex);
    }
    return head;
}

void test2(void) {
    
    LNode* head = getLList();
    test_print(head);
    printf("after delete\n");
    head = deleteMinNode(head);
    test_print(head);
}

void test5(void) {
    LNode* head = getLList();
    if(head -> next != NULL) {
        LNode* firstNode = head -> next;
        LNode* currentNode = firstNode;
        LNode* priorNode = NULL;
        while(firstNode -> next != NULL) {
            firstNode = firstNode -> next;
            currentNode -> next = priorNode;
            priorNode = currentNode;
            currentNode = firstNode;

        }
        currentNode -> next = priorNode;
        head -> next = currentNode;
    }
    forward_printf(head);
}

void test6(void) {
    LNode* head = getLList();
    LNode* newHead = head;
    LNode* currentNode = head -> next;
    int* minNumber = currentNode -> element;
    while(currentNode != NULL) {
        int* data_pt = currentNode -> element;
        if (*data_pt <= *minNumber) {
            
        }
        currentNode = currentNode -> next;
    }
}

LNode* deleteLNodeBetweenNumbers(LNode* node, int min, int max) {
    LNode* nextNode = NULL;
    if(node -> next != NULL) {
       nextNode = deleteLNodeBetweenNumbers(node -> next, min, max);
    }
    int* number = node -> element;
    if (number != NULL)
    {

        if (*number <= max && *number >= min)
        {
            return nextNode;
        } else {
            node -> next = nextNode;
        }
    }
    return node;
}

void test7(void) {
    LNode* head = getLList();
    head = deleteLNodeBetweenNumbers(head, 10, 20);
    forward_printf(head);
}

void test9(void) {
    LNode* head = getLList();    
    LNode* startNode = head -> next;
    int index = 1;
    while (startNode != NULL) {
        
        int* minNumber = startNode -> element;
        LNode* minNumberNode = startNode;
        LNode* minNumberPrior = head;
        LNode* minNumberNodeNext = startNode -> next;
        LNode* secondNode = startNode -> next;
        LNode* priorNode = startNode;
        while (secondNode != NULL)
        {
            int* currentNodeNumber = secondNode ->element;
            if (currentNodeNumber != NULL)
            {
                if (*currentNodeNumber <= *minNumber) {
                    minNumber = currentNodeNumber;
                    minNumberNode = secondNode;
                    minNumberNodeNext = secondNode -> next;
                    minNumberPrior = priorNode;
                    printf("in the %d time find the minNumber is %d\n", index, *minNumber);
                }
            }
            priorNode = secondNode;
            secondNode = secondNode -> next;           
        }
        printf("in the %d time find the minNumber is %d\n", index, *minNumber);
        minNumberPrior -> next = minNumberNodeNext;
        if (minNumberNode == startNode) {
            startNode = startNode -> next;
        }
        minNumberNode = NULL;
        index ++;
    }
    free(head);
    head = NULL;
}

void test10(void) {
    LNode* head = getLList();
    LNode* head1 = (LNode* )malloc(sizeof(LNode));
    LNode* head2 = (LNode* )malloc(sizeof(LNode));
    LNode* firstNode = head -> next;
    LNode* currentNode = firstNode;
    LNode* currentNode1 = head1;
    LNode* currentNode2 = head2;
    int index = 1;
    while (currentNode != NULL) {
        if (index % 2 == 1) {
            currentNode1 -> next = currentNode;
            currentNode1 = currentNode1 -> next;
            if (currentNode1 -> next != NULL) {
                if (currentNode1 -> next -> next == NULL) {
                    currentNode1 -> next = NULL;
                }
            }
        }

        if (index % 2 == 0) {
            currentNode2 -> next = currentNode;
            currentNode2 = currentNode2 -> next;
            if (currentNode2 -> next != NULL) {
                if (currentNode2 -> next -> next == NULL) {
                    currentNode2 -> next = NULL;
                }
            }
        }
        currentNode = currentNode -> next;
        index ++;
    }
    forward_printf(head1);
    forward_printf(head2);
}

int main(int argc, char const *argv[])
{
    // queue* que = initQueue();
    linkStack* stack_1 = initLinkStack();
    linkStack* stack_2 = initLinkStack();
    linkStack* stack_3 = initLinkStack();
    bitNode* root = getBitree("cbehgifda", LAST_ORDER, "bcaedghfi", MIDDLE_ORDER);
    puts("先序：");
    _travelBiTree(root, PRIOR_ORDER, stack_1);
    putchar('\n');
    puts("中序：");
    _travelBiTree(root, MIDDLE_ORDER, stack_2);
    putchar('\n');
    puts("后续：");
    _travelBiTree(root, LAST_ORDER, stack_3);
    putchar('\n');
    // travelBitree(root, LEVEL_ORDER, que);
    getchar();
}
