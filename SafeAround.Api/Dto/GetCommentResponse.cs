namespace SafeAround.Api.Dto;

public class GetCommentResponse
{
    public required int Id { get; set; }
    public required string Content { get; set; }
    public required DateTime CreatedOn { get; set; }
    public required Guid UserId { get; set; }
    public required string UserName { get; set; }
}