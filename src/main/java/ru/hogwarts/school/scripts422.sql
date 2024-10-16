CREATE TABLE person (
    Id INTEGER,
    User_name text,
    Age Integer,
    Driver_License BOOLEAN,
    Id INTEGER REFERENCES car (user_id)
   );

   CREATE TABLE car (
       Id INTEGER,
       Brand text,
       Model text,
       Price numeric,
       User_id INTEGER
      );