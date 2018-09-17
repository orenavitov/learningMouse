#include<sys/stat.h>
#include<stdio.h>
#include<stdlib.h>
int main(int argc, char const *argv[])
{
    // /*
    // buf存放文件信息
    //  */
    // struct stat* buf = (struct stat*) malloc(sizeof(struct stat)); 
    // if (argc != 2) {
    //     printf("we need the path of the dir\n");
    // }
    //  char* const path = argv[1];
    // if (stat(path, buf) == -1) {
    //     printf("error in get the stat\n");
    // }

    // /*
    // 无link型文件， 及套接字文件
    //  */
    // if(S_ISREG(buf -> st_mode)) {
    //     printf("普通文件.\n");
    // }
    // if (S_ISDIR(buf -> st_mode)) {
    //     DIR* dir = opendir(path);
    //     printf("目录文件.\n");
        
    // }
    // if (S_ISCHR(buf -> st_mode)) {
    //     printf("字符特殊文件.\n");
    // }
    // if (S_ISBLK(buf -> st_mode)) {
    //     printf("块特殊文件.\n");
    // }
    // if (S_ISFIFO(buf -> st_mode)) {
    //     printf("管道或FIFO文件.\n");
    // }

    // /*
    // 测试文件权限：用户权限， 组权限， 其他权限
    //  */
    // if (S_IRUSR & buf -> st_mode) {
    //     putchar('r');
    // } else {
    //     putchar('-');
    // }
    // if (S_IWUSR & buf -> st_mode) {
    //     putchar('w');
    // } else {
    //     putchar('-');
    // }
    // if (S_IXUSR & buf -> st_mode) {
    //     putchar('x');
    // } else {
    //     putchar('-');
    // }
    // if (S_IRGRP & buf -> st_mode) {
    //     putchar('r');
    // } else {
    //     putchar('-');
    // } 
    // if (S_IWGRP & buf -> st_mode) {
    //     putchar('w');
    // } else {
    //     putchar('-');
    // } 
    // if (S_IXGRP & buf -> st_mode) {
    //     putchar('x');
    // } else {
    //     putchar('-');
    // }
    // if (S_IROTH & buf -> st_mode) {
    //     putchar('r');
    // } else {
    //     putchar('-');
    // } 
    // if (S_IWOTH & buf -> st_mode) {
    //     putchar('w');
    // } else {
    //     putchar('-');
    // } 
    // if (S_IXOTH & buf -> st_mode) {
    //     putchar('x');
    // } else {
    //     putchar('-');
    // }
    // putchar('\n');
    printf("hello world");
    return 0;
}
