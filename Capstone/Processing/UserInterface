import processing.serial.*; 
import controlP5.*;

ControlP5 cp5;
Serial myPort;

PImage logo;
PImage heatIcon;
PFont font;
PFont titleFont;

boolean firstContact = false;
boolean submitFlag = false;

int heaterStatus = 0;
int sunShadePos = 0;
int motor55Pos = 0;
int motor66Pos = 0;

String data;

String sensorID55 = "";
String roomTemp55 = "";
String setTemp55 = "15";
String sendTemp55;


String sensorID66 = "";
String roomTemp66 = "";
String setTemp66 = "";
String sendTemp66;

String sendTemp = "0";
String sensorValue = "";



void setup() {
  myPort = new Serial(this, "COM6", 9600);
  myPort.bufferUntil('\n'); 
  
  cp5 = new ControlP5(this);
  size (1130, 560);
  
  logo = loadImage("heating-cooling.png");
  heatIcon = loadImage("rsz_rtnka4jxc[1].png");
  titleFont = loadFont("AgencyFB-Reg-100.vlw");
  font = loadFont("Calibri-60.vlw");
  textFont(font, 60);  
  
  cp5.addTextfield("Set Upstairs").setPosition(1030, 155).setSize(60, 20).setAutoClear(true);
  cp5.addTextfield("Set Downstairs").setPosition(1030, 205).setSize(60, 20).setAutoClear(true);
  cp5.addBang("Submit").setPosition(1030, 255).setSize(60, 40);
}

void draw() {
  background(0,0,0);
  image(logo, 60, 15, 128, 115);
  textFont(titleFont, 80);
  fill(45, 98, 153);  
  text("Smart Climate ", 200, 100);
  fill(209, 35, 42);
  text("Control System", 580, 100);  
  
  textFont(font, 80);
  fill(221, 247, 27);
  textSize(60);
  text("Upstairs", 50, 185);
  text("Downstairs", 350, 185);
  text("Light Sensor", 680, 185);//710
  textSize(28);
  text("ID: " + sensorID55, 110, 220);
  text("ID: " + sensorID66, 450, 220);
  text(sensorValue, 800, 220);

  textSize(60);  
  fill(45, 98, 153);
  text("Room Temp", 15, 300); 
  text(roomTemp55 + "°C", 90, 350); 
  if (motor55Pos == 1)
    text("Closed", 83, 400);
  else if (motor55Pos == 2)
    text("Half", 100, 400);
  else if (motor55Pos == 3)
    text("Open", 100, 400);
  
  text("Room Temp", 340, 300);
  text(roomTemp66 + "°C", 420, 350);
  if (motor66Pos == 1)
    text("Closed", 413, 400);
  else if (motor66Pos == 2)
    text("Half", 430, 400);
  else if (motor66Pos == 3)
    text("Open", 430, 400);
  
  text("Sun Shade", 695, 300);
  if (sunShadePos == 1)
    text("Down", 750, 400);
  else if (sunShadePos == 0)
    text("Up", 790, 350);
  
  fill(209, 35, 42);
  text("Set Temp", 45, 480);
  text("Set Temp", 380, 480);
  text(setTemp55 + "°C", 110, 530);
  text(setTemp66 + "°C", 440, 530);
   
  text("Heater", 740, 480);
  if (heaterStatus == 1) {
    text("ON", 788, 530);
    image(heatIcon, 873, 491, 40, 40);
  }
  else if (heaterStatus == 0)  
    text("OFF", 778, 530);
  
  textSize(16);
  fill(255, 255, 255);
  text("Rajdeep Singh", 1000, 500);
  text("&", 1000, 520);
  text("Chris Moffat", 1000, 540);

}

void serialEvent(Serial myPort) {
  data = myPort.readStringUntil('\n');
  if (data != null) {
    data = trim(data);
    println(data);
  
    if (firstContact == false) {
      if (data.equals("A")) {
        myPort.clear();
        firstContact = true;
        myPort.write("A");
        println("Contact! Let's Go.");
      }
    }
    
    else { //if we've already established contact, keep getting and parsing data
      if (data.length() >= 20) {
        if (data.substring(0, 2).equals("55")) {
            sensorID55 = data.substring(0, 2);
            roomTemp55 = data.substring(3, 7);
            setTemp55 = data.substring(8, 10);
            motor55Pos = Integer.parseInt(data.substring(13, 14));
          }
         else if (data.substring(0, 2).equals("66")) {
            sensorID66 = data.substring(0, 2);
            roomTemp66 = data.substring(3, 7);
            setTemp66 = data.substring(8, 10);
            motor66Pos = Integer.parseInt(data.substring(13, 14));
         }
        heaterStatus = Integer.parseInt(data.substring(11, 12));
        sunShadePos = Integer.parseInt(data.substring(15, 16));
        sensorValue = data.substring(17, data.length());
      }    
      if (submitFlag == true) {                           
        myPort.write("11" + sendTemp55 + sendTemp66);
        println("11" + sendTemp55 + sendTemp66);
        submitFlag = false;
      }
    }
  }
}

void Submit() {
  sendTemp55 = cp5.get(Textfield.class,"Set Upstairs").getText();
  if (sendTemp55.length() == 0) //Should work for 2nd temp only.
    sendTemp55 = setTemp55;
  sendTemp66 = cp5.get(Textfield.class,"Set Downstairs").getText();
  submitFlag = true;
}
