#include<windows.h>
#include<string.h>
#include<stdlib.h>

#define PATH "D:\\Cexe"

int main() {
    char* Path = (char* ) calloc(sizeof(char), 255);
    char FileName[255];
    char* data = "mih hello";
    for (int i = 0; i < 10; i ++) {
        // GetWindowsDirectory(Path, sizeof(Path));
        memcpy(Path, &PATH, strlen(PATH) * sizeof(char));
        wsprintf(FileName, "\\%d.txt", i);
        
        strcat(Path, FileName);
        HANDLE hFile;
        hFile = CreateFile(Path, GENERIC_WRITE, 0, NULL, CREATE_ALWAYS, FILE_ATTRIBUTE_NORMAL, NULL);
        if(hFile == INVALID_HANDLE_VALUE) {
            continue;
        }
        DWORD dwWrite;
        WriteFile(hFile, data, strlen(data), &dwWrite, NULL);
        CloseHandle(hFile);
        memset(Path, 0x00, 255);
        memset(FileName, 0x00, 255);
    }
    return 0;
}