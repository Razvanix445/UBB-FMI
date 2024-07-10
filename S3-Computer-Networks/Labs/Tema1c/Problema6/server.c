/*
Un client trimite unui server un sir de lungime cel mult 100 de caractere si un caracter. 
Serverul va returna clientului toate pozitiile pe care caracterul primit se regaseste in sir.
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
    char buf1[MAX_BUF_SIZE], buf2[MAX_BUF_SIZE], bufr[MAX_BUF_SIZE];
    char caracter;
    int a, b, rez;
    int slen = sizeof(client);

    s = socket(AF_INET, SOCK_DGRAM, 0);
    if (s < 0) {
        perror("Eroare la crearea socket-ului\n");
        exit(1);
    }

    memset(&server, 0, sizeof(server));
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
		//memset((char *) &buf1, 0, sizeof(buf1));
        memset(buf1, 0, sizeof(buf1));
        recvfrom(s, buf1, sizeof(buf1), 0, (struct sockaddr *) &client, &slen);
        recvfrom(s, &caracter, sizeof(char), 0, (struct sockaddr *) &client, &slen);
        //printf("Received packet from %s:%d\n", inet_ntoa(client.sin_addr), ntohs(client.sin_port));

        printf("Am primit sirul: %s\n", buf1);
        printf("Am primit caracterul: %c\n", caracter);

        int contor = 0;
        for (int i = 0; i < strlen(buf1); i ++) {
            if (buf1[i] == caracter) {
                bufr[contor++] = '0' + i;
                bufr[contor++] = ' ';
            }
        }
        bufr[contor] = '\0';

		//memset((char *) &bufr, 0, sizeof(bufr));
		printf("Trimit inapoi: %s\n", bufr);
		sendto(s, bufr, strlen(bufr) , 0 , (struct sockaddr *) &client, slen);

        //memset(bufr, 0, sizeof(bufr));
    }

    close(s);

    return 0;
}
