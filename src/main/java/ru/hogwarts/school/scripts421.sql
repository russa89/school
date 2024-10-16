alter table student
add CONSTRAINT age_constraint CHECK (age > 16),
ALTER COLUMN name SET NOT null,
ADD CONSTRAINT name_unique UNIQUE (name),
ALTER COLUMN age set default '20';