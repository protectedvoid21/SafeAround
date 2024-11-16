namespace SafeAround.Api.Persistence.Models;

public class Incident : BaseTrackingEntity
{
    public required string Title { get; set; }
    public required string Description { get; set; }
    public required float Latitude { get; set; }
    public required float Longitude { get; set; }
    public required DateTime OccurrenceDate { get; set; }
    public required Guid UserId { get; set; }
    public AppUser User { get; set; }
}