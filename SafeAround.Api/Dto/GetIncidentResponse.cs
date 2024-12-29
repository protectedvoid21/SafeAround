using System.Text.Json.Serialization;

namespace SafeAround.Api.Dto;

public record GetIncidentResponse
{
    public required int Id { get; set; }
    public required string Title { get; set; }
    public required string Description { get; set; }
    public required float Latitude { get; set; }
    public required float Longitude { get; set; }
    public required DateTime OccurrenceDate { get; set; }
    
    public required int CategoryId { get; set; }
    public required string CategoryName { get; set; }
    public required string CategoryCode { get; set; }
    
    public required Guid UserId { get; set; }
    public required float? DistanceInKm { get; set; }

    [JsonIgnore(Condition = JsonIgnoreCondition.WhenWritingNull)]
    public required GetCommentResponse[]? Comments { get; set; }
}