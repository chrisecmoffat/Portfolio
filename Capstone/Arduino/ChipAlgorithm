#include <SoftwareSerial.h>
#include <Stepper.h>

const int stepsPerRevolution = 6400;

//Setup pins for Xbee sheild and motor drivers
SoftwareSerial XBee(2, 3);
Stepper sunShade(stepsPerRevolution, 10, 11);
Stepper upStairs(stepsPerRevolution, 4, 5);
Stepper downStairs(stepsPerRevolution, 8, 9);

int count = 1;

int readByte;
String data;

byte PID;

int analogReading;
int MSB;
int LSB;

float temp;
float temp55;
float temp66;
int setTemp55 = 15;
int setTemp66 = 15;

int lightSensor = 0;
int heaterBinary = 0;
int sunShadeBinary = 0;
int upStairsMotorBinary = 3;
int downStairsMotorBinary = 3;

String sunShadePos = "UP";
String heaterStatus = "OFF";
String upStairsMotorPosition = "OPEN";
String downStairsMotorPosition = "OPEN";

void setup() {
  XBee.begin(9600);
  Serial.begin(9600);
  upStairs.setSpeed(60);
  downStairs.setSpeed(60);
  sunShade.setSpeed(60);
  pinMode(13, OUTPUT);
  establishContact();
}

