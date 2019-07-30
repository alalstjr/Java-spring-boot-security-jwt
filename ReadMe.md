# Spring Security 그리고 JPA, REST API JWT 인증

지금까지 공부해온 Spring Security 를 활용하여 REST API JWT Token 인증 시스템을 구현해 보도록 하겠습니다.

REST API를 디자인 할 때는 매우 중요한 보안을 탄탄하게 잡아줘야 합니다. 
마침 Spring 기반의 애플리케이션 `Spring Security` 라는 아주 훌륭한 `인증 및 권한` 부여 솔루션이 존재합니다.

## Spring Security(스프링 시큐리티) 란 무엇인가?

스프링 시큐리티 레퍼런스에서는 자바 EE 기반의 엔터프라이즈 소프트웨어 애플리케이션을 위한 포괄적인 보안 서비스들을 제공하고 
`오픈 플랫폼`이면서 자신만의 인증 매커니즘을 간단하게 만들 수 있습니다.

스프링 시큐리티를 이해하기 위해서는 스프링 시큐리티가 `애플리케이션 보안을 구성하는 두가지 영역`에 대해서 알아야 합니다. 

바로 `인증(Authentication)과 권한(Authorization)` 이라는 것입니다.

- 인증 : 애플리케이션의 작업을 수행할 수 있는 주체(사용자)라고 주장할 수 있는 것
- 권한 : 권한은 인증된 주체가 애플리케이션의 동작을 수행할 수 있도록 허락되있는지를 결정하는 것

권한 승인이 필요한 부분으로 접근하려면 인증 과정을 통해 주체가 증명 되어야만 한다는 것입니다.

## Spring Security 동작 방식

- 1. 클라이언트가 Resource에 URL을 통해 요청을 보낸다.
- 2. `DelegatingFilterProxy`는 요청을 Intercept! 가로채서 Spring Security빈으로 보낸다.
- 3. Spring Security빈은 인증 및 권한을 확인한다.
- 4. 권한이 잘 부여되어 있다면 리소스에 접근을 허용하고 그렇지 않다면 거부한다.

# JWT (JSON Web Token) 이란?

