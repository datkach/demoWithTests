create table public.users (
                              id   serial primary key not null ,
                              name character varying,
                              email character varying,
                              country character varying,
                              is_deleted boolean
);
create unique index users_id_uindex on users using btree (id);

create table public.addresses (
                                  id bigserial primary key not null ,
                                  address_has_active boolean,
                                  city character varying(255),
                                  country character varying(255),
                                  street character varying(255),
                                  employee_id integer,
                                  foreign key (employee_id) references public.users (id)
                                      match simple on update no action on delete no action
);
create table public.passports
(
    id               serial
        primary key,
    date_of_birthday timestamp,
    expire_date      timestamp,
    first_name       varchar(255),
    second_name      varchar(255),
    serial_number    varchar(255),
    current_state    varchar(255),
    is_free          boolean,
    previous_id      integer
        constraint fkcugkhihhfjpa54vuhujosjr6v
            references public.passports
);
create table public.cabinets
(
    id         serial
        primary key,
    capacity   integer,
    is_deleted boolean,
    name       varchar(255)
);
create table public.photos
(
    id          serial
        primary key,
    add_date    timestamp,
    camera_type varchar(255),
    description varchar(255),
    photo_url   varchar(255),
    employee_id integer
        constraint fkbxq293jvxh5d4t6qrndovypyt
            references public.users,
    image       bytea
);
create table public.users_cabinets
(
    employees_id integer not null
        constraint fkg3uq9dandywli6g3japd5ia26
            references public.users,
    cabinets_id  integer not null
        constraint fkgx4j90c7lby9iqbh0ei1s7pcf
            references public.cabinets,
    active       boolean,
    primary key (employees_id, cabinets_id)
);

