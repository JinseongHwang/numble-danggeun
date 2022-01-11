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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_item_image")
@AttributeOverride(name = "id", column = @Column(name = "item_image_id"))
@Where(clause = "deleted=0")
@Entity
public class ItemImageEntity extends BaseEntity {

    @NotBlank
    @Column(name = "url", nullable = false, length = 300)
    private String url;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false, foreignKey = @ForeignKey(name = "fk_itemimage_item"))
    private ItemEntity item;

    @Builder
    public ItemImageEntity(String url, ItemEntity item) {
        this.url = url;
        this.item = item;
    }
}
