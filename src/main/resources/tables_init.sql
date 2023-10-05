DROP DATABASE lowPriceMonitoringBot;
create database lowPriceMonitoringBot;
use lowPriceMonitoringBot;

create table goods_details (
                               id bigint not null auto_increment primary key,
                               last_update_time bigint,
                               link TEXT,
                               link_to_shop_with_lower_price TEXT,
                               details_name TEXT,
                               best_price varchar(255),
                               old_best_price varchar(255),
                               telegram_chat_id varchar(100),
                               search_regularity varchar(255),
                               active_search varchar(10)
);

create table goods_search (
                              id bigint not null auto_increment primary key,
                              link_to_root_goods TEXT,
                              goods_details_ids TEXT,
                              state varchar(255),
                              active_goods_id bigint,
                              foreign key (active_goods_id) references goods_details(id)
);

create table chat (
                      id bigint not null auto_increment primary key,
                      telegram_chat_id varchar(100) not null,
                      locale varchar(100) not null,
                      chat_step varchar(100),
                      active_search bigint,
                      foreign key (active_search) references goods_search(id)
);
create table feedback (
                      id bigint not null auto_increment primary key,
                      telegram_chat_id varchar(100) not null,
                      message TEXT
);