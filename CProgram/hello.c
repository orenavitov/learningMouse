#include<stdio.h>
#include<stdlib.h>
#include<string.h>

typedef struct graph {
    int v;
    int* neighors;
} graph;

int main(int args, char* argv) {
    int v = 0;
    scanf("%d", &v);
    graph* g = (graph* )malloc(sizeof(graph) * v);
    for (int i = 0; i < v; i++) {
        g -> neighors = (int* )malloc(sizeof(int) * (v - 1));
    }
    while(v >= 0) {
        int v1;
        int v2;
        scanf("%d %d", &v1, &v2);
        

        v --;
    }

    
}
