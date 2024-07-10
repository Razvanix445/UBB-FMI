/*
Un client trimite unui server doua siruri de caractere ordonate. 
Serverul va interclasa cele doua siruri si va returna clientului sirul rezultat interclasat.
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
    printf("Eroare la crearea socketului server\n");
    return 1;
  }

  memset(&server, 0, sizeof(server));
  server.sin_port = htons(53298);
  server.sin_family = AF_INET;
  server.sin_addr.s_addr = INADDR_ANY;

  if (bind(s, (struct sockaddr *) &server, sizeof(server)) < 0) {
    printf("Eroare la bind\n");
    return 1;
  }

  listen(s, 5);

  l = sizeof(client);
  memset(&client, 0, sizeof(client));

  while (1) {
    c = accept(s, (struct sockaddr *) &client, &l);
    printf("S-a conectat un client.\n");
    if (fork() == 0) {
        char sir1[1024], sir2[1024], sirInterclasat[2048];
        int lungimeSir1, lungimeSir2, lungimeSirInterclasat;
        recv(c, &lungimeSir1, sizeof(int), MSG_WAITALL);
        lungimeSir1 = ntohl(lungimeSir1);
        printf("Am primit lungimea sirului 1: %d\n", lungimeSir1);
        recv(c, sir1, lungimeSir1 * sizeof(char), MSG_WAITALL);
        printf("Am primit sirul 1: %s\n", sir1);

        recv(c, &lungimeSir2, sizeof(int), MSG_WAITALL);
        lungimeSir2 = ntohl(lungimeSir2);
        printf("Am primit lungimea sirului 2: %d\n", lungimeSir2);
        recv(c, sir2, lungimeSir2 * sizeof(char), MSG_WAITALL);
        printf("Am primit sirul 2: %s\n", sir2);

        int i = 0, j = 0, k = 0;
        while (i < lungimeSir1 && j < lungimeSir2)
            if (sir1[i] < sir2[j])
                sirInterclasat[k++] = sir1[i++];
            else
                sirInterclasat[k++] = sir2[j++];
        while (i < lungimeSir1) {
            sirInterclasat[k++] = sir1[i++];
        }
        while (j < lungimeSir2) {
            sirInterclasat[k++] = sir2[j++];
        }
        lungimeSirInterclasat = k;

        printf("Lungimea sirului interclasat este: %d\n", lungimeSirInterclasat);
        printf("Sirul interclasat este: %s\n", sirInterclasat);
        lungimeSirInterclasat = htonl(lungimeSirInterclasat);
        send(c, &lungimeSirInterclasat, sizeof(int), 0);
        printf("Am trimis lungimea: %d\n", lungimeSirInterclasat);
        lungimeSirInterclasat = ntohl(lungimeSirInterclasat);
        send(c, sirInterclasat, lungimeSirInterclasat * sizeof(char), 0);
        printf("Am trimis sirul interclasat: %s\n", sirInterclasat);

        close(c);
        return 0;
    }
  }
}