package neonaduri.domain.Review;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "tag")
@NoArgsConstructor
public class Tag {

    //다대다 관계 mapping
    @OneToMany(mappedBy = "tag")
    private List<ReviewTag> reviewTags = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private BigInteger tagId;

    @Column(name = "tag_content")
    private String tagContent;

    @Column(name = "tag_count")
    private BigInteger tagCount;
}
