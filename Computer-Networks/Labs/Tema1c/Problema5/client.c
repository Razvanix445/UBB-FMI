/*
Un client trimite unui server un numar reprezentat pe un octet fara semn. 
Serverul va returna clientului sirul divizorilor acestui numar.
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <arpa/inet.h>
#include <time.h>

#define PORT 53298
#define MAX_BUF_SIZE 1024

int main(int argc, char *argv[]) {
    int c;
    struct sockaddr_in server;
    socklen_t addrLen = sizeof(server);
    char buf[1], bufr[MAX_BUF_SIZE];
    int slen = sizeof(server), client;

    c = socket(AF_INET, SOCK_DGRAM, 0);
    if (c < 0) {
        perror("Eroare la crearea socket-ului\n");
        exit(1);
    }

    memset((char *) &server, 0, sizeof(server));
    server.sin_family = AF_INET;
    server.sin_port = htons(atoi(argv[1]));
    server.sin_addr.s_addr = inet_addr(argv[2]);

    printf("Introduceti numarul fara semn (0-255): ");
    scanf("%hhu", buf);
    sendto(c, buf, sizeof(char), 0, (struct sockaddr *) &server, slen);
    //sendto(c, buf, strlen(buf), 0, (struct sockaddr *) &server, slen);

    recvfrom(c, bufr, sizeof(bufr), 0, (struct suckaddr *) &client, &slen);
    printf("Divizorii numarului sunt: %s\n", bufr);

    close(c);

    return 0;
}