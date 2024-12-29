namespace SafeAround.Api.Dto;

public record AddCommentRequest(
    int IncidentId,
    string Content
);