/*
Un client trimite unui server doua siruri de numere. 
Serverul va returna clientului sirul de numere comune celor doua siruri primite.
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
  int sir1[1024], sir2[1024];
  int lungimeSir1, lungimeSir2;

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

  printf("Cate numere are primul sir? ");
  scanf("%d", &lungimeSir1);
  printf("Introduceti primul sir de caractere: ");
  for (int i = 0; i < lungimeSir1; i ++)
    scanf("%d", &sir1[i]);
  printf("Cate numere are al doilea sir? ");
  scanf("%d", &lungimeSir2);
  printf("Introduceti al doilea sir de caractere: ");
  for (int i = 0; i < lungimeSir2; i ++)
    scanf("%d", &sir2[i]);
  
  lungimeSir1 = htonl(lungimeSir1);
  send(c, &lungimeSir1, sizeof(int), 0);
  lungimeSir1 = ntohl(lungimeSir1);
  send(c, sir1, lungimeSir1 * sizeof(int), 0);
  lungimeSir2 = htonl(lungimeSir2);
  send(c, &lungimeSir2, sizeof(int), 0);
  lungimeSir2 = ntohl(lungimeSir2);
  send(c, sir2, lungimeSir2 * sizeof(int), 0);

  int lungimeSirRezultat;
  char sirRezultat[2048];
  recv(c, &lungimeSirRezultat, sizeof(int), MSG_WAITALL);
  lungimeSirRezultat = ntohl(lungimeSirRezultat);
  printf("Am primit lungimea: %d\n", lungimeSirRezultat);
  //recv(c, sirRezultat, lungimeSirRezultat * sizeof(int), MSG_WAITALL);
  for (int i = 0 ; i < lungimeSirRezultat; i ++)
    recv(c, &sirRezultat[i], sizeof(int), MSG_WAITALL);
  //sirRezultat[lungimeSirRezultat] = '\0';
  printf("Sirul: \n");
  for (int i = 0; i < lungimeSirRezultat; i ++)
    printf("%d ", sirRezultat[i]);

  close(c);
}