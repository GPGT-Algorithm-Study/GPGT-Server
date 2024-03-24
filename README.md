
# 알고리즘 스터디 동기부여 사이트 'GPGT'
![다운로드](https://github.com/GPGT-Algorithm-Study/GPGT-Server/assets/44383895/542be3c3-72d2-42fc-a3de-edf5288d4c6b)

🏠 https://randps.kr

## 서비스

본 레포지토리는 대표적인 알고리즘 문제 풀이 사이트인 'Beakjoon online judge', 'Solved.ac'의 데이터를 이용하여 유저들의 문제 풀이 현황을 자동으로 관리해주고, 규칙에 따라 처리해주는 사이트의 서버입니다.


## 사용 기술

<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=SpringSecurity&logoColor=white"> <img src="https://img.shields.io/badge/JAVA-007396?style=for-the-badge&logo=openjdk&logoColor=white"> <br>
<img src="https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=JUnit5&logoColor=white"> <br>
<img src="https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white"> <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <br>
<img src="https://img.shields.io/badge/apache tomcat-F8DC75?style=for-the-badge&logo=apachetomcat&logoColor=white"> <img src="https://img.shields.io/badge/nginx-%23009639.svg?style=for-the-badge&logo=nginx&logoColor=white"> <br>
<img src="https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white"> <img src="https://img.shields.io/badge/docker Compose-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white"> <img src="https://img.shields.io/badge/github%20actions-%232671E5.svg?style=for-the-badge&logo=githubactions&logoColor=white"> <br>
<img src="https://img.shields.io/badge/Amazon%20EC2-FF9900?style=for-the-badge&logo=Amazon%20EC2&logoColor=white"> <img src="https://img.shields.io/badge/Amazon%20S3-569A31?style=for-the-badge&logo=Amazon%20S3&logoColor=white"> <br>


## 배경

기존에 알고리즘 공부의 필요성을 느낀 대학생들이 모여 만든 '알고리즘 스터디'의 불편함을 인식하고, 이를 해결하기 위해 조직된 프로젝트입니다.


### 문제 인식

기존의 알고리즘 사이트는 25명 가량의 유저의 알고리즘 문제 풀이 정보를 매일 한 명의 관리자가 수기로 확인하고, 이를 기반으로 문제를 풀지 않은 유저와 문제를 많이 푼 유저들을 관리했습니다.

스터디의 규칙은 다음과 같습니다.

#### 경고 부여
1. 'Beakjoon online judge' 사이트에서 매일 하나 이상의 알고리즘 문제를 풀지 않으면 경고 1회 부여
2. 한 주에 노션에 5일 미만으로 스터디 Notion에 글을 작성할 시 경고 1회 부여

#### 경고 차감
1. 하루에 실버5 이상 난이도의 문제를 3개 해결하고 스터디 Notion에 글 3개 작성 시, 경고 1회 차감

위와 같은 규칙을 매일 한 명의 관리자가 모든 유저의 아이디를 확인하며 경고를 관리하는 일을 수기로 진행하고 있었습니다.

이에 따르는 단순 반복 작업을 자동화 하고, 추가로 스터디원들의 동기부여를 촉진하는 사이트를 개발하기로 결정하고 프로젝트를 조직하고 시스템을 개발했습니다.


## 서비스 주요 기능 요약

- 랜덤 문제 스트릭 (매일 원하는 난이도 구간의 문제를 랜덤하게 한 문제 풀 수 있는 동기부여 시스템)
- 문제 추천 (머신러닝 / 단순랜덤 기반 알고리즘 문제 추천 시스템)
- 포인트 시스템 (문제 해결 / 팀 경쟁 승리시 포인트를 획득해서, 경고 차감권 등의 아이템을 구매할 수 있는 동기부여 시스템)
- 팀 경쟁 시스템 (전체 유저가 반으로 나뉘어 매 주 진행되는 팀 단위 경쟁 시스템)
- 유저 통계 (유저 개인의 스터디에서의 통계 정보를 기간 단위로 확인할 수 있는 시스템)
- 게시판 기능 (Markdown 형식의 여러 형태의 글/댓글 작성이 가능한 게시판 시스템)
- 알고리즘 로드맵 기능 (구성자의 의도에 따라 주차별 문제 구성을 따라 체계적으로 공부가 가능한 로드맵 시스템)

위와 같은 기능들을 통해 사용자의 알고리즘 공부에 대한 학습 의욕을 촉진하고, 관리의 용이성을 제공합니다.


## 제공 기능 상세

기본적으로 기존의 알고리즘 스터디를 효율적으로 관리하기 위해 사용자들이 오늘 어떤 문제를 풀었는지 확인할 수 있도록 합니다. 스터디 관리자는 이를 기반으로 경고를 관리하고 사용자를 추가 및 삭제할 수 있는 기능을 제공하고 있습니다.
그리고 스터디원의 동기 부여를 위해 포인트 제도를 도입하고 몇 가지 추가 기능을 제공하고 있습니다.

**1. 랜덤 문제 추천 및 스트릭**

사용자가 마이페이지에서 랜덤 문제 난이도를 설정하면, 해당 난의도의 문제를 매일 하나씩 추천해줍니다. 그리고 이 문제를 풀면, 사용자 정보 하단에 있는 스트릭이 채워지게 됩니다.

<img width="472" alt="스크린샷 2023-10-05 오전 12 42 06" src="https://github.com/klloo/next-ts-x-clonecoding/assets/53117014/b01e2020-41ad-487d-90ec-cb8eca01c2c7">

<br/>

<br/>

**2. 팀 대항**

매주 사용자들이 무작위로 두 팀으로 나뉘어 대결하는데, 이 때 팀의 점수는 팀원들이 해결한 문제의 난이도를 기반으로 책정됩니다. 일주일이 지난 후에는 새로운 팀이 구성되며, 지난 주에 승리한 팀에 추가 포인트가 주어집니다.

![팀](https://github.com/klloo/next-ts-x-clonecoding/assets/53117014/b1f6ec7a-b49c-4369-bd10-dcb06adaff4a)

<br/>

**3. 통계**

사용자의 문제 풀이 현황, 포인트 등의 지표를 통해 스터디의 전반적인 상황을 시각적으로 확인할 수 있는 통계 기능을 제공합니다.


<img width="1438" alt="스크린샷 2023-10-05 오전 12 42 55" src="https://github.com/klloo/next-ts-x-clonecoding/assets/53117014/a7640dc4-b0d1-4bb7-bd29-232b537bbbdd">
<img width="1390" alt="스크린샷 2023-10-05 오전 12 43 10" src="https://github.com/klloo/next-ts-x-clonecoding/assets/53117014/24abca3c-4e92-409b-a5b3-17ce081316f2">

<br/>

<br/>

**4. 마크다운 게시판**

마크다운 게시판 기능을 제공합니다. 문제풀이와 질문 게시판에서는 문제 번호를 입력하면 자동으로 해당 문제의 정보와 링크가 제공됩니다.

<div>
<img width="1429" alt="스크린샷 2023-10-05 오후 3 17 20" src="https://github.com/klloo/next-ts-x-clonecoding/assets/53117014/c8d93729-c74f-4e8a-a344-d1db8a0a91fe">
<img width="1439" alt="스크린샷 2023-10-05 오전 12 44 02" src="https://github.com/klloo/next-ts-x-clonecoding/assets/53117014/cdff2e36-f2d7-400d-addc-4c61d3544340">
</div>

<br/>


**5. 아이템**

아이템과 상점 기능을 제공합니다. 문제를 풀어 획득한 포인트로 상점에서 아이템을 구매하고 사용할 수 있습니다.

<div>
<img width="1440" alt="스크린샷 2023-10-05 오후 3 17 20" src="https://github.com/klloo/next-ts-x-clonecoding/assets/53117014/c5764081-b967-42e7-988d-e5b2a840408b">
</div>

<br/>

**6. 프로필 상세 페이지**

사용자의 프로필 페이지에서 랜덤 스트릭 정보, 작성한 게시글과 같은 정보와 포인트 및 경고 로그를 조회할 수 있습니다.

<div>
<img width="1440" alt="스크린샷 2023-10-05 오후 3 17 20" src="https://github.com/klloo/next-ts-x-clonecoding/assets/53117014/58013b11-3568-440c-a353-2863b5c8d509">
</div>

<br/>


## 서비스 서버 구조
![서버구조withCICD 001](https://github.com/GPGT-Algorithm-Study/GPGT-Server/assets/44383895/ec88733e-9304-4388-89aa-ea5b548bf575)



## 의존성
- OpenJDK11



## Getting start

### 1. Build
``` shell
# build
./gradlew build
```
``` shell
# If you want to run, you need OpenJDK11 on your local

# run
java -jar /build/libs/randomdefence-0.0.1-SNAPSHOT.jar

# background run
nohup java -jar /build/libs/randomdefence-0.0.1-SNAPSHOT.jar > {my_log_file} &
```

### 2. Docker
``` shell
# docker image build
docker build -t [{dockerID}/{dockerHubRepository}] .

# dockerhub image push
docker push [{image}/{Repository}]

# docker run
docker run -p 8080:8080 {dockerHubID}/{image}
```

### ⚠️ 실제 서비스 환경에서 JWT secret key와 datasource를 변경하는 것을 잊지 마세요 ⚠️
