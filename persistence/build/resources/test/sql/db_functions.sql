create ALIAS fn_getCertificatesWithTags AS


$$
@CODE
ResultSet getCertificatesWithTags(String tag_name, String context) throws SQLException{

}
begin
    return query
        select distinct gift_certificates.id,
                        gift_certificates.name,
                        gift_certificates.description,
                        gift_certificates.price,
                        gift_certificates.duration,
                        gift_certificates.create_date,
                        gift_certificates.last_update_date,
                        gift_certificates.isdeleted,
                        tags.name
        from gift_certificates
                 LEFT JOIN gift_certificates_tags ON gift_certificates.id = gift_certificates_tags.gift_certificate_id
                 LEFT JOIN tags ON gift_certificates_tags.tag_id = tags.id

        WHERE gift_certificates.isdeleted = 0
          AND (
                (
                        (gift_certificates.name ILIKE concat('%', context, '%')) OR
                        (gift_certificates.description ILIKE concat('%', context, '%'))
                    ) AND
                (tags.name ILIKE concat('%', tag_name, '%'))
            );
end;
$$;