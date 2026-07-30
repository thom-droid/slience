# Introduction

- 영화 티켓 예매 시스템
- 실시간 영화 정보를 동기화
- 영화 좌석을 중복되지 않게 예매(double-booking)할 수 있도록 하는 동시성 시스템을 구축

---
# 작업하면서 궁금했던 내용들, 찾은 답변들
- 동시 예약 테스트
- 조인했을 때 락은 어떤 로우에 락이 걸리나?

# 핵심 목표

## 1. 동시 요청에 대해 올바른 트랜잭션 처리
- 동일 좌석에 대한 동시 요청 처리	
- race condition 방지	
- retry 상황에서도 데이터 무결성 유지

---

## 2. 트랜잭션 관리 비교

다음 전략들을 직접 구현하고 비교한다:
- Atomic update (UPDATE ... WHERE ...)
- Pessimistic locking (SELECT ... FOR UPDATE)
- Optimistic locking (version column)

---

## 3. 트랜잭션 범위 설계
- @Transactional 
- rollback 전략 (checked vs runtime exception)
- self-invocation 문제 이해

--- 

## 4. API 디자인 
- 멱등적 API (idempotent API 설계 (retry-safe)
- 명확한 API contract
- Controller / Service / Infrastructure 분리

--- 

## 도메인 중심 아키텍처
- Controller: Adapter 역할만 수행
- Service: orchestration
- Domain: 순수 비즈니스 로직

---

# 🔥 추가 예정: 선착순 쿠폰 발행

예약 시스템에 다음 기능을 추가한다:
- 한정 수량 쿠폰 발급
- 선착순 처리 (high contention scenario)
---

# Minimum Viable Product (최소 요구사항)

| 카테고리             | 기능                    | 설명                                   | 기술요구사항                               | 중요도    |
|:-----------------|:----------------------|:-------------------------------------|:-------------------------------------|:-------|
| **외부 API**       | **영화 정보 싱크**          | 일간 데이터를 KOFIC(영진위) API로부터 받아온다.      | Openfeign / RestTemplate, Scheduling | Must   |
| **비즈니스 로직, 도메인** | **좌석 정보 관리**          | 영화 상영시간, 좌석 데이터 등을 관리한다.             | Relational Data Modeling             | Must   |
| **동시성**          | **좌석 예약**             | 이중 예매를 방지한다.                         | **Pessimistic/Optimistic Locking**   | Must   |
| **트랜잭션 관리**      | **예약 로직**             | 트랜잭션 범위를 설정하여 예매와 결제의 일관성을 유지하도록 한다. | **@Transactional, ACID Integrity**   | Must   |
| **디자인 패턴**       | **예매 할인**             | 조조할인, 연령별 할인을 적용한다.                  | **Strategy Pattern**                 | Should |
| **API 디자인**      | **RESTful Endpoints** | URI 표현과 표준 http 상태 코드를 사용한다.         | **API Design Maturity**              | Must   |
| **프론트**          | **예약 및 화면 UI**        | 예매 좌석 화면 및 예매 현황을 보여주는 화면을 구성한다.     | Thymeleaf (SSR)                      | Should |

---

# 핵심 포인트

## 1. 동시성 상황 시뮬레이션	
-	동일 좌석에 대해 100+ 동시 요청하여 성공, 실패, 중복 발생 등의 결과를 확인할 수 있어야 한다.

---

## 2. 트랜잭션 전략 비교
- pessimistic vs optimistic vs atomic 각각의 전략을 비교한다.

---

## 3. 실패 시나리오 대응
- 각각의 실패 시나리오에 대응한다.
- 트랜잭션 처리 도중 실패
- 네트워크 재시도 
- 장기 트랜잭션 처리
---

# 기술스택

- **Language:** Java 17+
- **Framework:** Spring Boot 3.x
- **Database:** PostgreSQL
- **ORM:** Spring Data JPA
- **Template Engine:** Thymeleaf (for simple, fast UI development)
- **External API:** KOFIC Open API