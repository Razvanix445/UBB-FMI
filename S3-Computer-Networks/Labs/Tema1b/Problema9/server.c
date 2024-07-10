/*
Un client trimite unui server doua siruri de numere. 
Serverul va returna clientului sirul de numere care se regaseste in primul sir dar nu se regasesc in al doilea.
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
        int sir1[1024], sir2[1024], sirRezultat[2048];
        int lungimeSir1, lungimeSir2, lungimeSirRezultat;
        recv(c, &lungimeSir1, sizeof(int), MSG_WAITALL);
        lungimeSir1 = ntohl(lungimeSir1);
        printf("Am primit lungimea sirului 1: %d\n", lungimeSir1);
        recv(c, sir1, lungimeSir1 * sizeof(int), MSG_WAITALL);

        recv(c, &lungimeSir2, sizeof(int), MSG_WAITALL);
        lungimeSir2 = ntohl(lungimeSir2);
        printf("Am primit lungimea sirului 2: %d\n", lungimeSir2);
        recv(c, sir2, lungimeSir2 * sizeof(int), MSG_WAITALL);

        lungimeSirRezultat = 0;
        int exista = 0;
        for (int i = 0; i < lungimeSir1; i ++) {
            exista = 0;
            for (int j = 0; j < lungimeSir2; j ++)
                if (sir1[i] == sir2[j])
                    exista = 1;
            if (!exista)
                sirRezultat[lungimeSirRezultat++] = sir1[i];
        }

        printf("Lungimea sirului rezultat este: %d\n", lungimeSirRezultat);
        lungimeSirRezultat = htonl(lungimeSirRezultat);
        send(c, &lungimeSirRezultat, sizeof(int), 0);
        printf("Am trimis lungimea: %d\n", lungimeSirRezultat);
        lungimeSirRezultat = ntohl(lungimeSirRezultat);
        for (int i = 0; i < lungimeSirRezultat; i ++)
            send(c, &sirRezultat[i], sizeof(int), 0);
        //send(c, sirRezultat, lungimeSirRezultat * sizeof(int), 0);
        printf("Sirul: \n");
        for (int i = 0; i < lungimeSirRezultat; i ++)
            printf("%d ", sirRezultat[i]);

        close(c);
        return 0;
    }
  }
}