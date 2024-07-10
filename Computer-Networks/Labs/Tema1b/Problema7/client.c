/*
Un client trimite unui server un sir de caractere si doua numere (fie acestea s, i, l). 
Serverul va returna clientului subsirul de lungime l a lui s care incepe la pozitia i.
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
  char sirDeCaractere[1024];
  int lungime, pozitie;

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
  printf("Introduceti primul numar: ");
  scanf(" %d", &lungime);
  printf("Introduceti al doilea numar: ");
  scanf(" %d", &pozitie);

  int lungimeSir = strlen(sirDeCaractere);
  lungimeSir = htonl(lungimeSir);
  send(c, &lungimeSir, sizeof(int), 0);
  lungimeSir = ntohl(lungimeSir);
  send(c, sirDeCaractere, lungimeSir * sizeof(char), 0);
  lungime = htonl(lungime);
  send(c, &lungime, sizeof(int), 0);
  lungime = ntohl(lungime);
  pozitie = htonl(pozitie);
  send(c, &pozitie, sizeof(int), 0);
  pozitie = ntohl(pozitie);

  printf("Am trimis lungimeSir: %d\n", lungimeSir);
  printf("Am trimis sirDeCaractere: %s\n", sirDeCaractere);
  printf("Am trimis lungime: %d\n", lungime);
  printf("Am trimis pozitie: %d\n", pozitie);

  int lungimeSubsir;
  int subsir[1024];
  recv(c, &lungimeSubsir, sizeof(int), MSG_WAITALL);
  lungimeSubsir = ntohl(lungimeSubsir);
  printf("Am primit lungimea: %d\n", lungimeSubsir);
  recv(c, subsir, lungimeSubsir * sizeof(char), MSG_WAITALL);
  printf("Subsirul este: %s\n", subsir);

  close(c);
}