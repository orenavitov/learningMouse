

/*
 * HANDLE CreateFile(
 *                  //文件的完整路径名
 *                  LPCTSTR lpFileName, 
 *                  //表示对文件的何种操作， GENERIC_READ表示只读
 *                  //GENRIC_WIRTE表示只写。。。
 *                  DWORD dwDesAccess, 
 *                  //表示允许其他程序对该文件的操作
 *                  DWORD dwShareMode, 
 *                  LPSECURITY_ATTRIBUTES lpSecurityAttributes, 
 *                  //指明当文件存在或者不存在时对文件的操作
 *                  DWORD dwCreateionDisposition,
 *                  //表示新建文件的属性
 *                  DWORD dwFlagsAndAttributes,
 *                  //指向用于存取的模板文件的句柄， 系统会复制模板文件的所有属性到新创建的文件中， 没有则为NULL
 *                  HANDLE hTemplateFile)
 * 
 */

/*
 * BOOL WriteFile(HANDLE hFile, 
 *                LPCVOID lpBuffer,
 *                DWORD nNumberOfBytesToWrite,
 *                LPDWORD lpNumberOfBytesWritten,
 *                LPOVERLAPPEN lpOverlapped)
 * 
 * BOOL ReadFile(HANDLE hFile,
 *               LPCVOID lpBuffer,
 *               DWORD nNumberOfBytesToRead,
 *               LPDWORD lpNumberOfBytesRead,
 *               LPOVERLAPPED lpOverlapped)
 * hFile指向一个文件句柄， 由CreateFile创建
 * lpBuffer指向一个缓冲区
 * nNumberOfBytesToWrite / nNumberOfBytesToRead表示要求写入或读取的字节数
 * lpNumberOfBytesWritten / lpNumberOfBytesRead 表示实际读取的字节数 
 * lpOverlapped设为NULL
 */