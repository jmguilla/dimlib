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

create table dimension (
  id                        integer not null,
  test                      varchar(255),
  description               varchar(255),
  constraint pk_dimension primary key (id))
;

create table item (
  id                        bigint not null,
  name                      varchar(255),
  brand_id                  bigint,
  thumbnail                 varchar(255),
  constraint pk_item primary key (id))
;

create table measure (
  id                        integer not null,
  user_email                varchar(255),
  value                     decimal(38),
  constraint pk_measure primary key (id))
;

create table product_type (
  id                        integer not null,
  test                      varchar(255),
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
  first_name                varchar(255),
  last_name                 varchar(255),
  full_name                 varchar(255),
  user_id                   varchar(255),
  provider_id               varchar(255),
  constraint pk_user primary key (email))
;

create sequence brand_seq;

create sequence dimension_seq;

create sequence item_seq;

create sequence measure_seq;

create sequence product_type_seq;

create sequence size_seq;

create sequence user_seq;

alter table item add constraint fk_item_brand_1 foreign key (brand_id) references brand (id) on delete restrict on update restrict;
create index ix_item_brand_1 on item (brand_id);
alter table measure add constraint fk_measure_user_2 foreign key (user_email) references user (email) on delete restrict on update restrict;
create index ix_measure_user_2 on measure (user_email);
alter table size add constraint fk_size_productType_3 foreign key (product_type_id) references product_type (id) on delete restrict on update restrict;
create index ix_size_productType_3 on size (product_type_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists brand;

drop table if exists dimension;

drop table if exists item;

drop table if exists measure;

drop table if exists product_type;

drop table if exists size;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists brand_seq;

drop sequence if exists dimension_seq;

drop sequence if exists item_seq;

drop sequence if exists measure_seq;

drop sequence if exists product_type_seq;

drop sequence if exists size_seq;

drop sequence if exists user_seq;

