/*
Un client trimite unui server un numar. Serverul va returna clientului sirul divizorilor acestui numar.
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
        int numar, suma;
        recv(c, &numar, sizeof(int), MSG_WAITALL);
        numar = ntohl(numar);
        
        for (int d = 1; d <= numar; d ++)
            if (numar % d == 0)
                suma += d;

        suma = htonl(suma);
        send(c, &suma, sizeof(int), 0);

        close(c);
        return 0;
    }
  }
}