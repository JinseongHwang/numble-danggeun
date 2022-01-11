package it.numble.numbledanggeun.infrastructure.persistence.entity;

import it.numble.numbledanggeun.infrastructure.persistence.BaseEntity;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_comment")
@AttributeOverride(name = "id", column = @Column(name = "comment_id"))
@Where(clause = "deleted=0")
@Entity
public class CommentEntity extends BaseEntity {

    @NotBlank(message = "빈 댓글은 작성하실 수 없습니다.")
    @Column(name = "content", nullable = false, length = 500)
    private String content;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false, foreignKey = @ForeignKey(name = "fk_comment_item"))
    private ItemEntity item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_comment_user"))
    private UserEntity user;
}
