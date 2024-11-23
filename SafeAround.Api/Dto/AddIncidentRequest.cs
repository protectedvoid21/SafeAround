namespace SafeAround.Api.Dto;

public class AddIncidentRequest
{
    public required string Title { get; set; }
    public required string Description { get; set; }
    public required float Latitude { get; set; }
    public required float Longitude { get; set; }
    public required DateTime Date { get; set; }

    public required int CategoryId { get; set; }
}