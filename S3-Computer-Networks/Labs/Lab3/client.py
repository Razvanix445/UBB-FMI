import socket
import sys

def main():
    server_ip = "127.0.0.1"  # Replace with your server's IP address
    server_port = 53298  # Replace with your server's port

    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    try:
        client_socket.connect((server_ip, server_port))
        print("Connected to server")

        text = input("Enter the string: ")
        character = input("Enter the character to search: ")

        client_socket.send(text.encode())
        client_socket.send(character.encode())

        num_positions = int(client_socket.recv(1024).decode())

        if num_positions > 0:
            positions = [int(client_socket.recv(1024).decode()) for _ in range(num_positions)]
            print(f"Character '{character}' is found at positions: {positions}")
        else:
            print(f"Character '{character}' was not found in the string.")

    except ConnectionRefusedError:
        print("Connection to the server failed. Make sure the server is running.")
    except Exception as e:
        print(f"An error occurred: {e}")

    client_socket.close()

if __name__ == "__main__":
    main()
