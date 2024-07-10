/*
Un client trimite unui server o cerere prin care ii cere acestuia data si ora curenta. 
Serverul va returna clientului data sub forma unui intreg ce reprezinta numarul de secunde 
trecute de la 1 ianuarie 1970. Clientul va afisa corespunzator data pe baza acestui intreg.
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
    char comanda[MAX_BUF_SIZE];

    if ((c = socket(AF_INET, SOCK_DGRAM, 0)) == -1) {
        perror("Eroare la crearea socket-ului\n");
        exit(1);
    }

    server.sin_family = AF_INET;
    server.sin_port = htons(atoi(argv[1]));
    server.sin_addr.s_addr = inet_addr(argv[2]);

    while (1) {
        // Trimite cererea la server
        strcpy(comanda, "GET_TIME");
        sendto(c, comanda, strlen(comanda), 0, (struct sockaddr *)&server, addrLen);

        // Primeste numarul de secunde
        time_t numarSecunde;
        recvfrom(c, &numarSecunde, sizeof(numarSecunde), 0, (struct sockaddr *)&server, &addrLen);

        printf("Data si ora curenta: %s", asctime(localtime(&numarSecunde)));
        sleep(1);
    }

    close(c);

    return 0;
}
