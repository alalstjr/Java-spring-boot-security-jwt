--------------------
# Java-spring-boot-security-jwt
--------------------

# 목차

- [2021년 4월 코드 업데이트 완료](#2021년-4월-코드-업데이트-완료)
- [1. Spring Security 그리고 JPA, REST API JWT 인증](#Spring-Security-그리고-JPA,-REST-API-JWT-인증)
- [2. JWT (JSON Web Token) 이란?](#JWT-(JSON-Web-Token)-이란?)
    - [2-1. oauth 인증 그리고 JWT와 같은 Claim 기반의 토큰 흐름](#oauth-인증-그리고-JWT와-같은-Claim-기반의-토큰-흐름)
    - [2-2. JWT와 같은 Claim 기반의 토큰 흐름](#JWT와-같은-Claim-기반의-토큰-흐름)
    - [2-3. 많은 프로그래밍 언어의 지원](#많은-프로그래밍-언어의-지원)
    - [2-4. 자가 수용적 (self-contained)](#자가-수용적-(self_contained))
    - [2-5. 쉬운 전달](#쉬운-전달)
    - [2-6. Claim (메시지) 기반](#Claim-(메시지)-기반)
    - [2-7. JWT의 문제점](#JWT의-문제점)
    - [2-8. JWT를 유용하게 사용하는 상황](#JWT를-유용하게-사용하는-상황)
    - [2-9. JWT 코드의 구조](#JWT-코드의-구조)
        - [2-9-1. 헤더 Header](#헤더-Header)
        - [2-9-2. 정보 Payload](#정보-Payload)
            - [2-9-2-1. 등록된 (registered) 클레임](#등록된-(registered)-클레임)
            - [2-9-2-1. 공개 (public) 클레임](#공개-(public)-클레임)
            - [2-9-2-1. 비공개 (private) 클레임](#비공개-(private)-클레임)
        - [2-9-3. 서명 (signature)](#서명-(signature))
- [3. Spring Boot 개발환경 설정](#Spring-Boot-개발환경-설정)
    - [3-1. Spring Security 인증 일시 해제](#Spring-Security-인증-일시-해제)
    - [3-2. JPA 엔티티 Model Class 만들기](#JPA-엔티티-Model-Class-만들기)
    - [3-3. UserRole Enum 열거형 상수 정의](#UserRole-Enum-열거형-상수-정의)
        - [3-3-1. Enum 관리 모듈](#Enum-관리-모듈)
    - [3-4. model 을 관리할 repository 생성하기](#model-을-관리할-repository-생성하기)
        - [3-4-1. repository save & find Test](#repository-save-&-find-Test)
    - [3-5. REST APIS 만들기](#REST-APIS-만들기)
        - [3-5-1. UserService 구현하기](#UserService-구현하기)
        - [3-5-2. AccountController 구현하기](#AccountController-구현하기)
        - [3-5-3. Controller 에서 사용할 DTO 클래스를 생성하기](#Controller-에서-사용할-DTO-클래스를-생성하기)
        - [3-5-4. Account 새로운 유저 생성 확인](#Account-새로운-유저-생성-확인)
        - [3-5-5. 생성시간/수정시간 자동화 JPA Auditing](#생성시간/수정시간-자동화-JPAAuditing)
            - [3-5-5-1. LocalDate 사용](#LocalDate-사용)
            - [3-5-5-2. BaseTime Entity 추상 클래스 생성](#BaseTime-Entity-추상-클래스-생성)
- [4. Spring Security(스프링 시큐리티) 란 무엇인가?](#Spring-Security(스프링-시큐리티)-란-무엇인가?)
    - [4-1. Spring Security 동작 방식](#Spring-Security-동작-방식)
    - [4-2. DelegatingFilterProxy VS DispatcherServlet](#DelegatingFilterProxy-VS-DispatcherServlet)
    - [4-3. DelegatingFilterProxy 등록하기 ==> Request Intercept & Filter](#DelegatingFilterProxy-등록하기-==>-Request-Intercept-&-Filter)
    - [4-4. Java Configuration](#Java-Configuration)
    - [4-5. package 생성](#package-생성)
    - [4-6. 인증 진행 순서 (로그인)](#인증-진행-순서-(로그인))
        - [4-6-1. 1. SecurityConfig](#1.-SecurityConfig)
        - [4-6-2. 2. FormLoginFilter](#2.-FormLoginFilter)
            - [4-6-2-1. 로그인한 사용자의 DTO](#로그인한-사용자의-DTO)
            - [4-6-2-2. 인증 필터](#인증-필터)
        - [4-6-3. 3. 인증 전 Token PreAuthorizationToken 생성](#3.-인증-전-Token-PreAuthorizationToken-생성)
        - [4-6-4. 4. 인증 후 Token PostAuthorizationToken 생성](#4.-인증-후-Token-PostAuthorizationToken-생성)
        - [4-6-5. 5. FormLoginAuthenticationProvider](#5.-FormLoginAuthenticationProvider)
        - [4-6-7. 6. successfulAuthentication](#6.-successfulAuthentication)
        - [4-6-8. 7. handler 인증 성공(인증객체 생성)](#7.-handler-인증-성공(인증객체-생성))
            - [4-6-8-1. TokenDTO](#TokenDTO)
            - [4-6-8-2. JWT Token 생성](#JWT-Token-생성)
            - [4-6-8-3. handler 인증 실패](#handler-인증-실패)
        - [4-6-9. SecurityConfig 등록](#SecurityConfig-등록)
        - [4-6-10. Token 발급 결과 확인](#Token-발급-결과-확인)
- [5. 발급받은 JWToken 을 활용하여 USER 권한만 접근 가능한 페이지 만들기](#발급받은-JWToken-을-활용하여-USER-권한만-접근-가능한-페이지-만들기)
    - [5-1. JWT Provider 추가하기](#JWT-Provider-추가하기)
        - [5-1-1. JWToken 을 해석해서 받은 로그인 정보 DTO 로 전달하기](#JWToken-을-해석해서-받은-로그인-정보-DTO-로-전달하기)
        - [5-1-2. JwtDecoder](#JwtDecoder)
            - [5-1-2-1. AccountDTO 생성](#AccountDTO-생성)
    - [5-2. JwtAuthenticationFilter](#JwtAuthenticationFilter)
        - [5-2-1. JwtPreProcessingToken](#JwtPreProcessingToken)
        - [5-2-2. HeaderTokenExtractor](#HeaderTokenExtractor)
    - [5-3. FilterSkipMatcher](#FilterSkipMatcher)
    - [5-4. JWToken 로그인 결과 확인하기](#JWToken-로그인-결과-확인하기)


# 2021년 4월 코드 업데이트 완료

나름 직접 이곳 저곳 찾아보고 정리한 노트인데 많은 분들이 봐주시고 별(좋아요) 클릭과 포크도 해주셨습니다.  
해당 글을 작성한지 2년이 지난 지금 정리 안된 노트를 더 많은 분들이 보면 잘못된 지식을 가지고 갈까봐  
제가 2년 동안 실무에서 혹은 더 성장한 저의 지식 코드로 해당 `JWT & Spring Boot 노트를 다시 수정 보완 작성하였습니다.`  

보잘것없는 노트이지만 실제로 잘 동작하며 개인적으로 나름 큰? 홈페이지도 아래와 같이 작성하여 문제없이 구동하였습니다.  
모르던 지식을 알아가시는 분 혹은 도움이 되신분 꼭 별 표시를 클릭해 주세요.  
몰랐는데 별표시 하나씩 증가 할때마다 뿌듯 하더라구요.

감사합니다. 쭌프로 JJunpro

# Spring Security 그리고 JPA, REST API JWT 인증

지금까지 공부해온 Spring Security 를 활용하여 REST API JWT Token 인증 시스템을 구현해 보도록 하겠습니다.

REST API를 디자인 할 때는 매우 중요한 보안을 탄탄하게 잡아줘야 합니다.  
마침 Spring 기반의 애플리케이션 `Spring Security` 라는 아주 훌륭한 `인증 및 권한` 부여 솔루션이 존재합니다.

# JWT (JSON Web Token) 이란?

JSON Web Token (JWT) 은 웹표준 [(RFC 7519)](https://tools.ietf.org/html/rfc7519) 으로서  
두 개체에서 JSON 객체를 사용하여 가볍고 자가수용적인 (self-contained) 방식으로 정보를 안전성 있게 전달해줍니다.

JSON 객체로서 안전하게 정보를 전송할 수 있는 작고 self-contained 방법을 정의하는 표준이다.  
JWT 대표적인 특징으로는 Claim 기반 토큰의 개념 이라는 것입니다.

- OAuth 인증방식의 경우
    - OAuth에 의해서 발급되는 access_token은 random string으로 토큰 자체에는 특별한 정보를 가지고 있지 않는 일반적인 스트링 형태입니다.

API나 서비스를 제공하는 서버 입장에서 그 access_token을 통해서 사용자에 연관된 권한(예를 들어 scope같은 것)을 식별한 뒤 권한을 허용해주는 구조입니다.  
즉 서비스를 제공하는 입장에서는 토큰을 가지고 그 토큰과 연관된 정보를 서버쪽에서 찾아야 합니다. (사용자 ID나 권한 등)

JWT는 Claim 기반이라는 방식을 사용하는데, `Claim이라는 사용자에 대한 프로퍼티나 속성`을 이야기 합니다.  
토큰 자체가 정보를 가지고 있는 방식인데, JWT는 이 Claim을 JSON을 이용해서 정의 합니다.

## oauth 인증 그리고 JWT와 같은 Claim 기반의 토큰 흐름

![oauth](/images/oauth.png)

- 1. 
    - API 클라이언트가 `Authorization Server(토큰 발급서버)로 토큰을 요청`합니다. 이때, 토큰 발급을 요청하는 사용자의 계정과 비밀번호를 넘기고, 
    `이와 함께 토큰의 권한(용도)를 요청`합니다. 
    - 여기서는 일반 사용자 권한(enduser)과 관리자 권한(admin)을 같이 요청하였습니다.
- 2. 
    - 토큰 생성 요청을 받은 `Authorization Server는 사용자 계정을 확인한 후, 이 사용자에게 요청된 권한을 부여해도 되는지 계정 시스템 등에 물어본 후, 
    사용자에게 해당 토큰을 발급이 가능하면 토큰을 발급하고, 토큰에 대한 정보를 내부(토큰 저장소)에 저장`해 둡니다.
- 3. 
    - 이렇게 생성된 토큰은 API 클라이언트로 저장이 됩니다.
- 4. 
    - API 클라이언트는 API를 호출할때 이 `토큰을 이용해서 Resource Server(API 서버)에 있는 API를 호출`합니다.
- 5. 
    - 이때 호출되는 API는 관리자 권한을 가지고 있어야 사용할 수 있기 때문에, Resource Server가 토큰 저장소에서 토큰에 관련된 사용자 계정, 
    권한 등의 정보를 가지고 옵니다. 이 토큰에 `(관리자)admin 권한이 부여되어 있기 때문에, API 호출을 허용`합니다. 
    - 위에 정의한 시나리오에서는 그 사용자가 속한 "회사"의 사용자 정보만 조회할 수 있습니다. 
    - 라는 전제 조건을 가지고 있기 때문에 API 서버는 추가로 사용자 데이터베이스에서 이 사용자가 속한 "회사" 정보를 찾아와야 합니다.
- 6. 
    - API 서버는 응답을 보냅니다.

## JWT와 같은 Claim 기반의 토큰 흐름

![oauth](/images/jwttoken.png)

- 1. 토큰을 생성 요청하는 방식은 동일합니다. 마찬가지로 `사용자를 인증한 다음에 토큰을 생성`합니다.
- 2. 다른 점은 생성된 토큰에 관련된 정보를 별도로 저장하지 않는다는 것입니다. `토큰에 연관되는 사용자 정보나 권한 등을 토큰 자체에 넣어서 저장`합니다.
- 3. API를 호출하는 방식도 동일합니다.
- 4. Resource Server (API 서버)는 `토큰 내에 들어있는 사용자 정보를 가지고 권한 인가 처리를 하고 결과를 리턴`합니다.

결과적으로 차이점은 토큰을 생성하는 단계에서는 `생성된 토큰을 별도로 서버에서 유지할 필요가 없으며` 토큰을 사용하는 API 서버 입장에서는  
API 요청을 검증하기 위해서 토큰을 가지고 사용자 정보를 별도로 계정 시스템 등에서 조회할 필요가 없습니다.

## 많은 프로그래밍 언어의 지원

JWT 는 C, Java, Python, C++, R, C#, PHP, JavaScript, Ruby, Go, Swift 등 대부분의 주류 프로그래밍 언어에서 지원됩니다.

## 자가 수용적 (self_contained)

JWT 는 `필요한 모든 정보를 자체적으로` 지니고 있습니다.  
JWT 시스템에서 발급된 토큰은, `토큰에 대한 기본정보` 그리고 `전달 할 정보` (로그인시스템에서는 유저 정보) 그리고 토큰이 `검증됐다는것을 증명해주는 signature` 를 포함하고있습니다.

## 쉬운 전달

JWT 는 자가수용적이므로, 두 개체 사이에서 손쉽게 전달 될 수 있습니다. 웹서버의 경우 HTTP의 헤더에 넣어서 전달 할 수도 있고, URL 의 파라미터로 전달 할 수도 있습니다.

## Claim (메시지) 기반

JWT는 Claim를 JSON형태로 표현하는 것인데, 
JSON은 "\n"등 개행문자가 있기 때문에, REST API 호출시 HTTP Header등에 넣기가 매우 불편합니다. 
그래서, JWT에서는 이 Claim JSON 문자열을 BASE64 인코딩을 통해서 하나의 문자열로 변환합니다.

- 대표적으로 변조 방지 
메세지가 변조되지 않았음을 증명하는 것을 무결성(Integrity)라고 하는데, 
무결성을 보장하는 방법 중 많이 사용되는 방법이 서명(Signature)이나 HMAC을 사용하는 방식입니다

즉 원본 메시지에서 해쉬값을 추출한 후, 이를 비밀 키를 이용해서 복호화 시켜서 토큰의 뒤에 붙입니다. 이게 HMAC방식인데, 누군가 이 메시지를 변조를 했다면, 변조된 메시지에서 생성한 해쉬값과 토큰 뒤에 붙어있는 HMAC 값이 다르기 때문에 메시지가 변조되었음을 알 수 있습니다. 다른 누군가가 메시지를 변조한 후에, 새롭게 HMAC 값을 만들어내려고 하더라도, HMAC은 앞의 비밀키를 이용해서 복호화 되었기 때문에, 이 비밀키를 알 수 없는 이상 HMAC을 만들어 낼 수 없습니다.

## JWT의 문제점

- 1. 길이
    - Claim에 넣는 데이터가 많아질수록, JWT 토큰의 길이가 길어집니다. 
    - API 호출등에 사용할 시에, 매 호출마다 헤더에 붙어서 가야하기 때문에, 길이가 길다는 것은 그만큼 네트워크 대역폭 낭비가 심하다는 의미입니다.

- 2. 한번 발급된 토큰은 값을 수정하거나 폐기가 불가
    - JWT는 토큰 내에 모든 정보를 다 가지고 있기 때문에, 한번 발급된 토큰에 대한 변경은 서버에서는 더이상 불가능합니다. 
    - 예를 들어 토큰을 잘못 발행해서 삭제하고 싶더라도, Sinagture만 맞으면 맞는 토큰으로 인식을 하기 때문에, 서버에는 한번 발급된 토큰의 정보를 바꾸는 일등이 불가능합니다.
    - 그래서 만약에 JWT를 쓴다면, Expire time을 꼭 명시적으로 두도록하고, refresh token등을 이용해서, 중간중간 토큰을 재발행하도록 해야합니다.

- 3. 보안
    - JWT는 기본적으로 Claim에 대한 정보를 암호화하지 않는다. 단순히 BASE64로 인코딩만 하기 때문에, 중간에 패킷을 가로채거나, 
    기타 방법으로 토큰을 취득했으면 토큰 내부 정보를 통해서 사용자 정보가 누출 될 수 있는 가능성이 있습니다. 
    - 특히 자바스크립트 기반의 웹 클라이언트의 경우 브라우저상의 디버거등을 통해서 토큰이 노출될 가능성이 높습니다. 
    - 그래서, 이를 보완하는 방법으로는 토큰 자체를 암호화하는 방법이 잇습니다. JSON을 암호화하기 위한 스펙으로는 JWE(JSON Web Encryption)이 있습니다.

## JWT를 유용하게 사용하는 상황

- 회원 인증

JWT 를 사용하는 가장 흔한 경우 회원 인증 입니다.  
유저가 로그인을 하면, 서버는 `유저의 정보에 기반한 토큰을 발급하여 유저에게 전달`해줍니다.  
그 후, `유저가 서버에 요청을 할 때 마다 JWT를 포함하여 전달`합니다.  
서버가 클라이언트에게서 요청을 받을때 마다, `해당 토큰이 유효하고 인증됐는지 검증`을 하고,  
유저가 요청한 작업에 권한이 있는지 확인하여 작업을 처리합니다.  
중요! `서버측에서는 유저의 세션을 유지 할 필요가 없습니다.`  
즉 유저가 로그인되어있는지 안되어있는지 신경 쓸 필요가 없고,  
유저가 요청을 했을때 `토큰만 확인`하면 되니, 세션 관리가 필요 없어서 `서버 자원을 많이 아낄 수 있다.`

- 정보 교류

JWT는 두 개체 사이에서 `안정성있게 정보를 교환하기에 좋은 방법`입니다.  
그 이유는, 정보가 sign 이 되어있기 때문에 정보를 보낸이가 바뀌진 않았는지,  
또 `정보가 도중에 조작되지는 않았는지 검증`할 수 있습니다.

## JWT 코드의 구조

JWT 는 . 을 구분자로 `3가지의 문자열`로 되어있습니다. 구조는 다음과 같이 이루어져있습니다

aaa.bbb.ccc -> header.payload.signature

(헤더, 내용, 서명) 순으로 이루어있는 문자열 입니다.

### 헤더 Header

Header는 두가지 정보를 지니고 있습니다.

- typ : 토큰의 타입을 지정합니다.
- alg : 해싱 알고리즘을 지정합니다. 해싱 알고리즘은 보통 HMAC SHA256 혹은 RSA가 사용되며 토큰을 검증 할 때 사용되는 signature 부분에서 사용됩니다.

### 정보 Payload

Payload 부분에는 `토큰에 담을 정보`가 들어있습니다.  
여기에 담는 정보의 한 `‘조각’ 을 클레임(claim)` 이라고 부르고,  
이는 name / value 의 한 쌍으로 이뤄져있습니다.  
토큰에는 여러개의 클레임 들을 넣을 수 있습니다.

클레임 의 종류는 다음과 같이 크게 세 분류로 나뉘어져있습니다.

- 등록된 (registered) 클레임
- 공개 (public) 클레임
- 비공개 (private) 클레임

#### 등록된 (registered) 클레임

등록된 클레임들은 서비스에서 필요한 정보들이 아닌,  
토큰에 대한 정보들을 담기위하여 이름이 이미 정해진 클레임들입니다.  
등록된 클레임의 `사용은 모두 선택적 (optional)`이며,  
이에 포함된 클레임 이름들은 다음과 같습니다.

- iss: 토큰 발급자 (issuer)
- sub: 토큰 제목 (subject)
- aud: 토큰 대상자 (audience)
- exp: 토큰의 만료시간 (expiraton), 시간은 NumericDate 형식으로 되어있어야 하며 (예: 1480849147370) 언제나 `현재 시간보다 이후`로 설정되어있어야합니다.
- nbf: Not Before 를 의미하며, 토큰의 활성 날짜와 비슷한 개념입니다. 여기에도 NumericDate 형식으로 날짜를 지정하며, `이 날짜가 지나기 전까지는 토큰이 처리되지 않습니다.`
- iat: 토큰이 발급된 시간 (issued at), 이 값을 사용하여 `토큰의 age 가 얼마나 되었는지 판단` 할 수 있습니다.
- jti: JWT의 고유 식별자로서, 주로 `중복적인 처리를 방지`하기 위하여 사용됩니다. 일회용 토큰에 사용하면 유용합니다.

#### 공개 (public) 클레임

공개 클레임들은 `충돌이 방지된 (collision-resistant)` 이름을 가지고 있어야 합니다.  
충돌을 방지하기 위해서는, `클레임 이름을 URI 형식`으로 짓습니다.

~~~
{
    "http://localhost:3000/boardEvent/write": true
}
~~~

#### 비공개 (private) 클레임

등록된 클레임도아니고, 공개된 클레임들도 아닙니다.  
양 측간에 (보통 클라이언트 <->서버) 협의하에 사용되는 클레임 이름들입니다.  
공개 클레임과는 달리 이름이 중복되어 충돌이 될 수 있으니 사용할때에 유의해야합니다.

~~~
{
    "username": "jjunpro"
}
~~~
 
결국 JWT Token 을 전체적으로 확인해보면 

~~~
{
    "iss": "admin",
    "exp": "148794004800",
    "http://localhost:3000/boardEvent/write": true,
    "userId": "38048322648",
    "username": "jjunpro"
}
~~~

이런 형식의 토큰 코드가 완성됩니다.

### 서명 (signature)

JSON Web Token 의 마지막 부분은 바로 서명(signature) 입니다.  
이 서명은 `헤더의 인코딩값`과, `정보의 인코딩값`을 합친 후 주어진 비밀키로 해쉬를 하여 생성합니다.

# Spring Boot 개발환경 설정

Spring Boot 프로젝트를 만드는 가장 빠른 방법은 Spring Initializr 을 사용하여 기본 코드를 생성하는 것입니다.  
<a href="https://start.spring.io/" target="_blank">Spring Initializr</a> 
사이트로 접속하여 dependencies 를 다음과같이 선택 생성해 줍니다.

- DEVELOPER TOOLS
    - `Lombok`
        - 상용구 코드를 줄이는 데 도움이 되는 Java 주석 라이브러리.
- WEB
    - `Spring Web WEB`
        - 스프링 MVC를 사용하여 RESTful을 포함한 웹을 구축합니다. Apache Tomcat을 기본 내장형 컨테이너로 사용합니다.
- TEMPLATE ENGINES
    - `Thymeleaf`
        - 웹 및 독립 실행형 환경 모두를 위한 최신 서버측 Java 템플릿 엔진입니다. HTML을 브라우저 및 정적 프로토타입으로 올바르게 표시할 수 있습니다.
- SECURITY
    - `Spring Security`
        - Spring 애플리케이션을 위한 사용자 지정이 가능한 높은 인증 및 액세스 제어 프레임워크.
- SQL
    - `Spring Data JPA`
        - SpringData 및 Hibernate를 사용하여 Java Persistence API를 사용하여 SQL 저장소에 데이터를 유지합니다.
    - `H2 Database`

![spring-Initializr](/images/spring-Initializr.png)

## Spring Security 인증 일시 해제

![project-import](/images/spring-security-auth.png)

프로젝트를 가동시킨 후 local 서버로 접속하면 다음과 같은 인증 화면이 초기에 나오도록 되어있습니다.  
하지만 저는 개발자 입장으로 수시로 서버에 api 값을 전송하고 받아야하는데 인증 화면이 계속해서 나오면  
많이 곤란합니다. spring security 초기값 인증을 풀어줄 수 있도록 하겠습니다.

> demo.config.SecurityConfig

~~~
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable();

        http
                .headers()
                .frameOptions()
                .disable();
    }
}
~~~

## JPA 엔티티 Model Class 만들기

> demo.model.BaseEntity

BaseEntity 생성하기  
모든 Entity 는 기본적으로 상속받아 사용 받을 수 있는  
BaseEntity 추상 클래스를 생성하고 중복되는 필드값을 공통으로 관리하도록 합니다.

~~~
/**
 * Entity 기본이 되는 추상 클래스 모든 Entity 는 기본적으로 상속받아 사용받을 수 있도록 합니다. 중복되는 변수값을 공통으로 관리합니다.
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreatedDate
    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime modifiedDate;
}
~~~

> demo.model.Account

~~~
@Table
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    @Builder
    public Account(String username, String password, UserRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
~~~

- `@Getter` 
    - lombok 자동으로 getter 를 생성해 줍니다.
- `@Entity` 
    - 엔티티 클래스임을 지정하며 테이블과 매핑되도록 합니다.
- `@Table` 
    - 엔티티가 매핑될 테이블을 지정하고 생략시 엔티티 클래스 이름과 같은 테이블로 매핑된다.
- `@Id` 
    - 해당 프로퍼티가 테이블의 주키(primary key) 역할을 한다는 것을 나타낸다
- `@GeneratedValue` 
    - 주키의 값을 위한 자동 생성 전략을 명시하는데 사용한다. 기본값은 AUTO 로, MySQL의 auto_increment와 같이 자동증가하는 정수형 값이 됩니다.
- `@Column` 
    - 칼럼의 이름을 이용하여 지정된 필드나 속성을 테이블의 칼럼에 매핑 한다. 생략되면 속성과 같은 이름의 칼럼으로 매핑된다. 칼럼의 NULL 허용여부, 길이등을 속성으로 표시한다. 칼럼의 기본값은 columnDefinition 으로 정의하면 된다. columnDefinition = "number(5) default 0"

Model Account 클래스를 보시면 `setter가 없습니다.`  
이는 의도한 것인데, `getter와 달리 setter는 무분별하게 생성하지 않습니다.`  
domain 인스턴스에 변경이 필요한 이벤트가 있을 경우 그 이벤트를 나타낼 수 있는 메소드를 만들어야하며, `무분별하게 값을 변경하는 setter는 최대한 멀리`하시는게 좋습니다.  
(예를 들어, 주문취소 같은 경우 `setOrderStatus()가 아니라 cancelOrder()`를 만들어서 사용하는 것입니다.  
똑같이 orderStatus를 변경할지라도, `그 의도와 사용범위가 명확한 메소드`를 만드는것이 중요합니다.)

~~~
> 잘못된 사용

public class Order{
    public void setStatus(boolean status){
        this.status = status
    }
}

public void 주문서비스의_취소메소드 (){
   order.setStatus(false);
}

> 올바른 사용

public class Order{
    public void cancelOrder(){
        this.status = false;
    }
}
public void 주문서비스의_취소메소드 (){
   order.cancelOrder();
}
~~~

기본 생성자 또한 `@NoArgsConstructor(access = AccessLevel.PROTECTED)` lombok 어노테이션으로 처리하였는데  
`접근 권한을 최소화 하기 위해`서 사용했습니다.  
JPA에서는 `프록시를 생성을 위해서 기본 생성자를 반드시 하나를 생성`해야합니다.  
`기본 생성자를 아무 이유 없이 열어두는 것은 객체 생성 시 안전성`을 심각하게 떨어트린다고 생각합니다  
이때 접근 권한이 protected 이면 됩니다. 굳이 `외부에서 생성을 열어둘 필요가 없습니다.`  

- `@Builder` 
    - 해당 클래스의 빌더패턴 클래스를 생성 (생성자 상단에 선언시 생성자에 포함된 필드만 빌더에 포함)

기본생성자도 `AccessLevel.PROTECTED로 막아놓고`,  
`setter 메소드도 없는 이 상황`에서 어떻게 값을 채워 `DB에 insert` 해야할까요?

기본적인 구조는 `생성자를 통해 최종 값을 채운후 DB에 Insert` 하는것이며,  
값 `변경이 필요한 경우 해당 이벤트에 맞는 public 메소드를 호출`하여 변경하는 것을 전제로 합니다.  
여기서 `생성자 대신에 @Builder`를 통해 제공되는 빌더 클래스를 사용합니다.  
생성자나 빌더나 생성시점에 값을 채워주는 역할은 똑같습니다.  
다만, `생성자의 경우 지금 채워야할 필드가 무엇인지 명확히 지정할수가 없습니다.`  
예를 들어 아래와 같은 생성자가 있다면

~~~
public Example(String a, String b){
    this.a = a;
    this.b = b;
}
~~~

개발자가 new Example(b,a)처럼 `a와 b의 위치를 변경 해도` 실제로 코드를 실행하기전까진 전혀 `문제를 찾을수가 없습니다.`  
하지만 빌더를 사용하게 되면 아래와 같이

~~~
Example.builder()
    .a(a)
    .b(b)
    .build();
~~~

어느 필드에 `어떤 값을 채워야 할지 명확하게 인지`할 수 있습니다.  
<a href="https://using.tistory.com/71" target="_blank">빌더 패턴 소개</a>
에서 더 정확하게 확인하실 수 있습니다.

## UserRole Enum 열거형 상수 정의

> demo.constant.UserRole

~~~
public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_ADMIN");

    private final String role_user;

    UserRole(String role_user) {
        this.role_user = role_user;
    }

    @Override
    public String getKey() {
        return name();
    }

    @Override
    public String getValue() {
        return role_user;
    }
}
~~~

유저의 권한을 열거향 상수 Enum 으로 정의하여 값을 주도록 하겠습니다.  
Enum을 사용하면서 우리가 얻을 수 있는 이점

- 1. 코드가 `단순해지며, 가독성`이 좋습니다.
- 2. `인스턴스 생성과 상속을 방지`하여 `상수값의 타입안정성이 보장`됩니다.
- 3. enum class를 사용해 새로운 상수들의 타입을 정의함으로 `정의한 타입이외의 타입을 가진 데이터값을 컴파일시 체크`한다.
- 4. 키워드 enum을 사용하기 때문에 `구현의 의도가 열거임을 분명하게` 알 수 있습니다.

### Enum 관리 모듈

> demo.controller.EnumController

특정 enum 타입이 갖고 있는 모든 값을 출력시키는 기능은 Class의 getEnumConstants() 메소드를 사용하면 쉽게 해결할 수 있습니다.  
enum의 리스트는 select box 즉, view영역에 제공되어야 하기 때문에 Controller에서 전달하도록 만들어보겠습니다.

~~~
@RestController
public class EnumController {

    @GetMapping("/enum")
    public Map<String, Object> getEnum() {
        Map<String, Object> enums    = new LinkedHashMap<>();
        Class               userRole = UserRole.class;
        enums.put("userRole", userRole.getEnumConstants());

        return enums;
    }
}
~~~

![project-import](/images/enum-controller.png);

각 enum의 전체 리스트는 출력되었는데 뭔가 부족하지 않으신가요?  
바로 `enum의 value값이 출력되지 않았습니다.`  
enum은 인스턴스가 아닌 타입입니다. 그래서 view로 전달되었을 때는 name만 남게 됩니다.  
이를 해결하기 위해 `enum의 name과 value를 모두 가지는 Dto를 만들어보겠습니다.`  
Dto를 만들기 전, 앞으로의 모든 `enum들을 dto에서 사용할 수 있도록 인터페이스를 하나 만들겠습니다.`  
해당 인터페이스의 이름은 EnumModel이라 하겠습니다.

> demo.constant.EnumModel

~~~
public interface EnumModel {
    String getKey();
    String getValue();
}
~~~

enum의 name(좀더 명확한 이름을 위해 key로 하였습니다.)과 value를 사용하기 위해 추상메소드를 추가하였습니다.  
UserRole이 이를 구현(implements)하도록 변경하겠습니다.

~~~
public enum UserRole implements EnumModel {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");
    
    private String roleName;
    
    UserRole(String roleName) {
        this.roleName = roleName;
    }
    
    @Override
    public String getKey() {
        return name();
    }
    
    @Override
    public String getValue() {
        return roleName;
    }
}
~~~

enum 타입이 EnumModel을 구현하도록 변경하였습니다.  
Java의 다형성으로,  
인터페이스를 구현하게 될 경우 UserRole 이 EnumModel 타입으로 다룰수 있게 되었습니다.  

EnumModel을 이용하여 실제 값을 가지고 view에 전달할 수 있는 DTO를 만들겠습니다.  
DTO의 이름은 EnumValue입니다.

> demo.constant.EnumValue

~~~
public class EnumValue {

    private String key;
    private String value;

    public EnumValue(EnumModel enumModel) {
        key = enumModel.getKey();
        value = enumModel.getValue();
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
~~~

EnumValue는 생성자 인자로 위에서 만든 EnumModel을 받도록 하여 UserRole 를 받을 수 있습니다.  
Controller에 EnumValue를 이용한 메소드를 추가해보겠습니다.

> demo.controller.EnumController

~~~
...
@GetMapping("/value")
public Map<String, List<EnumValue>> getEnumValue() {
    Map<String, List<EnumValue>> enumValues = new LinkedHashMap<>();

    enumValues.put("userRole", toEnumValues(UserRole.class));

    return enumValues;
}

private List<EnumValue> toEnumValues(Class<? extends EnumModel> e) {
    /*
     * Java8이 아닐경우
     * List<EnumValue> enumValues = new ArrayList<>();
     * for (EnumModel enumType : e.getEnumConstants()) {
     *     enumValues.add(new EnumValue(enumType));
     * }
     * return enumValues;
     */
    return Arrays
            .stream(e.getEnumConstants())
            .map(EnumValue::new)
            .collect(Collectors.toList());
}
~~~

EnumModel 배열을 EnumValue 리스트로 전환하는 일을 하는 toEnumValues를 구현하여 getEnumValue에서는 해당 메소드를 호출만 하도록 변경하였습니다.  
toEnumValues의 경우 Java8의 stream을 사용하면 아주 깔끔한 코드로 전환이 가능하지만,  
혹시나 아직 Java8 문법이 어색하시거나 그 이하 버전을 사용하실 경우를 대비하여 주석으로 하위버전 코드를 추가하였습니다.  
그럼 위 코드가 정상적으로 View에 전달되는지 확인해보겠습니다.

![project-import](/images/enum-controller-value.png);

원하는 대로 key와 value가 나오는 것을 확인할 수 있습니다!  
그럼 이제 다 끝난걸까요? ApiController는 더이상 수정할 부분이 없을까요?  
위 코드를 다시 보시면 `실제로 사용하기에는 부족함이 많은 것을 알 수 있습니다.`

`매번 Controller를 호출할 때마다 EnumValue로 전환하는 작업을 수행`해야 합니다.  
다른 Controller/Service/Repository에서 `enum의 리스트를 사용하고 싶을 경우 중복코드가 발생`합니다.  
위 2가지 문제를 해결해야 한다면 어떤 방법이 가장 먼저 떠오르시나요?  
아마 대부분 `Spring의 Bean으로 등록`해야겠다는 생각이 드실것 같습니다.  
`어플리케이션이 시작할때만 EnumValue로 전환하는 작업을 수행`하고,  
그 이후에는 이미 등록된 것들을 호출하여 원하는 곳에서 사용하면 될것 같습니다.  
enum 타입들을 관리하는 `모듈의 이름을 EnumMapper로 하여` 개발을 진행하겠습니다.

> demo.constant.EnumMapper

~~~
public class EnumMapper {

    private Map<String, List<EnumValue>> factory = new HashMap<>();

    private List<EnumValue> toEnumValues(Class<? extends EnumModel> e) {
        /*
         * Java8이 아닐경우
         * List<EnumValue> enumValues = new ArrayList<>();
         * for (EnumModel enumType : e.getEnumConstants()) {
         *     enumValues.add(new EnumValue(enumType));
         * }
         * return enumValues;
         */
        return Arrays
                .stream(e.getEnumConstants())
                .map(EnumValue::new)
                .collect(Collectors.toList());
    }

    public void put(String key, Class<? extends EnumModel> e) {
        factory.put(key, toEnumValues(e));
    }

    public Map<String, List<EnumValue>> getAll() {
        return factory;
    }

    public Map<String, List<EnumValue>> get(String keys) {
        return Arrays
                .stream(keys.split(","))
                .collect(Collectors.toMap(Function.identity(), key -> factory.get(key)));
    }
}
~~~

혹시나 모든 enum 타입을 가져오는 것외에 지정한 enum만 가져오는 기능이 필요할 수도 있기에 get()도 추가로 구현하였습니다.  
어플리케이션 내부에 저장하기 위해 factory map를 생성하되, 다른 클래스에서 직접 접근하지 못하도록 private으로 막았습니다.  
이렇게 할 경우 외부 클래스에서 접근하려면 public으로 오픈한 put(), get(), getAll()만 가능하기 때문에 toEnumValues를 강제할 수가 있습니다.  
여기서 주의 깊게 보셔야 할 것은 **생성자에서 commissionType, commissionCutting을 등록하지 않은 점입니다.

이는 EnumMapper 자체가 단독 모듈로서 사용하기 위함인데, 만약 생성자에서 commissionType, commissionCutting을 추가하게 될 경우   
다른 프로젝트에서 EnumMapper를 사용할 때에는 EnumMapper 내부의 코드를 수정해야 하는 일이 발생합니다.  
이는 OCP원칙에 위반되기도 하며, 유지보수 하기가 어렵게 만드는 일이기 때문에 항상 공통 모듈을 만들때는 이 점을 주의해야 합니다.

이렇게 만든 EnumMapper를 Bean으로 등록하겠습니다.

> demo.config.WebConfig

~~~
@Order(1)
@Configuration
public class WebConfig {

    @Bean
    public EnumMapper enumMapper() {
        EnumMapper enumMapper = new EnumMapper();
        enumMapper.put("UserRole", UserRole.class);
        
        return enumMapper;
    }
}
~~~

스프링의 @Configuration 어노테이션은 어노테이션기반 환경구성을 돕는다.  
webconfig 를 따로 만들어서 빈을 등록해주고 등록 순서를 @Order(1) 로 가장 첫번째로 실행할 수 있도록 등록합니다.

<a href="http://tech.javacafe.io/spring/2018/11/04/%EC%8A%A4%ED%94%84%EB%A7%81-Configuration-%EC%96%B4%EB%85%B8%ED%85%8C%EC%9D%B4%EC%85%98-%EC%98%88%EC%A0%9C/" target="_blank">스프링 @Configuration 어노테이션 예제</a>

Mapper 확인을 위해서 Controller 를 수정하겠습니다.

> demo.controller.EnumController

~~~
@RestController
@RequiredArgsConstructor
public class EnumController {

    private final EnumMapper enumMapper;

    @GetMapping("/enum")
    public Map<String, Object> getEnum() {
        Map<String, Object> enums    = new LinkedHashMap<>();
        Class               userRole = UserRole.class;
        enums.put("userRole", userRole.getEnumConstants());

        return enums;
    }

    @GetMapping("/mapper")
    public Map<String, List<EnumValue>> getMapper() {

        return enumMapper.getAll();
    }
}
~~~

## model 을 관리할 repository 생성하기

~~~
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUserId(String userId);
}
~~~

보통 `ibatis/MyBatis 등에서 Dao`라고 불리는 `DB Layer 접근자`입니다.  
`JPA에선 Repository`라고 부르며 `인터페이스로 생성`합니다.  
단순히 인터페이스를 생성후,  
`JpaRepository<Entity클래스, PK타입>를 상속`하면 기본적인 `CRUD 메소드가 자동생성` 됩니다.  
특별히 @Repository를 추가할 필요가 없습니다.

### repository save & find Test

> src/test/java.ProjectApplicationTests

DB가 설치가 안되어있는데 Repository를 사용할 수 있는 이유는,  
`SpringBoot에서의 테스트 코드는 메모리 DB인 H2를 기본적으로 사용`하기 때문입니다.  
테스트 코드를 실행하는 시점에 H2 DB를 실행시킵니다.  
테스트가 끝나면 `H2 DB도 같이 종료`됩니다.

정상적으로 save & find 가 잘되는지 Test를 해보겠습니다.

~~~
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    @Autowired
    private AccountRepository accountRepository;

    @After
    public void cleanup() {
        /*
         * 이후 테스트 코드에 영향을 끼치지 않기 위해
         * 테스트 메소드가 끝날때 마다 respository 전체 비우는 코드
         */
        accountRepository.deleteAll();
    }

    @Test
    public void userInsert() {
        //given
        accountRepository.save(
                Account.builder()
                        .username("jjunpro")
                        .password("1234")
                        .role(UserRole.USER)
                        .build()
        );

        // when
        List<Account> userList = accountRepository.findAll();

        // then
        Account account = userList.get(0);
        assertThat(account.getUsername(), is("jjunpro"));
        assertThat(account.getPassword(), is("1234"));
        assertThat(account.getRole(), is(UserRole.USER));
    }
}
~~~

![project-import](/images/repository-test.png)

정상적으로 생성하고 값을 가져오는것을 확인 할 수 있습니다.  
생성자가 아닌 builder 로 값을 생성하는 것까지도 확인할 수 있었습니다.

- `given`
    - 테스트 기반 환경을 구축하는 단계
    - 여기선
    - @builder의 사용법도 같이 확인
- `when`
    - 테스트 하고자 하는 행위 선언
    - 여기선 Posts가 DB에 insert 되는것을 확인하기 위함
- `then`
    - 테스트 결과 검증
    - 실제로 DB에 insert 되었는지 확인하기 위해 조회후, 입력된 값 확인

<a href="https://www.youtube.com/watch?v=tyZMdwT3rIY" target="_blank">JUnit 강좌 영상</a>

given, when, then은 BDD(Behaviour-Driven Development)에서 사용하는 용어입니다.  
JUnit에선 이를 명시적으로 지원해주지 않아 주석으로 표현했습니다.

<a href="https://jojoldu.tistory.com/228" target="_blank">전문 BDD 프레임워크로 Groovy기반의 Spock</a>

이제 정상적으로 DB에 자료가 쌓이는지 확인하기 위해서 Controller 를 구현하여 직접 눈으로 확인해 보겠습니다.

## REST APIS 만들기

### UserService 구현하기

> demo.service.AccountService

모든 행동은 Controller 에서 행하지 않고 Service 에서 따로 행동하도록 하겠습니다. 
우선 Service Interface 만들어 줍니다.

~~~
public interface AccountService extends UserDetailsService {

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    Account saveOrUpdateAccount(Account account);
}
~~~

- saveOrUpdateAccount 유저의 정보를 DB에 생성 or 변경 의 역할을 합니다.

> demo.service.AccountServiceImpl
 
~~~
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder   passwordEncoder;

    @Override
    public Account saveOrUpdateAccount(Account account) {
        account.encodePassword(this.passwordEncoder);

        return this.accountRepository.save(account);
    }
}
~~~

PasswordEncoder 를 사용하기 위해서 WebConfig 에 Bean 을 등록합니다.

~~~
@Order(1)
@Configuration
public class WebConfig {

    ...

    // Spring Security 5 권장하는 인코더
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
~~~

보시면 `AccountRepository 필드에 @Autowired가 없습니다.`  
스프링프레임워크에선 `Bean 을 주입받는 방식`들이 아래와 같이 있는데요.

- @Autowired
- setter
- 생성자

이중 가장 `권장하는 방식이 생성자로 주입받는 방식`입니다.  
(`@Autowired는 비권장방식`입니다.)  
즉, `생성자로 Bean 객체를 받도록 하면 @Autowired와 동일한 효과`를 볼 수 있다는 것입니다.

그러면 위에서 생성자는 어디있을까요?  
바로 `@AllArgsConstructor` 에서 해결해줍니다.  
모든 필드를 인자값으로 하는 생성자를 Lombok의 @AllArgsConstructor이 대신 생성해 준 것입니다.  
위 코드는 실제로는 아래와 같은 형태입니다.

~~~
...생략
public saveOrUpdateAccount(AccountRepository accountRepository) {
	this.accountRepository = accountRepository;
}
~~~

생성자를 직접 안쓰고 `Lombok 어노테이션을 사용한 이유`는 간단합니다.  
`해당 클래스의 의존성 관계가 변경될때마다 생성자 코드를 계속해서 수정하는 번거로움을 해결`하기 위함입니다.  
(Lombok 어노테이션이 있으면 해당 컨트롤러에 새로운 서비스를 추가하거나, 기존 컴포넌트를 제거하는 등이 발생해도 생성자 코드는 전혀 손대지 않아도 됩니다.)

### AccountController 구현하기

유저 리소스 용 REST API 

| URL | request | response | description |
|:---|---|---|---|
| /api/account | GET | 200, [{id: '1', name:''}, {id:'2', name:''}] | 모든 유저정보를 가져옵니다. |
| /api/account | POST | 201, 생성된 user값 | 새로운 유저 생성 |
| /api/user/{id} | GET | 200, {id:'1', name:''} | ID로 특정 user 조회 |
| /api/user/{id} | PUT | 204, 업데이트된 값 | ID로 user 업데이트 |
| /api/user/{id} | DELETE | 204, 내용 없음 | ID로 user 삭제 |

> demo.controller.AccountController

~~~
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {

    private final AccountServiceImpl accountService;

    @PostMapping("")
    public ResponseEntity<?> insertAccount(
            @Valid @RequestBody AccountFormDTO dto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Account accountDB = accountService.saveOrUpdateAccount(dto.toEntity());

        return new ResponseEntity<>(accountDB, HttpStatus.CREATED);
    }
}
~~~

### Controller 에서 사용할 DTO 클래스를 생성하기

> demo.dto.AccountFormDTO

~~~
@Setter
@Getter
public class AccountFormDTO {

    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "영어 혹은 숫자만 입력 가능합니다.")
    @Length(min = 1, max = 10)
    @NotBlank(message = "아이디는 필수로 작성해야 합니다.")
    private String username;

    private String password;

    private final UserRole role = UserRole.USER;

    public Account toEntity() {
        return Account.builder()
                .username(this.username)
                .password(this.password)
                .role(this.role)
                .build();
    }
}
~~~

해당 `DTO 클래스에서는 @Setter 어노테이션을 사용`했습니다.  
`Controller에서 @RequestBody로 외부에서 데이터`를 받는 경우엔  
`기본생성자 + set메소드를 통해서만 값이 할당`됩니다.  
그래서 이때만 setter 를 허용합니다.

여기서 `Entity 클래스와 거의 유사한 형태임에도 DTO 클래스를 추가로 생성`했는데요.  
절대로 `테이블과 매핑되는 Entity 클래스를 Request/ Response 클래스로 사용해서는 안됩니다.`  
Entity 클래스는 가장 Core 한 클래스라고 보시면 되는데요.  
수많은 서비스 클래스나 비지니스 로직들이 `Entity 클래스를 기준으로 동작`합니다.  
Entity 클래스가 변경되면 여러 클래스에 영향을 끼치게 되는 반면 
`Request와 Response용 DTO는 View를 위한 클래스라 정말 자주 변경이 필요`합니다.  
View Layer와 DB Layer를 철저하게 `역할 분리`를 하는게 좋습니다.  
실제로 Controller에서 결과값으로 여러 테이블을 조인해서 줘야할 경우가 빈번하기 때문에   
Entity 클래스만으로 표현하기가 어려운 경우가 많습니다.  
꼭꼭 `Entity 클래스와 Controller에서 쓸 DTO는 분리해서 사용`하시길 바랍니다.

### Account 새로운 유저 생성 확인

![controller](/images/new-accont.png)
 
Postmen 확인 결과 값이 정상 전송 되는동시에 유저 정보가 return 되는것을 확인햇습니다.  
DB에 정상 저장됬는지 마지막 확인을 해보겠습니다.

> application.properties

~~~
spring.h2.console.enabled = true
spring.h2.console.path = /h2-console
spring.datasource.url = jdbc:h2:mem:testdb
spring.datasource.driver-class-name = org.h2.Driver
spring.datasource.username = sa
spring.datasource.password = 1234
~~~

h2 설정을 해줘야 웹으로 확인 가능합니다.

http://localhost:8080/h2-console

![controller](/images/h2-console-test.png)  
![controller](/images/new-accont-db.png)

정상적으로 h2 test db에 쌓인것을 확인할 수 있었습니다.

### 생성시간/수정시간 자동화 JPA Auditing

보통 Entity에는 해당 데이터의 생성시간과 수정시간을 포함시킵니다.  
`언제 만들어졌는지, 언제 수정`되었는지 등은 차후 `유지보수에 있어 굉장히 중요한 정보`이기 때문입니다.  
그렇다보니 매번 DB에 insert하기전, update 하기전에 날짜 데이터를 등록/수정 하는 코드가 여기저기 들어가게 됩니다.

~~~
// 생성일 추가 코드 예제
public void savePosts(){
    ...
    posts.setCreateDate(new LocalDate());
    postsRepository.save(posts);
    ...
}
~~~

이런 단순하고 반복적인 코드가 `모든 테이블과 서비스 메소드에 포함되어야 한다고 생각하면` 어마어마하게 귀찮고 코드가 더러워지겠죠?  
그래서 이 문제를 해결하기 위해 `JPA Auditing`를 사용하겠습니다.

#### LocalDate 사용

여기서부터는 날짜 타입을 사용합니다.  
Java8 부터 LocalDate와 LocalDateTime이 등장했는데요.  
그간 Java의 기본 날짜 타입인 Date의 문제점을 제대로 고친 타입이라 Java8일 경우 무조건 써야한다고 생각하시면 됩니다.

#### BaseTime Entity 추상 클래스 생성

> demo.model.BaseEntity

~~~
/**
 * Entity 기본이 되는 추상 클래스 모든 Entity 는 기본적으로 상속받아 사용받을 수 있도록 합니다. 중복되는 변수값을 공통으로 관리합니다.
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreatedDate
    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime modifiedDate;
}
~~~

BaseEntity Entity 클래스는  
`모든 Entity들의 상위 클래스가 되어 Entity들의 createdDate, modifiedDate를 자동으로 관리하는 역할`입니다.

- `@MappedSuperclass` 
    - JPA Entity 클래스들이 BaseEntity 를 상속할 경우 필드들(createdDate, modifiedDate)도 컬럼으로 인식하도록 합니다.
- `@EntityListeners(AuditingEntityListener.class)` 
    - 엔티티를 DB에 적용하기 이전 이후에 커스텀 콜백을 요청할 수 있는 어노테이션이다. BaseEntity 클래스에 Auditing 기능을 포함시킵니다.
- `@CreatedDate` 
    - Entity 가 생성되어 저장될 때 시간이 자동 저장됩니다.
- `@LastModifiedDate` 
    - 조회한 Entity 의 값을 변경할 때 시간이 자동 저장됩니다.

그리고 `Account 클래스가 BaseEntity Entity를 상속`받도록 변경합니다.

~~~
public class Account extends BaseEntity {
... 생략
}
~~~

마지막으로 `JPA Auditing 어노테이션들을 모두 활성화 시킬수 있도록` Application 클래스에 활성화 어노테이션 하나를 추가하겠습니다.

> demo.Application

~~~
@EnableJpaAuditing // JPA Auditing 활성화
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
~~~

![controller](/images/new-account-datetime.png)

# Spring Security(스프링 시큐리티) 란 무엇인가?

스프링 시큐리티 레퍼런스에서는 자바 EE 기반의 엔터프라이즈 소프트웨어 애플리케이션을 위한 포괄적인 보안 서비스들을 제공하고  
`오픈 플랫폼`이면서 자신만의 인증 매커니즘을 간단하게 만들 수 있습니다.

스프링 시큐리티를 이해하기 위해서는 스프링 시큐리티가 `애플리케이션 보안을 구성하는 두가지 영역`에 대해서 알아야 합니다.

바로 `인증(Authentication)과 권한(Authorization)` 이라는 것입니다.

- 인증 : 애플리케이션의 작업을 수행할 수 있는 주체(사용자)라고 주장할 수 있는 것
- 권한 : 권한은 인증된 주체가 애플리케이션의 동작을 수행할 수 있도록 허락되있는지를 결정하는 것

권한 승인이 필요한 부분으로 접근하려면 인증 과정을 통해 주체가 증명 되어야만 한다는 것입니다.

## Spring Security 동작 방식

간단한 그림 표현  
![controller](/images/springsecurity-progress.jpg)

- 1. 클라이언트가 Resource에 URL을 통해 요청을 보낸다.
- 2. `DelegatingFilterProxy`는 요청을 Intercept! 가로채서 Spring Security빈으로 보낸다.
- 3. Spring Security빈은 인증 및 권한을 확인한다.
- 4. 권한이 잘 부여되어 있다면 리소스에 접근을 허용하고 그렇지 않다면 거부한다.

## DelegatingFilterProxy VS DispatcherServlet

스프링을 사용해본 분이라면, DelegatingFilterProxy가 DispatcherServlet과 동작방식이 매우 비슷한 것을 알 수 있다.  
둘다 <a href="https://ko.wikipedia.org/wiki/%ED%8D%BC%EC%82%AC%EB%93%9C_%ED%8C%A8%ED%84%B4" target="_blank">Facade</a>
로서, `사용자의 요청을 가장먼저 받아서 요청을 처리할 곳`으로 전가한다.  
그렇다면 사용자의 요청을 누가 먼저처리할까? 누가 우선순위가 더 높을까?

우선 순위 `DelegatingFilterProxy >>> DispatcherServlet`  
Filter 가 먼저 동작하고 DispatcherServlet 이 다음으로 동작한다.  
인증되지 않은 사용자는 Filter에서 먼저 걸려저셔 `Facade Controller에게 조차 전달되지 않는다.`

## DelegatingFilterProxy 등록하기 ==> Request Intercept & Filter

스프링의 다른 기능들처럼, 빈만 등록하면 절반은 끝난다.  
빈을 ApplicationContext 에 등록하기만 하면 스프링이 자동으로 생성해주고 요청을 가로채서 DelegatingFilterProxy 로 전달해준다.  
그 다음에는 사용하기만 하면 된다.

## Java Configuration

스프링 시큐리티 레퍼런스에서는 자바 기반의 설정으로 설명하고 있습니다. 그 이유는 무엇일까요?  
스프링 프레임워크 3.1에서부터 어노테이션을 통한 자바 설정을 지원하기 때문에 스프링 시큐리티 3.2부터는 XML 로 설정하지 않고도 간단하게 설정할 수 있도록 지원하기 때문입니다.  
원래 XML 기반의 설정에서는 web.xml 에 org.springframework.web.filter.DelegatingFilterProxy 라는 springSecurityFilterChain 을 등록하는 것으로 시작합니다만,  
자바 기반의 설정에서는 WebSecurityConfigurerAdapter 를 상속받은 클래스에  
@EnableWebSecurity 어노테이션을 명시하는 것만으로도 springSecurityFilterChain 가 자동으로 포함되어집니다.

~~~
@EnableWebSecurity 
public class WebSecurityConfig extends WebSecurityConfigurerAdapter { 
}
~~~

## package 생성 

- dto
    - (각 계층간의 데이터 교환을 위해 사용되는 개체 모음)
- security.filter
    - (req를 가로체서 사용자의 인증을 확인하는 클레스 모음)
- security.handler
    - (인증 후 결과를 처리해주는 핸들러 클래스 모음)
- security.jwt
    - (인증이 완료되면 JWT Token 을 발행해주는 클래스 모음)
- security.provider
    - (실제 인증을 하는 클래스  UserDetails 객체를 전달 받은 이후 실제 사용자의 입력정보와 UserDetails 객체를 가지고 인증을 시도한다.)
- security.token
    - (인증 전 토큰확인과 인증 후 토큰 확인을 해주는 클래스 모음)
    - PreAuthorizationToken (인증 전 토큰 확인)
    - PostAuthorizationToken (인증 후 토큰 확인)

## 인증 진행 순서 (로그인)

- 1. SecurityConfig 
- 2. FormLoginFilter (attemptAuthentication 인증 시도)
- 3. PreAuthorizationToken
- 4. FormLoginAuthenticationProvider
- 5. PostAuthorizationToken
- 6. successfulAuthentication
- 7. FormLoginAuthenticationSuccessHandler
- 8. JwtFactory 

### 1. SecurityConfig

<a href="https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/configuration/WebSecurityConfigurerAdapter.html" target="_blank">WebSecurityConfigurerAdapter DOCS</a>

configure 메소드는 `인증을 담당할 프로바이더 구현체를 설정, 필터 등록을 하는 메소드`이다.  
WebSecurityConfigurerAdapter 추상 클래스를 상속 받는다.  
`스프링 자동 보안 구성을 건너뛰고 사용자정의 보안구성`하기 위해서 상속받는 클래스

반대되는 전역을 보안하는 상속 클래스 `GlobalAuthenticationConfigurerAdapter` 존재합니다.

- 가장 먼저 인증이 필요한 서버에 `사용자가 접속시 가장 처음 Filter를 연결`해주는 역할

### 2. FormLoginFilter

<a href="https://docs.spring.io/spring-security/site/docs/4.2.12.RELEASE/apidocs/org/springframework/security/web/authentication/AbstractAuthenticationProcessingFilter.html" target="_blank">AbstractAuthenticationProcessingFilter DOCS</a>

- `AbstractAuthenticationProcessingFilter 추상 클래스`
    - 웹 기반 인증 요청에 사용. 폼 POST, SSO 정보 또는 기타 사용자가 제공한 `크리덴셜(크리덴셜은 사용자가 본인을 증명하는 수단)`을 포함한 요청을 처리.
    - 브라우저 기반 HTTP 기반 인증 요청 에서 사용되는 컴포넌트로 POST 폼 데이터를 포함하는 요청을 처리한다.
    - `인증 실패와 인증 성공 관련 이벤트를 관련 핸들러 메서드`를 가지고 있습니다.
    - 사용자 비밀번호를 다른 필터로 전달하기 위해서 `Authentication 객체를 생성하고 일부 프로퍼티를 설정`한다.

간단하게 설명하자면 인증요청에 해당하는 URL 을 감지하면 최초로 `AbstractAuthenticationProcessingFilter 를 구현한 클래스(FormLoginFilter)가 요청을 가로챈 후 Authentication 객체를 생성`한다.  
AbstractAuthenticationProcessingFilter 클래스의 `doFilter 메서드로 인해서 가장 처음 인증 attemptAuthentication 메서드를 실행`합니다.

사용자 권한을 확인하는 인증 필터를 구현하겠습니다.

#### 로그인한 사용자의 DTO

먼저 로그인한 사용자의 정보를 담은 DTO 를 생성합니다.

#### 인증 필터

> demo.security.filter.FormLoginFilter

~~~
public class FormLoginFilter extends AbstractAuthenticationProcessingFilter {

    // 3.
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    private AuthenticationFailureHandler authenticationFailureHandler;

    // 1.
    protected FormLoginFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    public FormLoginFilter(
            String defaultUrl,
            AuthenticationSuccessHandler successHandler,
            AuthenticationFailureHandler failureHandler) {
        super(defaultUrl);
        this.authenticationSuccessHandler = successHandler;
        this.authenticationFailureHandler = failureHandler;
    }

    // 2.
    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest req,
            HttpServletResponse res
    )
            throws AuthenticationException, IOException, ServletException {

        // JSON 으로 변환
        AccountFormDTO dto = new ObjectMapper().readValue(
                req.getReader(),
                AccountFormDTO.class
        );

        // 사용자입력값이 존재하는지 비교
        PreAuthorizationToken token = new PreAuthorizationToken(dto);

        // PreAuthorizationToken 해당 객체에 맞는 Provider를
        // getAuthenticationManager 해당 메서드가 자동으로 찾아서 연결해 준다.
        // 자동으로 찾아준다고 해도 Provider 에 직접 PreAuthorizationToken 지정해 줘야 찾아갑니다.

        return super
                .getAuthenticationManager()
                .authenticate(token);
    }

    // 4.
    @Override
    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain,
            Authentication authResult
    ) throws IOException, ServletException {
        this
                .authenticationSuccessHandler
                .onAuthenticationSuccess(req, res, authResult);
    }

    // 4.
    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse res,
            AuthenticationException failed
    ) throws IOException, ServletException {
        this
                .authenticationFailureHandler
                .onAuthenticationFailure(req, res, failed);
    }
}
~~~

1. defaultFilterProcessesUrl - filterProcessesUrl 의 기본생성자 생성  
그리고 성공 실패 핸들러를 담은 생성자 두개 만들어 줍니다.  

2. 그리고 attemptAuthentication 메서드를 @Override 해줍니다.  
AbstractAuthenticationProcessingFilter 클래스의 doFilter 메서드로 인해서  
`가장 처음 인증 attemptAuthentication 메서드를 실행`합니다.

만약 attemptAuthentication 메서드에서 `인증이 성공한다면 doFilter 메서드` 에서

> AbstractAuthenticationProcessingFilter 87 번 줄

~~~
// Authentication success
if (continueChainBeforeSuccessfulAuthentication) {
	chain.doFilter(request, response);
}

successfulAuthentication(request, response, chain, authResult);
~~~

`successfulAuthentication 으로 메서드를 실행`시키도록 해줍니다. `(인증 실패도 동일)`

사용자입력 `ID and Password 를 req 로 받은 값을 ObjectMapper 객체로 JSON 으로 변환`하여 AccountFormDTO 형식으로 저장합니다.  
(결과 `AccountFormDTO(username=jjunpro, password=1234) 식으로 변환`됩니다.)

사용자입력값이 존재하는지 비교하기 위해서 DTO 를 `인증 '전' Token 객체에 넣어 PreAuthorizationToken 을 생성`합니다.

위 사용자의 값을 가지고 attemptAuthentication는 인증을 시도합니다.  
`인증 시도는 FormLoginAuthenticationProvider` 에서 하게됩니다.

PreAuthorizationToken 해당 객체에 맞는 Provider를  
`getAuthenticationManager 해당 메서드가 자동으로 찾아서 연결해` 준다.

3. 인증 성공 or 실패 메서드 구현하기 위해서 필요한 성공실패 인터페이스를 불러옵니다.

4. 인증 성공 or 실패 메서드 구현합니다.

### 3. 인증 전 Token PreAuthorizationToken 생성

> demo.security.token.PreAuthorizationToken

~~~
public class PreAuthorizationToken extends UsernamePasswordAuthenticationToken {

    private PreAuthorizationToken(String username, String password) {
        super(username, password);
    }

    public PreAuthorizationToken(AccountFormDTO dto) {
        this(dto.getUsername(), dto.getPassword());
    }

    public String getUsername() {
        return (String) super.getPrincipal();
    }

    public String getUserPassword() {
        return (String) super.getCredentials();
    }
}
~~~

UsernamePasswordAuthenticationToken 사용자 이름 비밀번호 인증 토큰 클래스를 상속받습니다.  
로그인한 사용자의 권한을 확인하기 위해서 Pre Token 을 생성완료했으니 Provider 로 보내도록 하겠습니다.

### 4. 인증 후 Token PostAuthorizationToken 생성

> demo.security.token.PostAuthorizationToken

~~~
public class PostAuthorizationToken extends UsernamePasswordAuthenticationToken {

    // 1.
    private PostAuthorizationToken(
            Object principal,
            Object credentials,
            Collection<? extends GrantedAuthority> authorities
    ) {
        super(principal, credentials, authorities);
    }

    public static PostAuthorizationToken getTokenFormUserDetails(UserDetails userDetails) {

        return new PostAuthorizationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
    }

    public UserDetails getUserDetails() {

        return (UserDetails) super.getPrincipal();
    }
}
~~~

- 1. 
    - Object principal - 주도자, 주역
        - `사용자 본인을 의미` 간단하게 유저 아이디 생각하면 될꺼같다. (쭌피셜)
    - Object credentials - 자격 인증서, 자격증
        - 이름 그대로 자격이다. 
    - Principal 에서 사용자 본인을 의미한다면 `Credentials 은 그에 따른 자격을 증명하고자 할 때 사용`된다.
    - Principal 과 마찬가지로 구현에 따라 어떠한 정보가 들어갈지 달라지지만 대체적으로 암호화된 정보를 저장하며, 보안에 신경을 많이 써야 되는 정보이다.
    - Collection<? extends GrantedAuthority> authorities - 권한을 List 형태로 받습니다.
        - `사용자의 지정한 권한 범위를 기술하기 위해 추상화된 클래스` (권한이 Role 만 있는것이 아니기에 List 형태로 받습니다. 여러 가지 조건으로 제한이 가능하다는 것입니다.)

### 5. FormLoginAuthenticationProvider

로그인한 사용자의 인증 권한을 검사합니다.

> demo.security.provider.FormLoginAuthenticationProvider

~~~
@Component
@RequiredArgsConstructor
public class FormLoginAuthenticationProvider implements AuthenticationProvider {

    private final AccountServiceImpl accountService;

    // 4.
    private final PasswordEncoder passwordEncoder;

    // 1.
    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        // 2.
        PreAuthorizationToken token = (PreAuthorizationToken) authentication;

        String username = token.getUsername();
        String password = token.getUserPassword();

        // 3.
        UserDetails accountDB = accountService.loadUserByUsername(username);

        // 4.
        if (isCorrectPassword(password, accountDB.getPassword())) {
            return PostAuthorizationToken
                    .getTokenFormUserDetails(accountDB);
        }

        // 이곳까지 통과하지 못하면 잘못된 요청으로 접근하지 못한것 그러므로 throw 해줘야 한다.
        throw new NoSuchElementException("인증 정보가 정확하지 않습니다.");
    }

    // 5.
    // Provider 를 연결 해주는 메소드 PreAuthorizationToken 사용한 filter 를 검색 후 연결
    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthorizationToken.class.isAssignableFrom(authentication);
    }

    // 4.
    private boolean isCorrectPassword(String password, String accountPassword) {
        return passwordEncoder.matches(password, accountPassword);
    }
}
~~~

<a href="https://docs.spring.io/spring-security/site/docs/4.2.12.RELEASE/apidocs/org/springframework/security/authentication/AuthenticationProvider.html" target="_blank">AuthenticationProvider DOCS</a>

AuthenticationProvider 인터페이스를 상속 받습니다. 특정 Authentication구현을 처리 할 수있는 클래스를 나타냅니다.  
`AuthenticationProvider` 인터페이스는 화면에서 입력한 `로그인 정보와 DB에서 가져온 사용자의 정보를 비교해주는 인터페이스`이다.  
해당 인터페이스에 오버라이드되는 authenticate() 메서드는 화면에서 사용자가 입력한 `로그인 정보를 담고 있는 Authentication 객체를 가지고 있다.`  
AuthenticationProvider 인터페이스는 인증에 성공하면 인증된 Authentication 객체를 생성하여 리턴하기 때문에   
비밀번호, 계정 활성화, 잠금 모든 부분에서 확인이 되었다면 리턴해주도록 하자.

- 1. 
    - 로그인한 사용자와 DB 사용자를 비교하는 메서드 authenticate() @Override 해줍니다. 

- 2. 
    - 기본적으로 이전에 받은 로그인 정보를 담고 있는 Authentication 객체 PreToken 값을 가지고 있습니다.
    - 해당 PreToken 에서 로그인한 유저의 정보를 변수에 담습니다.

- 3. 
    - `로그인한 유저가 DB에 존재하는지` accountService 를 통해서 확인합니다.

- 4. 
    - 로그인한 유저와 DB에 존재하는 유저의 `Password 가 동일한지 조회`하는 메서드입니다. `*무조건 비교 대상이 앞에 와야합니다.`
    - 로그인한 유저가 DB에 존재한다면 PostAuthorizationToken(권한이 부여된 토큰) 객체를 생성하여 return 합니다.
    - PasswordEncoder 클래스를 주입 받으려면 PasswordEncoder Bean 을 등록해 줘야합니다.

- 5. 
    - AuthenticationProvider 인터페이스가 지정된 Authentication 객체를 지원하는 경우에 true 를 리턴한다.
    - form action 진행 시 해당 클래스의 supports() > authenticate() 순으로 인증 절차 진행합니다.
    - 사용자 인증이 완료되면 사용자에게 권한을 부여한 토큰을 생성하여 return 해줘야 합니다.
    - PostAuthorizationToken 클래스를 생성합니다.
    - PreAuthorizationToken.class.isAssignableFrom(authentication) 를 지정하므로서 Security 에서 
    Filter 에서 사용한 PreAuthorizationToken 를 참고해서 여러곳을 검색하여 해당 Provider 로 연결 해줍니다.
    - 예시로 JWTProvider 같은경우에는 JwtPreProcessingToken 를 사용하고 있어서 JWTProvider 에는 
    JwtPreProcessingToken.class.isAssignableFrom(authentication)
    로 연결되어 있어서 자동으로 Provider 검색하여 해당 Pre 클래스가 있는곳을 찾아 Provider 를 연결해 줍니다.

### 6. successfulAuthentication

> AbstractAuthenticationProcessingFilter 91 번 줄

~~~
this.successfulAuthentication(request, response, chain, authenticationResult);
~~~

Provider 에서 인증이 끝난 후 다시 filter 로 돌아와서 위 doFilter 의 메서드로 인해서 인증이 성공 하였으니  
`successfulAuthentication(request, response, chain, authResult); 메서드로 이동하여 실행`합니다.

### 7. handler 인증 성공(인증객체 생성)

> demo.security.handler.FormLoginAuthenticationSuccessHandler

~~~
@Component
@RequiredArgsConstructor
public class FormLoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    // 2.
    private final JwtFactory   factory;
    private final ObjectMapper objectMapper;

    // 1.
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest req,
            HttpServletResponse res,
            Authentication auth
    ) throws IOException {

        PostAuthorizationToken token   = (PostAuthorizationToken) auth;
        UserDetails            userDetails = token.getUserDetails();

        // 2.
        String tokenString = factory.generateToken(userDetails);

        // 3.
        TokenDTO tokenDTO = new TokenDTO(tokenString, userDetails.getUsername());

        processResponse(res, tokenDTO);
    }

    private void processResponse(
            HttpServletResponse res,
            TokenDTO dto
    ) throws IOException {
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        res.setStatus(HttpStatus.OK.value());
        res.getWriter().write(objectMapper.writeValueAsString(dto));
    }
}
~~~

AuthenticationSuccessHandler 구현체에서는 `로그인을 성공`했을때 호출(인증 객체가 생성되어진 후)

- 1. 
    - onAuthenticationSuccess 메서드를 @Override 해줍니다.
    - `Token 값을 정형화된 DTO를 만들어서 res 으로 내려주는 역할`을 수행합니다. 
    - 인증결과 객체 auth 를 PostAuthorizationToken 객체 변수에 담아줍니다.
- 2.
    - PostAuthorizationToken 객체에 담아줄 JWT Token 을 생성해야 합니다. 
    - 그 후 processRespone 메서드를 통해서 Response 상태와 jwt 값을 전송합니다.
- 3. 
    - Token 값을 정형화된 DTO 를 만들어 줍니다.

#### TokenDTO

> demo.dto.TokenDTO

~~~
@Getter
@Setter
@NoArgsConstructor
public class TokenDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String token;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String username;

    public TokenDTO(String token, String username) {
        super();
        this.token = token;
        this.username = username;
    }
}
~~~

#### JWT Token 생성

> pom.xml jwt dependency 를 추가해줍니다.

~~~
<dependency>
    <groupId>com.auth0</groupId>
    <artifactId>java-jwt</artifactId>
    <version>3.3.0</version>
</dependency>
~~~

> demo.security.jwt.JwtFactory

~~~
@Component
public class JwtFactory {

    private static final Logger log = LoggerFactory.getLogger(JwtFactory.class);

    // 1.
    public String generateToken(UserDetails userDetails) {
        String token = null;
        try {
            Set<String> roles = userDetails.getAuthorities().stream()
                    .map(r -> r.getAuthority()).collect(Collectors.toSet());
            String role = roles.iterator().next();

            token = JWT.create()
                    .withIssuer("jjunpro")
                    .withClaim("USERNAME", userDetails.getUsername())
                    .withClaim("USER_ROLE", role)
                    .withClaim("EXP", new Date(System.currentTimeMillis() + 864000000))
                    .sign(generateAlgorithm());
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return token;
    }

    // 2.
    private Algorithm generateAlgorithm() throws UnsupportedEncodingException {
        String signingKey = "jwttest";
        return Algorithm.HMAC256(signingKey);
    }
}
~~~

- 1. 
    - JWT Token 을 만들어주는 메서드 generateToken() JWT 값으로 유저 아이디, 유저 권한, 토큰 유효시간 을 담았습니다.
- 2. 
    - signature 서명 값을 선언해주고 Algorithm generateAlgorithm() 암호화 메서드로 암호화 후 값을 넣어줍니다.

#### handler 인증 실패

> demo.security.handler.FormLoginAuthenticationFailureHandler

~~~
@Component
public class FormLoginAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private static final Logger log = LoggerFactory
            .getLogger(FormLoginAuthenticationFailureHandler.class);

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) {
        log.error(exception.getMessage());
    }
}
~~~

AuthenticationFailureHandler 구현체에서는 `로그인을 실패`했을때 호출  
onAuthenticationFailure 메서드를 @Override 해줍니다.  
`로그인 접근의 실패 정보`를 알려주도록 해줍니다.  

### SecurityConfig 등록

이제까지 만들어 놓은 Filter 그리고 Provider 를 등록하도록 하겠습니다.

> demo.config.SecurityConfig

~~~
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final FormLoginAuthenticationSuccessHandler formLoginAuthenticationSuccessHandler;
    private final FormLoginAuthenticationFailureHandler formLoginAuthenticationFailureHandler;

    private final FormLoginAuthenticationProvider provider;

    @Bean
    public AuthenticationManager getAuthenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    // 1.
    protected FormLoginFilter formLoginFilter() throws Exception {
        FormLoginFilter filter = new FormLoginFilter(
                "/api/account/login",
                formLoginAuthenticationSuccessHandler,
                formLoginAuthenticationFailureHandler
        );
        filter.setAuthenticationManager(super.authenticationManagerBean());

        return filter;
    }

    // 2.
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth
                .authenticationProvider(this.provider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable();

        http
                .headers()
                .frameOptions()
                .disable();

        // 1.
        http
                .addFilterBefore(
                        formLoginFilter(),
                        UsernamePasswordAuthenticationFilter.class
                );
    }
}
~~~

- 1. 
    - 사용자를 검사하는 특정 주소와 인증 성공&실패 핸들러를 담아서 formLoginFilter 메서드를 생성합니다.
    - addFilterBefore 필터 등록을 해줍니다.
- 2. 
    - provider 등록 해줍니다.
    - <a href="https://docs.spring.io/spring-security/site/docs/4.2.12.RELEASE/apidocs/org/springframework/security/authentication/AuthenticationManager.html" target="_blank">AuthenticationManager DOCS</a>

### Token 발급 결과 확인

![token-1](/images/token-1.png)
![token-2](/images/token-2.png)
![token-3](/images/token-3.png)

정상적으로 JWT Token 값이 발급되었습니다.

JWT Token 확인은 
<a href="https://jwt.io/" target="_blank">JWT IO</a> 
에서 할 수 있습니다.

JWT Token 발급 글만 정리하는데 엄청난 시간이 소비된거같습니다.  
회사 일이랑 병행하면서 하다보니 더 힘들었지만 회사 끝나고 틈틈히 작성하고 하다보니 완성이 되긴 했습니다.  
보는 사람이 나 말고는 없겠지만 틈틈히 참고해야겠습니다.

# 발급받은 JWToken 을 활용하여 USER 권한만 접근 가능한 페이지 만들기

> demo.controller.AccountController

~~~
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {

    ...

    @GetMapping("")
    public ResponseEntity<?> viewAccount() {

        return new ResponseEntity<>("Success!", HttpStatus.OK);
    }
}
~~~

Controller 위치에 GET 으로 '/api/account' 접근할 경우 JWToken 을 소유한 유저만 접근 가능하도록 만들겠습니다.  

> demo.config.SecurityConfig

~~~
...
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JWTAuthenticationProvider       jwtProvider;
    private final HeaderTokenExtractor            headerTokenExtractor;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth
                .authenticationProvider(this.provider)
                .authenticationProvider(this.jwtProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ...

        // 1.
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 2.
        http
                .addFilterBefore(
                        formLoginFilter(),
                        UsernamePasswordAuthenticationFilter.class
                )
                .addFilterBefore(
                        jwtFilter(),
                        UsernamePasswordAuthenticationFilter.class
                );

        // 3. 
        http
                .authorizeRequests()
                .mvcMatchers(
                        HttpMethod.GET,
                        "/api/account"
                )
                .hasRole("USER");
    }

    private JwtAuthenticationFilter jwtFilter() throws Exception {
        List<String> skipPath = new ArrayList<>();

        // Static 정보 접근 허용
        skipPath.add("GET,/error");
        skipPath.add("GET,/favicon.ico");
        skipPath.add("GET,/static");
        skipPath.add("GET,/static/**");

        skipPath.add("POST,/api/account");
        skipPath.add("POST,/api/account/login");

        FilterSkipMatcher matcher = new FilterSkipMatcher(
                skipPath,
                "/**"
        );

        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(
                matcher,
                headerTokenExtractor
        );
        filter.setAuthenticationManager(super.authenticationManagerBean());

        return filter;
    }
}
~~~

- 1.
    - 서버에서 인증은 JWT로 인증하기 때문에 Session의 생성을 막습니다.
- 2.
    - UsernamePasswordAuthenticationFilter 이전에 FormLoginFilter, JwtFilter 를 등록합니다.
    - FormLoginFilter : 로그인 인증을 실시합니다.
    - JwtFilter       : 서버에 접근시 JWT 확인 후 인증을 실시합니다.
- 3.
    - 권한(USER)이 필요한 접근 설정

이제 GET 으로 '/api/account' 접근하려면 발급받은 토큰을  
Bearer token 매칭되야 접근 할 수 있도록 하겠습니다.

## JWT Provider 추가하기

> demo.security.provider.JWTAuthenticationProvider

~~~
@Component
@RequiredArgsConstructor
public class JWTAuthenticationProvider implements AuthenticationProvider {

    private final JwtDecoder jwtDecoder;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String     token      = (String) authentication.getPrincipal();
        AccountDTO accountDTO = jwtDecoder.decodeJwt(token);

        return PostAuthorizationToken.getTokenFormUserDetails(accountDTO);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtPreProcessingToken.class.isAssignableFrom(authentication);
    }
}
~~~

### JWToken 을 해석해서 받은 로그인 정보 DTO 로 전달하기

> demo.security.token.PostAuthorizationToken

~~~
public class PostAuthorizationToken extends UsernamePasswordAuthenticationToken {

    ...

    public static PostAuthorizationToken getTokenFormUserDetails(AccountDTO accountDTO) {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(
                new SimpleGrantedAuthority(accountDTO.getRole())
        );

        return new PostAuthorizationToken(
                accountDTO,
                "null password",
                grantedAuthorities
        );
    }
    
    ...
}
~~~

### JwtDecoder

> demo.security.jwt.JwtDecoder

~~~
@Component
public class JwtDecoder {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public AccountDTO decodeJwt(String token) {
        DecodedJWT decodedJWT = isValidToken(token)
                .orElseThrow(() -> new NoSuchElementException("유효한 토큰아 아닙니다."));

        String username = decodedJWT
                .getClaim("USERNAME")
                .asString();

        String role = decodedJWT
                .getClaim("USER_ROLE")
                .asString();

        return new AccountDTO(username, role);
    }

    private Optional<DecodedJWT> isValidToken(String token) {
        DecodedJWT jwt = null;

        try {
            Algorithm algorithm = Algorithm.HMAC256("jwttest");
            JWTVerifier verifier = JWT
                    .require(algorithm)
                    .build();

            jwt = verifier.verify(token);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return Optional.ofNullable(jwt);
    }
}
~~~

#### AccountDTO 생성

> demo.dto.AccountDTO

~~~
@Getter
@Setter
@NoArgsConstructor
public class AccountDTO {

    private String username;

    private String role;

    public AccountDTO(String username, String role) {
        super();
        this.username = username;
        this.role = role;
    }
}
~~~

## JwtAuthenticationFilter

> demo.security.filter.JwtAuthenticationFilter

~~~
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final HeaderTokenExtractor extractor;

    public JwtAuthenticationFilter(
            RequestMatcher requiresAuthenticationRequestMatcher,
            HeaderTokenExtractor extractor
    ) {
        super(requiresAuthenticationRequestMatcher);

        this.extractor = extractor;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {

        // JWT 값을 담아주는 변수 TokenPayload
        String tokenPayload = request.getHeader("Authorization");

        JwtPreProcessingToken preAuthorizationToken = new JwtPreProcessingToken(
                extractor.extract(tokenPayload, request));

        return super
                .getAuthenticationManager()
                .authenticate(preAuthorizationToken);
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException, ServletException {
        /*
         *  SecurityContext 사용자 Token 저장소를 생성합니다.
         *  SecurityContext 에 사용자의 인증된 Token 값을 저장합니다.
         */
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);

        // FilterChain chain 해당 필터가 실행 후 다른 필터도 실행할 수 있도록 연결실켜주는 메서드
        chain.doFilter(
                request,
                response
        );
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed
    ) {
        /*
         *	로그인을 한 상태에서 Token값을 주고받는 상황에서 잘못된 Token값이라면
         *	인증이 성공하지 못한 단계 이기 때문에 잘못된 Token값을 제거합니다.
         *	모든 인증받은 Context 값이 삭제 됩니다.
         */
        SecurityContextHolder.clearContext();

        this.unsuccessfulAuthentication(
                request,
                response,
                failed
        );
    }
}
~~~

Token 을 내려주는 Filter 가 아닌  client 에서 받아지는 Token 을 서버 사이드에서 검증하는 클레스 SecurityContextHolder 보관소에 해당  
Token 값의 인증 상태를 보관 하고 필요할때 마다 인증 확인 후 권한 상태 확인 하는 기능

### JwtPreProcessingToken

> demo.security.token.JwtPreProcessingToken

~~~
public class JwtPreProcessingToken extends UsernamePasswordAuthenticationToken {

    private JwtPreProcessingToken(
            Object principal,
            Object credentials
    ) {
        super(
                principal,
                credentials
        );
    }

    public JwtPreProcessingToken(String token) {
        this(
                token,
                token.length()
        );
    }
}
~~~

### HeaderTokenExtractor

> demo.security.jwt.HeaderTokenExtractor

~~~
@Component
public class HeaderTokenExtractor {

    /*
     * HEADER_PREFIX
     * header Authorization token 값의 표준이되는 변수
     */
    public final String HEADER_PREFIX = "Bearer ";

    private Logger log = LoggerFactory.getLogger(this.getClass());

    public String extract(String header, HttpServletRequest request) {
        /*
         * - Token 값이 올바르지 않은경우 -
         * header 값이 비어있거나 또는 HEADER_PREFIX 값보다 짧은 경우
         * 이셉션을(예외)를 던져주어야 합니다.
         */
        if (header == null || header.equals("") || header.length() < HEADER_PREFIX.length()) {
            log.info("error request : " + request.getRequestURI());
            throw new NoSuchElementException("올바른 JWT 정보가 아닙니다.");
        }

        /*
         * - Token 값이 존재하는 경우 -
         * (bearer ) 부분만 제거 후 token 값 반환
         */
        return header.substring(
                HEADER_PREFIX.length(),
                header.length()
        );
    }
}
~~~

## FilterSkipMatcher

> demo.security.common.FilterSkipMatcher

~~~
public class FilterSkipMatcher implements RequestMatcher {

    private final OrRequestMatcher orRequestMatcher;
    private final RequestMatcher   processingMatcher;

    public FilterSkipMatcher(
            List<String> pathToSkip,
            String processingPath
    ) {
        this.orRequestMatcher = new OrRequestMatcher(pathToSkip
                .stream()
                .map(this :: httpPath)
                .collect(Collectors.toList()));
        this.processingMatcher = new AntPathRequestMatcher(processingPath);
    }

    private AntPathRequestMatcher httpPath(String skipPath) {
        String[] splitStr = skipPath.split(",");

        /*
         * 배열 [1] httpMathod 방식 post get 인지 구분
         * 배열 [0] 제외하는 url
         * */
        return new AntPathRequestMatcher(
                splitStr[1],
                splitStr[0]
        );
    }

    @Override
    public boolean matches(HttpServletRequest req) {
        return !orRequestMatcher.matches(req) && processingMatcher.matches(req);
    }
}
~~~

## JWToken 로그인 결과 확인하기

![token-1](/images/token-1.png)
![token-2](/images/token-2.png)
![token-4](/images/token-4.png)

# 공부에 도움이 많이 된 출처!

https://velopert.com/2389 - [JSON Web Token 이 뭘까?]
https://12bme.tistory.com/130 - [REST JWT(JSON Web Token) 이란?]

https://eglowc.tistory.com/38 - [Spring Boot 의존성 변경하기]

https://jsaver.tistory.com/entry/Id%EC%99%80-GeneratedValue-%EC%95%A0%EB%85%B8%ED%85%8C%EC%9D%B4%EC%85%98 - [@Id와 @GeneratedValue 애노테이션]

http://woowabros.github.io/tools/2017/07/10/java-enum-uses.html - [Java Enum 활용기]
https://jojoldu.tistory.com/122 - [Enum 활용 & Enum 리스트 가져오기]
https://limkydev.tistory.com/50 - [Enum 사용하는 이유]

https://12bme.tistory.com/271 - [개발방법론 계층별, 기능별 패키지 구성하기]

http://www.chidoo.me/index.php/2016/05/08/spring-data-jpa-for-short-memories/ - [JPA Repository CrudRepository, JpaRepository]

https://cheese10yun.github.io/lombok/ - [실무에서 Lombok 사용법-getter, setter 무분별 사용 막자]

https://www.feelteller.com/10 - [빌더]

https://jojoldu.tistory.com/251 - [스프링부트로 웹 서비스 출시하기 - 2. SpringBoot & JPA로 간단 API 만들기, setter 무분별한 막기]

https://frontierdev.tistory.com/89 - [WebSecurityConfigurerAdapter 란 무엇인가?]

https://to-dy.tistory.com/87 - [AuthenticationProvider 란?]

https://post.naver.com/viewer/postView.nhn?volumeNo=10142629&memberNo=559061 - [static 전역 변수]
https://12bme.tistory.com/94 - [static 변수 잘 사용하자]

https://stackoverflow.com/questions/41663652/consider-defining-a-bean-of-type-service-in-your-configuration-spring-boot?rq=1 -[Consider defining a bean of type 'service' in your configuration]