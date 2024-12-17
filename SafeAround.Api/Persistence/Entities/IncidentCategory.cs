namespace SafeAround.Api.Persistence.Entities;

public class IncidentCategory : BaseEntity
{
    public required string Name { get; set; }
    public required string Description { get; set; }
    public required string Code { get; set; }
    public ICollection<Incident> Incidents { get; set; } = [];
}