void loop() {
	 	
	//Keeping light sensor data at 3 digits for Processing	
	lightSensor = analogRead(A5);
	if (lightSensor < 100)
		lightSensor = 100;

  //Reading serial input from Processing
  while (Serial.available() >= 1) { 
    data = Serial.readString();
	  //First 2 digits are 11, that will match Processing output	
      if (data.substring(0, 2) == "11") {
        if (data.length() == 4) //Checks if 1 temperature is sent
          setTemp55 = data.substring(2, 4).toInt();
        else if (data.length() == 6) { //Checks if 2 temperatures are sent
          setTemp55 = data.substring(2, 4).toInt();
          setTemp66 = data.substring(4, 6).toInt();
      }
    }
  }

  //Read all incoming bytes from Xbee
  //Counter implemented to throw away unwanted bytes
  //Only looking for PID and MSB, LSB
  if (XBee.available()) {
    readByte = XBee.read();
  if (readByte == 0x7E)
    count = 1;
  count++;
  if (count == 6)
    PID = readByte;
  if (count == 13)
    MSB = readByte;
  if (count == 14)
    LSB = readByte;
  if (count == 15) {
	//Combining LSB and MSB bits into one variable
    analogReading = LSB + (MSB * 256);
    temp = (analogReading - 500);
    temp = temp / 10;
    
	//Organizing data, sending out on Serial so Processing can pickup incoming data from Arduino
    if (PID == 0x55) {
      temp55 = temp;
      Serial.print(PID, HEX);
      Serial.print(",");
      Serial.print(temp55, 1);
      Serial.print(",");
      Serial.print(setTemp55, DEC);
      Serial.print(",");
      Serial.print(heaterBinary);
      Serial.print(",");
      Serial.print(upStairsMotorBinary);
      Serial.print(",");
      Serial.print(sunShadeBinary);
      Serial.print(",");
      Serial.println(lightSensor);
    }
    else if (PID == 0x66) {
      temp66 = temp;
      Serial.print(PID, HEX);
      Serial.print(",");
      Serial.print(temp66, 1);
      Serial.print(",");
      Serial.print(setTemp66, DEC);
      Serial.print(",");
      Serial.print(heaterBinary);
      Serial.print(",");
      Serial.print(downStairsMotorBinary);
      Serial.print(",");
      Serial.print(sunShadeBinary);
      Serial.print(",");
      Serial.println(lightSensor);
    }
  }  
 }

  //Heater On/Off
  if ((temp55 + 0.5) < setTemp55 || (temp66 + 0.5) < setTemp66) {
    heaterStatus = "ON";
    heaterBinary = 1;
    digitalWrite(13, HIGH);
  }

  else if (temp55 >= setTemp55 && temp66 >= setTemp66) {
    heaterStatus = "OFF";
    heaterBinary = 0;
    digitalWrite(13, LOW);
  }

  //Light Sensor algorithm
  if (lightSensor > 500 && sunShadePos == "UP") {
    sunShadePos = "DOWN";
    sunShadeBinary = 1;
    sunShade.step(-stepsPerRevolution * 2);
  }
  
  else if (lightSensor <= 500 && sunShadePos == "DOWN") {
    sunShadePos = "UP";
    sunShadeBinary = 0;
    sunShade.step(stepsPerRevolution * 2);
  }

  //upStairs motor alogrithm
  if (setTemp55 - temp55 <= 0.5) { //FULL CLOSE
    if(upStairsMotorPosition == "OPEN") {
      upStairsMotorPosition = "CLOSED";
      upStairsMotorBinary = 1; 
      upStairs.step(-stepsPerRevolution/4);
    }
    else if(upStairsMotorPosition == "HALF") {
      upStairsMotorPosition = "CLOSED";
      upStairsMotorBinary = 1;
      upStairs.step(-stepsPerRevolution/8);
    }
  }

  else if (setTemp55 - temp55 > 3) { //FULL OPEN
    if (upStairsMotorPosition == "CLOSED") {
      upStairsMotorPosition = "OPEN";
      upStairsMotorBinary = 3;
      upStairs.step(stepsPerRevolution/4);
    }
    else if(upStairsMotorPosition == "HALF") {
      upStairsMotorPosition = "OPEN";
      upStairsMotorBinary = 3;
      upStairs.step(stepsPerRevolution/8);
    }
  }

  else if (setTemp55 - temp55 > 0.5) { //HALF
    if (upStairsMotorPosition == "CLOSED") {
      upStairsMotorPosition = "HALF";
      upStairsMotorBinary = 2;
      upStairs.step(stepsPerRevolution/8);
      }
   else if(upStairsMotorPosition == "OPEN") {
      upStairsMotorPosition = "HALF";
      upStairsMotorBinary = 2;
      upStairs.step(-stepsPerRevolution/8);
      }
  }

  //downStairs motor alogrithm
  if (setTemp66 - temp66 <= 0.5) { //FULL CLOSE
    if(downStairsMotorPosition == "OPEN") {
      downStairsMotorPosition = "CLOSED";
      downStairsMotorBinary = 1;
      downStairs.step(stepsPerRevolution/4);
    }
    else if(downStairsMotorPosition == "HALF") {
      downStairsMotorPosition = "CLOSED";
      downStairsMotorBinary = 1;
      downStairs.step(stepsPerRevolution/8);
    }
  }

  else if (setTemp66 - temp66 > 3) { //FULL OPEN
    if (downStairsMotorPosition == "CLOSED") {
      downStairsMotorPosition = "OPEN";
      downStairsMotorBinary = 3;
      downStairs.step(-stepsPerRevolution/4);
    }
    else if(downStairsMotorPosition == "HALF") {
      downStairsMotorPosition = "OPEN";
      downStairsMotorBinary = 3;
      downStairs.step(-stepsPerRevolution/8);
    }
  }

  else if (setTemp66 - temp66 > 0.5) { //HALF
    if (downStairsMotorPosition == "CLOSED") {
      downStairsMotorPosition = "HALF";
      downStairsMotorBinary = 2;
      downStairs.step(-stepsPerRevolution/8);
      }
    else if(downStairsMotorPosition == "OPEN") {
      downStairsMotorPosition = "HALF";
      downStairsMotorBinary = 2;
      downStairs.step(stepsPerRevolution/8);
      }
  }
}

//Handshake for Processing
void establishContact() {
  while (Serial.available() <= 0) {
    Serial.println("A");
    delay(300);
  }
}

