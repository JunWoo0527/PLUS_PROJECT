## 사용버전 ##
 - SpringBoot 3.1.6
 - Gradle 1.1.4
 - Java 17

## 의존성 목록 ##
dependencies {

    //Mail
    implementation group: 'org.springframework.boot', name: 'spring-boot-starter-mail', version: '3.0.5'

    //Redis
    implementation 'org.springframework.boot:spring-boot-starter-data-redis'

    //Spring-Boot-Starter
    implementation 'org.springframework.boot:spring-boot-starter-validation'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'org.springframework.boot:spring-boot-starter-web'

    //S3
    implementation 'org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE'

    developmentOnly 'org.springframework.boot:spring-boot-devtools'

    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.springframework.security:spring-security-test'

    //MySQL
    runtimeOnly 'com.mysql:mysql-connector-j'

    //JWT
    compileOnly group: 'io.jsonwebtoken', name: 'jjwt-api', version: '0.11.5'
    runtimeOnly group: 'io.jsonwebtoken', name: 'jjwt-impl', version: '0.11.5'
    runtimeOnly group: 'io.jsonwebtoken', name: 'jjwt-jackson', version: '0.11.5'

    //Lombok
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'

}

## 사용기술 ##

 - Redis (ver:3.0.504) : Google Eamil 인증 
 - S3 : User profile 이미지 저장소
 - JWT : AccessToken(인증) & RefreshToken(ATK 만료시 재발급)


## 구현 목록 ##
- 💬  12/12 - 회원 가입 API
    - [x]  닉네임, 비밀번호, 비밀번호 확인을 **request**에서 전달받기
    - [x]  닉네임은 최소 3자 이상, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성하기
    - [x]  비밀번호는 최소 4자 이상이며, 닉네임과 같은 값이 포함된 경우 회원가입에 실패로 만들기
    - [x]  비밀번호 확인은 비밀번호와 정확하게 일치하기
    - [x]  데이터베이스에 존재하는 닉네임을 입력한 채 회원가입 버튼을 누른 경우 "중복된 닉네임입니다." 라는 에러메세지를 **response**에 포함하기
    - [x]  회원 가입 버튼을 누르기 전, 같은 닉네임이 존재하는지 "확인" 버튼을 눌러 먼저 유효성 검증부터 할 수 있도록 해보기
    - [x]  (챌린지 과제) 데이터베이스에 비밀번호를 평문으로 저장하는 것이 아닌, 단방향 암호화 알고리즘을 이용하여 암호화 해서 저장하도록 하기
    - [x]  (챌린지 과제) 회원 가입 시, 이메일 혹은 SNS로 인증 번호를 전달 받고 5분 이내에 해당 인증 번호를 검증해야 회원 가입에 성공하도록 해보기 (redis TTL 특징을 좀 더 파악하기 위함.)
- 💬 12/13 - 로그인 API
    - [x]  닉네임, 비밀번호를 **request**에서 전달받기
    - [x]  로그인 버튼을 누른 경우 닉네임과 비밀번호가 데이터베이스에 등록됐는지 확인한 뒤, 하나라도 맞지 않는 정보가 있다면 "닉네임 또는 패스워드를 확인해주세요."라는 에러 메세지를 **response**에 포함하기
    - [x]  로그인 성공 시, 로그인에 성공한 유저의 정보를 JWT를 활용하여 클라이언트에게 Cookie로 전달하기
- 💬 12/14 - 전체 게시글 목록 조회 API
    - [x]  제목, 작성자명(nickname), 작성 날짜를 조회하기
    - [x]  작성 날짜 기준으로 내림차순 정렬하기
    - [x]  (챌린지 과제) 전체 조회가 아닌 페이징 조회를 할 수 있도록 해보기
    - [x]  (챌린지 과제) 페이징 + 커스텀 정렬 기능 구현하기 -> 사용자가 입력한 key와 정렬 기준을 동적으로 입력 받아, 해당 기준에 맞게 데이터를 제공. (예. 작성자명 오름차순 정렬 and 작성 날짜 오름차순 정렬된 결과를 상위 5개만 출력)
- 💬  12/15 - 게시글 작성 API
    - [x]  토큰을 검사하여, 유효한 토큰일 경우에만 게시글 작성 가능
    - [x]  제목(500자 까지 입력 가능), 작성 내용을 입력하기(5000자 까지 입력 가능)
    - [x]  (챌린지 과제) 이미지 업로드 가능
- 💬 12/18 - 게시글 조회 API
    - [x]  제목, 작성자명(nickname), 작성 날짜, 작성 내용을 조회하기 
    (검색 기능이 아닙니다. 간단한 게시글 조회만 구현해주세요.)
- 💬 12/19 - 게시글 수정 API
    - [x]  토큰을 검사하여, 해당 사용자가 작성한 게시글만 수정 가능
- 💬 12/20 - 게시글 삭제 API
    - [x]  토큰을 검사하여, 해당 사용자가 작성한 게시글만 삭제 가능
    - [x]  (챌린지 과제) 수정된지 90일이 지난 데이터는 자동으로 지우는 스케줄러 기능을 개발해보기. (데이터 삭제 및 백업도 굉장히 중요한 기능인데, 수강생들이 이런 내용을 잘 인지하지 못 함.)
        - [x]  스케줄러에 대한 가이드라인은 별도로 제공하지 말 것. (Spring Scheduler를 쓰던, 크론잡을 쓰던 선택지를 다양하게 줄 것.)
        - [x]  90일이라고 하는 스펙은 수강생들이 알아서 정하게 내두기. (다만, 그 이유를 적도록 하기)
            - [ ]  UTC의 스케줄러가 동작하는 현재 일시 (2023-12-11T02:11:23) 기준으로 90일이 지난 데이터를 지운다.
            - [ ]  UTC의 스케줄러가 동작하는 현재 날짜 (2023-12-10) 기준으로 90일이 지난 데이터를 지운다.
            - [ ]  LocalTime(+09:00)의 스케줄러가 동작하는 현재 일시 (2023-12-11T11:11:23) 기준으로 90일이 지난 데이터를 지운다.
            - [x]  LocalTime(+09:00)의 스케줄러가 동작하는 현재 일시 (2023-12-11) 기준으로 90일이 지난 데이터를 지운다.
- 💬 12/21 - 댓글 작성 API
    - [x]  게시글과 연관 관계를 가진 댓글 테이블 추가
    - [x]  토큰을 검사하여, 유효한 토큰일 경우에만 게시글 작성 가능
    - [x]  작성 내용을 입력하기
    - [x]  게시글에 대한 좋아요

- 💬 12/22 - 게시글과 댓글 목록 조회 API, 댓글 수정/삭제 API
    - [x]  댓글 목록 조회
        - [x]  (챌린지 과제) 전체 조회가 아닌 페이징 조회를 할 수 있도록 해보기
        - [x]  (챌린지 과제) 페이징 + 커스텀 정렬 기능 구현하기 -> 사용자가 입력한 key와 정렬 기준을 동적으로 입력 받아, 해당 기준에 맞게 데이터를 제공. (예. 작성자명 오름차순 정렬 and 작성 날짜 오름차순 정렬된 결과를 상위 5개만 출력)
    - [x]  게시글 조회 API 호출시 해당 게시글의 댓글 목록도 응답
    - [x]  토큰을 검사하여, 해당 사용자가 작성한 댓글만 수정/삭제 가능
        - [x]  (챌린지 과제) 게시글이 삭제될 때 연관된 댓글도 같이 지우도록 스케줄러 코드 기능 추가
