# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table brand (
  id                        bigint not null,
  name                      varchar(255),
  thumbnail                 varchar(255),
  constraint uq_brand_name unique (name),
  constraint pk_brand primary key (id))
;

create table contribution (
  id                        bigint not null,
  user_email                varchar(255),
  size_id                   bigint,
  item_id                   bigint,
  adjustment                integer,
  constraint pk_contribution primary key (id))
;

create table item (
  id                        bigint not null,
  name                      varchar(255),
  brand_id                  bigint,
  thumbnail                 varchar(255),
  constraint pk_item primary key (id))
;

create table local_token (
  uuid                      varchar(255) not null,
  creation_time             timestamp,
  expiration_time           timestamp,
  sign_up                   boolean,
  expired                   boolean,
  email                     varchar(255),
  constraint pk_local_token primary key (uuid))
;

create table product_type (
  id                        integer not null,
  description               varchar(255),
  constraint pk_product_type primary key (id))
;

create table size (
  id                        bigint not null,
  product_type_id           integer,
  constraint pk_size primary key (id))
;

create table user (
  email                     varchar(255) not null,
  shoes_measure             decimal(38),
  hasher                    varchar(255),
  password                  varchar(255),
  salt                      varchar(255),
  first_name                varchar(255),
  last_name                 varchar(255),
  full_name                 varchar(255),
  user_id                   varchar(255),
  provider_id               varchar(255),
  avatar_url                varchar(255),
  constraint pk_user primary key (email))
;


create table item_product_type (
  item_id                        bigint not null,
  product_type_id                integer not null,
  constraint pk_item_product_type primary key (item_id, product_type_id))
;

create table product_type_item (
  product_type_id                integer not null,
  item_id                        bigint not null,
  constraint pk_product_type_item primary key (product_type_id, item_id))
;
create sequence brand_seq;

create sequence contribution_seq;

create sequence item_seq;

create sequence local_token_seq;

create sequence product_type_seq;

create sequence size_seq;

create sequence user_seq;

alter table contribution add constraint fk_contribution_user_1 foreign key (user_email) references user (email) on delete restrict on update restrict;
create index ix_contribution_user_1 on contribution (user_email);
alter table contribution add constraint fk_contribution_size_2 foreign key (size_id) references size (id) on delete restrict on update restrict;
create index ix_contribution_size_2 on contribution (size_id);
alter table contribution add constraint fk_contribution_item_3 foreign key (item_id) references item (id) on delete restrict on update restrict;
create index ix_contribution_item_3 on contribution (item_id);
alter table item add constraint fk_item_brand_4 foreign key (brand_id) references brand (id) on delete restrict on update restrict;
create index ix_item_brand_4 on item (brand_id);
alter table size add constraint fk_size_productType_5 foreign key (product_type_id) references product_type (id) on delete restrict on update restrict;
create index ix_size_productType_5 on size (product_type_id);



alter table item_product_type add constraint fk_item_product_type_item_01 foreign key (item_id) references item (id) on delete restrict on update restrict;

alter table item_product_type add constraint fk_item_product_type_product__02 foreign key (product_type_id) references product_type (id) on delete restrict on update restrict;

alter table product_type_item add constraint fk_product_type_item_product__01 foreign key (product_type_id) references product_type (id) on delete restrict on update restrict;

alter table product_type_item add constraint fk_product_type_item_item_02 foreign key (item_id) references item (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists brand;

drop table if exists contribution;

drop table if exists item;

drop table if exists item_product_type;

drop table if exists local_token;

drop table if exists product_type;

drop table if exists product_type_item;

drop table if exists size;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists brand_seq;

drop sequence if exists contribution_seq;

drop sequence if exists item_seq;

drop sequence if exists local_token_seq;

drop sequence if exists product_type_seq;

drop sequence if exists size_seq;

drop sequence if exists user_seq;

