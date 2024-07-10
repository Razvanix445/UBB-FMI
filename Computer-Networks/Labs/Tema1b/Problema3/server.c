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
        char sirDeCaractere[1024], sirOglindit[1024];
        int lungimeSir;
        recv(c, &lungimeSir, sizeof(int), MSG_WAITALL);
        lungimeSir = ntohl(lungimeSir);
        recv(c, sirDeCaractere, lungimeSir * sizeof(char), MSG_WAITALL);

        for (int i = 0; i < lungimeSir; i ++) {
            sirOglindit[i] = sirDeCaractere[lungimeSir - i - 1];
        }

        int lungimeOglindit = strlen(sirOglindit);
        lungimeOglindit = htonl(lungimeOglindit);
        send(c, &lungimeOglindit, sizeof(int), 0);
        lungimeOglindit = ntohl(lungimeOglindit);
        send(c, sirOglindit, lungimeOglindit * sizeof(char), 0);

        close(c);
        return 0;
    }
  }
}