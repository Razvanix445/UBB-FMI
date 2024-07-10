/*
Un client trimite unui server doua siruri de numere. 
Serverul va returna clientului sirul de numere comune celor doua siruri primite.
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <arpa/inet.h>

#define PORT 53298
#define MAX_BUF_SIZE 1024

int main() {
    int s;
    struct sockaddr_in server, client;
    socklen_t addrLen = sizeof(client);
    char buf1[MAX_BUF_SIZE], buf2[MAX_BUF_SIZE], common_numbers[MAX_BUF_SIZE];
    int slen = sizeof(client);

    s = socket(AF_INET, SOCK_DGRAM, 0);
    if (s < 0) {
        perror("Eroare la crearea socket-ului\n");
        exit(1);
    }

    memset(&server, 0, sizeof(server));
    server.sin_family = AF_INET;
    server.sin_port = htons(PORT);
    server.sin_addr.s_addr = INADDR_ANY;

    if (bind(s, (struct sockaddr *)&server, sizeof(server)) == -1) {
        perror("Eroare la bind...\n");
        close(s);
        exit(1);
    }

    printf("Serverul asteapta clienti...\n");

    while (1) {
        memset(buf1, 0, sizeof(buf1));
        memset(buf2, 0, sizeof(buf2));
        memset(common_numbers, 0, sizeof(common_numbers));

        recvfrom(s, buf1, sizeof(buf1), 0, (struct sockaddr *) &client, &slen);
        recvfrom(s, buf2, sizeof(buf2), 0, (struct sockaddr *) &client, &slen);

        // Parse the received strings into arrays of integers
        int nums1[MAX_BUF_SIZE], nums2[MAX_BUF_SIZE];
        int common_nums[MAX_BUF_SIZE];
        int count = 0;

        char *token = strtok(buf1, " ");
        while (token != NULL) {
            nums1[count] = atoi(token);
            token = strtok(NULL, " ");
            count++;
        }

        count = 0;
        token = strtok(buf2, " ");
        while (token != NULL) {
            nums2[count] = atoi(token);
            token = strtok(NULL, " ");
            count++;
        }

        // Find the common numbers
        int common_count = 0;
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
                if (nums1[i] == nums2[j]) {
                    common_nums[common_count] = nums1[i];
                    common_count++;
                    break;
                }
            }
        }

        // Build the common numbers string
        strcpy(common_numbers, "");
        for (int i = 0; i < common_count; i++) {
            char num_str[10];
            sprintf(num_str, "%d", common_nums[i]);
            strcat(common_numbers, num_str);
            if (i < common_count - 1) {
                strcat(common_numbers, " ");
            }
        }

        sendto(s, common_numbers, strlen(common_numbers), 0, (struct sockaddr *) &client, slen);
    }

    close(s);

    return 0;
}
