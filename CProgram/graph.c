/*
graph 表示图
int** weightsMatrix 图中顶点的权重矩阵
int* eachNodeWeights 图中每个顶点到其他顶点边的权重
int weight 每条弧的权重
int* distances 从起点到其他各顶点的最小权重
vNode* vertexNode 顶点
arcNode* arcnode 弧节点
 */

#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<stdarg.h>
#include".\mihInclude\mihdatastruct.h"

#define TEST 0
#define VISITED 1
#define NOT_VISITED 0
#define MAX_INPUT_SIZE 128
#define V_NUM 6
//存储顶点的顶点表下标从0开始计数， 顶点的编号可能不从0开始计数， START_NUMBER表示顶点编号从多少开始计数
#define START_NUMBER 1
#define ACCESSIBLE 1
#define NOT_ACCESSIBLE -1

int* vertexAccessible(algGraph* graph, vNode* vertexNode, int* vertexAccessibleTable);
int dLoop(vNode** vertices, int* distances, int startNodeNum, int originalDistance);
void visitVNode(algGraph* graph, int i);
void BFS(algGraph* graph, queue* que, int* visited, int i);
void DFS(algGraph* graph, int* visited, int vertexlab);

int main(int argc, char const *argv[])
{
    //邻接矩阵存储
    int** weightsMatrix = (int** )malloc(V_NUM * sizeof(int* ));
    //生成邻接矩阵数据
    for (int i = 0; i < V_NUM; i ++) {
        char* input = (char* )malloc(sizeof(char) * MAX_INPUT_SIZE);
        scanf("%s", input);
        int* eachNodeWeights = (int* )malloc(V_NUM * sizeof(int));
        //将权重由字符串转化成整形
        char* weightsOfchar = strtok(input, ",");
        int index = 0;
        while(weightsOfchar != NULL) {
            int weight = atoi(weightsOfchar);
            eachNodeWeights[index] = weight;
            index ++;
            weightsOfchar = strtok(NULL, ",");
        }
        weightsMatrix[i] = eachNodeWeights;
    }
    algGraph* graph = getGraph(weightsMatrix);

    //连通性
    accessible(graph);

    // //D算法
    // puts("start numbe is 1");
    // int* distances = dijkstra(graph, 1);
    
    // //BFS
    // puts("bfs ---------------------------------------------------------------\n");
    // travelGraphBFS(graph);
    // //DFS
    // puts("dfs ---------------------------------------------------------------\n");
    // travelGraphDFS(graph);

    system("pause");
    return 0;
}

algGraph* getGraph(int** weightsMatrix) {
    algGraph* graph = (algGraph* )malloc(sizeof(algGraph));
    graph -> amountOfvertexes = V_NUM;
    //初始化顶点表， 有多少个顶点就有多少顶点表
    vNode** vertexes = (vNode** )malloc(sizeof(vNode* ) * V_NUM);
    for (int i = 0; i < V_NUM; i ++) {
        int* eachNodeWeights = weightsMatrix[i];
        //初始化每一个顶点表
        vNode* vertexNode = (vNode* )malloc(sizeof(vNode));
        vertexNode -> arcNodeTable = NULL;
        vertexNode -> lab = START_NUMBER + i;
        for (int j = 0; j < V_NUM; j ++) {
            int weight = eachNodeWeights[j];
            //筛选除自身外的可达顶点
            if (weight != NOT_ACCESSIBLE && weight != 0) {
                arcNode* arcnode = (arcNode* )malloc(sizeof(arcNode));
                arcnode -> edgeVertexNumber = START_NUMBER + j;
                arcnode -> weight = weight;
                vertexNode -> arcNodeTable = llinkTableAppend(vertexNode -> arcNodeTable, arcnode, WITH_NO_HEAD);
            }
        }

        vertexes[i] = vertexNode;
    }
    graph -> vertexes = vertexes;
    //默认无环
    graph -> isloop = NO_LOOP;
    //默认不可达
    graph -> accessible = NOT_ACCESSIBLE;
    return graph;
}

/*
accessibleTable 为n * n矩阵存放每一点到其他各点的可达性， 自身可达
vertexAccessibleTable 为一个n元数组表示每一点到其他各点的可达性
 */
