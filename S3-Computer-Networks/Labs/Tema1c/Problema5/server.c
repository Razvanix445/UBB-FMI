/*
Un client trimite unui server un numar reprezentat pe un octet fara semn. 
Serverul va returna clientului sirul divizorilor acestui numar.

NU FUNCTIONEAZA!!! (NU AFISEAZA REZULTATUL DORIT...)
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
    char buf1[1], buf2[MAX_BUF_SIZE], bufr[MAX_BUF_SIZE];
    unsigned char numar;
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
        //printf("Received packet from %s:%d\n", inet_ntoa(client.sin_addr), ntohs(client.sin_port));

        numar = buf1[0];
        printf("Numar primit: %u\n", numar);

        int numarDivizori = 0;
        for (unsigned char i = 1; i <= numar; i ++) {
            if (numar % i == 0)
                bufr[numarDivizori++] = i;
        }

		//memset((char *) &bufr, 0, sizeof(bufr));
        int pozitie = 0;
        for (int i = 0; i < numarDivizori; i ++)
            pozitie += sprintf(bufr + pozitie, "%d ", bufr[i]);
        bufr[pozitie - 1] = '\0';
        printf("%s", bufr);
		sendto(s, bufr, strlen(bufr) , 0 , (struct sockaddr *) &client, slen);
    }

    close(s);

    return 0;
}