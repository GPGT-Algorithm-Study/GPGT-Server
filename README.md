
# 🥑 Problem-Solving Study Group website
A site where you can manage study groups based on user information from the 'Beakjoon online judge' and 'Solved.ac'.

https://randps.kr


## Functionality

- Random Problem Streak
- Problem suggestions
- Points Shop
- Team-based Competitions
- User Statistics

These features help you manage your study and increase the motivation of your study members to learn algorithms.


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

### ⚠️ Don't forget change your JWT secret key And datasource ⚠️