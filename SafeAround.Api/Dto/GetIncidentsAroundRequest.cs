namespace SafeAround.Api.Dto;

public class GetIncidentsAroundRequest
{
    public float Latitude { get; set; }
    public float Longitude { get; set; }
    public float Radius { get; set; }
    public RadiusUnit RadiusUnit { get; set; }
}