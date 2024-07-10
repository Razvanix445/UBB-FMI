/*
Un client trimite unui server un sir de caractere si un caracter.
Serverul va returna clientului toate pozitiile pe care caracterul
primit se regaseste in sir.
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
  char caracter;

  c = socket(AF_INET, SOCK_STREAM, 0);
  if (c < 0) {
    printf("Eroare la crearea socketului client\n");
    return 1;
  }

  memset(&server, 0, sizeof(server));
  server.sin_port = htons(atoi(argv[1]));
  server.sin_family = AF_INET;
  server.sin_addr.s_addr = inet_addr(argv[2]);
  //printf("%d %s\n", atoi(argv[1]), argv[2]);

  if (connect(c, (struct sockaddr *) &server, sizeof(server)) < 0) {
    printf("Eroare la conectarea la server\n");
    return 1;
  }

  printf("Introduceti sirul de caractere: ");
  //fgets(sirDeCaractere, sizeof(sirDeCaractere), stdin);
  scanf(" %s", sirDeCaractere);
  printf("Introduceti caracterul cautat: ");
  scanf(" %c", &caracter);

  int lungimeSir = strlen(sirDeCaractere);
  lungimeSir = htonl(lungimeSir);
  send(c, &lungimeSir, sizeof(int), 0);
  lungimeSir = ntohl(lungimeSir);

  for (int i = 0; i < lungimeSir; i++)
    send(c, &sirDeCaractere[i], sizeof(char), 0);
    //send(c, sirDeCaractere, lungimeSir * sizeof(char), 0);
  send(c, &caracter, sizeof(char), 0);
  printf("Am trimis lungimea sirului la server: %d\n", lungimeSir);
  printf("Am trimis sirul de caractere la server: %s\n", sirDeCaractere);
  printf("Am trimis caracterul la server: %c\n\n", caracter);

  int numarPozitii;
  recv(c, &numarPozitii, sizeof(int), MSG_WAITALL);
  printf("numarPozitii primite de la server: %d\n", numarPozitii);
  numarPozitii = ntohl(numarPozitii);
  if (numarPozitii > 0) {
    int pozitii[1024];
    recv(c, pozitii, sizeof(int) * numarPozitii, MSG_WAITALL);

    printf("Caracterul '%c' se gaseste la urmatoarele pozitii in text: ", caracter);
    for (int i = 0; i < numarPozitii; i++) {
      pozitii[i] = ntohl(pozitii[i]);
      printf("%d ", pozitii[i]);
    }
    printf("\n");
  } else {
    printf("Caracterul '%c' nu a fost gasit in text.\n", caracter);
  }

  close(c);
}
