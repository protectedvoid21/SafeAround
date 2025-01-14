namespace SafeAround.Api.Dto;

public record UploadIncidentImageRequest
{
    public required int IncidentId { get; set; }
    public required string FileName { get; set; }
    public required Stream ImageStream { get; set; }
}