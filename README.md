# Pgyebu 📝
안녕하세요 mbti P를 위한 가계부, P계부 안드로이드 앱입니다.

### 그게 뭐에요?
<img src="https://github.com/heojungeun/Pgyebu/blob/main/capture.png" />

> **세상에서 가장 간단한 가계부를 소개합니다.** 터치하고, 기록만 하세요. 정리는 저희가 다 해드려요.

### 왜 만들었어요?
- [x] 오늘 돈 얼마를 썼는지 생각하다가 몇 자 써보고, 복잡하고 피곤해서 가계부 적기를 포기하신 적이 있으신가요?

- [x] 분명 필요하지만 쓰기 부담스러웠던 적 있으신가요?

- [x] 간단하게 기록하고 싶고, 치밀하게 작성할 필요 없으신가요?

그런 당신을 위해 만든, P계부 지금 바로 사용해보세요.

### 어떻게 만들었어요?
- 🛠️ 사용 기술 및 라이브러리
  - Android Kotlin
  - MVVM 패턴
  - constraintlayout
  - Retrofit2
  - [캘린더뷰](https://github.com/prolificinteractive/material-calendarview)
  - [통계 차트](https://github.com/PhilJay/MPAndroidChart)
  - ...

- 사용 폰트
  - [Leferi](http://leferitype.com/)
  
- 기능
  - 로그인 화면(MainActivity)
    - 로그인
    - 비밀번호 찾기
    - 회원가입
  - 홈 화면(HomeActivity)
    - 메뉴
      - 설정
      - 마이페이지
    - 통계
    - 항목 추가
    - 메인 화면 요약 금액
    - 달력
    - 날짜별 항목 리스트 출력
  - 항목 추가(AddActivity)
    - 금액 입력
    - 수입 / 지출, 카테고리 선택
    - 메모
  - 통계(ChartActivity)
    - 수입과 지출 비율 차트
    - 달별 최대 지출 카테고리
    - 지난 달과 현재 달 비교 지출 금액
  - 설정(SettingActivity)
    - 달력 노출 기준 일자 선택
    - 메인 화면 요약 금액 종류 선택
  - 마이페이지(MypageActivity)
    - 계정 정보 수정
    - 비밀번호 변경
    - 로그아웃
    - 회원 탈퇴
    

### 백엔드는 어떻게 했어요??
**[백엔드팀 레포지토리](https://github.com/charmdong/account-book)**