int accessible(algGraph* graph) {
    int** accessibleTable = (int** )malloc(sizeof(int* ) * V_NUM);
    for (int i = 0; i < V_NUM; i ++) {
        int* vertexAccessibleTable = (int* )malloc(sizeof(int) * V_NUM);
        for (int j = 0; j < V_NUM; j ++) {
            vertexAccessibleTable[j] = NOT_ACCESSIBLE;
        }
        accessibleTable[i] = vertexAccessibleTable;
    }
    vNode** vertexes = graph -> vertexes;
    //从第一个节点开始
    for (int i = 0; i < V_NUM; i ++) {
        accessibleTable[i][i] = ACCESSIBLE;
        vNode* vertexNode = vertexes[i];
        accessibleTable[i] = vertexAccessible(graph, vertexNode, accessibleTable[i]);
    }
    puts("accessible--------------------------------------------------\n");
    for (int i = 0; i < V_NUM; i ++) {
        for (int j = 0; j < V_NUM; j ++) {
            if (accessibleTable[i][j] == NOT_ACCESSIBLE) {
                graph -> accessible = NOT_ACCESSIBLE;
            }
        }
        puts("\n");
    }
    for (int i = 0; i < V_NUM; i ++) {
        free(accessibleTable[i]);
        accessibleTable[i] = NULL;
    }
    free(accessibleTable);
    accessibleTable = NULL;
    return 0;
}

int* vertexAccessible(algGraph* graph, vNode* vertexNode, int* vertexAccessibleTable) {
    vNode** vertexes = graph -> vertexes; 
    queue* que = initQueue();
    que = inQueue(que, vertexNode);
    while(queueIsEmpty(que) != EMPTY) {
        LNode* node = outQueue(que);
        vNode* vertexNode = (vNode* )node -> element;
        LNode* arcnodeTable = (LNode* )vertexNode -> arcNodeTable;
        LNode* currentArcNodeTable = arcnodeTable;
        while(currentArcNodeTable != NULL) {
            arcNode* arcnode = currentArcNodeTable -> element;
            int vertexlab = arcnode -> edgeVertexNumber;
            if (vertexAccessibleTable[vertexlab - START_NUMBER] != ACCESSIBLE) {
                vertexAccessibleTable[vertexlab - START_NUMBER] = ACCESSIBLE;
                //只有没有访问过的才入队列， 防止出现环的情况
                que = inQueue(que, vertexes[vertexlab - START_NUMBER]);
            } else {
                graph -> isloop = LOOP;
            }
        
            currentArcNodeTable = currentArcNodeTable -> next;
        }
    }
    free(que);
    que = NULL;
    return vertexAccessibleTable;
}

int* dijkstra(algGraph* graph, int startNodeNum) {
    int* distances = (int* )malloc(V_NUM * sizeof(int));
    for (int i = 0; i < V_NUM; i ++) {
        distances[i] = NOT_ACCESSIBLE;
    }
    vNode** vertexes = graph -> vertexes;
    if (vertexes != NULL) {
        //originalDistance 表示之前获得的最短路径长度
        int originalDistance = 0;
        for (int time = 0; time < V_NUM; time ++) {
            startNodeNum = dLoop(vertexes, distances, startNodeNum, originalDistance);
            originalDistance = distances[startNodeNum - START_NUMBER];
            printf("the %d time select startNodeNum is %d, the distance is %d\n", time, startNodeNum, originalDistance);
            for (int i = 0; i < V_NUM; i ++) {
                printf("%d ", distances[i]);
            }
            puts("------------------------------------------------------------\n");

        }
    }
    return distances;
}

int dLoop(vNode** vertices, int* distances, int startNodeNum, int originalDistance) {

    vNode* startNode = vertices[startNodeNum - START_NUMBER];
    //将开始顶点到其他顶点的初始长度设成-1， 表示不可达
    int shortestDistance = NOT_ACCESSIBLE;
    int shortDistanceNode = startNodeNum;
        if (startNode != NULL) {
            LNode* arcNodeTable = startNode -> arcNodeTable;
            LNode* currentArcNode = arcNodeTable;
            while(currentArcNode != NULL) {
                arcNode* arcnode = (arcNode* )currentArcNode -> element;
                int neighborNum = arcnode -> edgeVertexNumber;
                int weight = arcnode -> weight;
                //如果之前是不可达， 直接更新
                if (distances[neighborNum - START_NUMBER] == NOT_ACCESSIBLE) {
                    distances[neighborNum - START_NUMBER] = weight + originalDistance;
                }
                //
                else {
                    if (originalDistance + weight < distances[neighborNum - START_NUMBER]) {
                        distances[neighborNum - START_NUMBER] = originalDistance + weight;
                    }
                }

                currentArcNode = currentArcNode -> next;
                if (shortestDistance == -1) {
                    shortestDistance = weight;
                    shortDistanceNode = neighborNum;
                } else {
                    if (weight < shortestDistance) {
                        shortDistanceNode = neighborNum;
                    }
                }
            }
        }
    return shortDistanceNode;
}

void visitVNode(algGraph* graph, int vertexlab) {
    vNode** nodes = graph -> vertexes;
    vNode* vertexNode = nodes[vertexlab - START_NUMBER];
    printf("%d  ", vertexNode -> lab);
}

