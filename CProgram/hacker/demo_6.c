#include<stdio.h>
#include<stdlib.h>
#include<Windows.h>
#include<tlhelp32.h>
#include<iphlpapi.h>
#include<string.h>

typedef DWORD (*PGetExtendedTcpTable) (
    PVOID pTcpTable, PDWORD pdwSize, WINBOOL bOrder, ULONG ulAf, TCP_TABLE_CLASS TableClass, ULONG Reserved
);

int GetTcpConnect();

void ipAddressDW2Str(DWORD ipDword) {
    if (ipDword == 0) {
        return;
    }
    unsigned char* startAddress = (unsigned char* )(&ipDword);
    printf("remote ip address is: %u.%u.%u.%u\n", *(startAddress), *(startAddress + 1), *(startAddress + 2), *(startAddress + 3));    
}

int GetTcpConnect(DWORD processID) {
    HINSTANCE hLibrary = LoadLibrary("Iphlpapi.dll");
    PGetExtendedTcpTable pGetExtendedTcpTable = (PGetExtendedTcpTable) GetProcAddress(hLibrary, "GetExtendedTcpTable");
    PMIB_TCPTABLE_OWNER_PID pTcpTable;
    DWORD dwSize = 0;
    pGetExtendedTcpTable(pTcpTable, &dwSize, TRUE, AF_INET, TCP_TABLE_OWNER_PID_ALL, 0);
    pTcpTable = (MIB_TCPTABLE_OWNER_PID* ) calloc(dwSize, sizeof(char));
    if (pGetExtendedTcpTable(pTcpTable, &dwSize, TRUE, AF_INET, TCP_TABLE_OWNER_PID_ALL, 0) != NO_ERROR) {
        puts("Error in GetExtendedTcpTable!");
        free(pTcpTable);
        return 0;
    }
    int tcpCount = (int) (pTcpTable -> dwNumEntries);
    for (int index = 0; index < tcpCount; index ++) {
        MIB_TCPROW_OWNER_PID table = pTcpTable -> table[index];
        // DWORD localAddr = table.dwLocalAddr;
        DWORD remoteAddr = table.dwRemoteAddr;
        
        // DWORD localPort = table.dwLocalPort;
        // DWORD remotePort = table.dwRemotePort;
        DWORD pid = table.dwOwningPid;
        if (pid == processID) {
            ipAddressDW2Str(remoteAddr);
        }
    }
    return 0;
}

int main(int argc, char const *argv[])
{
    /*
     * CreateToolHelp32napshot 得到进程快照
     * HANDLE WINAPI CreaeToolhelp32napshot (
     *          DWORD dwFlags,
     *          DWORD th32ProcessID
     * )
     * dwFlags指定了获取系统进程快照的类型：
     * TH32CS_SNAPHEAPLIST: 列举th32ProcessID指定进程中的堆
     * TH32CS_SNAPMODULE: 列举th32ProcessID指定进程中的模块
     * TH32CS_SNAPPROCESS: 列举系统范围内的所有进程
     * TH32CS_SNAPTHREAD: 列举系统范围内的所有线程
     * 
     * th32ProcessID 指向获取进程快照的进程ID， 获取系统内所有进程快照时设为0
     * 
     * BOOL Process32First (
     *          HANDLE hSnapshot,
     *          LPPROCESSENTRY32 lppe
     * )
     * 
     * BOOL Process32Next (
     *          HANDLE hSnapshot,
     *          LPPROCESSENTRY32 lppe
     * )
     * 上述两个函数用于枚举进程， hSnapshot为CreateToolHelp32napshot返回的进程句柄
     * PROCESSENTRY32结构为：
     * Typedef struct {
     *          DWORD dwSize; //processentry32结构长度
     *          DWORD cntUsage; //进程应用记号
     *          DWORD th32ProcessID; //进程ID
     *          DWORD th32ModuleID; //进程模块ID
     *          DWORD cnThreads; //进程所创建的线程数
     *          DWORD th32ParentProcessID; //父进程ID
     *          DWORD pcPriClassBase; //常见线程的优先级
     *          DWORD dwFlags; //未使用
     *          CHAR szExeFile[MAX_PATH]; //进程的可执行文件名
     * } PROCESSENTRY32
     */

    HANDLE hProcesse = CreateToolhelp32Snapshot(TH32CS_SNAPPROCESS, 0);
    if (hProcesse == INVALID_HANDLE_VALUE) {
        printf("CreateToolhelp32Snapshot error!\n");
        return 0;
    }
    PROCESSENTRY32 pe32;
    pe32.dwSize = sizeof(pe32);
    BOOL bProcess = Process32First(hProcesse, &pe32);
    while (bProcess) {
        char* szExeFile = pe32.szExeFile;
        DWORD th32ProcessID = pe32.th32ProcessID;
        printf("the exe program is: %s\n", szExeFile);
        printf("the process id is: %d\n", th32ProcessID);
        GetTcpConnect(th32ProcessID);
        puts("--------------------------------------------------------------");
        bProcess = Process32Next(hProcesse, &pe32);
    }
    
    getchar();
    return 0;
}
