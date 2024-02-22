# 오늘 뭐하지 ? (할일 추천 사이트)


## ⚒️ 개발 환경
- Language : JAVA 17, JavaScript
- Framework : Spring Boot 3.1.5 , Spring Data JPA
- DB : MariaDB , Redis
- OpenAPI : BootStrap , Kakao Map Api
- Library : Security , Lombok , Thymeleaf
- DevOps : AWS EC2 , Docker , Jenkins , AWS Route 53

## ⚒️ 배포 주소
http://todaywork.site

## ⚒️ 관계도
![오늘뭐하지관계도](https://github.com/qwer7824/demo/assets/8233989/073bcb1f-48c3-448d-a26b-4e5772926c81)
## ⚒️ ERD
![image](https://github.com/qwer7824/demo/assets/8233989/f0b2475a-9d9c-4611-b7c8-16f3757260a9)

## ⚒️ TroubleShooting
1. Permission denied (publickey,gssapi-keyex,gssapi-with-mic) 이슈

jenkins 적용 도중 Permission denied 발생하여 pem 키 확인도 해보고 키페어를 교체도 해봤지만 해결이 되지 않았다.<br>
authorized_keys 권한 문제로 chmod 600 으로 해결

[키페어 교체 블로그 참조](https://inpa.tistory.com/entry/AWS-%F0%9F%93%9A-%ED%82%A4%ED%8E%98%EC%96%B4SSH-Key-%EB%B6%84%EC%8B%A4%EC%8B%9C-%EB%B3%B5%EA%B5%AC%ED%95%98%EB%8A%94-2%EA%B0%80%EC%A7%80-%EB%B0%A9%EB%B2%95)

2. jenkins CD 가 되지않는 이슈

Publish over SSH 사용하여 푸쉬 -> 빌드 -> jenkins 컨테이너의 빌드파일을 todayApp 컨테이너에 전달 하는건 작동이 되었지만<br>
todayApp 컨테이너를 정지 - > 실행을 해야하는데 작동 되지 않았다.

빌드후 조치 Send build artifacts over SSH 작성
```
chmod -x /home/ec2-user/demo/build/libs/run.sh
sh /home/ec2-user/demo/build/libs/run.sh
```

`demo/build/libs` 위치에 `run.sh` 쉴 스크립트를 작성하여 CICD가 작동되게 만들었다.

run.sh
```
if [ $? -eq 0 ]; then
    echo "today-app 컨테이너가 성공적으로 종료되었습니다."
    docker-compose up --build today-app >> /dev/null 2>&1
    exit_code=$?
    if [ $exit_code -eq 0 ]; then
        echo "today-app 컨테이너가 성공적으로 실행되었습니다."
    else
        echo "today-app 컨테이너 실행에 실패했습니다. (Exit code: $exit_code)"
    fi
else
    echo "today-app 컨테이너 종료에 실패했습니다."
    exit 1
fi
```
