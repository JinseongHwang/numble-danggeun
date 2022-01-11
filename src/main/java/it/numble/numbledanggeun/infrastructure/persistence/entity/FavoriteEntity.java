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
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_favorite")
@AttributeOverride(name = "id", column = @Column(name = "favorite_id"))
@Where(clause = "deleted=0")
@Entity
public class FavoriteEntity extends BaseEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_favorite_user"))
    private UserEntity user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false, foreignKey = @ForeignKey(name = "fk_favorite_item"))
    private ItemEntity item;

    @Builder
    public FavoriteEntity(UserEntity user, ItemEntity item) {
        this.user = user;
        this.item = item;
    }
}
