package member;

public class MemberVO {
//    DB 설계 : Table 생성(컬럼)
//    입력화면 태그의 name 속성 값을 Table의 컬럼명과 동일하게 지정
//    데이터객체(DTO,VO) 의 필드를 화면의 태그 name 속성 값과 동일하게 선언
    private String name, gender, email;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
