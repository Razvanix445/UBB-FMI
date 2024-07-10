/*
Un client trimite unui server doua siruri de numere. 
Serverul va returna clientului sirul de numere comune celor doua siruri primite.
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <arpa/inet.h>

#define PORT 53298
#define MAX_BUF_SIZE 1024

int main(int argc, char *argv[]) {
    int c;
    struct sockaddr_in server;
    socklen_t addrLen = sizeof(server);
    char buf1[MAX_BUF_SIZE], buf2[MAX_BUF_SIZE], bufr[MAX_BUF_SIZE];
    int slen = sizeof(server);

    if (argc != 3) {
        printf("Usage: %s <server_port> <server_ip>\n", argv[0]);
        exit(1);
    }

    c = socket(AF_INET, SOCK_DGRAM, 0);
    if (c < 0) {
        perror("Eroare la crearea socket-ului\n");
        exit(1);
    }

    memset((char *) &server, 0, sizeof(server));
    server.sin_family = AF_INET;
    server.sin_port = htons(atoi(argv[1]));
    server.sin_addr.s_addr = inet_addr(argv[2]);

    printf("Introduceti primul sir de numere separate prin spatiu: ");
    fgets(buf1, MAX_BUF_SIZE, stdin);
    sendto(c, buf1, strlen(buf1), 0, (struct sockaddr *) &server, slen);

    printf("Introduceti al doilea sir de numere separate prin spatiu: ");
    fgets(buf2, MAX_BUF_SIZE, stdin);
    sendto(c, buf2, strlen(buf2), 0, (struct sockaddr *) &server, slen);

    recvfrom(c, bufr, sizeof(bufr), 0, (struct sockaddr *) &server, &slen);
    printf("Sirul de numere comune este: %s\n", bufr);

    close(c);

    return 0;
}
