create table suppliers
(
  sup_id   integer     not null,
  sup_name varchar(40) not null,
  street   varchar(40) not null,
  city     varchar(20) not null,
  state    char(2)     not null,
  zip      char(5),
  primary key (sup_id)
);

create table coffees
(
  cof_name varchar(32)    not null,
  sup_id   int            not null,
  price    numeric(10, 2) not null,
  sales    integer        not null,
  total    integer        not null,
  primary key (cof_name),
  foreign key (sup_id) references suppliers (sup_id)
);

create table coffee_descriptions
(
  cof_name varchar(32) not null,
  cof_desc blob        not null,
  primary key (cof_name),
  foreign key (cof_name) references coffees (cof_name)
);

create table rss_feeds
(
  rss_name     varchar(32) not null,
  rss_feed_xml longtext    not null,
  primary key (rss_name)
);

create table cof_inventory
(
  warehouse_id integer     not null,
  cof_name     varchar(32) not null,
  sup_id       int         not null,
  quan         int         not null,
  date_val     timestamp,
  foreign key (cof_name) references coffees (cof_name),
  foreign key (sup_id) references suppliers (sup_id)
);

create table merch_inventory
(
  item_id   integer not null,
  item_name varchar(20),
  sup_id    int,
  quan      int,
  date_val  timestamp,
  primary key (item_id),
  foreign key (sup_id) references suppliers (sup_id)
);

create table coffee_houses
(
  store_id integer not null,
  city     varchar(32),
  coffee   int     not null,
  merch    int     not null,
  total    int     not null,
  primary key (store_id)
);

create table data_repository
(
  document_name varchar(50),
  url           varchar(200)
);
