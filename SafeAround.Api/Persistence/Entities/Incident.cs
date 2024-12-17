namespace SafeAround.Api.Persistence.Entities;

public class Incident : BaseTrackingEntity
{
    public required string Title { get; set; }
    public required string Description { get; set; }
    public required float Latitude { get; set; }
    public required float Longitude { get; set; }
    public required Guid UserId { get; set; }
    public AppUser User { get; set; }
    
    public IncidentCategory Category { get; set; }
    public required int CategoryId { get; set; }
}