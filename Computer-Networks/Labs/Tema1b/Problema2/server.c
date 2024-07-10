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

int main() {
    int s;
    struct sockaddr_in server, client;
    int c, l;

    s = socket(AF_INET, SOCK_STREAM, 0);
    if (s < 0) {
        printf("Eroare la crearea socket-ului server!\n");
        return 1;
    }

    memset(&server, 0, sizeof(server));
    server.sin_port = htons(53298);
    server.sin_family = AF_INET;
    server.sin_addr.s_addr = INADDR_ANY;

    if (bind(s, (struct sockaddr *) &server, sizeof(server)) < 0) {
        printf("Eroare la bind...\n");
        return 1;
    }
    listen(s, 5);

    l = sizeof(client);
    memset(&client, 0, sizeof(client));

    while(1) {
        c = accept(s, (struct sockaddr *) &client, &l);
        printf("S-a conectat un client!\n");
        if (fork() == 0) {
            int n, estePrim = 0;
            recv(c, &n, sizeof(int), MSG_WAITALL);
            n = ntohl(n);

            if (n <= 1)
                estePrim = 0;
            else if (n == 2)
                estePrim = 1;
            else for (int d = 2; d * d <= n; d ++)
                if (n % d == 0) {
                    estePrim = 1;
                    break;
                }

            printf("estePrim: %d\n", estePrim);
            estePrim = htonl(estePrim);
            send(c, &estePrim, sizeof(int), 0);

            close(c);
        }
    }
}