package it.numble.numbledanggeun.infrastructure.persistence.entity;

import it.numble.numbledanggeun.domain.shared.ItemCategory;
import it.numble.numbledanggeun.domain.shared.ItemStatus;
import it.numble.numbledanggeun.domain.shared.Location;
import it.numble.numbledanggeun.infrastructure.persistence.BaseEntity;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_item")
@AttributeOverride(name = "id", column = @Column(name = "item_id"))
@Where(clause = "deleted=0")
@Entity
public class ItemEntity extends BaseEntity {

    @NotBlank(message = "게시글 제목을 입력해주세요.")
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "location", nullable = false)
    @Enumerated(EnumType.STRING)
    private Location location;

    @Column(name = "price", nullable = false)
    private long price;

    @NotBlank(message = "게시글 내용을 작성해주세요.")
    @Column(name = "content", nullable = false, length = 2_000)
    private String content;

    @NotNull
    @Column(name = "status", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private ItemStatus status;

    @Column(name = "comment_count", nullable = false)
    private long commentCount;

    @Column(name = "like_count", nullable = false)
    private long likeCount;

    @NotNull
    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemCategory category;

    @Builder
    public ItemEntity(String title, long price, String content, ItemCategory category) {
        this.title = title;
        this.location = Location.GASAN_DIGITAL_COMPLEX;
        this.price = price;
        this.content = content;
        this.status = ItemStatus.ON_SALE;
        this.commentCount = 0;
        this.likeCount = 0;
        this.category = category;
    }
}
