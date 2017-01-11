CREATE OR REPLACE FUNCTION func_book_trigger()
RETURNS TRIGGER AS $$
declare
	tableName  character varying(100);
	publishedYear int;
	isTableAlreadyExist boolean;
	queryString text;
BEGIN
		select 'book_'||(NEW.publishyear::text) into tableName;
		select NEW.publishyear into publishedYear;
		IF NOT EXISTS (SELECT relname FROM pg_class WHERE relname=''||tableName||'') 
			then
				queryString:='create table '||tableName;
				queryString:=queryString||'	( ';
				queryString:=queryString||'		CHECK ( publishyear = '||publishedYear||' ) ';
				queryString:=queryString||'	) ';
				queryString:=queryString||'	INHERITS (book);';
				EXECUTE queryString;	
				
		END IF;
		queryString:=' INSERT INTO '||tableName||' select ($1).* ';
		EXECUTE queryString using NEW;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;