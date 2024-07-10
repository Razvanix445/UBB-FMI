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
      int pozitii[1024], numarPozitii = 0;
      int lenSir;
      recv(c, &lenSir, sizeof(lenSir), MSG_WAITALL);
      lenSir = ntohl(lenSir);
      //recv(c, sirDeCaractere, lenSir, MSG_WAITALL);
      recv(c, sirDeCaractere, lenSir * sizeof(char), MSG_WAITALL);
        //recv(c, &sirDeCaractere[i], sizeof(char), MSG_WAITALL);
      caracter = sirDeCaractere[lenSir - 1];
      //recv(c, &caracter, sizeof(char), MSG_WAITALL);

      printf("LungimeSir: %d\n", lenSir);
      printf("Sirul de caractere: %s\n", sirDeCaractere);
      printf("Caracterul: %c\n\n", caracter);

      for (int i = 0; i < lenSir - 1; i++) {
      	if (sirDeCaractere[i] == caracter) {
          printf("Gasit egalitate: sirDeCaractere[i]: %c, caracter: %c\n", sirDeCaractere[i], caracter);
          pozitii[numarPozitii] = i;
        	printf("numarPozitii: %d\n\n", numarPozitii);
		      numarPozitii++;
      	}
        else {
          printf("Negasit: sirDeCaractere[i]: %c, caracter: %c\n\n", sirDeCaractere[i], caracter);
        }
    	}

      printf("NumarPozitii dupa calculare: %d\n", numarPozitii);
	    numarPozitii = htonl(numarPozitii);
	    printf("Am trimis numarPozitii: %d la client\n", numarPozitii);
    	send(c, &numarPozitii, sizeof(int), 0);
	    numarPozitii = ntohl(numarPozitii);

      for (int i = 0; i < numarPozitii; i++) {
        pozitii[i] = htonl(pozitii[i]);
        send(c, &pozitii[i], sizeof(int), 0);
      }

	    close(c);
	    return 0;
    }
  }
}