import java.util.Map;
import java.util.Objects;

class Person {
    private String uuid;
    private String firstname;
    private String lastname;
    private String nickname;
    private String email;

    public Person(String uuid, String firstname, String lastname, String nickname, String email) {
        this.uuid = uuid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.nickname = nickname;
        this.email = email;
    }


    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, String> toMap(){
        return Map.of(
            "uuid", this.getUuid(),
            "firstname", this.getFirstname(), 
            "lastname", this.getLastname(),
            "nickname", this.getNickname(),
            "email", this.getEmail()
        );
    }

    @Override
    public String toString() {
        return "{" +
            " uuid='" + getUuid() + "'" +
            ", firstname='" + getFirstname() + "'" +
            ", lastname='" + getLastname() + "'" +
            ", nickname='" + getNickname() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this)
            return true;
        if (!(other instanceof Person)) {
            return false;
        }
        Person person = (Person) other;
        return Objects.equals(uuid, person.uuid) && Objects.equals(firstname, person.firstname) && Objects.equals(lastname, person.lastname) && Objects.equals(nickname, person.nickname) && Objects.equals(email, person.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, firstname, lastname, nickname, email);
    }

}
