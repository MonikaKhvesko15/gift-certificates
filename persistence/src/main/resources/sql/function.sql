 DROP FUNCTION IF EXISTS fn_getMostWidelyUsedTag(var_user_id bigint);
---------------------------------------------------
create function fn_getMostWidelyUsedTag(
  var_user_id bigint
)
  returns table
            (
                id               bigint,
                name             varchar(255),
				sum              double precision
            )
as
$$
begin
            return query
                 select distinct
                     tags.id,
                     tags.name,
                     sum(orders.total_price) OVER (partition by tags.id)
                from tags
				         LEFT JOIN gift_certificates_tags ON tags.id = gift_certificates_tags.tag_id
                         LEFT JOIN gift_certificates_orders ON gift_certificates_tags.gift_certificate_id = gift_certificates_orders.gift_certificate_id
                         LEFT JOIN orders ON orders.id = gift_certificates_orders.order_id
						 where orders.user_id = var_user_id
						 order by sum desc
						 FETCH FIRST ROW ONLY;
end;
$$
language 'plpgsql';
------------------------------------------------------------------------
--for test
  select distinct
  tags.id,
  tags.name,
  sum(orders.total_price) OVER (partition by tags.id)
                from tags
				         LEFT JOIN gift_certificates_tags ON tags.id = gift_certificates_tags.tag_id
                         LEFT JOIN gift_certificates_orders ON gift_certificates_tags.gift_certificate_id = gift_certificates_orders.gift_certificate_id
                         LEFT JOIN orders ON orders.id = gift_certificates_orders.order_id
						 where orders.user_id = 3
						 order by sum desc
						 FETCH FIRST ROW ONLY;
