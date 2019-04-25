#include <winsock2.h>
#include<stdio.h>
#pragma comment(lib, "ws2_32.lib")

int main()
{
    WSADATA wsaData;
    WORD sockVersion = MAKEWORD(2, 2);
    //WSAStartup(sockVersion, &wsaData)用于加载Winsock库
    if (WSAStartup(sockVersion, &wsaData) != 0)
    {

        return 0;
    }
    //创建套接字
    /*  
     * Socket(int af, int type, int protocol)
     * af用来指定套接字使用的地址格式， 这里只能用AF_INET
     * Type 指定套接字的类型， 具体类型有：
     *  1. sock_stream流套接字， 只用TCP传输
     *  2. sock_dgram数据包套接字， 使用UDP传输
     *  3. sock_raw原始套接字
     * protocol指定协议类型，配合type使用
     * 创建成功返回一个新的句柄， 失败返回invalid_socket
     */
    SOCKET sListen = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
    if (sListen == INVALID_SOCKET) {
        return 0;
    }
    //绑定套接字
    /*
     * bind(SOCKET s, const struct sockaddr FAR *addr, int namelen)
     * addr是一个sockaddr结构：
     * struct sockaddr_in {
     *      short int sin_family;
     *      unsigned short int sin_port;
     *      struct in_addr sin_addr;
     *      unsigned char sin_zero[8];
     * }
     */
    struct sockaddr_in sin;
    sin.sin_family =AF_INET;
    //转换成网络字节序
    sin.sin_port = htons(4500);
    sin.sin_addr.S_un.S_addr = INADDR_ANY;
    if (bind(sListen, (LPSOCKADDR)&sin, sizeof(sin)) == SOCKET_ERROR) {
        return 0;
    }
    //调用listen
    /*
     * listen（SOCKET s, int backlog）仅支持连接的套接字也就是仅支持TCP
     * s指向一个套接字句柄， 由socket函数创建
     * backlog表示支持的最大连接数
     */
    if (listen(sListen, 5) == SOCKET_ERROR) {
        closesocket(sListen);
        return 0;
    }
    struct sockaddr_in remoteAddr;
    SOCKET sClient;
    int nAddrLen = sizeof(remoteAddr);
    char revData[255];
    while(TRUE) {
        /*
         * accept(SOCKET s, struct sockaddr* addr, int* addrlen)
         * s指向一个socket函数创建的套接字句柄
         * addr指向一个sockaddr结构指针， 用来存储客户端的地址信息
         * accept在未收到客户端的连接时会一直处于阻塞状态， 在收到一个客户端的连接后会返回一个新的客户端
         * 的套接字句柄
         */
        sClient = accept(sListen, (SOCKADDR* )&remoteAddr, &nAddrLen);
        if (sClient == INVALID_SOCKET) {
            continue;
        }
        printf("receive a connection:%s\n", inet_ntoa(remoteAddr.sin_addr));
        /*
         * int recv(SOCKET s, char FAR * buf, int len, int flags)由于接受
         * s 由accept返回
         * buf 指向一个缓冲区
         * len 缓冲区大小
         * flags 一般为0
         */
        int ret = recv(sClient, revData, 255, 0);
        if (ret > 0) {
            revData[ret] = 0x00;
            puts(revData);
        }
        char* buf = "i am coming!";
        /*
         * send(SOCKET s, Const char FAR * buf, int len, int flags)用于发送， 参数同recv
         */
        send(sClient, buf, strlen(buf), 0);
        closesocket(sClient);

    }
    closesocket(sListen);
    WSACleanup();
    return 1;
}