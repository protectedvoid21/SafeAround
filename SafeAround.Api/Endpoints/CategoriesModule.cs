using Carter;
using Microsoft.EntityFrameworkCore;
using SafeAround.Api.Dto;
using SafeAround.Api.Persistence;

namespace SafeAround.Api.Endpoints;

public class CategoriesModule : ICarterModule
{
    public void AddRoutes(IEndpointRouteBuilder app)
    {
        var group = app.MapGroup("/categories");

        group.MapGet("/", async (SafeAroundDbContext dbContext) =>
        {
            var categories = await dbContext.IncidentCategories
                .Select(c => new GetCategoryResponse
                {
                    Id = c.Id,
                    Name = c.Name,
                    IconCode = c.Code,
                }).ToListAsync();

            return Results.Ok(categories);
        }).Produces<IEnumerable<GetCategoryResponse>>();
    }
}