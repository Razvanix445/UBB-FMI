/*
Un client trimite unui server doua siruri de caractere. 
Serverul ii raspunde clientului cu caracterul care se regaseste de cele mai multe 
ori pe pozitii identice in cele doua siruri si cu numarul de aparitii ale acestui caracter.
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
        char sir1[1024], sir2[1024], caracter;
        int lungimeSir1, lungimeSir2, numarAparitii;

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

        int maxAparitii = 0;
        char maxChar = '\0';
        int lungimeMinima = lungimeSir1 < lungimeSir2 ? lungimeSir1 : lungimeSir2;
        for (int i = 0; i < lungimeSir1; i ++) {
            if (sir1[i] == sir2[i]) {
                int count = 1;
                for (int j = i + 1; j < lungimeMinima; j ++) {
                    if (sir1[j] == sir2[j] && sir1[j] == sir2[i])
                        count ++;
                }
                if (count > maxAparitii) {
                    maxAparitii = count;
                    maxChar = sir1[i];
                }
            }
        }

        numarAparitii = maxAparitii;
        caracter = maxChar;
        send(c, &caracter, sizeof(char), 0);
        printf("Am trimis caracterul: %c\n", caracter);
        numarAparitii = htonl(numarAparitii);
        send(c, &numarAparitii, sizeof(int), 0);
        numarAparitii = ntohl(numarAparitii);
        printf("Am trimis lungimea: %d\n", numarAparitii);

        close(c);
        return 0;
    }
  }
}