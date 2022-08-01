BEGIN;
ALTER TABLE t_user ADD COLUMN user_status VARCHAR(20);
UPDATE t_user SET user_status = 'OFFLINE';
ALTER TABLE t_user ALTER COLUMN user_status SET NOT NULL;
commit;