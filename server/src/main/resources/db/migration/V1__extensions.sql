-- btree_gist is required for the reservation overlap exclusion constraint.
create extension if not exists btree_gist;
-- pg_trgm backs the ILIKE keyword search on space name and address.
create extension if not exists pg_trgm;
