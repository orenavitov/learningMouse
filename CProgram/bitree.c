#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include".\mihInclude\mihstring.h"
#include".\mihInclude\mihdatastruct.h"
#define TEST 1


bitNode* getBitNode(void* element, bitNode* lchild, bitNode* rchild) {
    bitNode* node = (bitNode* )malloc(sizeof(bitNode));
    node -> element = element;
    node -> lchild = lchild;
    node -> rchild = rchild;
    return node;
}

bitNode* getBitree(char* order1, int orderType1, char* order2, int orderType2) {
    bitNode* lchild = NULL;
    bitNode* rchild = NULL;
    //先序 + 中序 TEST
    if (orderType1 == PRIOR_ORDER) {
        if (orderType2 == MIDDLE_ORDER) {
            char* root = (char* )malloc(sizeof(char));
            root = order1;
            int index = indexofchar(order2, *root);
            char* order2_1 = stringslip(order2, 0, index - 1);
            char* order2_2 = stringslip(order2, index + 1, strlen(order2) - 1);
            if (order2_1 != NULL) {
                int length1 = strlen(order2_1);
                if (length1 == 1) {
                    lchild = getBitNode(&order2_1[0], NULL, NULL);
                } else {
                    char* order1_1 = stringslip(order1, 1, length1);
                    lchild = getBitree(order1_1, PRIOR_ORDER, order2_1, MIDDLE_ORDER);
                }
            }
            if (order2_2 != NULL) {
                int length2 = strlen(order2_2);
                if (length2 == 1) {
                    rchild = getBitNode(&order2_2[0], NULL, NULL);
                } else {
                    char* order1_2 = stringslip(order1, index + 1, strlen(order1) - 1);
                    rchild = getBitree(order1_2, PRIOR_ORDER, order2_2, MIDDLE_ORDER);
                }
            }
            return getBitNode(root, lchild, rchild);
        }
    }
    //中序 + 先序； 中序 + 后序； 中序 + 层次
    if (orderType1 == MIDDLE_ORDER) {
        //
        if (orderType2 == PRIOR_ORDER) {
            char* root = (char* )malloc(sizeof(char));
            root = order2;
            int index = indexofchar(order1, *root);
            char* order1_1 = stringslip(order1, 0, index - 1);
            char* order1_2 = stringslip(order1, index + 1, strlen(order1) - 1);
            if (order1_1 != NULL) {
                int length1 = strlen(order1_1);
                if (length1 == 1) {
                    lchild = getBitNode(order1_1, NULL, NULL);
                } else {
                    char* order2_1 = stringslip(order2, 1, length1);
                    lchild = getBitree(order1_1, MIDDLE_ORDER, order2_1, PRIOR_ORDER);
                }
            }
            if (order1_2 != NULL) {
                int length2 = strlen(order1_2);
                if (length2 == 1) {
                    rchild = getBitNode(order1_2, NULL, NULL);
                } else {
                    char* order2_2 = stringslip(order2, index + 1, strlen(order2) - 1);
                    rchild = getBitree(order1_2, MIDDLE_ORDER, order2_2, PRIOR_ORDER);
                }
            }
            return getBitNode(root, lchild, rchild);
        }
        //
        if (orderType2 == LAST_ORDER) {
            char* root = (char* )malloc(sizeof(char));
            int length = strlen(order2);
            root = &order2[length - 1];
            int index = indexofchar(order1, *root);
            char* order1_1 = stringslip(order1, 0, index - 1);
            char* order1_2 = stringslip(order1, index + 1, strlen(order1) - 1);
            if (order1_1 != NULL) {
                int length1 = strlen(order1_1);
                if (length1 == 1) {
                    lchild = getBitNode(order1_1, NULL, NULL);
                } else {
                    char* order2_1 = stringslip(order2, 0, length1 - 1);
                    lchild = getBitree(order1_1, MIDDLE_ORDER, order2_1, LAST_ORDER);
                }
            }
            if (order1_2 != NULL) {
                int length2 = strlen(order1_2);
                if (length2 == 1) {
                    rchild = getBitNode(order1_2, NULL, NULL);
                } else {
                    char* order2_2 = stringslip(order2, strlen(order2) - length2 - 1, strlen(order2) - 2);
                    rchild = getBitree(order1_2, MIDDLE_ORDER, order2_2, LAST_ORDER);
                }
            }
            return getBitNode(root, lchild, rchild);

        }
        if (orderType2 == LEVEL_ORDER) {

        }
    }
    //后续 + 中序
    if (orderType1 == LAST_ORDER) {
        if (orderType2 == MIDDLE_ORDER) {
            char* root = (char* )malloc(sizeof(char));
            int length = strlen(order1);
            root = &order1[length - 1];
            int index = indexofchar(order2, *root);
            char* order2_1 = stringslip(order2, 0, index - 1);
            char* order2_2 = stringslip(order2, index + 1, strlen(order2) - 1);
            if (order2_1 != NULL) {
                int length1 = strlen(order2_1);
                if (length1 == 1) {
                    lchild = getBitNode(order2_1, NULL, NULL);
                } else {
                    char* order1_1 = stringslip(order1, 0, length1 - 1);
                    lchild = getBitree(order1_1, LAST_ORDER, order2_1, MIDDLE_ORDER);
                }
            }
            if (order2_2 != NULL) {
                int length2 = strlen(order2_2);
                if (length2 == 1) {
                    rchild = getBitNode(order2_2, NULL, NULL);
                } else {
                    char* order1_2 = stringslip(order1, strlen(order1) - length2 - 1, strlen(order1) - 2);
                    rchild = getBitree(order1_2, LAST_ORDER, order2_2, MIDDLE_ORDER);
                }
            }
            return getBitNode(root, lchild, rchild);
        }
    }
    if (orderType1 == LEVEL_ORDER) {
        if (orderType2 == MIDDLE_ORDER) {

        }
    }

    return NULL;
}

