#include<windows.h>
#include<stdlib.h>
#include<stdio.h>

/*
 * windows 钩子实现
 * 
 * 安装钩子：
 * HHOOK SetWindowsHookEx (
 *  int idHock,
 *  HOOKPROC lpfn,
 *  INSTANCE hMod,
 *  DWORD dwThreadId
 * 
 * )
 * 
 * idHock执行了安装钩子的类型：
 * WH_CALLWNDPROC:
 * 
 * WH_CALLWNDPROCRET:
 * 
 * WH_GETMESSAGE:
 * 
 * WH_KEYBOARD:
 * 
 * WH_MOUSE:
 * 
 * WH_MSGFILTER:
 * 
 * WH_SYSMSGFILTER:
 * 
 * WH_SHELL:
 * 
 * WH_DBUG:
 * 
 * WH_CBT:
 * 
 * WH_JOURANLRECORD:
 * 
 * WH_FOREGROUNDIDLE:
 * 
 * 
 * lpfn指向钩子函数的地址；
 * hMod指向钩子函数所在DLL的实例句柄， 如果函数不在DLL中， 此参数为NULL;
 * dwThredId指定了安装钩子后想监控的线程的id， 这个参数决定了钩子是局部还是系统范围的；
 * 注意dwThread可以是本进程的线程id， 可以是其他进程的线程id， 也可以是NULL(系统范围)，
 * 如果是其他进程的线程id， 或者是null， 则钩子函数必须是一个dll文件中的函数；
 * 函数执行成功返回一个钩子句柄；
 * 
 * 卸载钩子：
 * BOOL UnhookWindwsHookEx (
 *  HHOOK hhk
 * )
 * 
 * 
 * 
 */

LRESULT CALLBACK KeyHookProc(int nCode, WPARAM wParm, LPARAM lParam) {

    

}