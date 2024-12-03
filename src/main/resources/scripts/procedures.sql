CREATE TABLE IF NOT EXISTS user_logs(user_id INTEGER, activity varchar(30), log_time timestamp);


CREATE OR REPLACE FUNCTION log_user_activity()
    RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO user_logs(user_id, activity, log_time)
    VALUES (NEW.id, 'User Added/Updated', NOW());
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER user_activity_trigger
    AFTER INSERT OR UPDATE
    ON users
    FOR EACH ROW
EXECUTE FUNCTION log_user_activity();



CREATE OR REPLACE FUNCTION count_users()
    RETURNS INTEGER AS $$
DECLARE
    user_count INTEGER;
BEGIN
    SELECT COUNT(*) INTO user_count FROM users;
    RETURN user_count;
END;
$$ LANGUAGE plpgsql;









