# Corona Busan

## 개요

![CoronaBusan](https://user-images.githubusercontent.com/57826388/82758378-347df200-9e21-11ea-8340-e52c3510ce44.png)

본 프로젝트는 부산광역시 시민들을 위한 부산시 코로나19 알림 애플리케이션입니다.  
부산광역시 시민들이 누구나 쉽고 빠르게 코로나19 관련 소식을 접할 수 있고, 대처할 수 있도록 하는 목적으로 개발을 진행하게 되었습니다.  
본 프로젝트는 부산광역시 시민들을 위한 재능기부 형식으로 프로그램을 개발하여 광고없이 배포할 예정입니다.

또한, `Wimmy` 개발 이후 지금까지 추가적으로 학습하고 실습하며 배운 기술들을 직접적으로 활용해보고자 개발을 진행하게 되었습니다.  
따라서 Mvvm 아키텍처 스타일을 최대한 위반하지않도록 개발하였습니다.

<br>

- 개발 환경: Android Studio 3.5/3.6
- 개발 언어: Kotlin, XML
- 기타 환경: Git
- 외부 자원: Google Map API, Open Api
- 관련 기술: Mvvm, LiveData, ViewModel, Retrofit, RestApi, RxJava, Databinding, Jsoup, FCM

<br>

본 프로그램은 코로나19 관련 기사, 현황판, 대처법, 지도 서비스로 크게 나뉘어집니다.

<br>

## 개발 일지

- 2020\. 05. 22: 프로젝트 계획 수립

- 2020\. 05. 23: MVVM 아키텍처 구조 구현

- ~ 2020\. 05. 26: News Search 기능 구현
  - Retrofit + RxJava + Databinding

- ~ 2020\. 05. 28: 현황판, 대처법 기능 추가(현황판 - Jsoup 크롤링)

- ~ 2020\. 06. 01: 1차 배포버전 개발 완료

<br>

## 애플리케이션 소개

### 부산광역시 코로나19에 관련된 종합 알리미 애플리케이션

안녕하세요!! 부산광역시 코로나19 전용 앱이 출시되었어요.

'코로나19 부산' 은 부산광역시 시민들을 위한 재능기부 형식으로 제작 되었어요.

따라서 일체의 광고를 포함하지 않아요.


- 부산, 국내, 해외별 코로나 관련 기사를 쉽고 빠르게 접할 수 있어요! 
- 해당 기사를 누르면 전체 기사를 보여줘요.

- 상황판을 통해 부산, 국내, 전세계 별 확진자 현황을 실시간으로 확인할 수 있어요!

- 부산시 확진자 목록을 간편하게 확인할 수 있어요!

- 알림을 설정하여 부산광역시 공식 코로나 현황이 발표되는 시간에 알림으로 받아볼 수 있어요! (물론 거부도 가능해요) 

- 예방 및 대처법을 영상과 사진을 통해 쉽고 간편하게 확인할 수 있어요!

- 긴급한 상황의 경우, 질병관리본부, 부산 콜센터, 지역 보건소로 신속하게 전화할 수 있어요!

- 마스크 맵을 통해 현재 위치를 기준으로 4km 반경 내의 마스크 판매처를 조사하여 재고를 확인할 수 있어요!

- 지도의 마커 색상은 마스크의 재고 수량 기준입니다.

초록 - 100개 이상 
노랑 - 30개 이상
빨강 - 2개 이상
회색 - 1개 이하
검정 - 알 수 없음 


코로나 맵 또한 기능으로 개발하려고 했으나, 정부 정책으로 인하여 마스크 맵으로 대체하여 개발하였습니다!

사용중, 문제점이나 불편한 오류가 발생한다면 언제든지 말해주세요!

필요한 기능이 있어도 의견 주시면 적극적으로 수용하여 반영하도록 하겠습니다.

<br>

### 기능

#### **관련 기사 검색**

![image](https://user-images.githubusercontent.com/57826388/83366695-bf478980-a3eb-11ea-8c5c-d053e864a0d2.png)

<br>

#### **현황판**

![image](https://user-images.githubusercontent.com/57826388/83366697-c2db1080-a3eb-11ea-8bb6-d124c6c6683e.png)

<br>

#### **예방 및 대처법**

![image](https://user-images.githubusercontent.com/57826388/83366700-c7072e00-a3eb-11ea-9359-94a7cf022046.png)

<br>

#### **코로나 마스크 맵**

![image](https://user-images.githubusercontent.com/57826388/83366710-d0909600-a3eb-11ea-89cc-2365a653979e.png)

![image](https://user-images.githubusercontent.com/57826388/83366702-ca021e80-a3eb-11ea-8b08-61fe72294513.png)

<br>

#### **확진자 동선**

![image](https://user-images.githubusercontent.com/57826388/83366717-d4241d00-a3eb-11ea-8384-c30b5b4db6c1.png)

<br>

#### **지역 보건소 검색**

![image](https://user-images.githubusercontent.com/57826388/83366722-d71f0d80-a3eb-11ea-8b60-29075b0f588e.png)

<br>

#### **푸시 알림**

![image](https://user-images.githubusercontent.com/57826388/83366725-da19fe00-a3eb-11ea-813e-3e69b240cdbc.png)

![image](https://user-images.githubusercontent.com/57826388/83366728-dd14ee80-a3eb-11ea-8a1a-bf2eac5a1089.png)