JSON Web Token (JWT) 은 웹표준 [(RFC 7519)](https://tools.ietf.org/html/rfc7519) 으로서 
두 개체에서 JSON 객체를 사용하여 가볍고 자가수용적인 (self-contained) 방식으로 정보를 안전성 있게 전달해줍니다.

JSON 객체로서 안전하게 정보를 전송할 수 있는 작고 self-contained 방법을 정의하는 표준이다.

JWT 대표적인 특징으로는 Claim 기반 토큰의 개념 이라는 것입니다.

- OAuth 인증방식의 경우
OAuth에 의해서 발급되는 access_token은 random string으로 토큰 자체에는 특별한 정보를 가지고 있지 않는 일반적인 스트링 형태입니다.

API나 서비스를 제공하는 서버 입장에서 그 access_token을 통해서 사용자에 연관된 권한(예를 들어 scope같은 것)을 식별한 뒤 권한을 허용해주는 구조입니다. 즉 서비스를 제공하는 입장에서는 토큰을 가지고 그 토큰과 연관된 정보를 서버쪽에서 찾아야 합니다. (사용자 ID나 권한 등)

JWT는 Claim 기반이라는 방식을 사용하는데, `Claim이라는 사용자에 대한 프로퍼티나 속성`을 이야기 합니다. 
토큰 자체가 정보를 가지고 있는 방식인데, JWT는 이 Claim을 JSON을 이용해서 정의 합니다.

## oauth 인증 그리고 JWT와 같은 Claim 기반의 토큰 흐름

### oauth 인증의 흐름
![oauth](/images/oauth.png)

- 1. API 클라이언트가 `Authorization Server(토큰 발급서버)로 토큰을 요청`합니다. 이때, 토큰 발급을 요청하는 사용자의 계정과 비밀번호를 넘기고, `이와 함께 토큰의 권한(용도)를 요청`합니다. 여기서는 일반 사용자 권한(enduser)과 관리자 권한(admin)을 같이 요청하였습니다.
- 2. 토큰 생성 요청을 받은 `Authorization Server는 사용자 계정을 확인한 후, 이 사용자에게 요청된 권한을 부여해도 되는지 계정 시스템 등에 물어본 후, 사용자에게 해당 토큰을 발급이 가능하면 토큰을 발급하고, 토큰에 대한 정보를 내부(토큰 저장소)에 저장`해 둡니다.
- 3. 이렇게 생성된 토큰은 API 클라이언트로 저장이 됩니다.
- 4. API 클라이언트는 API를 호출할때 이 `토큰을 이용해서 Resource Server(API 서버)에 있는 API를 호출`합니다.
- 5. 이때 호출되는 API는 관리자 권한을 가지고 있어야 사용할 수 있기 때문에, Resource Server가 토큰 저장소에서 토큰에 관련된 사용자 계정, 권한 등의 정보를 가지고 옵니다. 이 토큰에 `(관리자)admin 권한이 부여되어 있기 때문에, API 호출을 허용`합니다. 위에 정의한 시나리오에서는 그 사용자가 속한 "회사"의 사용자 정보만 조회할 수 있습니다. 라는 전제 조건을 가지고 있기 때문에 API 서버는 추가로 사용자 데이터베이스에서 이 사용자가 속한 "회사" 정보를 찾아와야 합니다.
- 6. API 서버는 응답을 보냅니다.

### JWT와 같은 Claim 기반의 토큰 흐름
![oauth](/images/jwttoken.png)

- 1. 토큰을 생성 요청하는 방식은 동일합니다. 마찬가지로 `사용자를 인증한 다음에 토큰을 생성`합니다.
- 2. 다른 점은 생성된 토큰에 관련된 정보를 별도로 저장하지 않는다는 것입니다. `토큰에 연관되는 사용자 정보나 권한 등을 토큰 자체에 넣어서 저장`합니다.
- 3. API를 호출하는 방식도 동일합니다.
- 4. Resource Server (API 서버)는 `토큰 내에 들어있는 사용자 정보를 가지고 권한 인가 처리를 하고 결과를 리턴`합니다.

결과적으로 차이점은 토큰을 생성하는 단계에서는 `생성된 토큰을 별도로 서버에서 유지할 필요가 없으며` 토큰을 사용하는 API 서버 입장에서는 API 요청을 검증하기 위해서 토큰을 가지고 사용자 정보를 별도로 계정 시스템 등에서 조회할 필요가 없습니다.

## 많은 프로그래밍 언어의 지원

JWT 는 C, Java, Python, C++, R, C#, PHP, JavaScript, Ruby, Go, Swift 등 대부분의 주류 프로그래밍 언어에서 지원됩니다.

## 자가 수용적 (self-contained)

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
Claim에 넣는 데이터가 많아질수록, JWT 토큰의 길이가 길어집니다. API 호출등에 사용할 시에, 매 호출마다 헤더에 붙어서 가야하기 때문에, 길이가 길다는 것은 그만큼 네트워크 대역폭 낭비가 심하다는 의미입니다.

- 2. 한번 발급된 토큰은 값을 수정하거나 폐기가 불가
JWT는 토큰 내에 모든 정보를 다 가지고 있기 때문에, 한번 발급된 토큰에 대한 변경은 서버에서는 더이상 불가능합니다. 예를 들어 토큰을 잘못 발행해서 삭제하고 싶더라도, Sinagture만 맞으면 맞는 토큰으로 인식을 하기 때문에, 서버에는 한번 발급된 토큰의 정보를 바꾸는 일등이 불가능합니다.

그래서 만약에 JWT를 쓴다면, Expire time을 꼭 명시적으로 두도록하고, refresh token등을 이용해서, 중간중간 토큰을 재발행하도록 해야합니다.

- 3. 보안
JWT는 기본적으로 Claim에 대한 정보를 암호화하지 않는다. 단순히 BASE64로 인코딩만 하기 때문에, 중간에 패킷을 가로채거나, 기타 방법으로 토큰을 취득했으면 토큰 내부 정보를 통해서 사용자 정보가 누출 될 수 있는 가능성이 있습니다. 특히 자바스크립트 기반의 웹 클라이언트의 경우 브라우저상의 디버거등을 통해서 토큰이 노출될 가능성이 높습니다. 그래서, 이를 보완하는 방법으로는 토큰 자체를 암호화하는 방법이 잇습니다. JSON을 암호화하기 위한 스펙으로는 JWE(JSON Web Encryption)이 있습니다.

## JWT를 유용하게 사용하는 상황

- 회원 인증

JWT 를 사용하는 가장 흔한 경우 회원 인증 입니다. 
유저가 로그인을 하면, 서버는 `유저의 정보에 기반한 토큰을 발급하여 유저에게 전달`해줍니다. 
그 후, `유저가 서버에 요청을 할 때 마다 JWT를 포함하여 전달`합니다. 
서버가 클라이언트에게서 요청을 받을때 마다, `해당 토큰이 유효하고 인증됐는지 검증`을 하고, 
유저가 요청한 작업에 권한이 있는지 확인하여 작업을 처리합니다.
중요! `서버측에서는 유저의 세션을 유지 할 필요가 없습니다. `
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

{% highlight matlab %}
{
    "http://localhost:3000/boardEvent/write": true
}
{% endhighlight %}

#### 비공개 (private) 클레임

등록된 클레임도아니고, 공개된 클레임들도 아닙니다. 
양 측간에 (보통 클라이언트 <->서버) 협의하에 사용되는 클레임 이름들입니다. 
공개 클레임과는 달리 이름이 중복되어 충돌이 될 수 있으니 사용할때에 유의해야합니다.

{% highlight matlab %}
{
    "username": "jjunpro"
}
{% endhighlight %}
 
결국 JWT Token 을 전체적으로 확인해보면 

{% highlight matlab %}
{
    "iss": "admin",
    "exp": "148794004800",
    "http://localhost:3000/boardEvent/write": true,
    "userId": "38048322648",
    "username": "jjunpro"
}
{% endhighlight %}

이런 형식의 토큰 코드가 완성됩니다.

### 서명 (signature)

JSON Web Token 의 마지막 부분은 바로 서명(signature) 입니다. 
이 서명은 `헤더의 인코딩값`과, `정보의 인코딩값`을 합친 후 주어진 비밀키로 해쉬를 하여 생성합니다.

# Spring Boot 개발환경 설정

Spring Boot 프로젝트를 만드는 가장 빠른 방법은 <a href=" http://start.spring.io">Spring Initializr</a> 을 사용하여 기본 코드를 생성하는 것입니다.

 http://start.spring.io 사이트로 접속하여 dependencies 를 다음과같이 선택 생성해 줍니다.

 - Spring Web Starter
 - Spring Security
 - Spring Data JPA
 - Lombok 

![spring-Initializr](/images/spring-Initializr.png)

압축을 풀어준 다음 (Spring Tool Suite4, Maven project 기준 입니다.) 

File -> Import -> Maven -> Existing Maven Projects 

생성한 Project를 불러옵니다.

![project-import](/images/project-import.png)

그러면 프로젝트에 ERROR 하나가 발생합니다.

> Description Resource Path Location Type Unknown pom.xml /project line 1 Maven Configuration Problem

구글링으로 찾아본 결과 

Spring Framework는  빌드 및 컴파일을 할 때 Maven에 의존하고 있는데, 둘 간에 뭔가가 맞지 않기 때문에 발생한 것이다. 
정확한 원인은 파악하지 않았지만, 해결책은 spring-boot-starter-parent 버전 단계를 낮추는 것입니다.

pom.xml 기존이 2.1.6.RELEASE 이였다면 2.1.4.RELEASE 로 버전다운그래이를 해줍니다.

다음 ALT + F5 단축키로 Update Maven Project 실행합니다.

https://stackoverflow.com/questions/56142369/why-am-i-getting-unknown-error-in-line-1-of-pom-xml
https://stackoverflow.com/questions/56154266/why-does-change-from-spring-boot-version-2-1-4-to-2-1-5-gives-unknown-configurat

stack overFlow 오류 해결 과정 모범 답안 입니다. 

## 첫번째 JPA 엔티티 Domain Class 만들기

> project.domain.Account

~~~
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "ACCOUNT")
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "ACCOUNT_ID", nullable = false)
	@NotBlank(message = "아이디는 비워둘 수 없습니다.")
	private String userId;
	
	@Column(name = "ACCOUNT_USERNAME", nullable = false)
	@NotBlank(message = "이름은 비워둘 수 없습니다.")
	private String username;
	
	@Column(name = "ACCOUNT_PASSWORD", nullable = false)
	@NotBlank(message = "비밀번호는 비워 둘 수 없브니다.")
	private String password;
	
	@Column(name = "ACCOUN_ROLE", nullable = false)
	@Enumerated(value = EnumType.STRING)
	public UserRole userRole = UserRole.USER;
	
	public Account(String userId, String username, String password, UserRole role) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.userRole = role;
	}
}
~~~

