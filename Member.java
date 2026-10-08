public class Member {
    private int memberId;
    private String name;
    private String phone;

    public Member(int memberId, String name, String phone) {
        this.memberId = memberId;
        this.name = name;
        this.phone = phone;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void displayMember() {
        System.out.println("Member ID : " + memberId);
        System.out.println("Name      : " + name);
        System.out.println("Phone     : " + phone);
        System.out.println("----------------------------");
    }
}
