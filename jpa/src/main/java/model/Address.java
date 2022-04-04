package model;

import jdk.jfr.Enabled;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "aadress")
public class Address extends BaseEntity{

    @Column(name = "tanav")
    private String street;

    public Address(String street) {
        this.street = street;
    }
}