//广度优先遍历, 需要借助队列, 该算法稍加更改就可以变成求不带权最短路径的算法
void travelGraphBFS(algGraph* graph) {
    if (graph == NULL) {
        return;
    }
    int vertexNum = graph -> amountOfvertexes;
    //visited 表示该顶点是否已经遍历过
    int* visited = (int* )malloc(sizeof(int) * V_NUM);
    for (int i = 0; i < V_NUM; i ++) {
        visited[i] = NOT_VISITED;
    }
    queue* que = initQueue();
    for (int i = 0; i < V_NUM; i ++) {
        if (visited[i] == NOT_VISITED) {
            BFS(graph, que, visited, i + START_NUMBER);
        }
    }
    free(visited);
    visited = NULL;
    free(que);
    que = NULL;
    puts("\n");
}

//vertexlab 要访问的顶点标号
void BFS(algGraph* graph, queue* que, int* visited, int vertexlab) {
    vNode** vertexes = graph -> vertexes;
    visitVNode(graph, vertexlab);
    que = inQueue(que, vertexes[vertexlab - START_NUMBER]);
    while(queueIsEmpty(que) == NOT_EMPTY) {
        LNode* node = (LNode* )outQueue(que);
        vNode* vertexNode = node -> element;
        LNode* arcnodeTable = vertexNode -> arcNodeTable;
        LNode* currentArcNodeTable = arcnodeTable;
        while(currentArcNodeTable != NULL) {
            arcNode* arcnode = (arcNode* )currentArcNodeTable -> element;
            int edgeVertexNumber = arcnode -> edgeVertexNumber;
            if (visited[edgeVertexNumber - START_NUMBER] == NOT_VISITED) {
                visitVNode(graph, edgeVertexNumber);
                visited[edgeVertexNumber - START_NUMBER] = VISITED;
                //因为只有没访问过时才入队列， 所以不用考虑存在环的情况
               que = inQueue(que, vertexes[vertexlab - START_NUMBER]);               
            }
            currentArcNodeTable = currentArcNodeTable -> next;
        }

    }
}

//深度优先遍历
void travelGraphDFS(algGraph* graph) {
    if (graph == NULL) {
        return;
    }
    int vertexNum = graph -> amountOfvertexes;
    //visited 表示该顶点是否已经遍历过
    int* visited = (int* )malloc(sizeof(int) * V_NUM);
    for (int i = 0; i < V_NUM; i ++) {
        visited[i] = NOT_VISITED;
    }
    for (int i = 0; i < V_NUM; i ++) {
        if (visited[i] == NOT_VISITED) {
            DFS(graph, visited, i + START_NUMBER);
        }
    }
    puts("\n");
}

//深度优先遍历不需要借助队列
void DFS(algGraph* graph, int* visited, int vertexlab) {
    vNode** vertexes = graph -> vertexes;
    visitVNode(graph, vertexlab);
    visited[vertexlab - START_NUMBER] = VISITED;
    vNode* vertexNode = vertexes[vertexlab - START_NUMBER];
    LNode* arcnodeTable = vertexNode -> arcNodeTable;
    LNode* currentArcNodeTable = arcnodeTable;
    while(currentArcNodeTable != NULL) {
        arcNode* arcnode = (arcNode* )currentArcNodeTable -> element;
        int edgeVertexNumber = arcnode -> edgeVertexNumber;
        if (visited[edgeVertexNumber - START_NUMBER] == NOT_VISITED) {
            DFS(graph, visited, edgeVertexNumber);
        }

        currentArcNodeTable = currentArcNodeTable -> next;
    }
}

//最小生成树, Prim算法
algGraph* prim(algGraph* graph, int startNodeNum) {
    algGraph* primGraph = (algGraph* )malloc(sizeof(algGraph));
    vNode** originalVertexes = (vNode** )malloc(sizeof(vNode* ) * V_NUM);
    memcpy(originalVertexes, graph -> vertexes, _msize(graph -> vertexes));
    vNode** targetVertexes = (vNode** )malloc(sizeof(vNode* ) * V_NUM);
    

    for (int i = 1; i < V_NUM; i ++) {
        vNode* startVertexNode = originalVertexes[startNodeNum - START_NUMBER]; 
        LNode* arcNodeTable = startVertexNode -> arcNodeTable;
        LNode* currentArcNodeTable = arcNodeTable;

        int targetVNodeLab = startNodeNum;
        int weight = ACCESSIBLE;
        while(currentArcNodeTable != NULL) {
            
            currentArcNodeTable = currentArcNodeTable -> next;
        }
    }
    return primGraph;
}

//最小生成树, Kruskal算法
algGraph* kruskal(algGraph* graph) {

}