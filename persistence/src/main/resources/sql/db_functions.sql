-------------------------------------------------
create function fn_getCertificateByPartOfContext(
 context varchar(255)
)
	returns table (
		id               bigint,
    name             varchar(255),
    description      varchar(255),
    price            double precision,
    duration         int,
    create_date      timestamptz      ,
    last_update_date timestamptz      ,
    isDeleted        int
	)
	language plpgsql
as $$
begin
	return query
		select
			*
		from
			gift_certificates
		where
		gift_certificates.name ILIKE context OR gift_certificates.description ILIKE context;
end;$$
---------------------------------------------------
DROP FUNCTION fn_getCertificateByPartOfContext
 --------------------------------------------------
SELECT * FROM fn_getCertificateByPartOfContext ('%test%');
---------------------------------------------------



---------------------------------------------------
create function fn_getCertificateByTagName(
 tagName varchar(255)
)
	returns table (
	id               bigint,
    name             varchar(255),
    description      varchar(255),
    price            double precision,
    duration         int,
    create_date      timestamptz      ,
    last_update_date timestamptz      ,
    isDeleted        int
	)
	language plpgsql
as $$
begin
	return query
		select
			gift_certificates.id, gift_certificates.name, gift_certificates.description,
			gift_certificates.price, gift_certificates.duration, gift_certificates.create_date,
			gift_certificates.last_update_date, gift_certificates.isDeleted
		from
			gift_certificates JOIN gift_certificates_tags ON gift_certificates_tags.gift_certificate_id = gift_certificates.id
                JOIN tags ON gift_certificates_tags.tag_id = tags.id
		where
		tags.name =tagName;
end;$$
----------------------------------------------------
 DROP FUNCTION  fn_getCertificateByTagName;
 ---------------------------------------------------
 SELECT * FROM fn_getCertificateByTagName ('test')
 ---------------------------------------------------



 ---------------------------------------------------
 create function fn_sortCertificates(
 sortBy varchar(255),
 order_sort varchar(255)
)
	returns table (
	id               bigint,
    name             varchar(255),
    description      varchar(255),
    price            double precision,
    duration         int,
    create_date      timestamptz      ,
    last_update_date timestamptz      ,
    isDeleted        int
	)
	language plpgsql
as $$
begin

CASE

WHEN (UPPER(sortBy)='NAME' and UPPER(order_sort)='ASC') THEN
return query
select * from gift_certificates
ORDER BY gift_certificates.name ASC;

WHEN  (UPPER(sortBy)='NAME' and UPPER(order_sort)='DESC') THEN
return query
select * from gift_certificates
ORDER BY gift_certificates.name DESC;

WHEN (UPPER(sortBy)='DATE' and UPPER(order_sort)='ASC') THEN
return query
select * from gift_certificates
ORDER BY gift_certificates.create_date ASC;

WHEN (UPPER(sortBy)='DATE' and UPPER(order_sort)='DESC') THEN
return query
select * from gift_certificates
ORDER BY gift_certificates.create_date DESC;

END CASE;

end;$$.
-----------------------------------------------------------------
 DROP FUNCTION  fn_sortCertificates;
 ----------------------------------------------------------------
 SELECT * FROM fn_sortCertificates ('name','asc')