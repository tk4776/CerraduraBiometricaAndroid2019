#include <Servo.h>
Servo servoMotor;
int pos = 0;
void setup() {
  // put your setup code here, to run once:  
  Serial.begin(38400);
  Serial.println("HABITACION CONECTADA");
  Serial.println("#");
  servoMotor.attach(9);
  servoMotor.write(0);
}

void loop() {
  // put your main code here, to run repeatedly:
  if(Serial.available()){
    char VarChar;
    VarChar=Serial.read();
    //---------------------------------
    if(VarChar=='1'){   //ABRIR
      for(pos=0; pos<=80; pos +=1)
      {
         servoMotor.write(pos);
         delay(10);
      }
      delay(10);
      Serial.println("ABIERTO");
      Serial.println("#");
      delay(5000);
      Serial.println("CERRANDO");
      Serial.println("#");
      servoMotor.write(0);
    }
    //---------------------------------
    if(VarChar=='0'){   //MOTORES APAGADOS
      servoMotor.write(0);
      Serial.println("DESCONECTADO");
      Serial.println("#");
    }
  }
}
