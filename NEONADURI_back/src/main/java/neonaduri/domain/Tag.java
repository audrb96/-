package neonaduri.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Table(name = "tag")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long tagId;

    @OneToMany(mappedBy = "tag",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ReviewTag> reviewTags = new HashSet<>();

    @Column(name = "tag_content", nullable = false)
    private String tagContent;

    @Column(name = "tag_count", nullable = false)
    private Long tagCount;
}
