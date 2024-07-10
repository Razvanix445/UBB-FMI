/*
Un client trimite unui server un sir de lungime cel mult 100 de caractere si un caracter. 
Serverul va returna clientului toate pozitiile pe care caracterul primit se regaseste in sir.
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
    char buf1[MAX_BUF_SIZE], buf2[MAX_BUF_SIZE], bufr[MAX_BUF_SIZE];
    char caracter;
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

    printf("Introduceti sirul de caractere: ");
    fgets(buf1, MAX_BUF_SIZE, stdin);
    sendto(c, buf1, strlen(buf1), 0, (struct sockaddr *) &server, slen);
    printf("Introduceti caracterul: ");
    scanf(" %c", &caracter);
    sendto(c, &caracter, sizeof(char), 0, (struct sockaddr *) &server, slen);

    printf("Am citit sirul: %s\n", buf1);
    printf("Am citit caracterul: %c\n", caracter);

    recvfrom(c, bufr, sizeof(bufr), 0, (struct suckaddr *) &client, &slen);
    printf("Pozitiile pe care caracterul se gaseste in sir: %s\n", bufr);

    close(c);

    return 0;
}