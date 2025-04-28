# ☕ 카페 메뉴 관리 서비스

## 프로젝트 소개
>"카페 메뉴 관리 서비스"는 로컬 카페를 위한 메뉴 등록 및 주문 관리를 지원하는 웹 애플리케이션입니다. Spring Boot 기반으로 CRUD 기능을 구현하고, 데이터베이스와 연동하여 메뉴와 주문을 효과적으로 관리합니다.

주요 기능: 메뉴 등록, 조회, 수정, 삭제

개발 기간: 2024.04.22 ~ 2024.04.30

팀 구성: 조건웅(팀장), 황치열(팀원), 전민서(팀원), 한정민(팀원), 김석완(팀원)

<br/>

### 기술 스택
Backend: Java 21, Spring Boot 3.4.4

Database: MySQL

ORM: Spring Data JPA

Build Tool: Gradle

Version Control: Git, GitHub

Frountend : React


<br/>

### 기능 명세


|기능 | HTTP 메서드 |설명|
|---|---|---|
|메뉴 등록 | POST | 새로운 메뉴를 등록합니다. (ID 자동 증가)|
|메뉴 조회 | GET | 전체 메뉴 목록을 조회합니다.|
|메뉴 수정 | PUT | 특정 ID에 해당하는 메뉴 정보를 수정합니다.|
|메뉴 삭제 | DELETE | 특정 ID에 해당하는 메뉴를 삭제합니다.|

당일 오후 2시 이후의 주문은 다음날 배송 처리합니다.

<br/>

### 시스템 구성도

![client-flowchart.png](docs%2Fclient-flowchart.png)

![admin-flowchart.png](docs%2Fadmin-flowchart.png)

<br/>
API 명세서
(추후 Postman 문서나 Swagger 문서로 대체 가능)

bash
복사
편집
POST /menus
GET /menus
PUT /menus/{id}
DELETE /menus/{id}
모든 요청과 응답은 JSON 포맷 사용

<br/>
프로젝트 실행 방법
bash
복사
편집
# 프로젝트 클론
git clone https://github.com/your-repo/cafe-menu-service.git

# 프로젝트 디렉토리 이동
cd cafe-menu-service

# 빌드 및 실행
./gradlew bootRun
주의: application.yml 또는 application.properties에서 DB 연결 설정 필요

<br/>
기타
SQL 또는 ORM 중 **ORM (JPA)**를 사용해 구현

기본적인 예외 처리(GlobalExceptionHandler) 적용

테스트 코드 일부 작성

<br/>
✨ 목표
Spring Boot를 활용한 CRUD 서비스 개발 경험

JPA를 통한 데이터베이스 연동 실습

REST API 설계 및 구현 학습

팀 프로젝트 협업 경험

📌 추가로, 예를 들면
WBS (프로젝트 일정) 링크

팀원별 역할

DB ERD 이미지
같은 것도 추가하면 완성도 훨씬 올라간다.