- `@Getter` - lombok 자동으로 getter 를 생성해 줍니다.
- `@Entity` - 엔티티 클래스임을 지정하며 테이블과 매핑되도록 합니다.
- `@Table` - 엔티티가 매핑될 테이블을 지정하고 생략시 엔티티 클래스 이름과 같은 테이블로 매핑된다.
- `@Id` - 해당 프로퍼티가 테이블의 주키(primary key) 역할을 한다는 것을 나타낸다
- `@GeneratedValue` - 주키의 값을 위한 자동 생성 전략을 명시하는데 사용한다. 
- `@Column` - 칼럼의 이름을 이용하여 지정된 필드나 속성을 테이블의 칼럼에 매핑 한다. 생략되면 속성과 같은 이름의 칼럼으로 매핑된다. 칼럼의 NULL 허용여부, 길이등을 속성으로 표시한다. 칼럼의 기본값은 columnDefinition 으로 정의하면 된다. columnDefinition = "number(5) default 0"

domain 클래스를 보시면 `setter가 없습니다.` 
이는 의도한 것인데, `getter와 달리 setter는 무분별하게 생성하지 않습니다.`
domain 인스턴스에 변경이 필요한 이벤트가 있을 경우 그 이벤트를 나타낼 수 있는 메소드를 만들어야하며, `무분별하게 값을 변경하는 setter는 최대한 멀리`하시는게 좋습니다.
(예를 들어, 주문취소 같은 경우 `setOrderStatus()가 아니라 cancelOrder()`를 만들어서 사용하는 것입니다.
똑같이 orderStatus를 변경할지라도, `그 의도와 사용범위가 명확한 메소드`를 만드는것이 중요합니다.)

