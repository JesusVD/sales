package com.jesus.sales.model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Sale {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSale;

    @ManyToOne
    @JoinColumn(name = "id_client" ,nullable = false, foreignKey = @ForeignKey(name = "FK_Sale_Client"))
    private Client client;

    @ManyToOne
    @JoinColumn(name = "id_user" ,nullable = false, foreignKey = @ForeignKey(name = "FK_Sale_User"))
    private User user;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false, columnDefinition = "decimal(6,2)")
    private double total;

    @Column(nullable = false, columnDefinition = "decimal(6,2)")
    private double tax;

    @OneToMany(mappedBy = "sale" , cascade = CascadeType.ALL)
    private List<SaleDetail> details;

}
