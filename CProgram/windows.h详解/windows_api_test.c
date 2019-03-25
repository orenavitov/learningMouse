#include <windows.h>
#include <stdio.h>
#include <tlhelp32.h>
#include <string.h>
void test5()
{
    POINT curpos;
    while (1)
    {
        GetCursorPos(&curpos);
        HWND hWnd = WindowFromPoint(curpos);
        MoveWindow(hWnd, 0, 0, 220, 120, 0);
    }
}

void test8()
{
    CopyFile("C:\\Users\\mihao\\Desktop\\abc.txt",
             "C:\\Users\\mihao\\Desktop\\a.txt", FALSE);
}

void test9()
{
    DeleteFile("C:\\Users\\mihao\\Desktop\\abc.txt");
}

void test11()
{
    POINT curpos;
    RECT rect;
    while (1)
    {
        GetCursorPos(&curpos);
        HWND hWnd = WindowFromPoint(curpos);
        GetClientRect(hWnd, &rect);
        printf("rect.left:%d, rect.right:%d, rect.bottom, rect.top", rect.left, rect.right, rect.bottom, rect.top);
    }
}

void test13()
{
    BOOL done = TRUE;
    WIN32_FIND_DATA fd;
    HANDLE hFind = FindFirstFile("D:\\python\\*.*", &fd);
    while (done)
    {
        printf("%s\n", fd.cFileName);
        done = FindNextFile(hFind, &fd);
    }
}

void test17()
{
    WIN32_FIND_DATA fd;
    FindFirstFile("C:\\Users\\mihao\\Desktop\\abc.txt", &fd);
    fd.dwFileAttributes = fd.dwFileAttributes | FILE_ATTRIBUTE_READONLY;
    SetFileAttributes("C:\\Users\\mihao\\Desktop\\abc.txt", fd.dwFileAttributes);
}

void test18()
{
    ShellExecute(NULL, "open", "D:\\QQbrowser\\QQBrowser\\QQBrowser.exe", "https://www.baidu.com/?tn=98010089_dg&ch=15", NULL, SW_SHOWNORMAL);
}

void test30()
{
    PROCESSENTRY32 pe32;
    pe32.dwSize = sizeof(pe32);
    HANDLE hProcessSnap = CreateToolhelp32Snapshot(TH32CS_SNAPPROCESS, 0);
    BOOL bMore = Process32First(hProcessSnap, &pe32);
    while (bMore)
    {

        printf("thread name:%s\n", pe32.szExeFile);     //szExeFile是进程名
        printf("thread ID:%u\n\n", pe32.th32ProcessID); //th32ProcessID是进程ID号

        bMore = Process32Next(hProcessSnap, &pe32); //寻找下个进程，函数返回0，则没有进程可寻
    }
}

//把firefox进程关闭
// void test32() {
//     PROCESSENTRY32 pe32;
//     pe32.dwSize = sizeof(pe32);
//     HANDLE hProcessSnap = CreateToolhelp32Snapshot(TH32CS_SNAPPROCESS, 0);
//     BOOL bMore = Process32First(hProcessSnap, &pe32);
//     while(bMore){
//         printf("%s\n", pe32.szExeFile);
//         if(strcmp("firefox.exe", pe32.szExeFile)) {
//             printf("find firefox.exe\n");
//             HANDLE hProcess = OpenProcess(PROCESS_ALL_ACCESS, FALSE, pe32.th32ProcessID);
//             TerminateProcess(hProcess, 0);
//         }
//         bMore = Process32Next(hProcessSnap, &pe32);
//     }
    
// }

void test() {
    
}

int main(int argc, char const *argv[])
{
    // test32();
    getchar();
    return 0;
}
