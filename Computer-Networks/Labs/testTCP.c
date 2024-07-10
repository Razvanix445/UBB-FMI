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
    uint16_t numar, lungime;

    c = socket(AF_INET, SOCK_STREAM, 0);
    if (c < 0) {
        printf("Eroare la crearea socket-ului client!\n");
        return 1;
    }

    memset(&server, 0, sizeof(server));
    server.sin_port = htons(atoi(argv[1]));
    server.sin_family = AF_INET;
    server.sin_addr.s_addr = inet_addr(argv[2]);

    if (connect(c, (struct sockaddr *) &server, sizeof(server)) < 0) {
        printf("Eroare la conectarea la server!\n");
        return 1;
    }

    numar = 3298;
    numar = htons(numar);
    send(c, &numar, sizeof(uint16_t), 0);
    numar = ntohs(numar);
    printf("Am trimis numarul: %hd\n", numar);

    recv(c, &lungime, sizeof(uint16_t), MSG_WAITALL);
    lungime = ntohs(lungime);
    printf("Am primit lungimea: %hd\n", lungime);

    int lungimeSir = lungime;
    char sir[lungimeSir + 1];
    //recv(c, &sir, sizeof(char) * lungime, MSG_WAITALL);
    for (int i = 0; i < lungimeSir; i ++)
        recv(c, &sir[i], sizeof(char), MSG_WAITALL);
    printf("Am primit sirul: %s\n", sir);

    char sirCerinta[1024], ch = 'a';
    int k = 0;
    while (ch != '\0') {
        recv(c, &ch, sizeof(char), MSG_WAITALL);
        k = k + 1;
    }

    int lungimeSirCerinta = k - 1;
    //lungimeSirCerinta = htonl(lungimeSirCerinta);
    send(c, &lungimeSirCerinta, sizeof(int), 0);
    printf("Am trimis lungimea sirului din cerinta: %d\n", lungimeSirCerinta);

    close(c);
}
