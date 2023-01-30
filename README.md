# 편의점 추천 서비스 Convenience-Store-Recommendation
주소 기반 가까운 편의점 추천 및 길찾기/로드뷰 연결 제공 서비스

## 서비스 상세 기능 

해당 서비스로 주소 정보를 입력하여 요청하면 위치 기준에서 **_3km 내 가까운 편의점 5 곳을 추출_** 한다.

주소는 도로명 주소 또는 지번을 입력하여 요청 받는다.

정확한 주소를 입력 받기 위해 `Kakao 우편번호 서비스 사용` <br>
주소는 정확한 상세 주소(동, 호수)를 제외한 주소 정보를 이용하여 추천 한다.

ex) 서울 송파구 올림픽로 265 <br>
입력 받은 주소를 위도, 경도로 변환 하여 <br>
Kakao > 로컬 > [카테고리로 장소 검색하기] API를 호출해 추천할 곳 데이터를 반환한다.
--> 카테고리 코드: CS2(편의점), 반경(3km) 내, 최대 5곳 <br>

반환된 결과에 대해 `상세 URL`, `길안내 UR`L, `로드뷰 URL`로 제공 한다.

ex) <br>
길안내 URL : https://map.kakao.com/link/map/잠실역,37.402056,127.108212 <br>
로드뷰 URL : https://map.kakao.com/link/roadview/37.402056,127.108212 <br>
길안내 URL은 고객에게 제공 되기 때문에 가독성을 위해 `shorten url`로 제공 한다.
<br><br>
shorten url에 사용 되는 key값은 인코딩하여 제공 한다.
<br>
ex) http://localhost:8080/dir/nqxtX <br>
`base62`를 통한 인코딩


## 개발환경

* IntelliJ 2022.03
* Java 11
* Gradle 7.6
* Spring Boot 2.7.7

## 세부 기술 스택

Spring Boot

* Spring Web
* Spring Data JPA
* Lombok
* MariaDB Driver
* Spring Retry
* Handlebars

그외 

* Docker
* Redis
* Spock
* Testcontainers
* ByteBuddy
* MockWebServer
* Base62
* AWS EC2

## Feature List

* `Spring Data JPA`를 이용한 CRUD 메서드 구현
* `Spock Framework`를 이용한 테스트 코드 작성
* `Spock Framework` + `MockWebServer` 이용해 외부 API(ex. Kakao API) 호출 테스트 코드 작성
* `Testcontainers`를 이용하여 독립 테스트 환경 구축 : Redis/MariaDB
* 카카오 주소검색 API 연동하여 주소를 위도, 경도로 변환
* 카카오 로컬 >> '카테고리별 장소 추천 API' 사용해 추천 결과 반환
* 검색된 추천 결과 --> 카카오 지도 URL로 연동하여 제공
* `Handlebars`를 이용한 간단한 View 만들기
* `Docker` 사용한 다중 컨테이너 애플리케이션: `Docker-compose` 작성
* `Spring retry`를 이용한 재처리 구현 (카카오 API의 네트워크 오류 등에 대한 재처리)
* `base62`를 이용한 shorten url 개발하기 (길안내 URL)
* `Redis`를 이용한 성능 최적화: Redis 에 편의점 정보 데이터 캐싱
* `AWS EC2`를 이용해 애플리케이션 서버에 배포

## Results

* 메인 화면
<img width="80%" src="https://user-images.githubusercontent.com/55842092/215458039-88017920-7d4f-4e37-8370-09452473cc1a.png">

* 주소 검색 화면 
<img width="80%" src="https://user-images.githubusercontent.com/55842092/215458165-cabd0366-bd54-4137-a8f4-021156c1f34d.png">

* 최종 검색 결과 화면 
<img width="80%" src="https://user-images.githubusercontent.com/55842092/215461327-d7942599-c7c6-403c-84d3-d58705b88f36.png">

* 편의점 상세 화면 
<img width="80%" src="https://user-images.githubusercontent.com/55842092/215458401-34f0e536-921e-4bbf-8d4b-9a1299c865b2.png">

* 편의점 로드뷰 화면
<img width="80%" src="https://user-images.githubusercontent.com/55842092/215458566-32512247-872e-4e06-8cbb-de356ec774a3.png">

* 편의점 길찾기 url 전환 화면 
<img width="80%" src="https://user-images.githubusercontent.com/55842092/215458705-9adc2f57-f8ec-4938-873f-6a48d03d4b2e.png">
