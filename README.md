# 프로젝트 개요

모노미Monomi는 이미지를 검색하고 북마크할 수 있는 안드로이드 앱입니다. 사용자는 키워드로 이미지를 검색하고, 마음에 드는 이미지를 북마크에 저장하여 나중에 볼 수 있습니다.

### 📍모노미의 뜻

- **모아(moa)** + **이미지(image)** 의 앞글자를 쏙 뽑아 “모–이미” → “모노미”
- 즉, “내가 검색한/좋아하는 이미지를 모아두는 앱” 이라는 뜻입니다.

# 주요 기능

- **이미지 검색:** 키워드 기반 이미지 검색
- **북마크**: 마음에 드는 이미지를 저장하고 관리
- **페이지네이션**: 무한 스크롤로 추가 검색 결과 로드
- 데이터 캐싱: 5분 이내 동일 키워드로 검색했을 때 네트워크 통신없이 결과 로드
- **로컬 저장소**: 북마크된 항목은 기기 내부에 저장

# 기술 스택

- 언어: Kotlin
- UI: Jetpack Compose
- 비동기 처리: Kotlin Coroutines, Flow
- 의존성 주입: Hilt
- 로컬 데이터베이스: Room
- 네트워크: Retrofit

# 프로젝트 구조

```kotlin
monomi                     
├── app                                # 앱 모듈
│   ├── ui                             # UI 관련 패키지
│   │   ├── component                  # 재사용 가능한 컴포넌트
│   │   ├── feature                    # 기능별 화면
│   │   │   ├── search                 # 검색 화면
│   │   │   └── bookmark               # 북마크 화면
│   │   └── theme                      # 앱 테마
│   ├── navigation                     # 화면 간 네비게이션
│   └── di                             # 앱 레벨 의존성 주입
│
├── core                               # 코어 모듈 그룹
│   ├── network                        # 네트워크 모듈
│   │   ├── service                    # API 서비스
│   │   ├── model                      # 네트워크 모델
│   │   └── di                         # 네트워크 DI 모듈
│   │
│   ├── data                           # 데이터 모듈
│   │   ├── repository                 # 리포지토리 구현
│   │   │   ├── search                 # 검색 리포지토리
│   │   │   └── bookmark               # 북마크 리포지토리
│   │   ├── cache                      # 캐시 관련 구현
│   │   ├── mapper                     # 데이터 변환기
│   │   ├── source                     # 데이터 소스
│   │   │   ├── local                  # 로컬 데이터 소스
│   │   │   └── remote                 # 원격 데이터 소스
│   │   └── di                         # 데이터 DI 모듈
│   │
│   ├── model                          # 모델 모듈 (공통 데이터 모델)
│   │
│   └── database                       # 데이터베이스 모듈
│       ├── dao                        # 데이터 액세스 객체
│       ├── entity                     # 엔티티
│       └── di                         # 데이터베이스 DI

```

전체적으로 이 프로젝트는 멀티 모듈 아키텍처를 채택하여 각 계층의 책임을 명확히 분리했으며, Jetpack Compose와 MVVM, MVI 그리고 [Google 공식 아키텍처 지침](https://developer.android.com/topic/architecture?hl=ko)에 맞취 구현하였습니다.

### 📍secrets.properties

민감한 정보 관리 방식으로 별도의 파일(secrets.properties)을 추가하였습니다.
