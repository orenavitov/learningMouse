#include<stdio.h>
#include<Windows.h>
#pragma comment(lib, "ws2_32.lib")

void CreateDWORDReg(HKEY hRoot, char* szSubKey, char* valueName, DWORD data) {
    HKEY hKey;
    /* 
     * long RegCreateKeyEx(HKEY hKey,
     *                     LPCTSTR lpSubKey,
     *                     DWORD Reserved,
     *                     LPTSTR lpClass,
     *                     DWORD dwOptions,
     *                     REGSAM samDesired,
     *                     LPSECURITY_ATTRIBUTES lpSecurityAttributes,
     *                     PHKEY phkResult,
     *                     LPDWORD lpdwDisposition 
     *                      )
     * hKey指向一个打开了的键的句柄， 可以由RegCreateEx
     * 函数或者RegOpenKeyEx函数返回， 也可以是注册表的根
     * 键；
     * szSubKey指向要代开的子健的名称；
     * Reserved参数保留未使用， 设为0即可；
     * lpClass一般设为0；
     * REG_OPTION_NON_VOLATILE表示创建子健的选项， 表示
     * 信息被保存在内存中， 系统重启后失效， REG_OPTION_NON_
     * VOLATLE表示信息被保存在文件中；
     * KEY_ALL_ACCESS子健的打开方式， 允许所有操作；
     * lpSecurityAttributes指定句柄的继承性；
     * phkResult用于返回创建或者打开的子健的句柄；
     * lpdwDisposition指向当子健不存在时是否要创建
     */
    long lRet = RegCreateKeyEx(hRoot, szSubKey, 0, NULL, 
    REG_OPTION_NON_VOLATILE, KEY_ALL_ACCESS, NULL, &hKey, NULL);
    if (lRet != ERROR_SUCCESS) {
        return;
    }
    
}

int main() {
    
}