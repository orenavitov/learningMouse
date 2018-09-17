#define ERROR -1
#define SUCESS 0
#define WITH_HEAD 1
#define WITH_NO_HEAD 0
#define WITH_TEAR 1
#define WITH_NO_TEAR 0
#define LOOP 1
#define NO_LOOP 0
#define ISEMPTY int
#define EMPTY 0
#define NOT_EMPTY 1 
#define ISFULL int
#define FULL 1
#define NOT_FULL 0
#define PRIOR_ORDER 1
#define MIDDLE_ORDER 2
#define LAST_ORDER 3
#define LEVEL_ORDER 4
#define EXIST 1
#define NOT_EXIST 0

typedef struct list {
    void* element;
    int size;
} mihlist;

typedef struct LlinkTableNode {

    void* element;
    struct LlinkTableNode* next; 
} LNode;

typedef struct DlinkTableNode {
    void* element;
    struct DlinkTableNode* prior;
    struct DlinkTableNode* next;
} DNode;

struct head {
    int size;
    void* next;
};

typedef struct SortStack {
    int top;
    void** elements; 
} sortStack;

typedef struct LinkStack {
    int top;
    LNode* link;
} linkStack;

typedef struct Queue {
    LNode* front;
    LNode* rear;
} queue;

typedef struct BiTNode {
    void* element;
    struct BiTNode* lchild;
    struct BiTNode* rchild;
} bitNode;

//顶点表示
typedef struct VNode {
    //顶点编号(唯一标识从0开始计数， 必须初始化)
    int lab;
    //顶点数据
    void* element;
    //相邻节点
    LNode* arcNodeTable;
} vNode;

//边表节点
typedef struct ArcNode {
    //指向的节点序号(目标顶点)
    int edgeVertexNumber;
    //权重
    int weight;
} arcNode;

//图表示
typedef struct {
    //顶点
    vNode** vertexes;
    //顶点数
    int amountOfvertexes;
    //是否有环
    int isloop;
    //是否连通
    int accessible;
} algGraph;

mihlist* InitList(int size, void* elements);

int length(mihlist* list);

mihlist* insert(mihlist* list, int index);

void* index(mihlist* list, int index);

mihlist* empty(mihlist* list);

LNode* getLLinkTable(void** elements, int flag_1, int flag_2);

//返回单向列表的长度, flag_1表示有无头节点
int lengthOfLLinkTable(LNode* lNode, int flag_1);

//单向列表按位置插入
LNode* llinkTableInsert(LNode* lNode, void* element, int index, int flag_1);

LNode* llinkTableAppend(LNode* lNode, void* element, int flag_1);

//返回指定位置的单向列表节点
LNode* indexOfLLinkTable(LNode* lNode, int index, int flag_1);

//删除指定位置的单向列表节点
LNode* deleteLLinkTableNode(LNode* lNode, int index, int flag_1);

LNode* findLLinkTableTearNode(LNode* lNode, int flag_1);

DNode* initDlinkList(int size, void* elements[size], int flag_1, int flag_2);

int lengthOfDLinkList(DNode* dNode);

DNode* dlinkListInsert(DNode* dNode, void* element, int index);

DNode* indexOfDLinkList(DNode* dNode, int index);

DNode* deleteDNode(DNode* dNode, int index);

//初始化一个顺序栈
sortStack* initSortStack(int maxSize);

//顺序栈进栈
sortStack* sortStackpush(void* element, sortStack* stack);

//顺序栈出栈
sortStack* sortStackpull(sortStack* stack);

//初始化一个链栈
linkStack* initLinkStack();

//链栈进栈
linkStack* linkStackpush(linkStack* stack, void* element);

//链栈出栈
LNode* linkStackpop(linkStack* stack);

//获取栈顶节点， 不弹出
LNode* getTopOfLinkStack(linkStack* stack);

//判断链栈是否为空
ISEMPTY linkStackIsEmpty(linkStack* stack);

//初始化一个队列， flag_1代表是否是循环队列
queue* initQueue();

//入队列
queue* inQueue(queue* que, void* element);

//出队列
LNode* outQueue(queue*);

//队列是否为空
ISEMPTY queueIsEmpty(const queue* const que);

//队列是否为满
ISFULL queueIsFull(const queue* const que);

//判断队列中是否有相同的元素（指针相同）
int queueExistSameElement(queue* que, void* element);

//
bitNode* getBitNode(void* element, bitNode* lchild, bitNode* rchild);

//生成一棵二叉树， 需要俩个遍历序列
bitNode* getBitree(char* order1, int orderType1, char* order2, int orderType2);

//通过遍历的方式遍历一棵二叉树
void travelBitree(bitNode* root, int orderType, queue* que);

//通过非递归的方式遍历一棵二叉树
void _travelBitree(bitNode* root, int orderType, linkStack* stack);

algGraph* getGraph(int** heighs);

//广度优先搜索
void travelGraphBFS(algGraph* graph);

//深度优先搜索
void travelGraphDFS(algGraph* graph);

int* dijkstra(algGraph* graph, int startNodeNum);

//判断一个图是否联通, 同时会检测图的联通性
int accessible(algGraph* graph);

//最小生成树, Prim算法
algGraph* prim(algGraph* graph);

//最小生成树, Kruskal算法
algGraph* kruskal(algGraph* graph);