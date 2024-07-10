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
        char sirDeCaractere[1024], caracter;
        int sirPozitii[1024];
        int lungimeSir, lungimeSirPozitii;

        recv(c, &lungimeSir, sizeof(int), MSG_WAITALL);
        lungimeSir = ntohl(lungimeSir);
        printf("Am primit lungimea sirului: %d\n", lungimeSir);
        recv(c, sirDeCaractere, lungimeSir * sizeof(char), MSG_WAITALL);
        printf("Am primit sirul: %s\n", sirDeCaractere);
        recv(c, &caracter, sizeof(char), MSG_WAITALL);

        printf("Caracterul: %c\n", caracter);
        lungimeSirPozitii = 0;
        for (int i = 0; i < lungimeSir; i ++) {
            if (sirDeCaractere[i] == caracter)
                sirPozitii[lungimeSirPozitii++] = i;
        }

        lungimeSirPozitii = htonl(lungimeSirPozitii);
        send(c, &lungimeSirPozitii, sizeof(int), 0);

        lungimeSirPozitii = ntohl(lungimeSirPozitii);
        send(c, sirPozitii, lungimeSirPozitii * sizeof(int), 0);

        close(c);
        return 0;
    }
  }
}