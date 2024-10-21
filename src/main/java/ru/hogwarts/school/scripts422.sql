CREATE TABLE person (
    Id INTEGER PRIMARY KEY,
    User_name text,
    Age Integer,
    Driver_License BOOLEAN,
    Id INTEGER REFERENCES car (user_id)
   );

   CREATE TABLE car (
       Id INTEGER PRIMARY KEY,
       Brand text,
       Model text,
       Price numeric,
       User_id INTEGER
      );