void travelBitree(bitNode* root, int orderType, queue* que) {
    if (root != NULL)
    {
        switch (orderType)
        {
        case PRIOR_ORDER:
            printf("%c", *((char* )(root -> element)));
            travelBitree(root -> lchild, PRIOR_ORDER, NULL);            
            travelBitree(root -> rchild, PRIOR_ORDER, NULL);
            break;
        case MIDDLE_ORDER:
            travelBitree(root -> lchild, MIDDLE_ORDER, NULL);
            printf("%c", *((char* )(root -> element)));
            travelBitree(root -> rchild, MIDDLE_ORDER, NULL);
            break;
        case LAST_ORDER:
            travelBitree(root -> lchild, LAST_ORDER, NULL);
            travelBitree(root -> rchild, LAST_ORDER, NULL);
            printf("%c", *((char* )(root -> element)));
            break;
        case LEVEL_ORDER:
            printf("%c", *((char* )(root -> element)));
            if (root -> lchild != NULL) {
                inQueue(que, root -> lchild);
            }
            if (root -> rchild != NULL) {
                inQueue(que, root -> rchild);
            }
            
            if (queueIsEmpty(que) == NOT_EMPTY) {
                LNode* front = outQueue(que);
                root = (bitNode* )(front -> element);
                travelBitree(root, LEVEL_ORDER, que);
            }
            
            break;
        }
    }
}

void _travelBitree(bitNode *root, int orderType, linkStack *stack)
{
    switch (orderType)
    {
    case PRIOR_ORDER:
        while (root != NULL || linkStackIsEmpty(stack) == NOT_EMPTY)
        {
            if (root != NULL)
            {
                printf("%c", *(char *)(root->element));
                if (root->rchild != NULL)
                {
                    linkStackpush(stack, root->rchild);
                }
                root = root->lchild;
            }
            else
            {
                bitNode *currentrchildNode = (bitNode *)(linkStackpop(stack)->element);
                printf("%c", *(char *)(currentrchildNode->element));
                root = currentrchildNode->lchild;
                if (currentrchildNode->rchild != NULL)
                {
                    linkStackpush(stack, currentrchildNode->rchild);
                }
            }
        }
        break;
    case MIDDLE_ORDER:
        while (root != NULL || linkStackIsEmpty(stack) == NOT_EMPTY)
        {
            if (root != NULL)
            {
                linkStackpush(stack, root);
                root = root->lchild;
            }
            else
            {
                bitNode *currentrootNode = (bitNode *)(linkStackpop(stack)->element);
                printf("%c", *(char *)(currentrootNode->element));
                root = currentrootNode->rchild;
            }
        }
        break;
    /*
    传入参数的栈用于控制遍历顺序， printStack栈用于控制打印顺序
    */
    case LAST_ORDER:
    {
        
        break;
    }
    }
}

#if TEST
int main(int argc, char const *argv[])
{
    linkStack* stack = initLinkStack();
    queue* que = initQueue();

    //先序：abcdefghi; 后续：cbehgifda; 中序：bcaedghfi; 层次：abdcefgih
    bitNode* root = getBitree("abcdefghi", PRIOR_ORDER, "bcaedghfi", MIDDLE_ORDER);
    puts("middle order:");
    _travelBitree(root, LAST_ORDER, stack);
    putchar('\n');
    // puts("middle order:");
    // travelBitree(root, MIDDLE_ORDER, que);
    // putchar('\n');
    // puts("last order:");
    // travelBitree(root, LAST_ORDER, que);
    // putchar('\n');

    return 0;
}

#endif