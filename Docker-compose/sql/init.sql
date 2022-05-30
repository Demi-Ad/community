create table account
(
    account_id    bigint auto_increment
        primary key,
    email         varchar(255) not null,
    nickname      varchar(255) not null,
    password      varchar(255) not null,
    profile_img   varchar(255) null,
    registered_at datetime(6)  not null,
    role          varchar(255) not null
);

create table account_block
(
    account_block_id bigint auto_increment
        primary key,
    block_comment    varchar(255) not null,
    block_date       datetime(6)  null,
    block_until_date datetime(6)  not null,
    account_id       bigint       not null,
    constraint block_account_fk
        foreign key (account_id) references account (account_id)
);

create table admin
(
    admin_seq   bigint       not null
        primary key,
    admin_id    varchar(255) not null,
    admin_name  varchar(255) not null,
    create_date datetime(6)  null,
    password    varchar(255) not null,
    role        varchar(255) null
);

create table forbidden_word
(
    forbidden_word_id       bigint auto_increment
        primary key,
    forbidden_text          varchar(255) not null,
    is_comment_forbidden    bit          null,
    is_guest_book_forbidden bit          null,
    is_post_forbidden       bit          null
);

create table guest_book
(
    guest_book_id bigint auto_increment
        primary key,
    content       varchar(255) not null,
    created_at    datetime(6)  null,
    guest_id      bigint       not null,
    owner_id      bigint       not null,
    constraint guest_fk
        foreign key (guest_id) references account (account_id),
    constraint owner_fk
        foreign key (owner_id) references account (account_id)
);

create table notice
(
    notice_id  bigint auto_increment
        primary key,
    content    varchar(255) not null,
    created_at datetime(6)  null,
    title      varchar(255) not null,
    admin_id   bigint       not null,
    constraint admin_fk
        foreign key (admin_id) references admin (admin_seq)
);

create table notification
(
    notification_id bigint auto_increment
        primary key,
    content         varchar(255) not null,
    created_at      datetime(6)  null,
    event_type      varchar(255) not null,
    uri             varchar(255) not null,
    from_account_id bigint       not null,
    to_account_id   bigint       not null,
    constraint publisher_fk
        foreign key (from_account_id) references account (account_id),
    constraint subscriber_fk
        foreign key (to_account_id) references account (account_id)
);

create table post
(
    post_id      bigint auto_increment
        primary key,
    created_at   datetime(6)  null,
    updated_at   datetime(6)  null,
    post_content longtext     null,
    post_title   varchar(255) not null,
    account_id   bigint       not null,
    constraint post_account_fk
        foreign key (account_id) references account (account_id)
);

create table comment
(
    comment_id bigint auto_increment
        primary key,
    content    varchar(255) not null,
    created_at datetime(6)  not null,
    account_id bigint       not null,
    parent_id  bigint       null,
    post_id    bigint       not null,
    constraint comment_account_fk
        foreign key (account_id) references account (account_id),
    constraint comment_post_fk
        foreign key (post_id) references post (post_id),
    constraint parent_comment_kf
        foreign key (parent_id) references comment (comment_id)
);

create table post_file
(
    post_file_id      bigint auto_increment
        primary key,
    file_convert_name varchar(255) not null,
    file_extension    varchar(255) not null,
    file_origin_name  varchar(255) not null,
    post_id           bigint       not null,
    constraint post_fk
        foreign key (post_id) references post (post_id)
);

create table post_like
(
    post_like_id bigint auto_increment
        primary key,
    liked_at     datetime(6) null,
    account_id   bigint      null,
    post_id      bigint      null,
    constraint like_account_fk
        foreign key (account_id) references account (account_id),
    constraint like_post_fk
        foreign key (post_id) references post (post_id)
);

create table tag
(
    tag_id   bigint auto_increment
        primary key,
    tag_item varchar(255) not null
);

create table post_tag
(
    post_tag_id bigint auto_increment
        primary key,
    created_at  datetime(6) null,
    updated_at  datetime(6) null,
    post_id     bigint      not null,
    tag_id      bigint      not null,
    constraint pt_post_fk
        foreign key (post_id) references post (post_id),
    constraint pt_tag_fk
        foreign key (tag_id) references tag (tag_id)
);

