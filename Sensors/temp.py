import socket
import time
from time import sleep

UDP_IP = "127.0.0.1"
UDP_PORT = 1234
sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

while 1:
  MESSAGE = "mami\n"
  sock.sendto(MESSAGE, (UDP_IP, UDP_PORT))
  time.sleep(0.1)

#I'm still having nightmares because of this.