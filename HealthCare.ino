//디스플레이 라이브러리
#include "U8glib.h"
#include <SoftwareSerial.h>

//U8GLIB 설정변수
U8GLIB_SSD1306_128X64 u8g(U8G_I2C_OPT_NONE);

//핀 설정
int pulsePin = 0;                 // 심박센서  이날로그 0핀
int blinkPin = 13;                // 아두이노 LED 디지털 13핀
int fadePin = 5;                  // //
int fadeRate = 0;                 // //
SoftwareSerial BTSerial(2, 3); // 블루투스 커넥터 TX,RX 디지털 2,3핀

// Volatile Variables, used in the interrupt service routine!
volatile int BPM;                   // 심박수
volatile int Signal;                // 아날로그 0핀으로 들어오는 데이터
volatile int IBI = 600;             // 심박수 사이의 시간 간격을 유지하는 변수
volatile boolean Pulse = false;     // 심박센서 접근 확인변수
volatile boolean QS = false;        // 심박센서 아두이노 인식 확인 변수

//아두이노 시리얼 변수
static boolean serialVisual = false;   // 측정한 데이터를 시리얼에 뿌려주는 변수.

//블루투스 통신 카운트 변수
int count =0;

void setup(){
  pinMode(blinkPin,OUTPUT);         // 아두이노 led핀 OUTPUT 설정
  pinMode(fadePin,OUTPUT);          // fadepin 설정
  Serial.begin(115200);             // 아두이노 속도 설정
  BTSerial.begin(115200);  // 블루투스 시리얼 속도 설정
  interruptSetup();                 // 아날로그신호 BPM연산 프로시저
}

void loop(){

  if(count==300000 ||BTSerial.read("check")){
      serialOutput() ;       
      
      if (QS == true){     // 심박수 데이터 확인
                        
            fadeRate = 255;         // LED Fade효과 발생
                                   
            serialOutputWhenBeatHappens();   //시리얼에 BPM출력 함수 
            QS = false;                      // 다음신호를 받기위해서 리셋.
      }
       
    ledFadeToBeat();                      // Makes the LED Fade Effect Happen 
    count=0;
  }
  delay(100);                             //  take a break
}




//아두이노 LED의 박동 표시
void ledFadeToBeat(){
    fadeRate -= 15;                        
    fadeRate = constrain(fadeRate,0,255);   
    analogWrite(fadePin,fadeRate);          
  }




