namespace SafeAround.Api.Persistence.Models;

public class IncidentCategory : BaseEntity
{
    public required string Name { get; set; }
    public required string Description { get; set; }
    public required string Code { get; set; }
    public ICollection<Incident> Incidents { get; set; } = [];
}