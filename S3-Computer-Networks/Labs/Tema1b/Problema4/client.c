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
#include <arpa/inet.h>
#include <unistd.h>


int main(int argc, char *argv[]) {
  int c;
  struct sockaddr_in server;
  char sir1[1024], sir2[1024];

  c = socket(AF_INET, SOCK_STREAM, 0);
  if (c < 0) {
    printf("Eroare la crearea socketului client\n");
    return 1;
  }

  memset(&server, 0, sizeof(server));
  server.sin_port = htons(atoi(argv[1]));
  server.sin_family = AF_INET;
  server.sin_addr.s_addr = inet_addr(argv[2]);

  if (connect(c, (struct sockaddr *) &server, sizeof(server)) < 0) {
    printf("Eroare la conectarea la server\n");
    return 1;
  }

  printf("Introduceti primul sir de caractere: ");
  scanf(" %s", sir1);
  printf("Introduceti al doilea sir de caractere: ");
  scanf(" %s", sir2);

  int lungimeSir1 = strlen(sir1);
  lungimeSir1 = htonl(lungimeSir1);
  int lungimeSir2 = strlen(sir2);
  lungimeSir2 = htonl(lungimeSir2);
  
  send(c, &lungimeSir1, sizeof(int), 0);
  lungimeSir1 = ntohl(lungimeSir1);
  send(c, sir1, lungimeSir1 * sizeof(char), 0);
  send(c, &lungimeSir2, sizeof(int), 0);
  lungimeSir2 = ntohl(lungimeSir2);
  send(c, sir2, lungimeSir2 * sizeof(char), 0);

  printf("Am trimis sirul 1: %s\n", sir1);
  printf("Am trimis sirul 2: %s\n", sir2);

  int lungimeSirInterclasat;
  char sirInterclasat[2048];
  recv(c, &lungimeSirInterclasat, sizeof(int), MSG_WAITALL);
  lungimeSirInterclasat = ntohl(lungimeSirInterclasat);
  printf("Am primit lungimea: %d\n", lungimeSirInterclasat);
  recv(c, sirInterclasat, lungimeSirInterclasat * sizeof(char), MSG_WAITALL);
  sirInterclasat[lungimeSirInterclasat] = '\0';
  printf("Sirul interclasat este: %s\n", sirInterclasat);

  close(c);
}