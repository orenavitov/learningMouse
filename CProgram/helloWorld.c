#include<stdio.h>
#include<stdlib.h>

#define TEST 1
#define TEST_1 0
#define TEST_2 0
#define TEST_3 0
#define TEST_4 0
#define TEST_5 0



int main(int arg, char** args) {

	#if TEST
	int x = 100;
	int count = 1;
	while(x++ < 104){
		int x = 1;
		printf("%d:x is: %d\n", count++, x);
		x = x + 1;
	}
	#endif

	#if TEST_1
	char* str_1 = (char* )malloc(20 * sizeof(char));
	char* str_2 = (char* )malloc(20 * sizeof(char));

	puts("input str_1:");
	scanf("%s", str_1);

	puts("input str_2");
	scanf("%s", str_2);

	printf("str_1:%s\n", str_1);
	printf("str_2:%s\n", str_2);

	#endif

	#if TEST_2
	char inputchar;
	while((inputchar = getchar()) != EOF){
		putchar(inputchar);
	}
	
	#endif

	#if TEST_3
	char inputchar;
	FILE* srcFile = fopen("E:\\Cexe\\text.txt", "rw");
	FILE* dstFile = fopen("E:\\Cexe\\text_copy.txt", "a+");
	
	while((inputchar = getc(srcFile)) != EOF){
		int result = putc(inputchar, dstFile);
		printf("%c", result);
	}
	fclose(srcFile);
	fclose(dstFile);
	#endif

	#if TEST_4
	FILE* srcFile = fopen("E:\\Cexe\\text.txt", "rw");
	char* str = (char* )malloc(100 * sizeof(char));
	while(!feof(srcFile)){
		if(fscanf(srcFile, "%s", str) != -1) {
			puts(str);
		} else {
			puts("error!");
		}
	}
	fclose(srcFile);
	#endif

	getchar();
	return 0;
}