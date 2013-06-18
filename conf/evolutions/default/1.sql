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

create table item (
  id                        bigint not null,
  name                      varchar(255),
  brand_id                  bigint,
  thumbnail                 varchar(255),
  constraint pk_item primary key (id))
;

create table shoe (
  id                        bigint not null,
  name                      varchar(255),
  brand_id                  bigint,
  thumbnail                 varchar(255),
  constraint pk_shoe primary key (id))
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

create sequence item_seq;

create sequence shoe_seq;

create sequence user_seq;

alter table item add constraint fk_item_brand_1 foreign key (brand_id) references brand (id) on delete restrict on update restrict;
create index ix_item_brand_1 on item (brand_id);
alter table shoe add constraint fk_shoe_brand_2 foreign key (brand_id) references brand (id) on delete restrict on update restrict;
create index ix_shoe_brand_2 on shoe (brand_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists brand;

drop table if exists item;

drop table if exists shoe;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists brand_seq;

drop sequence if exists item_seq;

drop sequence if exists shoe_seq;

drop sequence if exists user_seq;

