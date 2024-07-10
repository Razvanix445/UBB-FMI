/*
Un client trimite unui server un sir de numere. Serverul va returna clientului suma numerelor primite.
*/

#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
#include <arpa/inet.h>
#include <unistd.h>

int main(int argc, char *argv[]) {
    int c;
    struct sockaddr_in server;
    int a, b, suma;

    c = socket(AF_INET, SOCK_STREAM, 0);
    if (c < 0) {
        printf("Eroare la crearea socket-ului client!\n");
        return 1;
    }

    memset(&server, 0, sizeof(server));
    server.sin_port = htons(atoi(argv[1]));
    server.sin_family = AF_INET;
    server.sin_addr.s_addr = inet_addr(argv[2]);

    if (connect(c, (struct sockaddr *) &server, sizeof(server)) < 0) {
        printf("Eroare la conectarea la server!\n");
        return 1;
    }

    printf("Introduceti primul numar: ");
    scanf("%d", &a);
    printf("Introduceti al doilea numar: ");
    scanf("%d", &b);

    a = htonl(a);
    b = htonl(b);
    send(c, &a, sizeof(int), 0);
    send(c, &b, sizeof(int), 0);

    recv(c, &suma, sizeof(int), MSG_WAITALL);
    suma = ntohl(suma);
    printf("Suma este: %d\n", suma);

    close(c);
}