기본 생성자 또한 `@NoArgsConstructor(access = AccessLevel.PUBLIC)` lombok 어노테이션으로 처리하였는데
접근 권한을 최소화 하기 위해서 사용했습니다.
JPA에서는 프록시를 생성을 위해서 기본 생성자를 반드시 하나를 생성해야합니다. 
기본 생성자를 아무 이유 없이 열어두는 것은 객체 생성 시 안전성을 심각하게 떨어트린다고 생각합니다
이때 접근 권한이 protected 이면 됩니다. 굳이 외부에서 생성을 열어둘 필요가 없습니다.

### UserRole Enum 열거형 상수 정의

> project.domain.UserRole

~~~
public enum UserRole {
	ADMIN("ROLE_ADMIN"),
	USER("ROLE_ADMIN");
	
	private String roleName;
	
	UserRole(String roleName) {
		this.roleName = roleName;
	}
	
	public String getKey() {
		return name();
	}
	
    public String getRoleName() {
        return roleName;
    }
}
~~~

유저의 권환을 열거향 상수 Enum 으로 정의하여 값을 주도록 하겠습니다.

Enum을 사용하면서 우리가 얻을 수 있는 이점

- 1. 코드가 `단순해지며, 가독성`이 좋습니다.
- 2. `인스턴스 생성과 상속을 방지`하여 `상수값의 타입안정성이 보장`됩니다.
- 3. enum class를 사용해 새로운 상수들의 타입을 정의함으로 `정의한 타입이외의 타입을 가진 데이터값을 컴파일시 체크`한다.
- 4. 키워드 enum을 사용하기 때문에 `구현의 의도가 열거임을 분명하게` 알 수 있습니다.

#### Enum 관리 모듈

> project.controller.AuthController

특정 enum 타입이 갖고 있는 모든 값을 출력시키는 기능은 Class의 getEnumConstants() 메소드를 사용하면 쉽게 해결할 수 있습니다.
enum의 리스트는 select box 즉, view영역에 제공되어야 하기 때문에 Controller에서 전달하도록 만들어보겠습니다.

