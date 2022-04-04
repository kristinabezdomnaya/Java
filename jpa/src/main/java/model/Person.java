package model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
@Entity
@Table(name = "isik")

public class Person extends BaseEntity {

    @NonNull
    @Column(name = "nimi")
    private String name;

    @OneToOne(cascade = { CascadeType.PERSIST})
    @JoinColumn(name = "aadressi_id")
    private Address address;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "isiku_telefonid",
            joinColumns=@JoinColumn(name = "isiku_id",
                    referencedColumnName = "id")
    )
    private List<Phone> phones = new ArrayList<>();


}
