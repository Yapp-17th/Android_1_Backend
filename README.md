
# Picon-Server
2020 Yapp 17기 안드로이드 1팀 서버입니다. 
- [안드로이드 다운로드](https://play.google.com/store/apps/details?id=com.yapp.picon)

## 📄 API 문서
- [게시글 관련](http://www.yappandone17.shop/display/docs/api-guide.html)
- [auth 관련](http://www.yappandone17.shop/auth/docs/api-guide.html)

## 📂 프로젝트 구조(MSA)
- display-service
  - 조회 관련 서비스  
- domain-service
  - 게시글 관련 서비스 
- eureka-server
  - 마이크로서비스 등록 및 탐지
- zuul-server
  - 회원 인증 및 서비스 라우팅
  
## 💻 기술 스택
- Java8, Springboot2.3, springCloud, JPA, MySQL, JUnit5, SpringRestDocs, AWS(S3, RDS), Docker

## 😽 설치 
- [mysql docker 설치](http://jmlim.github.io/docker/2019/07/30/docker-mysql-setup/)
