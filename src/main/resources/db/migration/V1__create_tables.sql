create table if not exists component
(
    id                  bigint generated by default as identity,
    "name"              varchar(120),
    "type"              smallint,
    primary_value       float(53),
    primary_unit        varchar(4),
    secondary_value     float(53),
    secondary_unit      varchar(4),
    tolerance           integer,
    weight_in_grammes   float(53),
    package_dimensions  varchar(11),
    manufacturer_id     varchar(40),
    trader_component_id varchar(40),
    "visible"           boolean not null,
    primary key (id)
);

create table if not exists "order"
(
    id             bigint generated by default as identity,
    product_id     bigint,
    quantity       bigint,
    status         smallint,
    date_received  timestamp(6),
    deadline       timestamp(6),
    date_started   timestamp(6),
    date_completed timestamp(6),
    "visible"      boolean not null,
    primary key (id)
);

create table if not exists parts_list_row
(
    id            bigint generated by default as identity,
    product_id    bigint,
    component_id  bigint,
    quantity      float(53),
    unit          varchar(4),
    "comment"     varchar(1000),
    date_added    timestamp(6),
    date_modified timestamp(6),
    "visible"     boolean not null,
    primary key (id)
);

create table if not exists product
(
    id                bigint generated by default as identity,
    "name"            varchar(120),
    version           varchar(10),
    weight_in_grammes integer,
    dimensions        varchar(11),
    "visible"         boolean not null,
    primary key (id)
);

create table if not exists storage_unit
(
    id           bigint generated by default as identity,
    "row"        integer,
    "column"     integer,
    shelf        integer,
    component_id bigint,
    quantity     float(53),
    "full"       boolean,
    "visible"    boolean not null,
    primary key (id)
);

alter table if exists "order"
    add constraint FK__order__product
        foreign key (product_id)
            references product;

alter table if exists parts_list_row
    add constraint FK__parts_list_row__component
        foreign key (component_id)
            references component;

alter table if exists parts_list_row
    add constraint FK__parts_list_row__product
        foreign key (product_id)
            references product;

alter table if exists storage_unit
    add constraint FK__storage_unit__component
        foreign key (component_id)
            references component;