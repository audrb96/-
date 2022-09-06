package neonaduri.domain.Review;


import com.fasterxml.jackson.databind.node.BigIntegerNode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Table(name = "review")
@NoArgsConstructor
public class Review {

    //다대다 관계 mapping
    @OneToMany(mappedBy = "review")
    private List<ReviewTag> reviewTags = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private BigInteger reviewId;

    
    //foreign key
//    private BigInteger spotId;


    @Column(name = "review_content", nullable = false)
    private String reviewContent;

    @Column(name = "review_date", nullable = false)
    private Date reviewDate;

    @Column(name = "review_image", nullable = false)
    private String reviewImage;
}
