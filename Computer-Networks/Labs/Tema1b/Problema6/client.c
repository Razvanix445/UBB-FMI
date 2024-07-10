/*
Un client trimite unui server un sir de caractere si un caracter. 
Serverul va returna clientului toate pozitiile pe care caracterul primit se regaseste in sir.
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
  char sirDeCaractere[1024], caracter;

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
  printf("Introduceti caracterul: ");
  scanf(" %c", &caracter);

  int lungimeSir = strlen(sirDeCaractere);
  lungimeSir = htonl(lungimeSir);
  
  send(c, &lungimeSir, sizeof(int), 0);
  lungimeSir = ntohl(lungimeSir);
  send(c, sirDeCaractere, lungimeSir * sizeof(char), 0);
  send(c, &caracter, sizeof(char), 0);

  printf("Am trimis sirul: %s\n", sirDeCaractere);
  printf("Am trimis caracterul: %c\n", caracter);

  int lungimeSirPozitii;
  int sirPozitii[1024];
  recv(c, &lungimeSirPozitii, sizeof(int), MSG_WAITALL);
  lungimeSirPozitii = ntohl(lungimeSirPozitii);
  printf("Am primit lungimea: %d\n", lungimeSirPozitii);
  recv(c, sirPozitii, lungimeSirPozitii * sizeof(int), MSG_WAITALL);
  printf("Pozitiile: ");
  for (int i = 0; i < lungimeSirPozitii; i ++) {
    printf("%d ", sirPozitii[i]);
  }
  printf("\n");

  close(c);
}