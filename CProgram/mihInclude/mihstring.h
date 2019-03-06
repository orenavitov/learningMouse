//字符串切片， start起始位置， end终止位置, start从0开始， end到length - 1;
char* stringslip(char* origin, int start, int end);

//返回指定字符第一次出现的位置， 从0开始计数; 成功找到时返回相应的位置， 未找到时返回-1;
int indexofchar(char* origin, char target);

//KMP字符串模式匹配
int KMP(char* string, char* targetString);