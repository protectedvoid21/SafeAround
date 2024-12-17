namespace SafeAround.Api.Dto;

public class GetCategoryResponse
{
    public required int Id { get; set; }
    public required string Name { get; set; }
    public required string IconCode { get; set; }
}