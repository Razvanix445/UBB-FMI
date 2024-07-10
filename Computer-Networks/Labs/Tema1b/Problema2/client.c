/*
Un client trimite unui server un numar. Serverul va returna clientului 
un boolean care sa indice daca numarul respectiv este prim sau nu.
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
    int n, estePrim;

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

    printf("Introduceti numarul: ");
    scanf("%d", &n);

    n = htonl(n);
    send(c, &n, sizeof(int), 0);
    
    recv(c, &estePrim, sizeof(int), MSG_WAITALL);
    estePrim = ntohl(estePrim);
    if (estePrim == 0)
        printf("Numarul este prim.\n");
    else
        printf("Numarul nu este prim.\n");

    close(c);
}