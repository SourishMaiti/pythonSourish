

get_ipython().system('pip install opencv-python')
get_ipython().system('pip install mediapipe')
get_ipython().system('pip install pycaw')

import cv2
import math
import mediapipe as mp
import numpy as np
from ctypes import cast, POINTER
from comtypes import CLSCTX_ALL
from pycaw.pycaw import AudioUtilities, IAudioEndpointVolume

devices = AudioUtilities.GetSpeakers()
interface = devices.Activate(
    IAudioEndpointVolume._iid_, CLSCTX_ALL, None)
volume = cast(interface, POINTER(IAudioEndpointVolume))

cap = cv2.VideoCapture(0)
mpHands=mp.solutions.hands
hands = mpHands.Hands()
mpDraw=mp.solutions.drawing_utils

while True :
    volume.SetMasterVolumeLevel(-20.0, None)
    success,img = cap.read()
    imgrgb=cv2.cvtColor(img,cv2.COLOR_BGR2RGB)
    results=hands.process(imgrgb)
   
    if results.multi_hand_landmarks:
        for hand in results.multi_hand_landmarks:
            l = []
            for id,lm in enumerate(hand.landmark):
                h,w,c=img.shape
                cx,cy=int(lm.x*w),int(lm.y*h)
                l.append([id,cx,cy])
        if l:
            x1,y1= l[4][1],l[4][2]
            x2,y2= l[8][1],l[8][2]
            cv2.circle(img,(x1,y1),5,(15,170,240),cv2.FILLED)
            cv2.circle(img,(x2,y2),10,(12,145,255),cv2.FILLED)
            cv2.line(img,(x1,y1),(x2,y2),(0,12,255),5)
            length = math.hypot(x2-x1,y2-y1)
            if length < 50:
                z1=(x1+x2)//2
                z2=(y1+y2)//2
                cv2.circle(img,(z1,z2),7,(255,0,7),cv2.FILLED)
        volRange= volume.GetVolumeRange()
        minVol = volRange[0]
        maxVol = volRange[1]
        vol = np.interp(length,[50,300],[minVol,maxVol])
        volume.SetMasterVolumeLevel(vol, None)
        volBar=np.interp(length,[50,300],[400,150])
        cv2.rectangle(img,(50,150),(85,400),(254,8,0),3)
        cv2.rectangle(img,(50,int(volBar)),(85,400),(0,124,234),cv2.FILLED)
        
    cv2.imshow("img",img)
    cv2.waitKey(1)
        
