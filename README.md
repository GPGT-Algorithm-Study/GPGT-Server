
# 알고리즘 스터디 동기부여 사이트 'GPGT'
![다운로드](https://github.com/GPGT-Algorithm-Study/GPGT-Server/assets/44383895/542be3c3-72d2-42fc-a3de-edf5288d4c6b)

🏠 https://randps.kr

## 사용 기술

<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=SpringSecurity&logoColor=white"> <img src="https://img.shields.io/badge/JAVA-007396?style=for-the-badge&logo=openjdk&logoColor=white"> <br>
<img src="https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=JUnit5&logoColor=white"> <br>
<img src="https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white"> <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <br>
<img src="https://img.shields.io/badge/apache tomcat-F8DC75?style=for-the-badge&logo=apachetomcat&logoColor=white"> <img src="https://img.shields.io/badge/nginx-%23009639.svg?style=for-the-badge&logo=nginx&logoColor=white"> <br>
<img src="https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white"> <img src="https://img.shields.io/badge/docker Compose-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white"> <img src="https://img.shields.io/badge/Github Action-181717?style=for-the-badge&logo=github&logoColor=white"> <br>
<img src="https://img.shields.io/badge/Amazon%20EC2-FF9900?style=for-the-badge&logo=Amazon%20EC2&logoColor=white"> <img src="https://img.shields.io/badge/Amazon%20S3-569A31?style=for-the-badge&logo=Amazon%20S3&logoColor=white"> <br>


## 배경

A site where you can manage study groups based on user information from the 'Beakjoon online judge' and 'Solved.ac'.



## Functionality

- Random Problem Streak
- Problem suggestions
- Points Shop
- Team-based Competitions
- User Statistics

These features help you manage your study and increase the motivation of your study members to learn algorithms.

## Architecture
![서버구조withCICD 001](https://github.com/GPGT-Algorithm-Study/GPGT-Server/assets/44383895/ec88733e-9304-4388-89aa-ea5b548bf575)



## Dependencies
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

### ⚠️ Don't forget to change your JWT secret key and datasource ⚠️
