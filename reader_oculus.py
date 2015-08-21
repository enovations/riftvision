import socket
import time
from time import sleep
from ovrsdk import *

ovr_Initialize()
hmd = ovrHmd_Create(0)
hmdDesc = ovrHmdDesc()
ovrHmd_GetDesc(hmd, byref(hmdDesc))
print hmdDesc.ProductName
ovrHmd_StartSensor( \
hmd, 
  ovrSensorCap_Orientation | 
  ovrSensorCap_YawCorrection, 
  0
)

UDP_IP = "127.0.0.1"
#UDP_IP = "192.168.5.1"
UDP_PORT = 1234
sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

interval = 1000 / 20
updates = 0
delta = 0
start_time = int(round(time.time() * 1000))
timer = int(round(time.time() * 1000))

updates = 0

while 1:
  now = int(round(time.time() * 1000)) - start_time
  delta += now - start_time
  start_time = now

  while delta >= interval:
    ss = ovrHmd_GetSensorState(hmd, ovr_GetTimeInSeconds())
    pose = ss.Predicted.Pose
    MESSAGE = str(pose.Orientation.w) + ';' + str(pose.Orientation.x) + ';' + str(pose.Orientation.y) + ';' + str(pose.Orientation.z) + '\n\r'
    sock.sendto(MESSAGE, (UDP_IP, UDP_PORT))
    delta -= interval

vrHmd_Destroy(hmd)
ovr_Shutdown()
#I'm still having nightmares because of this.
