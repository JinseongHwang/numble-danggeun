alter table tbl_user
    add column refresh_token varchar(500);

alter table tbl_user
    add column role integer not null;
