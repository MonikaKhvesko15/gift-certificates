DROP ALIAS IF EXISTS fn_getCertificatesWithTags;
CREATE ALIAS fn_getCertificatesWithTags AS
$$
String getTableContent(java.sql.Connection con) throws Exception {
    String resultValue;
    java.sql.ResultSet rs = con.createStatement().executeQuery(
    "select distinct
			gift_certificates.id, gift_certificates.name, gift_certificates.description,
			gift_certificates.price, gift_certificates.duration, gift_certificates.create_date,
			gift_certificates.last_update_date, gift_certificates.isDeleted, tags.name
		from

		gift_certificates LEFT JOIN gift_certificates_tags ON gift_certificates.id = gift_certificates_tags.gift_certificate_id
		LEFT JOIN tags ON gift_certificates_tags.tag_id = tags.id

		WHERE gift_certificates.isDeleted = 0;");
       while(rs.next())
       {
        resultValue=rs.getString(1);
       }
    return resultValue;
}
$$;
 --------------------------------------------------
-- SELECT *
-- FROM  fn_getCertificatesWithTags();
---------------------------------------------------