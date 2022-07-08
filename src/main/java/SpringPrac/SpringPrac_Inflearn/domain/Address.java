package SpringPrac.SpringPrac_Inflearn.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter //Setter를 설정아지 않음 >
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
