using System.ComponentModel.DataAnnotations;

namespace SafeAround.Api.Persistence.Entities;

public class IncidentImage : BaseTrackingEntity
{
    [MaxLength(200)]
    public required string FileName { get; set; }

    public Incident Incident { get; set; } = null!;
    public required int IncidentId { get; set; }
}