#include <BearSSLHelpers.h>
#include <CertStoreBearSSL.h>
#include <ESP8266WiFi.h>
#include <ESP8266WiFiAP.h>
#include <ESP8266WiFiGeneric.h>
#include <ESP8266WiFiGratuitous.h>
#include <ESP8266WiFiMulti.h>
#include <ESP8266WiFiScan.h>
#include <ESP8266WiFiSTA.h>
#include <ESP8266WiFiType.h>
#include <WiFiClient.h>
#include <WiFiClientSecure.h>
#include <WiFiClientSecureBearSSL.h>
#include <WiFiServer.h>
#include <WiFiServerSecure.h>
#include <WiFiServerSecureBearSSL.h>
#include <WiFiUdp.h>

#include <SPI.h>                               //RFID 헤더
#include <MFRC522.h>                           //RFID 헤더
#include <Adafruit_NeoPixel.h>                 //네오픽셀 헤더
 
#define RST_PIN   D0                           
#define SS_PIN    D8                           
                                               
#define led D1                                 //data out이 연결된 핀 번호
#define NUM_LEDS 12                            //네오픽셀 LED 갯수
#define BRIGHTNESS 255                         //네오픽셀 밝기   
Adafruit_NeoPixel strip = Adafruit_NeoPixel(NUM_LEDS, led, NEO_GRB + NEO_KHZ800); //네오픽셀 설정

int tactSwitch = D2;                           //스위치 핀번호  
int cnt = 0;                                   //스위치 카운트 값

int buzzer = D3;                               //부저 핀번호
int noteDuration = 500;                        //소리의 지속시간
int note_c5 = 262;                             //도에 해당하는 주파수 설정
int note_e4 = 330;                             //미에 해당하는 주파수 설정
int note_g4 = 392;                             //솔에 해당하는 주파수 설정


boolean current_switch = LOW;                  //현재 스위치 값
boolean last_switch = HIGH;                    //이전 스위치 값 
boolean red_on = false;                        //빨간색 on/off 상태

uint32_t red = strip.Color(255, 0, 0);         //네오픽셀 빨간색
uint32_t green = strip.Color(0, 255, 0);       //네오픽셀 초록색

MFRC522 mfrc(SS_PIN, RST_PIN);                 //MFR522를 이용하기 위해 mfrc객체를 생성해 줍니다.

const char *ssid = "Galaxy";                   //와이파이 ID
const char *pwd = "mwsg5047";                  //와이파이 PW
const char *serverid = "3.23.221.118";

void setup(){
  Serial.begin(9600);
  delay(10);

  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid,pwd);                        //와이파이 연결

  while(WiFi.status() != WL_CONNECTED)
  {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.println("WIFI CONNECTED");
  Serial.println(WiFi.localIP());
  Serial.println("connection Start");

  strip.setBrightness(BRIGHTNESS);        
  strip.begin();                          
  strip.show();                                //네오픽셀 초기화  
  pinMode(tactSwitch, INPUT);
  

  SPI.begin();                                 // SPI 초기화
  mfrc.PCD_Init();
         
  pinMode(buzzer,OUTPUT);
}

void loop (){
  current_switch = digitalRead(tactSwitch);

  if(last_switch == LOW && current_switch == HIGH){
    red_on=!red_on;
    cnt ++;
    }

  last_switch = current_switch;

  if(red_on){
       for(int i= 0; i<NUM_LEDS; i++){
          strip.setPixelColor(i, red);
          strip.show();
      }
    }
  if(cnt == 2){
      for(int i= 0; i<NUM_LEDS; i++){
          strip.setPixelColor(i, green);
          strip.show();
      }
     delay(3000);
     strip.clear();
     strip.show();
     cnt = 0;
    }
  
  WiFiClient client ;
  
   if (!client.connect("3.23.221.118", 7579)) {
    Serial.println("connection failed");//서버 접속에 실패
    return;
  }
  else{

    //서버로 보낼 정보를 구성해 보내고 받는다.

     char *id_check = "0";

     if ( !mfrc.PICC_IsNewCardPresent() || !mfrc.PICC_ReadCardSerial() ) {   
       delay(500);                                // 태그 접촉이 되지 않았을때 또는 ID가 읽혀지지 않았을때 0.5초 딜레이 
       return;                                    // return
     } 
    
     Serial.print("Card UID:");                   // 태그의 ID출력
  
     for (byte i = 0; i < 4; i++) {               // 태그의 ID출력하는 반복문.태그의 ID사이즈(4)까지
       Serial.print(mfrc.uid.uidByte[i]);         // mfrc.uid.uidByte[0] ~ mfrc.uid.uidByte[3]까지 출력
       Serial.print(" ");
       id_check = "1";
     }
     Serial.println();

     client.write("POST /Mobius/CART_A/RFID HTTP/1.1\r\nX-M2M-Origin: S\r\nX-M2M-RI: RFID\r\nContent-Type: ");
     client.write("application/json;ty=4\r\nhost: 3.23.221.118:7579\r\naccept: application/json\r\ncontent-length: 23\r\nConnection: close\r\n\r\n{\"m2m:cin\":{\"con\":\"");
     client.write(id_check);
     client.write("\"}}");
     
     String recevbline = client.readStringUntil('\r');
     Serial.println(recevbline);

     if(id_check == "1"){
           digitalWrite(buzzer, HIGH);

           tone(buzzer, note_g4, noteDuration); //스피커 센서에 솔 출력
           delay(500);                          
           
           tone(buzzer, note_e4, noteDuration); //스피커 센서에 미 출력
           delay(500);

           tone(buzzer, note_c5, noteDuration); //스피커 센서에 도 출력
           delay(500);
           digitalWrite(buzzer,LOW);
     }
  }


  delay(5000);
}
