namespace SafeAround.Api.Persistence.Entities;

public class IncidentComment : BaseTrackingEntity
{
    public required string Content { get; set; }
    
    public required Guid UserId { get; set; }
    public AppUser User { get; set; }
    
    public required int IncidentId { get; set; }
    public Incident Incident { get; set; }
}