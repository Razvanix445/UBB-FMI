/*
Un client trimite unui server o cerere prin care ii cere acestuia data si ora curenta. 
Serverul va returna clientului data sub forma unui intreg ce reprezinta numarul de secunde 
trecute de la 1 ianuarie 1970. Clientul va afisa corespunzator data pe baza acestui intreg.
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <unistd.h>
#include <arpa/inet.h>

#define PORT 53298
#define MAX_BUF_SIZE 1024

int main() {
    int s;
    struct sockaddr_in server, client;
    socklen_t addrLen = sizeof(client);
    char comanda[MAX_BUF_SIZE];

    if ((s = socket(AF_INET, SOCK_DGRAM, 0)) == -1) {
        perror("Eroare la crearea socket-ului\n");
        exit(1);
    }

    server.sin_family = AF_INET;
    server.sin_port = htons(PORT);
    server.sin_addr.s_addr = INADDR_ANY;

    if (bind(s, (struct sockaddr *)&server, sizeof(server)) == -1) {
        perror("Eroare la bind...\n");
        close(s);
        exit(1);
    }

    printf("Serverul asteapta clienti...\n");

    while (1) {
        // Primeste de la client
        ssize_t recvSize = recvfrom(s, comanda, sizeof(comanda), 0, (struct sockaddr *)&client, &addrLen);
        if (recvSize < 0) {
            perror("Eroare la primirea cererii");
            continue;
        }

        comanda[recvSize] = '\0';

        if (strcmp(comanda, "GET_TIME") == 0) {
            time_t numarSecunde;
            numarSecunde = time(NULL);

            // Trimite la client
            sendto(s, &numarSecunde, sizeof(numarSecunde), 0, (struct sockaddr *)&client, addrLen);
        }
    }

    close(s);

    return 0;
}
