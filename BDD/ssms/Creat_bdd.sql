-- Suppression des tables
DROP TABLE IF EXISTS Orders_Dishes, Dishes, Orders, Messages, Schedules, Reservations, Users, Tables, Restaurants, Cards;


-- Creation des Cards
CREATE TABLE Cards (
	id				INT				PRIMARY KEY identity,
	name			VARCHAR(30)		NOT NULL
	
);

CREATE TABLE Restaurants (
	id				INT				PRIMARY KEY identity,
    name			VARCHAR(50)		NOT NULL,
    address			VARCHAR(60)		NOT NULL,
    postal_code     CHAR(5)         not null,
    town            varchar(40)     not null,
    id_card			int		        NULL,

    FOREIGN KEY (id_card) REFERENCES Cards(id) on delete cascade
	 
);

CREATE TABLE Schedules (
    id				    INT				    PRIMARY KEY identity,
    open_hour		    time			not null,
    close_hour		    time			not null,
    id_restaurant		int			        null,

    FOREIGN KEY (id_restaurant) REFERENCES Restaurants(id) on delete cascade,
    check(close_hour > open_hour)
	
);

CREATE TABLE Tables (
	id				INT				PRIMARY KEY identity,
	number_place	int		        NOT NULL,
    state           char(4)         null,
	id_restaurant	INT				null,

    FOREIGN KEY (id_restaurant) REFERENCES Restaurants(id) on delete cascade,
    check(state in(null,'pres'))


);

CREATE TABLE Users (
	id				INT				PRIMARY KEY identity,
    name            varchar(40)     not null,
    lastname        varchar(40)     not null,
    email           varchar(50)     not null,
    password        varchar(150)    not null,
    token			varchar(255)	null,
    expiration_time	datetime		null,
    role            char(4)         not null default 'cust',

    check( role in('cust', 'admi', 'staf'))
);


CREATE TABLE Reservations (
	id				    INT				PRIMARY KEY identity,
	reservation_time    datetime        not null,
    state               char(4)         not null default 'hold',
    id_table            int             ,
    id_user             int             ,

    FOREIGN KEY (id_table) REFERENCES Tables(id),
    FOREIGN KEY (id_user) REFERENCES Users(id),
    check( state in('hold', 'gran', 'deni', 'here'))

);

CREATE TABLE Messages (
	id				INT				PRIMARY KEY identity,
	object			VARCHAR(100)	not NULL,
    content         varchar(250)    not null,
	id_user			int				,

	FOREIGN KEY (id_user) REFERENCES Users(id) on delete cascade
);


CREATE TABLE Orders (
	id				INT				PRIMARY KEY identity,
	state           char(4)         null,
    id_table        int             not null,

    FOREIGN KEY (id_table) REFERENCES Tables(id) on delete cascade,
    check(state in( null,'take','read', 'serv', 'sold'))

);


CREATE TABLE Dishes (
	id				INT				PRIMARY KEY identity,
	name            varchar(40)     not null,
    price           decimal(5,2)    not null check(price > 0),
    description     varchar(250)    not null,
    category        varchar(30)     not null,
    id_card         int             null,

    FOREIGN KEY (id_card) REFERENCES Cards(id) on delete cascade,
    check(category in('entry', 'dish', 'desert', 'beverage'))

);


CREATE TABLE Orders_Dishes (
    id_order         int             not null,
    id_dish        int             not null,

    FOREIGN KEY (id_order) REFERENCES Orders(id) on delete cascade,
    FOREIGN KEY (id_dish) REFERENCES Dishes(id)
);


