package vn.iotstar.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class ProductEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(name = "product_name", length = 500, columnDefinition = "nvarchar(500) not null")
    private String name;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private double unitPrice;

    @Column(length = 200, columnDefinition = "nvarchar(200) not null")
    private String image;

    @Column(columnDefinition = "nvarchar(500)")
    private String description;

    @Column
    private double discount;

    @Temporal(TemporalType.DATE)
    private Date enteredDate;

    @Column
    private short status;

    // Quan hệ nhiều sản phẩm thuộc 1 category
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    // Quan hệ 1 sản phẩm có nhiều orderDetails
//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
//    private Set<OrderDetailEntity> orderDetails;
}
