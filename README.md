# Introduction

- 영화 티켓 예매 시스템
- 실시간 영화 정보를 받아오고, 영화 좌석을 중복되지 않게 예매(double-booking)할 수 있도록 하는 시스템을 구축

---

# 프로젝트 목표
- 트랜잭션 동시성 제어 (atomic update, pessimistic / optimistic locking)
- 트랜잭션 관리 (@Transactional, exception strategy)
- API 디자인 (API contract, 레이어 분리)
  - Controller: API entry point
  - Service: logic, 트랜잭션 orchestration
  - Infrastructure: DB, mapping

---

# Minimum Viable Product (최소 요구사항)

| 카테고리             | 기능                | 설명                                   | 기술요구사항                               | 중요도    |
|:-----------------|:------------------|:-------------------------------------|:-------------------------------------|:-------|
| **외부 API**       | 영화 정보 싱크          | 일간 데이터를 KOFIC(영진위) API로부터 받아온다.      | Openfeign / RestTemplate, Scheduling | Must   |
| **비즈니스 로직, 도메인** | 좌석 정보 관리          | 영화 상영시간, 좌석 데이터 등을 관리한다.             | Relational Data Modeling             | Must   |
| **동시성**          | **좌석 예약**         | 이중 예매를 방지한다.                         | **Pessimistic/Optimistic Locking**   | Must   |
| **트랜잭션 관리**      | **예약 로직**         | 트랜잭션 범위를 설정하여 예매와 결제의 일관성을 유지하도록 한다. | **@Transactional, ACID Integrity**   | Must   |
| **디자인 패턴**       | **예매 할인**         | 조조할인, 연령별 할인을 적용한다.                  | **Strategy Pattern**                 | Should |
| **API 디자인**      | RESTful Endpoints | URI 표현과 표준 http 상태 코드를 사용한다.         | **API Design Maturity**              | Must   |
| **프론트**          | 예약 및 화면 UI        | 예매 좌석 화면 및 예매 현황을 보여주는 화면을 구성한다.     | Thymeleaf (SSR)                      | Should |

---

# 🛠 기술스택
- **Language:** Java 17+
- **Framework:** Spring Boot 3.x
- **Database:** PostgreSQL
- **ORM:** Spring Data JPA
- **Template Engine:** Thymeleaf (for simple, fast UI development)
- **External API:** KOFIC Open API