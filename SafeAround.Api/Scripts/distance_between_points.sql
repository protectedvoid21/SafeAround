CREATE OR REPLACE FUNCTION distance_between_points(
    p_lat1 double precision,
    p_lon1 double precision,
    p_lat2 double precision,
    p_lon2 double precision
) RETURNS double precision AS
$$
SELECT earth_distance(
               ll_to_earth(p_lat1, p_lon1),
               ll_to_earth(p_lat2, p_lon2)
       )
$$ LANGUAGE SQL;

COMMENT ON FUNCTION distance_between_points IS 'Calculates the great-circle distance between two geographic points in meters.';
