/*
Un client trimite unui server doua numere. Serverul va returna clientului suma celor doua numere.
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
		memset((char *) &buf1, 0, sizeof(buf1));		
        recvfrom(s, &buf1, sizeof(buf1), 0, (struct sockaddr *) &client, &slen);
        //printf("Received packet from %s:%d\n", inet_ntoa(client.sin_addr), ntohs(client.sin_port));

		memset((char *) &buf2, 0, sizeof(buf2));		
        recvfrom(s, &buf2, sizeof(buf2), 0, (struct sockaddr *) &client, &slen);
        //printf("Received packet from %s:%d\n", inet_ntoa(client.sin_addr), ntohs(client.sin_port));
		
        //ASCII to Integer
		a = atoi(buf1);
		b = atoi(buf2);
        printf("Numarul 1: %d\n", a);        
        printf("Numarul 2: %d\n", b);        
		rez = a + b;

		memset((char *) &bufr, 0, sizeof(bufr));
		sprintf(bufr ,"%d" , rez);                            //String Printf
		printf("Trimit inapoi: %s\n", bufr);
		sendto(s, &bufr, strlen(bufr)*sizeof(char) , 0 , (struct sockaddr *) &client, slen);
    }

    close(s);

    return 0;
}
