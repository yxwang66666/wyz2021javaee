create schema bms_schema;

create table book (
    ISBN            char(13)        not null,
    title           varchar(100)     not null,
    author          varchar(100)    not null,
    publisher       varchar(100)     not null,
    price           float           not null,
    introduction    varchar(3000),
    image           varchar(200)    default 'https://img1.doubanio.com/view/subject/l/public/s25041427.jpg',
    primary key(ISBN)
);

create table barCode (
    barCode         char(13)        not null,
    ISBN            char(13)        not null,
    isBorrowed      bool            default false,
    primary key(barCode),
    foreign key(ISBN) references book(ISBN)
        on delete cascade
        on update cascade
);

create table fineRecord (
    userAccount     varchar(20)     not null,
    fineDate        datetime        not null,
    amount          int             not null,
    primary key(userAccount, fineDate, amount),
    foreign key(userAccount) references user(account)
        on delete cascade
);

create table borrowRecord (
    userAccount     varchar(20)     not null,
    bookBarCode     char(13)        not null,
    borrowDate      datetime        not null,
    days            int             not null,
    primary key(userAccount, bookBarCode),
    foreign key(userAccount) references user(account),
    foreign key(bookBarCode) references barCode(barCode),
    check (days  <= 120)
);

create table returnRecord (
    userAccount     varchar(20)     not null,
    bookBarCode     char(13)        not null,
    borrowDate      datetime        not null,
    returnDate      datetime        not null,
    primary key(userAccount, bookBarCode, borrowDate),
    foreign key(userAccount) references user(account)
        on delete cascade ,
    foreign key(bookBarCode) references barCode(barCode)
        on delete cascade
);


create table administrator
(
    account         varchar(20)     primary key,
    password        varchar(20)     not null,
    nickname        varchar(20)     not null,
    telephone       char(11)        unique not null,
    email           varchar(20)     unique not null
);


create table user
(
    account         varchar(20)     primary key,
    password        varchar(20)     not null,
    nickname        varchar(20)     not null,
    telephone       char(11)        unique not null,
    email           varchar(20)     unique not null
);