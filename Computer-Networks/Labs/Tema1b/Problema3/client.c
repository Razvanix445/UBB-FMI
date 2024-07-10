/*
Un client trimite unui server un sir de caractere. 
Serverul va returna clientului acest sir oglindit (caracterele sirului in ordine inversa).
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
  char sirDeCaractere[1024], sirOglindit[1024];

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

  printf("Introduceti sirul de caractere: ");
  scanf(" %s", sirDeCaractere);

  int lungimeSir = strlen(sirDeCaractere);
  lungimeSir = htonl(lungimeSir);
  send(c, &lungimeSir, sizeof(int), 0);
  lungimeSir = ntohl(lungimeSir);
  send(c, sirDeCaractere, lungimeSir * sizeof(char), 0);

  int lungimeOglindit;
  recv(c, &lungimeOglindit, sizeof(int), MSG_WAITALL);
  lungimeOglindit = htonl(lungimeOglindit);
  recv(c, sirOglindit, lungimeOglindit * sizeof(char), MSG_WAITALL);
  printf("Sirul oglindit este: %s\n", sirOglindit);

  close(c);
}