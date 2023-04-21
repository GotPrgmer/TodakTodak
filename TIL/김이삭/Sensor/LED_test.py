import time
import paho.mqtt.client as mqtt
import ssl
import json
import _thread

import RPi.GPIO as GPIO

#GPIO Mode (BOARD / BCM)
GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM)
# GPIO.setup(18, GPIO.OUT)
GPIO.setup(18, GPIO.IN)

def on_connect(client, userdata, flags, rc): # The callback for when the client receives a CONNACK response from the server.
    print("Connected to AWS IoT: " + str(rc)) # rc is the error code returned when connecting to the broker

client = mqtt.Client()
client.on_connect = on_connect # Assign on_connect function to callback
client.tls_set(ca_certs='./rootCA.pem', certfile='./certificate.pem.crt', keyfile='./private.pem.key', tls_version=ssl.PROTOCOL_SSLv23) # Set the TLS configuration
client.tls_insecure_set(True) # Disable certificate validation
client.connect("aw9r86eiouxek-ats.iot.us-east-1.amazonaws.com", 8883, 60) # Connect to AWS IoT

def publishData(txt):
    while (True):
        try:
            print(txt)        
            
            # led_data = GPIO.input(18)
            client.publish("raspi/data", payload=json.dumps({"msg": 1}), qos=0, retain=False)
            
            time.sleep(0.5)
            
        except KeyboardInterrupt:
            pass
            print('KeyboardInterrupt')
            GPIO.cleanup()
            exit()
       
_thread.start_new_thread(publishData,("Spin-up new Thread...",))
client.loop_forever()
