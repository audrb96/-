# 너나두리
![logo-black (1)](https://user-images.githubusercontent.com/58455540/204408856-e1fc1814-3ceb-4f3e-83dc-176a81a9fa9d.png)
### 당신의 여행지 선택의 우선순위는 무엇인가요?
### 맛있는 음식을 위한 여행? 좋은 풍경과 함께하는 휴양지 여행? 볼거리 많은 관광지 여행?
### 너나두리를 통해 나만의 여행지를 찾아봐요!

## 🛠 기술 스택

#### Communication Tool

> <img src="https://img.shields.io/badge/gitlab-FC6D26?style=for-the-badge&logo=gitlab&logoColor=white">
> <img src="https://img.shields.io/badge/jira-0052CC?style=for-the-badge&logo=jira&logoColor=white">
> <img src="https://img.shields.io/badge/mattermost-0058CC?style=for-the-badge&logo=mattermost&logoColor=white">
> <img src="https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white">

#### Development Tool

> <img src="https://img.shields.io/badge/vscode-007ACC?style=for-the-badge&logo=visualstudiocode&logoColor=white">
> <img src="https://img.shields.io/badge/intellij-000000?style=for-the-badge&logo=intellijidea&logoColor=white">
> <img src="https://img.shields.io/badge/mysql_workbench-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
> <img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">

#### FrontEnd

> <img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black">
> <img src="https://img.shields.io/badge/Nuxt.js-00DC82?style=for-the-badge&logo=Nuxt.js&logoColor=white">

#### BackEnd

> <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white">
> <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
> <img src="https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=Spring-Security&logoColor=white">
> <img src="https://img.shields.io/badge/django-092E20?style=for-the-badge&logo=django&logoColor=white">
> <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">

#### Server

> <img src="https://img.shields.io/badge/aws-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white">
> <img src="https://img.shields.io/badge/ec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white">
> <img src="https://img.shields.io/badge/s3-569A31?style=for-the-badge&logo=amazons3&logoColor=white">
> <img src="https://img.shields.io/badge/nginx-009639?style=for-the-badge&logo=nginx&logoColor=white">
> <img src="https://img.shields.io/badge/jenkins-D24939?style=for-the-badge&logo=jenkins&logoColor=white">
> <img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">

<br/>
<br/>


## 📱 Wireframe

[Miro](https://miro.com/app/board/uXjVPaktUw8=/?share_link_id=646659700024)<br>
![너나두리](https://user-images.githubusercontent.com/58455540/204410047-91e5ccd8-c41d-4798-a89b-c38e9ac9bdba.jpg)

## 📏 ERD

![NuNaDuRi (2)](https://user-images.githubusercontent.com/58455540/204410271-ced6a8c1-1c45-4a12-b0a0-25ec4c2a220e.png)

## 💻 Software Architecture

![image](https://user-images.githubusercontent.com/58455540/204410614-21964a09-69a4-4ff3-b743-e8fb3a39d6ba.png)

## 📋 Convention

### JIRA

- STORY: 사용자 입장에서 작성하는 거

  - 사용자가 서비스 사용 흐름을 STORY로 적어 놓고 TASK를 이에 묶어놓기

- TASK

  - 기능

  - 앞에 태그 붙이기

    `[태그]<space>제목`

    - [TEAM] : 팀 회의
    - [BE] : 백엔드
    - [FE] : 프론트엔드
    - [DOCS]: 문서 작성
    - [STUDY]: 개인 학습

### GIT

- 태그 종류
  - `FEAT` : 새로운 기능 추가
  - `FIX` : 버그 수정
  - `DOCS` : 문서 수정
  - `STYLE` : 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우
  - `REFACTOR` : 코드 리펙토링
  - `TEST` : 테스트 코드, 리펙토링 테스트 코드 추가
  - `CHORE` : 빌드 업무 수정, 패키지 매니저 수정
- 형식

```java
[태그]<space>제목<space>(Jira Issue 링크)
ex) [FEAT] Add login (https://ssafy.atlassian.net/browse/S07P31A707-9)
```

- [template.md](http://template.md/) (MR 날릴때)

```java
## ⛅️ 내용

> 이 PR의 작업 요약 여기에 작성

## 🎸 특이사항

> 리뷰시 참고할만한 내용, 주의깊게 봐줬으면 하는 내용 여기에 작성

## 🎸 테스트

> 어떻게 테스트 할 수 있을까요? 여기에 작성
```

- Branch

```java
master
ㄴdevelop
		ㄴbackend_dev
			ㄴfeature/api/login
		ㄴfrontend_dev
			ㄴfeature/fe/register
```

- feature/api/login
- feature/fe/register

### **BE**

- 변수명 :

  camelCase

  - 카멜케이스

- 클래스명 :

  PascalCase

  - 카멜케이스 & 첫글자 대문자
  - DTO명
    - rest method 따라가기
    - 맨 마지막 url 따라가기
    - 겹치면 앞에 url까지 쓰기

### **FE**

- 파일명: 소문자

- CSS :

  kebab-case

  - 케밥케이스

### **DB**

- table: **snake_case**
- column
  - **snake_case:** 소문자&스네이크케이스
  - **primary key → id (Long, auto increment)**
  - full length → **column 명**

## 👪 팀원 소개

<table>
    <tr>
        <td height="140px" align="center"> <a href="https://github.com/PowerBBear">
            <img src="assets/member/avatar1.png" /> <br><br> 👑 박명규 <br>(Back-End) </a> <br></td>
        <td height="140px" align="center"> <a href="https://github.com/JunPark364">
            <img src="assets/member/avatar2.png" /> <br><br> 🙂 김상현 <br>(Back-End) </a> <br></td>
        <td height="140px" align="center"> <a href="https://github.com/pch8349">
            <img src="assets/member/avatar3.png" /> <br><br> 😆 정지현 <br>(Back-End) </a> <br></td>
        <td height="140px" align="center"> <a href="https://github.com/SilverLight96">
            <img src="assets/member/avatar4.png" /> <br><br> 😁 이선호 <br>(Front-End) </a> <br></td>
        <td height="140px" align="center"> <a href="https://github.com/heunhanireum">
            <img src="assets/member/avatar6.png" /> <br><br> 🙄 권민지 <br>(Front-End) </a> <br></td>
        <td height="140px" align="center"> <a href="https://github.com/kkh9700">
            <img src="assets/member/avatar5.png" /> <br><br> 😶 박종찬 <br>(Front-End) </a> <br></td>
    </tr>
    <tr>
        <td align="center">DB설계<br/>REST API<br/>devOps<br/></td>
        <td align="center">DB설계<br/>REST API<br/>
        <td align="center">데이터 처리<br/>
        <td align="center">프론트엔드</td>
        <td align="center">데이터 처리<br/></td>
        <td align="center">REST API<br/>데이터 처리<br/></td>
    </tr>
</table>
