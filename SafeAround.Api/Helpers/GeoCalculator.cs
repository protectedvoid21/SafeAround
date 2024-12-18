using System.Drawing;

namespace SafeAround.Api.Helpers;

public static class GeoCalculator
{
    private const double EarthRadiusKm = 6371.0;
    
    public static double HaversineDistance(Point p1, Point p2)
    {
        return HaversineDistance(p1.Latitude, p1.Longitude, p2.Latitude, p2.Longitude);
    }
    
    public static double HaversineDistance(double lat1, double lon1, double lat2, double lon2)
    {
        double dLat = DegreesToRadians(lat2 - lat1);
        double dLon = DegreesToRadians(lon2 - lon1);

        double a = Math.Sin(dLat / 2) * Math.Sin(dLat / 2) +
                   Math.Cos(DegreesToRadians(lat1)) * Math.Cos(DegreesToRadians(lat2)) *
                   Math.Sin(dLon / 2) * Math.Sin(dLon / 2);

        double c = 2 * Math.Atan2(Math.Sqrt(a), Math.Sqrt(1 - a));
        return EarthRadiusKm * c;
    }

    private static double DegreesToRadians(double degrees)
    {
        return degrees * Math.PI / 180.0;
    }

    public static List<Point> GetPointsWithinRadius(Point center, List<Point> points, double radiusKm)
    {
        return points.Where(p => HaversineDistance(center, p) <= radiusKm).ToList();
    }
}