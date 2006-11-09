-- Perform a check after the xref split script has been run, it counts
-- the sum of all xrefs tables. That count should be matching the count of
-- rows in IA_XREF. The tables sub tables are :
--    * IA_biosource_xref
--    * IA_controlledvocab_xref
--    * IA_feature_xref
--    * IA_interactor_xref
--    * IA_experiment_xref
--    * IA_publication_xref
--    * IA_component_xref
DECLARE

  v_count_xref INTEGER;
  v_diff       INTEGER;
  v_count      INTEGER;
  v_total      INTEGER;
  
BEGIN

  dbms_output.enable(1000000000000);

  v_total := 0;

  SELECT count(1) into v_count_xref
  FROM IA_XREF;  
  dbms_output.put_line( '-----------------------------------------------' );
  dbms_output.put_line( 'IA_XREF: ' || v_count_xref || ' row(s)' );
  dbms_output.put_line( '-----------------------------------------------' );
  
  SELECT count(1) into v_count
  FROM IA_biosource_xref;  
  dbms_output.put_line( 'IA_biosource_xref:       ' || v_count || ' row(s)' );
  v_total := v_total + v_count;

  SELECT count(1) into v_count
  FROM IA_controlledvocab_xref;  
  dbms_output.put_line( 'IA_controlledvocab_xref: ' || v_count || ' row(s)' );
  v_total := v_total + v_count;
  
  SELECT count(1) into v_count
  FROM IA_feature_xref;  
  dbms_output.put_line( 'IA_feature_xref:         ' || v_count || ' row(s)' );
  v_total := v_total + v_count;

  SELECT count(1) into v_count
  FROM IA_interactor_xref;  
  dbms_output.put_line( 'IA_interactor_xref:      ' || v_count || ' row(s)' );
  v_total := v_total + v_count;
  
  SELECT count(1) into v_count
  FROM IA_experiment_xref;  
  dbms_output.put_line( 'IA_experiment_xref:      ' || v_count || ' row(s)' );
  v_total := v_total + v_count;
  
  SELECT count(1) into v_count
  FROM IA_publication_xref;  
  dbms_output.put_line( 'IA_publication_xref:     ' || v_count || ' row(s)' );
  v_total := v_total + v_count;
  
  SELECT count(1) into v_count
  FROM IA_component_xref;  
  dbms_output.put_line( 'IA_component_xref:       ' || v_count || ' row(s)' );
  v_total := v_total + v_count;

  dbms_output.put_line( '===============================================' );
  dbms_output.put_line( 'Sum of all sub-tables: ' || v_total || ' row(s)' );
  
  v_diff := v_count_xref - v_total;
  if ( v_diff = 0 ) then
     dbms_output.put_line( 'All aliases were transfered successfully.' );
  elsif ( v_diff > 0 ) then
     dbms_output.put_line( v_diff || ' row(s) were not transfered to sub-tables.'  );
  else
     -- v_diff < 0
     dbms_output.put_line( 'Sub tables contain more rows that the original table: ' || ( v_diff * -1)  );
  end if;
  
END;
/

