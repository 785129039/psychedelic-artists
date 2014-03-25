CREATE OR REPLACE VIEW overview(
id,

TYPE ,
name,
description
) AS 
SELECT id,  'PRESET', name, description
FROM preset
UNION 
SELECT id,  'SAMPLE', name, description
FROM sample