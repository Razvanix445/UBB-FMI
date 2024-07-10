-- works with SQLite!

create table main.ReservationManagers
(
    id       integer
        constraint ReservationManagers_pk
            primary key autoincrement,
    name     varchar(20) not null,
    password varchar(20) not null
);

create table main.Trips
(
    id                 integer
        constraint Trips_pk
            primary key autoincrement,
    destination        varchar(20) not null,
    dateDeparture      date        not null,
    noOfAvailableSeats integer     not null
);

create table main.Reservations
(
    id             integer
        constraint Reservations_pk
            primary key autoincrement,
    trip_id        integer     not null
        constraint Reservations_Trips_id_fk
            references main.Trips,
    reserved_seats integer     not null,
    client_name    varchar(20) not null
);

create table main.Seats
(
    id             integer
        constraint Seats_pk
            primary key autoincrement,
    reservation_id integer not null
        constraint Seats_Reservations_id_fk
            references main.Reservations,
    seat_number    integer not null
);