#include <winsock2.h>
#include<stdio.h>
#pragma comment(lib, "ws2_32.lib")

int main() {
    WSADATA wsaData;
    WORD sockVersion = MAKEWORD(2, 2);
    if (WSAStartup(sockVersion, &wsaData) != 0) {
        return 0;
    }
    SOCKET sClient = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
    if (sClient == INVALID_SOCKET) {
        return 0;
    }
    struct sockaddr_in servAddr;
    servAddr.sin_family = AF_INET;
    servAddr.sin_port = htons(4500);
    servAddr.sin_addr.S_un.S_addr = inet_addr("127.0.0.1");
    if (connect(sClient, (struct sockaddr* )&servAddr, sizeof(servAddr)) == SOCKET_ERROR) {
        closesocket(sClient);
        return 0;
    }
    char* buf = "i am coming!";
    send(sClient, buf, strlen(buf), 0);
    char revData[255];
    int ret = recv(sClient, revData, 255, 0);
    if (ret > 0) {
        revData[ret] = 0x00;
        puts(revData);
    }
    closesocket(sClient);
    WSACleanup();
    return 0;
}