~~~
@RestController
public class AuthController {
	@GetMapping("/enum")
	public Map<String, Object> getEnum() {
		Map<String, Object> enums = new LinkedHashMap<String, Object>();
		
		Class userRole = UserRole.class;
		
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

> project.enums.EnumModel

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
	USER("ROLE_ADMIN");
	
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

EnumModel을 이용하여 실제 값을 가지고 view에 전달할 수 있는 Dto를 만들겠습니다.
Dto의 이름은 EnumValue입니다.

> project.enums.EnumValue

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

> project.controller.AuthController

~~~
...생략
	@GetMapping("/value")
	public Map<String, List<EnumValue>> getEnumValue() {
	    Map<String, List<EnumValue>> enumValues = new LinkedHashMap<>();

	    enumValues.put("userRole", toEnumValues(UserRole.class));

	    return enumValues;
	}
	
	private List<EnumValue> toEnumValues(Class<? extends EnumModel> e){
	    /*
	        // Java8이 아닐경우
	        List<EnumValue> enumValues = new ArrayList<>();
	        for (EnumModel enumType : e.getEnumConstants()) {
	            enumValues.add(new EnumValue(enumType));
	        }
	        return enumValues;
	     */
	    return Arrays
	            .stream(e.getEnumConstants())
	            .map(EnumValue::new)
	            .collect(Collectors.toList());
	}
~~~

EnumModel 배열을 EnumValue 리스트로 전환하는 일을 하는 toEnumValues를 구현하여 getEnumValue에서는 해당 메소드를 호출만 하도록 변경하였습니다.
toEnumValues의 경우 Java8의 stream을 사용하면 아주 깔끔한 코드로 전환이 가능하지만, 혹시나 아직 Java8 문법이 어색하시거나 그 이하 버전을 사용하실 경우를 대비하여 주석으로 하위버전 코드를 추가하였습니다.
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

> project.enums.EnumMapper

~~~
public class EnumMapper {
    private Map<String, List<EnumValue>> factory = new HashMap<>();

    private List<EnumValue> toEnumValues(Class<? extends EnumModel> e){
        return Arrays
                .stream(e.getEnumConstants())
                .map(EnumValue::new)
                .collect(Collectors.toList());
    }

    public void put(String key, Class<? extends EnumModel> e){
        factory.put(key, toEnumValues(e));
    }

    public Map<String, List<EnumValue>> getAll(){
        return factory;
    }

    public Map<String, List<EnumValue>> get(String keys){
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

이는 EnumMapper 자체가 단독 모듈로서 사용하기 위함인데, 만약 생성자에서 commissionType, commissionCutting을 추가하게 될 경우 다른 프로젝트에서 EnumMapper를 사용할 때에는 EnumMapper 내부의 코드를 수정해야 하는 일이 발생합니다. 이는 OCP원칙에 위반되기도 하며, 유지보수 하기가 어렵게 만드는 일이기 때문에 항상 공통 모듈을 만들때는 이 점을 주의해야 합니다.

이렇게 만든 EnumMapper를 Bean으로 등록하겠습니다.

> project.enums.AppConfig

~~~
@Configuration
public class AppConfig {
    @Bean
    public EnumMapper enumMapper() {
        EnumMapper enumMapper = new EnumMapper();
        enumMapper.put("UserRole", UserRole.class);
        return enumMapper;
    }
}
~~~

Mapper 확인을 위해서 Controller 추가도 하겠습니다.

> project.controller.AuthController

~~~
... 생략
    private EnumMapper enumMapper;

    public AuthController(EnumMapper enumMapper) {
        this.enumMapper = enumMapper;
    }

    @GetMapping("/mapper")
    public Map<String, List<EnumValue>> getMapper() {
        return enumMapper.getAll();
    }
~~~

## 두번째 domain을 관리할 repository 생성하기

~~~
public interface AccountRepository extends JpaRepository<Account, Long>{
	Optional<Account> findByUserId(String userId);
}
~~~

### repository save & find Test

> src/test/java.ProjectApplicationTests

정상적으로 save & find 가 잘되는지 Test를 해보겠습니다.

~~~
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectApplicationTests {

	@Autowired
	private AccountRepository repository;
	
	@Test
	public void contextLoads() {
		Account account = new Account(
				"ID",
				"NAME",
				"PASSWORD",
				UserRole.USER
				);
		
		repository.save(account);
		Account saved = repository.findAll().get(0);
		assertThat(saved.getUserId(), is("ID"));
		assertThat(saved.getUsername(), is("NAME"));
		assertThat(saved.getPassword(), is("PASSWORD"));
		assertThat(saved.getUserRole(), is(UserRole.USER));
	}
}
~~~

![project-import](/images/repository-test.png)

정상적으로 생성하고 값을 가져오는것을 확인 할 수 있습니다.

## 세번째 REST APIS 만들기

> project.controller.AuthController

유저 리소스 용 REST API 

|---|---|---|---|
| ### URL | request | response | description |
| /api/user | GET | 200, [{id: '1', name:''}, {id:'2', name:''}] | 모든 유저정보를 가져옵니다. |
| /api/user | POST | 201, 생성된 user값 | 새로운 유저 생성 |
| /api/user/{id} | GET | 200, {id:'1', name:''} | ID로 특정 user 조회 |
| /api/user/{id} | PUT | 204, 업데이트된 값 | ID로 user 업데이트 |
| /api/user/{id} | DELETE | 204, 내용 없음 | ID로 user 삭제 |

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

싱글톤 패턴

펙토리 패턴

전략 패턴