drop table if exists tbl_comment;

drop table if exists tbl_favorite;

drop table if exists tbl_item;

drop table if exists tbl_item_image;

drop table if exists tbl_user;

create table tbl_comment
(
    comment_id bigint        not null auto_increment,
    created_at datetime      not null,
    deleted    bit default 0 not null,
    updated_at datetime      not null,
    content    varchar(500)  not null,
    item_id    bigint        not null,
    user_id    bigint        not null,
    primary key (comment_id)
) engine = InnoDB;

create table tbl_favorite
(
    favorite_id bigint        not null auto_increment,
    created_at  datetime      not null,
    deleted     bit default 0 not null,
    updated_at  datetime      not null,
    item_id     bigint        not null,
    user_id     bigint        not null,
    primary key (favorite_id)
) engine = InnoDB;

create table tbl_item
(
    item_id       bigint        not null auto_increment,
    created_at    datetime      not null,
    deleted       bit default 0 not null,
    updated_at    datetime      not null,
    category      varchar(255)  not null,
    comment_count bigint        not null,
    content       varchar(2000) not null,
    like_count    bigint        not null,
    location      varchar(255)  not null,
    price         bigint        not null,
    status        varchar(50)   not null,
    title         varchar(255)  not null,
    primary key (item_id)
) engine = InnoDB;

create table tbl_item_image
(
    item_image_id bigint        not null auto_increment,
    created_at    datetime      not null,
    deleted       bit default 0 not null,
    updated_at    datetime      not null,
    url           varchar(300)  not null,
    item_id       bigint        not null,
    primary key (item_image_id)
) engine = InnoDB;

create table tbl_user
(
    user_id           bigint        not null auto_increment,
    created_at        datetime      not null,
    deleted           bit default 0 not null,
    updated_at        datetime      not null,
    email             varchar(100)  not null,
    nickname          varchar(255)  not null,
    password          varchar(255)  not null,
    phone_number      varchar(13)   not null,
    profile_image_url varchar(255),
    username          varchar(50)   not null,
    primary key (user_id)
) engine = InnoDB;

# alter table tbl_user
#     drop index user_nickname_unique;

alter table tbl_user
    add constraint user_nickname_unique unique (nickname);

# alter table tbl_user
#     drop index user_email_unique;

alter table tbl_user
    add constraint user_email_unique unique (email);

alter table tbl_comment
    add constraint fk_comment_item
        foreign key (item_id)
            references tbl_item (item_id);

alter table tbl_comment
    add constraint fk_comment_user
        foreign key (user_id)
            references tbl_user (user_id);

alter table tbl_favorite
    add constraint fk_favorite_item
        foreign key (item_id)
            references tbl_item (item_id);

alter table tbl_favorite
    add constraint fk_favorite_user
        foreign key (user_id)
            references tbl_user (user_id);

alter table tbl_item_image
    add constraint fk_itemimage_item
        foreign key (item_id)
            references tbl_item (item_id);