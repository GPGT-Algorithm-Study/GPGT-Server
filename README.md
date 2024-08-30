# GPGT: 알고리즘 스터디 동기부여 플랫폼

![GPGT 로고](https://github.com/GPGT-Algorithm-Study/GPGT-Server/assets/44383895/542be3c3-72d2-42fc-a3de-edf5288d4c6b)

[🔗 GPGT 웹사이트](https://randps.kr)

## 프로젝트 개요

GPGT는 대표적인 알고리즘 문제 풀이 사이트인 'Baekjoon Online Judge'와 'Solved.ac'의 데이터를 기반으로 스터디원의 문제 풀이 현황을 자동으로 관리하고, 규칙에 따라 경고를 부여하며, 동기부여를 촉진하는 알고리즘 스터디 플랫폼입니다.

## 주요 기능

- **랜덤 문제 스트릭**: 매일 난이도 구간에 맞는 랜덤 문제 제공.
- **문제 추천**: 머신러닝 기반 또는 단순랜덤 문제 추천 시스템.
- **포인트 시스템**: 문제 해결, 팀 경쟁 승리 시 포인트 획득 및 아이템 구매.
- **팀 경쟁 시스템**: 매주 팀 대결을 통한 스터디 동기부여.
- **유저 통계**: 개인의 문제 풀이 통계 및 포인트 확인.
- **게시판 기능**: Markdown 형식의 글 작성 및 댓글 기능.
- **알고리즘 로드맵**: 주차별 문제 구성을 통한 체계적인 학습.

## 사용 기술

<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=SpringSecurity&logoColor=white"> <img src="https://img.shields.io/badge/JAVA-007396?style=for-the-badge&logo=openjdk&logoColor=white"> <br>
<img src="https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=JUnit5&logoColor=white"> <br>
<img src="https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white"> <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <br>
<img src="https://img.shields.io/badge/apache tomcat-F8DC75?style=for-the-badge&logo=apachetomcat&logoColor=white"> <img src="https://img.shields.io/badge/nginx-%23009639.svg?style=for-the-badge&logo=nginx&logoColor=white"> <br>
<img src="https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white"> <img src="https://img.shields.io/badge/docker Compose-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white"> <img src="https://img.shields.io/badge/github%20actions-%232671E5.svg?style=for-the-badge&logo=githubactions&logoColor=white"> <br>
<img src="https://img.shields.io/badge/Amazon%20EC2-FF9900?style=for-the-badge&logo=Amazon%20EC2&logoColor=white"> <img src="https://img.shields.io/badge/Amazon%20S3-569A31?style=for-the-badge&logo=Amazon%20S3&logoColor=white"> <br>

## 프로젝트 배경

이 프로젝트는 알고리즘 스터디의 효율성을 높이기 위해 조직되었습니다. 기존 스터디에서는 매일 수기로 유저의 문제 풀이 현황을 확인하고 관리하는 작업이 필요했으며, 이러한 반복적인 작업을 자동화하고, 스터디원들의 동기부여를 위해 새로운 기능들을 추가한 사이트입니다.

## 서비스 기능 상세

### 1. 랜덤 문제 스트릭

- 사용자가 원하는 난이도 구간을 설정하면, 해당 구간의 문제를 매일 하나씩 추천받습니다. 문제를 풀면 스트릭이 채워지며 동기부여를 제공합니다.

![랜덤 문제 스트릭](https://github.com/klloo/next-ts-x-clonecoding/assets/53117014/b01e2020-41ad-487d-90ec-cb8eca01c2c7)

### 2. 팀 대항 시스템

- 매주 두 팀으로 나뉘어 팀 대결을 진행합니다. 팀원의 문제 해결 난이도에 따라 점수가 책정되며, 승리 팀에게 추가 포인트가 부여됩니다.

![팀 대항](https://github.com/klloo/next-ts-x-clonecoding/assets/53117014/b1f6ec7a-b49c-4369-bd10-dcb06adaff4a)

### 3. 유저 통계

- 사용자의 문제 풀이 현황 및 포인트를 시각적으로 확인할 수 있습니다.

![유저 통계](https://github.com/klloo/next-ts-x-clonecoding/assets/53117014/a7640dc4-b0d1-4bb7-bd29-232b537bbbdd)

### 4. 마크다운 게시판

- Markdown 형식의 글 작성과 댓글 기능을 지원하는 게시판을 제공합니다. 문제풀이와 질문 게시판에서는 문제 번호를 입력하면 자동으로 해당 문제의 정보가 링크됩니다.

![마크다운 게시판](https://github.com/klloo/next-ts-x-clonecoding/assets/53117014/c8d93729-c74f-4e8a-a344-d1db8a0a91fe)

### 5. 아이템 상점

- 문제 풀이로 얻은 포인트로 아이템을 구매하고 사용할 수 있는 상점 기능을 제공합니다.

![아이템 상점](https://github.com/klloo/next-ts-x-clonecoding/assets/53117014/c5764081-b967-42e7-988d-e5b2a840408b)

### 6. 프로필 상세 페이지

- 사용자 프로필에서 랜덤 스트릭 정보, 게시글, 포인트 및 경고 로그를 조회할 수 있습니다.

![프로필 페이지](https://github.com/klloo/next-ts-x-clonecoding/assets/53117014/58013b11-3568-440c-a353-2863b5c8d509)

## 서비스 아키텍처

![서버 구조](https://github.com/GPGT-Algorithm-Study/GPGT-Server/assets/44383895/ec88733e-9304-4388-89aa-ea5b548bf575)

## 개발 환경

- **OpenJDK 11**

## 시작하기

### 1. 빌드

```bash
# Build
./gradlew build
```

```bash
# Run (OpenJDK 11 필요)
java -jar /build/libs/randomdefence-0.0.1-SNAPSHOT.jar

# Background run
nohup java -jar /build/libs/randomdefence-0.0.1-SNAPSHOT.jar > {my_log_file} &
```

### 2. Docker 사용

```bash
# Docker 이미지 빌드
docker build -t [{dockerID}/{dockerHubRepository}] .

# DockerHub에 이미지 푸시
docker push [{image}/{Repository}]

# Docker 컨테이너 실행
docker run -p 8080:8080 {dockerHubID}/{image}
```
