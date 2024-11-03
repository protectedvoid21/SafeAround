namespace SafeAround.Api.Dto;

public class AddIncidentRequest
{
    public string Title { get; set; }
    public string Description { get; set; }
    public float Latitude { get; set; }
    public float Longitude { get; set; }
    public DateTime Date { get; set; }
}