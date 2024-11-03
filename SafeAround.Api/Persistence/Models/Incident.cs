namespace SafeAround.Api.Persistence.Models;

public class Incident : BaseTrackingEntity
{
    public required string Title { get; set; }
    public required string Description { get; set; }
}