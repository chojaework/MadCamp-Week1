# Two표

***
### A. 팀원
 * KAIST 전산학부 [이현수](https://github.com/twinDarft)
 * 고려대학교 컴퓨터학과 [조재원](https://github.com/chojaework)

***
 ### B. 개발 환경  
 * OS: Android (minSdk: 24, targetSdk: 33)  
 * Language: Java
 * IDE: Android Studio
 * Target Device: Galaxy S10e

***
### C. 프로젝트 설명
 Android Studio와 java 를 이용하여 탭 3개가 있는 휴대폰 앱을 개발했다.
 탭 3개는 각각 연락처, 갤러리, 투표 기능을 구현하고 있다.
 상세 설명은 다음과 같다.

#### 1. 연락처
 * 첫 화면에서는 recyclerView에 '이름'과 '전화번호' 요소가 들어간 json 파일의 내용을 삽입하여 표현한 연락처 목록과, 연락처의 '추가' 기능을 구현했다. 또한, recyclerView의 각 요소를 클릭했을 때, onClick 요소를 적용하여 '상세 연락처' 화면으로 이동하도록 했다.
 * '상세 연락처'화면에서는 해당 요소의 상세 정보가 나타나고, 연락처의 '편집'과 '삭제'를 할 수 있다. 또한, 통화 버튼과 메시지 버튼을 눌렀을 때에는 각각 해당 번호에서의 실제 통화, 메시지 앱과 연동되어 화면이 나타난다.
 * '상세 연락처' 화면에서 '삭제' 버튼을 누르면, "정말로 삭제하시겠습니까?"라는 메시지가 포함된 다이얼로그와 함께 '삭제', '취소' 버튼이 나온다. '삭제'를 클릭하면, 연락처에서 해당 요소가 삭제되고, '취소'를 클릭하면 아무 일도 일어나지 않는다.
 * 첫 화면에서의 '추가' 버튼이나, '상세 연락처' 화면에서의 '편집' 버튼을 클릭하면, '연락처 편집' 화면이 나온다. 이름과 전화번호를 입력하고, '저장'버튼을 누르면 연락처에 추가되거나, 내용이 편집된다. 이름과 전화번호 중 하나의 요소라도 누락되면 잘못되었다는 toast가 발생하며, 아무 일도 일어나지 않는다.

![initial](https://github.com/chojaework/MolAndroid/assets/92250113/17d1c9ad-d4c7-4007-98ee-e224a9817377)
![initial](https://github.com/chojaework/MolAndroid/assets/92250113/ad4b6870-f13d-4193-8f00-a439b814a746)
![initial](https://github.com/chojaework/MolAndroid/assets/92250113/8078de0e-8899-41b0-bfe8-ada56d217a1e)
#### 2. 갤러리
* tab 2에서는 갤러리 기능을 구현했다.
· 첫 화면에서는 '+' 버튼을 이용해서 실제 휴대폰 '갤러리 앱'과 연동하여 사진을 불러올 수 있다. 또한, 불러온 사진들은 화면에 gridView로 보여진다.
* gridView의 각 item(사진)을 클릭하면, 해당 사진이 큰 화면으로 나타난다. '닫기' 버튼을 클릭하면 첫 화면으로 돌아가고, '삭제' 버튼을 클릭하면 해당 사진이 gridView에서 삭제된다.

![initial](https://github.com/chojaework/MolAndroid/assets/92250113/41634bfa-8296-4922-add5-00806b10d06a)
![initial](https://github.com/chojaework/MolAndroid/assets/92250113/0d48c22e-4ec3-4355-8a72-0d4d86b5a035)
#### 3. 투표
 * tab 3에서는 익명 투표 기능을 구현했다.
 * '익명 투표' 버튼을 클릭하면, '후보 추가' 화면으로 이동한다. '후보 추가' 화면에서는 메뉴의 이름과 메뉴 사진을 후보로 올리는 기능을 구현한다. 후보에 올라간 메뉴는 아래에 ListView를 통해 나타나도록 했다. '투표 시작' 버튼을 누르면 '투표' 화면으로 넘어간다.
 * '투표' 화면에서는 '후보 추가' 화면에서 후보들이 그대로 나타난다. 메뉴를 클릭하면, "투표하시겠습니까?"라는 다이얼로그와 함께 '확인'과 '취소' 버튼이 나타난다. '확인'을 누르면, 투표가 완료되고, '취소'를 누르면 아무 일도 일어나지 않는다. '투표 끝내기' 버튼을 누르면 '투표 결과' 화면으로 이동한다.
 * '투표 결과' 화면에서는 가장 많은 표를 받은 메뉴와, 해당 메뉴의 투표 수, 전체 투표 수가 나타난다.

![initial](https://github.com/chojaework/MolAndroid/assets/92250113/5ac19cbd-bee9-49fe-8133-f55b1f935800)
![initial](https://github.com/chojaework/MolAndroid/assets/92250113/f8c3c8f0-0bcf-43c0-b679-183112c7fadf)
![initial](https://github.com/chojaework/MolAndroid/assets/92250113/33cb0ed7-b2b1-41d9-aff9-b6ee05cb23ba)
![initial](https://github.com/chojaework/MolAndroid/assets/92250113/3c952f8c-09e1-4b41-b7bb-a0fd640cd012)
![initial](https://github.com/chojaework/MolAndroid/assets/92250113/06301a59-c130-4ece-b036-dd774d870144)
