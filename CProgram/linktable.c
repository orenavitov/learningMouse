#include<stdio.h>
#include<stdlib.h>
#include<math.h>
#include<string.h>
#include".\mihInclude\mihdatastruct.h"
#define TEST 0
#define TEST_1 0
#define TEST_2 0
#define TEST_3 0

#define TEST_4 1
#define TEST_5 1
#define TEST_6 1
#define TEST_7 1
#define TEST_8 1
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

LNode* findLastNode(LNode* head) {
    while(head -> next != NULL){
        head = head -> next;
    }
    return head;
}

//单链表逆序
LNode* reserveLNode(LNode* head) {
    if(head -> next != NULL) {
        reserveLNode(head -> next) -> next = head;
    } 
    head -> next = NULL;
    return head;
}

//实现n的阶乘
int test1_1(int n) {
    if (n > 1) {
        n = n * test1_1(n - 1);
        return n;
    } else {
        return 1;
    }
}

//计算1-x+x^2/2!-x^3/3!+...+x^n/n!，x是单浮点数，n是整数
float test1(float x, int n) {
    float result = 1.0;
    while (n >= 1) {
        int ns = test1_1(n);
        if (n % 2 == 0) {
            result = result + pow(x, n) / ns;
        } else {
            result = result + (- pow(x, n)) / ns;
        }
        n -= 1;
        
    }
    return result;
}

//file1的内容全部复制到file2中，且在file2的每一行都要加上行号，最后返回file1中的字符个数
#define BUFF_SIZE 100
void copyFile(char* srcFile, char* dstFile) {
    FILE* src = fopen(srcFile, "rw");
    FILE* dst = fopen(dstFile, "rw");
    while(/* condition */){
        /* code */
    }
    
    fread()
}

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

int main(int argc, char const *argv[])
{
    #if TEST_1
    int* numbers = (int* )malloc(5 * sizeof(int));
    int** numbersAddress = (int** )malloc(5 * sizeof(int* ));
    for (int i = 0; i < 5; i++) {
        numbers[i] = i;
        numbersAddress[i] = &numbers[i];
    }
    LNode* head = getLLinkTable(numbersAddress, WITH_NO_HEAD, NO_LOOP);
    LNode* lastNode = findLastNode(head);
    reserveLNode(head);
    printLinktable(lastNode, WITH_NO_HEAD);
    #endif 

    #if TEST_2
    float x;
    int n;
    float result;
    printf("please input x and n\n");
    printf("x:");
    scanf("%f", &x);

    printf("n:");
    scanf("%d", &n);

    result = test1(x, n);
    #endif

    #if TEST_3
    char* str = (char* )malloc(30* sizeof(char));
    printf("input string:");
    scanf("%s", str);
    str = reserveString(str);
    printf("the reserve string is:%s", str);
    #endif

    #if TEST_4

    #endif
    getchar();
    return 0;
}