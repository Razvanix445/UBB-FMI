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
        char sirDeCaractere[1024], subsir[1024];
        int lungime, pozitie;
        int lungimeSir, lungimeSubsir;

        recv(c, &lungimeSir, sizeof(int), MSG_WAITALL);
        lungimeSir = ntohl(lungimeSir);
        printf("Am primit lungimea sirului: %d\n", lungimeSir);
        recv(c, sirDeCaractere, lungimeSir * sizeof(char), MSG_WAITALL);
        printf("Am primit sirul: %s\n", sirDeCaractere);
        recv(c, &lungime, sizeof(int), MSG_WAITALL);
        lungime = ntohl(lungime);
        printf("Am primit lungimea: %d\n", lungime);
        recv(c, &pozitie, sizeof(int), MSG_WAITALL);
        pozitie = ntohl(pozitie);
        printf("Am primit pozitia: %d\n", pozitie);

        int k = 0;
        for (int i = pozitie; k < lungime; i ++) {
            subsir[k++] = sirDeCaractere[i];
        }
        subsir[k] = '\0';
        printf("subsir: %s\n", subsir);

        lungimeSubsir = strlen(subsir);
        lungimeSubsir = htonl(lungimeSubsir);
        send(c, &lungimeSubsir, sizeof(int), 0);
        lungimeSubsir = ntohl(lungimeSubsir);
        send(c, subsir, lungimeSubsir * sizeof(char), 0);

        close(c);
        return 0;
    }
  }
}