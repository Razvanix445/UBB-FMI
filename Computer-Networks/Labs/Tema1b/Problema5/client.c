/*
Un client trimite unui server un numar. Serverul va returna clientului sirul divizorilor acestui numar.
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
 int numar;

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

  printf("Introduceti numarul: ");
  scanf("%d", &numar);

  numar = htonl(numar);
  send(c, &numar, sizeof(int), 0);
  
  int sumaDivizori;
  recv(c, &sumaDivizori, sizeof(int), MSG_WAITALL);
  sumaDivizori = ntohl(sumaDivizori);
  printf("Suma divizorilor este: %d\n", sumaDivizori);

  close(c);
}